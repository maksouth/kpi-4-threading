package lab4.producer_consumer

import java.util.concurrent.locks.ReentrantLock

class BoundedArrayBlockingQueue<T>
    : BlockingQueue<T> {

    private val lock = ReentrantLock()
    private val readCondition = lock.newCondition()
    private val writeCondition = lock.newCondition()

    private val list: MutableList<T>
    private val size: Int

    constructor(size: Int = 32) {
        this.size = size
        list = ArrayList(size)
    }

    override fun put(element: T) {
        lock.lock()
        try {
            while (list.size >= size) {
                writeCondition.await()
            }

            list.add(element)
            readCondition.signalAll()
        } finally {
            lock.unlock()
        }
    }

    override fun poll(): T {
        lock.lock()
        try {
            while (list.size == 0) {
                readCondition.await()
            }

            val element = list.removeAt(0)
            writeCondition.signalAll()

            return element
        } finally {
            lock.unlock()
        }
    }

}