import kotlin.random.Random

class TicketImpl:Ticket{

    var ticket = fillTicket()

    override fun fillTicket(): MutableList<MutableList<Int>> {
        val listUniqueValues = mutableListOf<Int>()
        while (listUniqueValues.size < 15) {
            var tmp = Random.nextInt(1, 91)
            if (!listUniqueValues.contains(tmp)) {
                listUniqueValues.add(tmp)
            }
        }
        var ticket = mutableListOf<MutableList<Int>>()
        var stringTicket = mutableListOf<Int>()
        while (!listUniqueValues.isEmpty()) {
            stringTicket.add(listUniqueValues.first())
            listUniqueValues.removeAt(0)
            if (stringTicket.size == 5) {
                ticket.add(stringTicket)
                stringTicket = mutableListOf()
            }
        }
        return ticket
    }
}