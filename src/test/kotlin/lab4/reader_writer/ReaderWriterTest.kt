package lab4.reader_writer

import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ReaderWriterTest {

    @Test fun testOneReaderOneWriter() {
        testCombination(1, 1)
    }

    @Test fun test10Readers10Writers() =
        testCombination(10, 10)

    fun testCombination(readers: Int,
                        writers: Int,
                        readersRepeat: Int = 10,
                        writersRepeat: Int = 10) {
        val startLatch = CountDownLatch(1)

        val mutableList = mutableListOf<Int>()
        val readWriteList = ReadWriteList(mutableList)

        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        var reader: Reader<Int>
        var writer: Writer<Int>

        fun submitReader(number: Int) {
            reader = TestReader("reader-$number", readWriteList, readersRepeat,
                    doOnRead = { Thread.sleep(100) },
                    beforeAction = { startLatch.await(); Thread.sleep(100)})
            executor.submit(reader)
        }

        fun submitWriter(number: Int) {
            writer = TestWriter("writer-$number", readWriteList, IntSequenceFactory(), writersRepeat,
                    doOnWrite = { Thread.sleep(100) },
                    beforeAction = { startLatch.await() })
            executor.submit(writer)
        }

        if (readers < writers) {
            repeat(readers) {
                submitWriter(it)
                submitReader(it)
            }

            (readers..writers).forEach {
                submitWriter(it)
            }
        } else {
            repeat(writers) {
                submitWriter(it)
                submitReader(it)
            }

            (writers..readers).forEach {
                submitReader(it)
            }
        }

        startLatch.countDown()

        executor.shutdown()
        executor.awaitTermination(60, TimeUnit.SECONDS)

    }

}

class IntSequenceFactory : () -> Int {
    private var counter = 0

    override fun invoke() = counter++
}