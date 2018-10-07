package lab4.dining_philosophers

import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class DiningPhilosophersTest {

    @Test fun test3Philosophers2Dishes() {
        val size = 3
        val dishes = 2

        testPhilosophersDishesCombination(size, dishes)
    }

    @Test fun test2Philosophers20Dishes() {
        val size = 2
        val dishes = 20

        testPhilosophersDishesCombination(size, dishes)
    }

    @Test fun test100Philosophers100Dishes() {
        val size = 100
        val dishes = 100

       testPhilosophersDishesCombination(size, dishes)
    }

    fun testPhilosophersDishesCombination(size: Int, dishes: Int) {

        val table = Table(size)
        val startLatch = CountDownLatch(1)
        val stopLatch = CountDownLatch(size)
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

        repeat(size - 1) {
            executor.submit{
                TestPhilosopher("phil-$it",
                        it,
                        dishes,
                        table,
                        beforeAction = { startLatch.await() },
                        afterAction = { stopLatch.countDown() })
                        .run()
            }
        }

        executor.submit {
            TestPhilosopher("phil-${size-1}",
                    size-1,
                    dishes,
                    table,
                    table::reversedOrder,
                    beforeAction = { startLatch.await() },
                    afterAction = { stopLatch.countDown() })
                    .run()
        }

        startLatch.countDown()
        stopLatch.await()
    }

}