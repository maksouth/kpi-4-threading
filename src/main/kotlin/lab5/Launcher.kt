package lab5

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ThreadLocalRandom

fun main(args: Array<String>) {
    val first = generateListAsync(100, randomIntProducer)
            .filter { multiplied3Predicate(it) }
            .sorted()

    val second = generateListAsync(200, randomIntProducer)
            .thenApplyAsync { it.max()!! to it }
            .thenApplyAsync { (maxElement, list) ->
                val predicate = secondPredicateProducer(maxElement)
                list.filter(predicate)
            }
            .sorted()

    val result = mergeSortedAsync(first, second).get()
    println("Sorted merged: $result")
}

val multiplied3Predicate = { element: Int -> element % 3 == 0 }
fun secondPredicateProducer(max: Int): (Int) -> Boolean  =
        { it > 0.7 * max }


val randomIntProducer: (Int) -> Int = { _ -> ThreadLocalRandom.current().nextInt(-100, 100) }

fun <T> generateListAsync(size: Int, itemFunction: (Int) -> T): CompletableFuture<List<T>> =
        CompletableFuture.supplyAsync{ List(size, itemFunction) }

fun <T> CompletableFuture<List<T>>.filter(predicate: (T) -> Boolean): CompletableFuture<List<T>> = thenApplyAsync {
    it.filter(predicate)
}

fun <T: Comparable<T>> CompletableFuture<List<T>>.sorted(): CompletableFuture<List<T>> = thenApplyAsync {
    it.sortedBy { it }
}

fun <T: Comparable<T>> mergeSortedAsync(
        first: CompletableFuture<List<T>>,
        second: CompletableFuture<List<T>>
): CompletableFuture<List<T>> =
        first.thenCombineAsync(second) { firstArray, secondArray ->
            mergeSorted(firstArray, secondArray)
        }

fun <T: Comparable<T>> mergeSorted(first: List<T>, second: List<T>): List<T> {
    var i = 0
    var j = 0
    val result = ArrayList<T>(first.size + second.size)

    while (i < first.size && j < second.size) {
        val nextElement =
                if (first[i] < second[j]) first[i++]
                else second[j++]

        result.add(nextElement)
    }

    while (i < first.size)
        result.add(first[i++])

    while (j < second.size)
        result.add(second[j++])

    return result
}