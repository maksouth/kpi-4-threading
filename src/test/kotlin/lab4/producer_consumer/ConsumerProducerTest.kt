package lab4.producer_consumer

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

class ConsumerProducerTest {

    @Test fun testOneConsumerOneProducer5Times5QueueSize() {
        val queue = BoundedArrayBlockingQueue<Int>(size = 5)
        val producer = Producer<Int>("prod-1", queue, { -> 1}, 5)
        val consumer = Consumer<Int>("cons-1", queue, 5)

        val producerThread = Thread(producer)
        val consumerThread = Thread(consumer)

        producerThread.start()
        consumerThread.start()

        consumerThread.join()
        producerThread.join()
    }

    @Test fun testOneConsumerOneProducer10000Times5QueueSize() {
        val produceCounter = AtomicInteger()
        val consumeCounter = AtomicInteger()
        val executor = Executors.newFixedThreadPool(2)

        val queue = BoundedArrayBlockingQueue<Int>(size = 5)
        val producer = Producer<Int>("prod-1", queue, IntSequenceFactory(), 10000, doOnProduce = { e -> executor.submit { produceCounter.addAndGet(e)} })
        val consumer = Consumer<Int>("cons-1", queue, 10000, doOnConsume = { e -> executor.submit { consumeCounter.addAndGet(e)} })

        val producerThread = Thread(producer)
        val consumerThread = Thread(consumer)

        producerThread.start()
        consumerThread.start()

        consumerThread.join()
        producerThread.join()

        println("Produced total: ${produceCounter.get()}")
        println("Consumed total: ${consumeCounter.get()}")
        assertEquals(produceCounter.get(), consumeCounter.get())
    }

    @Test fun test10Consumers10Producers100000Times10QueueSize() {
        val queueSize = 10
        val repeatTimes = 100000
        val pairsNumber = 10

        val produceCounter = AtomicLong()
        val consumeCounter = AtomicLong()
        val executor = Executors.newFixedThreadPool(4)

        val queue = BoundedArrayBlockingQueue<Int>(size = queueSize)

        var producerThread: Thread
        var consumerThread: Thread
        var producer: Producer<Int>
        var consumer: Consumer<Int>

        val startLatch = CountDownLatch(1)
        val stopLatch = CountDownLatch(2 * pairsNumber)
        fun doBeforeActionFactory(): () -> Unit =
                { startLatch.await() }

        fun doOnConsumeFactory(): (Int) -> Unit {
            return {
                e
                ->
                executor.submit { consumeCounter.addAndGet(e.toLong()) }
            }
        }

        fun doOnProduceFactory(): (Int) -> Unit {
            return {
                e
                ->
                executor.submit { produceCounter.addAndGet(e.toLong()) }
            }
        }

        fun doOnStopFactory(): () -> Unit =
                { stopLatch.countDown() }

        repeat(pairsNumber) {
            producer = Producer<Int>("prod-$it",
                    queue,
                    IntSequenceFactory(),
                    repeatTimes,
                    doOnProduce = doOnProduceFactory(),
                    doBefore = doBeforeActionFactory(),
                    doOnStop = doOnStopFactory())

            consumer = Consumer<Int>("cons-$it",
                    queue,
                    repeatTimes,
                    doOnConsume = doOnConsumeFactory(),
                    doBefore = doBeforeActionFactory(),
                    doOnStop = doOnStopFactory())
            producerThread = Thread(producer)
            consumerThread = Thread(consumer)

            producerThread.start()
            consumerThread.start()
        }

        startLatch.countDown()

        stopLatch.await()

        println("Produced total: ${produceCounter.get()}")
        println("Consumed total: ${consumeCounter.get()}")
        assertEquals(produceCounter.get(), consumeCounter.get())
    }

}

class IntSequenceFactory : () -> Int {
    private var counter = 0

    override fun invoke() = counter++
}