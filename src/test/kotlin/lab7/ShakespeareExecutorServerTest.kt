package lab7

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.concurrent.Executors

const val port = 2589

class ShakespeareExecutorServerTest {

    lateinit var server: ShakespeareExecutorServer

    @Test
    fun `server should return some fake poem on new client connection`() {
        server = ShakespeareExecutorServer(DummyPoemRepository(), Executors.newFixedThreadPool(4))
        server.startAsync(port)

        val client = PoemSocketClient()

        client.startConnection("localhost", port)
        Assert.assertEquals(POEM, client.poem)

        server.stop()
    }

    @Test
    fun `file poem repository should read poems from file`() {
        val poemsFile = getPoemsFile()
        val poemRepository = FilePoemRepository(poemsFile)

        repeat(5) {
            Assert.assertTrue(poemRepository.getRandom().isNotEmpty())
            Assert.assertTrue(poemRepository.getRandom().contains("sonnet"))
        }
    }

    @Test
    fun `server should return some poem on multiple client connections`() {
        server = ShakespeareExecutorServer(FilePoemRepository(getPoemsFile()), Executors.newFixedThreadPool(4))
        server.startAsync(port)

        repeat(15) {
            val client = PoemSocketClient()
            client.startConnection("localhost", port)
            Assert.assertTrue(client.poem.contains("sonnet"))
        }

        server.stop()
    }

    fun getPoemsFile() =
            File(javaClass.classLoader.getResource("poems.txt").file)
}



val POEM = "Some poem"
class DummyPoemRepository: PoemRepository {
    override fun getRandom(): String = POEM

}