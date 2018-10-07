package lab4.sleeping_barber

class Client(private val name: String,
             private val barbershop: Barbershop,
             private val preference: String)
    : Runnable {

    override fun run() {
        if (barbershop.waitReception(this)) {
            println("[C] $name: Ok, I can wait a while")
        } else {
            println("[C] $name: Phew! I will find another barbershop")
        }

    }

    fun preference(): String {
        println("[C] $name: I want you to cut me $preference")
        return preference
    }

    fun afterHaircut() =
            println("[C] $name: Looks very nice, thanks!")

    override fun toString() = name
}