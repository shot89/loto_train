class PlayerImpl(private var name: String, var ticketCount: Int) : Player {

    var tickets = mutableListOf<Ticket>()

    init {
        for (i in 1..ticketCount){
            tickets.add(TicketImpl())
        }
    }

    override fun getTicket(): MutableList<Ticket> {
        return tickets
    }

    override fun getName(): String {
        return name
    }

}