package lab4.sleeping_barber

class Barber (private val name: String,
              private val barbershop: Barbershop,
              private val haircutTime: Int)
    : Runnable {

    override fun run() {
        while (true) {
            val client = barbershop.nextClient()

            if (client == null) {
                println("[B] $name: Finally I can go home")
                return
            }

            println("[B] $name: Hi $client, what is you haircut preference?")
            val preference = client.preference()

            println("[B] Cutting $client like $preference")
            Thread.sleep(haircutTime.toLong())

            println("[B] $name: Done!")
            client.afterHaircut()
            println("[B] $name: Thanks! C u later!")
        }
    }

}