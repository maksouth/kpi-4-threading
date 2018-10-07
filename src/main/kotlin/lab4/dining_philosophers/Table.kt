package lab4.dining_philosophers

import java.util.concurrent.Semaphore

class Table(private val forksNumber: Int) {
    val forks: List<Semaphore> by lazyForksInit(forksNumber)


    fun directOrder(chairNumber: Int) =
            chairNumber to ( chairNumber + 1) % forksNumber

    fun reversedOrder(chairNumber: Int) =
            (chairNumber + 1) % forksNumber to chairNumber
}


private fun lazyForksInit(forksNumber: Int) = lazy {
    with(mutableListOf<Semaphore>()) {

        repeat(forksNumber) {
            add(Semaphore(1))
        }

        this
    }
}