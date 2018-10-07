package lab4.dining_philosophers

class TestPhilosopher(name: String,
                      chairNumber: Int,
                      dishesNumber: Int,
                      table: Table,
                      forksOrder: (Int) -> Pair<Int, Int> = table::directOrder,
                      private val beforeAction: () -> Unit = {},
                      private val afterAction: () -> Unit = {})
    : Philosopher(name, chairNumber, dishesNumber, table, forksOrder) {

    override fun run() {
        beforeAction()
        super.run()
        afterAction()
    }
}
