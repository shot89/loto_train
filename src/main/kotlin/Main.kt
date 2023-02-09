import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

val parentJob = Job()
val scope = CoroutineScope(parentJob + Dispatchers.Default)

suspend fun main() {

    val a = TicketImpl()
    var players = mutableListOf<Player>()
    while (true){
        println("Enter name of player")
        var name = readlnOrNull().toString()
        if (name.equals("q")) break
        println("Enter ticket count")
        var input = readlnOrNull()
        var ticketCount:Int
        try {
            ticketCount = Integer.parseInt(input)
            players.add(PlayerImpl(name,ticketCount))
        } catch (e:NumberFormatException){
            println(e.message)
        }
    }

    scope.launch {
        var ticket = a.ticket

        Generator.flow.collect {
            println("Выпал бачонок - $it")
            for (stringTicket in ticket) {
                if (stringTicket.contains(it)) {
                    stringTicket.remove(it)
                    if (stringTicket.isEmpty()) {
                        cancel()
                        println("билет выиграл")
                    }
                }
            }
            println(ticket)
            println()
        }
    }
    parentJob.complete()
    parentJob.join()
}


object Generator {
    var flow = flow<Int> {
        var numberBagImpl = NumberBagImpl()
        var numbers = numberBagImpl.fillTheBag()
        while (currentCoroutineContext().isActive) {
            var number = numbers.first()
            emit(number)
            numbers.remove(number)
        }
    }
}

