package lab4.reader_writer

import java.util.concurrent.locks.ReentrantReadWriteLock

class ReadWriteList<T> (private val mutableList: MutableList<T>) {
    private val lock = ReentrantReadWriteLock()
    private val readerLock = lock.readLock()
    private val writerLock = lock.writeLock()

    fun read(index: Int): T? {
        val element: T

        if (index >= mutableList.size) {
            return null
        }

        readerLock.lock()

        try {
            element = mutableList[index]
        } finally {
            readerLock.unlock()
        }

        return element
    }

    fun write(value: T) {
        writerLock.lock()
        try {
            mutableList.add(value)
        } finally {
            writerLock.unlock()
        }
    }
}