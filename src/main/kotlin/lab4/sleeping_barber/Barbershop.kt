package lab4.sleeping_barber

import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class Barbershop(private val sitsNumber: Int,
                 private val sits: Queue<Client>) {

    private val lock = ReentrantLock()
    private val barberCondition = lock.newCondition()

    fun nextClient(): Client? {
        lock.lock()
        try {
            if (sits.size == 0) {
                println("[BS] No clients, barber sleeps..")
                barberCondition.await(10, TimeUnit.SECONDS)
            }

            val client: Client? = sits.poll()

            if (client != null)
                println("[BS] Hi $client, barber is waiting for you")

            return client
        } finally {
            lock.unlock()
        }
    }

    fun waitReception(client: Client): Boolean {
        lock.lock()
        try {
            if(sits.size >= sitsNumber) {
                println("[BS] sorry $client we do not have free sits")
                return false
            }

            println("[BS] $client please wait before barber can serve you..")
            sits.offer(client)

            barberCondition.signal()
            return true
        } finally {
            lock.unlock()
        }
    }

}