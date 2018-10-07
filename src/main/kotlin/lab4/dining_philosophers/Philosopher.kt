package lab4.dining_philosophers

open class Philosopher(private val name: String,
                  private val chairNumber: Int,
                  private val dishesNumber: Int,
                  private val table: Table,
                  private val forksOrder:
                  (Int) -> Pair<Int, Int> = table::directOrder)
    : Runnable {

    override fun run() {
        repeat(dishesNumber) { dish ->
            val (left, right) = forksOrder(chairNumber)

            table.forks[left].acquire()
            println("[PHIL] $name got left fork $left")

            table.forks[right].acquire()
            println("[PHIL] $name got right fork $right")

            println("[PHIL] $name enjoying dish $dish")
            Thread.sleep(100)
            table.forks[left].release()
            table.forks[right].release()
            println("[PHIL] $name putted down forks")
        }
    }
}