package lab5

import org.junit.Assert
import org.junit.Test
import java.util.concurrent.CompletableFuture

class LauncherKtTest {

    @Test
    fun testListGeneration() {
        val listFuture = generateListAsync(5) {
            it + 1
        }

        val expected = listOf(1, 2, 3, 4, 5)

        Assert.assertEquals(expected, listFuture.get())
    }

    @Test
    fun testRandomListGeneration() {
        val listFuture = generateListAsync(100, randomIntProducer)

        Assert.assertEquals(100, listFuture.get().size)
    }

    @Test
    fun testFilterListFuture() {
        val list = listOf(1, 2, 3, 4, 5, 6)
        val listFuture = CompletableFuture.supplyAsync { list }

        val filteredFuture = listFuture.filter { it % 2 != 0 }

        Assert.assertEquals(listOf(1, 3, 5), filteredFuture.get())
    }

    @Test
    fun testSortedFuture() {
        val list = List<Int>(100) { it }
        val shuffledList = list.shuffled()

        val listFuture = CompletableFuture.supplyAsync { shuffledList }.sorted()

        Assert.assertEquals(list, listFuture.get())
    }

    @Test
    fun testMergeSortedLists() {
        val odds = List(100) { it * 2 + 1 }
        val evens = List(100) { it * 2 }

        val expected = List(200) {it}

        val merged = mergeSorted(odds, evens)

        Assert.assertEquals(expected, merged)
    }

    @Test
    fun testMergeSortedListsFuture() {
        val odds = List(100) { it * 2 + 1 }
        val evens = List(100) { it * 2 }

        val oddsFuture = CompletableFuture.supplyAsync { odds }
        val evensFuture = CompletableFuture.supplyAsync { evens }

        val expected = List(200) {it}

        val mergedFuture = mergeSortedAsync(oddsFuture, evensFuture)
        Assert.assertEquals(expected, mergedFuture.get())
    }

}