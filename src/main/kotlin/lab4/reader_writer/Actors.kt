package lab4.reader_writer

open class Writer<T>(private val name: String,
                        private val list: ReadWriteList<T>,
                        /* should not be leaked and shared */
                        private val factory: () -> T,
                        private val repeat: Int,
                        private val doOnWrite: (T) -> Unit = {})
    : () -> Unit {

    override fun invoke() {
        var element: T

        repeat(repeat) {
            element = factory()
            list.write(element)

            println("[W] $name wrote $element")
            doOnWrite(element)
        }
    }

}

open class Reader<T>(private val name: String,
                        private val list: ReadWriteList<T>,
                        private val repeat: Int,
                        private val doOnRead: (T) -> Unit = {})
    : () -> Unit {

    override fun invoke() {
        repeat(repeat) {
            val element = list.read(it)

            if (element == null) {
                println("[R] $name cannot read element on position $it")
                return
            }

            println("[R] $name read $element")
            doOnRead(element)
        }
    }

}

class TestWriter<T>(name: String,
                    list: ReadWriteList<T>,
                    /* should not be leaked and shared */
                    factory: () -> T,
                    repeat: Int,
                    doOnWrite: (T) -> Unit = {},
                    private val beforeAction: () -> Unit = {},
                    private val afterAction: () -> Unit = {})
    : Writer<T>(name, list, factory, repeat, doOnWrite) {

    override fun invoke() {
        beforeAction()
        super.invoke()
        afterAction()
    }

}

class TestReader<T>(name: String,
                    list: ReadWriteList<T>,
                    repeat: Int,
                    doOnRead: (T) -> Unit = {},
                    private val beforeAction: () -> Unit = {},
                    private val afterAction: () -> Unit = {})
    : Reader<T>(name, list, repeat, doOnRead) {

    override fun invoke() {
        beforeAction()
        super.invoke()
        afterAction()
    }
}