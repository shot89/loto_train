import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.takeWhile

val parentJob = Job()
val scope = CoroutineScope(parentJob + Dispatchers.Default)

suspend fun main() {

    var players = mutableListOf<Player>()
    println("Let's play Russian LOTO. For quit please enter 'Q'")
    while (true) {
        println("Enter name of player")
        var name = readlnOrNull().toString()
        if (name.equals("q") || name.equals("Q")) break
        println("Enter ticket count")
        var input = readlnOrNull()
        var ticketCount: Int
        try {
            ticketCount = Integer.parseInt(input)
            players.add(PlayerImpl(name, ticketCount))
        } catch (e: NumberFormatException) {
            println(e.message)
        }
    }

    if (!players.isEmpty()) {
        scope.launch {
            var ended: Boolean = false
            var winnerName: String = ""
            Generator().flow.takeWhile { !ended }.collect {
                println("Barrel is - $it")
                for (player in players) {
                    for (ticket in player.getTicket()) {
                        for (stringTicket in ticket.getTicket()) {
                            if (stringTicket.contains(it)) {
                                stringTicket.remove(it)
                                if (stringTicket.isEmpty()) {
                                    winnerName = player.getName()
                                    ended = true
                                }
                            }
                        }
                        println(player.getName())
                        println(ticket.getTicket())
                        println()
                    }

                }
            }
            println("Tiket $winnerName win, congratulations!")
        }
    }

    parentJob.complete()
    parentJob.join()

}


class Generator {
    private val bag = (1..90).shuffled()
    val flow = flow {
        for (number in bag) {
            emit(number)
            delay(10)
        }
    }
}

