package lab7

import java.io.File
import java.util.concurrent.ThreadLocalRandom

class FilePoemRepository(file: File): PoemRepository {

    private val lines = file.readLines()

    override fun getRandom(): String =
        lines[ThreadLocalRandom.current().nextInt(0, lines.size)]
}