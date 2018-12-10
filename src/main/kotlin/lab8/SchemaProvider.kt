package lab8

import java.io.File
import java.io.InputStream

object SchemaProvider: (String) -> InputStream {
    override fun invoke(fileName: String): InputStream =
            File(javaClass.classLoader.getResource(fileName).file).inputStream()
}
