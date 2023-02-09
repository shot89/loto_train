class PlayerImpl(var name: String, var ticketCount: Int) : Player {

    var tickets = mutableListOf<Ticket>()

    init {
        for (i in 1..ticketCount){
            tickets.add(TicketImpl())
        }
    }

}