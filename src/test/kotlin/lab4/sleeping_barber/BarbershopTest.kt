package lab4.sleeping_barber

import com.sun.istack.internal.ByteArrayDataSource
import org.junit.Test
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BarbershopTest {

    @Test fun testBarberSleepWhenNoClient() {
        val barbershop = Barbershop(3, LinkedList())
        val barber = Barber("Mario", barbershop, 10)

        val executor = Executors.newSingleThreadExecutor()
        executor.submit(barber)
    }

    @Test fun testNoSitsClientGoToAnotherBarbershop() {
        val barbershop = Barbershop(3, LinkedList())
        val clientAnton = Client("Anton", barbershop, "african")
        val clientMark = Client("Mark", barbershop, "african")
        val clientOstap = Client("Ostap", barbershop, "african")
        val clientDima = Client("Dima", barbershop, "african")

        val executor = Executors.newCachedThreadPool()
        executor.submit(clientAnton)
        executor.submit(clientDima)
        executor.submit(clientOstap)
        executor.submit(clientMark)
    }

    @Test fun test10Clients2Sits() {
        val clients = 10
        val sits = 2

        val barbershop = Barbershop(sits, LinkedList())
        val barber = Barber("Mario", barbershop, 10)
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

        executor.submit(barber)
        repeat(clients) {
            executor.submit{
                Thread.sleep(it.toLong() * 4)
                Client("name-$it", barbershop, "preference-$it").run()
            }
        }

        executor.shutdown()
        executor.awaitTermination(1, TimeUnit.MINUTES)
    }

}