package lab4.producer_consumer

class Producer<T>(private val name: String,
                  private val queue: BlockingQueue<T>,
                  /* should not be leaked and shared */
                  private val factory: () -> T,
                  private val repeat: Int,
                  private val doBefore: () -> Unit = {},
                  private val doOnProduce: (T) -> Unit = {},
                  private val doOnStop: () -> Unit = {})
    : Runnable {

    override fun run() {
        doBefore()

        repeat(repeat) {
            val element = factory()
            queue.put(element)

            println("[P] $name $it produced $element")
            doOnProduce(element)
        }

        doOnStop()
    }
}

class Consumer<T>(private val name: String,
                  private val queue: BlockingQueue<T>,
                  private val repeat: Int,
                  private val doBefore: () -> Unit = {},
                  private val doOnConsume: (T) -> Unit = {},
                  private val doOnStop: () -> Unit = {})
    : Runnable {

    override fun run() {
        doBefore()

        repeat(repeat) {
            val element = queue.poll()

            println("[C] $name $it consumed $element")
            doOnConsume(element)
        }

        doOnStop()
    }

}