package lab4.producer_consumer

interface BlockingQueue<T> {
    fun put(element: T)
    fun poll(): T
}