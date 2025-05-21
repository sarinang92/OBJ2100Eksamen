// src/main/java/com/kinoapp/service/TicketService.java
package kino.service;

import kino.repository.TicketDAO;
import kino.util.DeletedBookingsFileReader;

public class TicketService {

    private TicketDAO ticketDAO;
    private DeletedBookingsFileReader deletedBookingsFileReader;

    public TicketService() {
        this.ticketDAO = new TicketDAO();
        this.deletedBookingsFileReader = new DeletedBookingsFileReader();
    }

    /**
     * Deletes all unpaid bookings for a given showing and logs the deletion count.
     *
     * @param showingId The ID of the showing.
     */
    public void deleteUnpaidBookingsForShowing(int showingId) {
        int deletedCount = ticketDAO.deleteUnpaidTicketsForShowing(showingId);
        if (deletedCount > 0) {
            deletedBookingsFileReader.updateDeletedBookingsCount(showingId, deletedCount);
        }
    }

    // You might add other service methods for tickets here, e.g., createTicket, etc.
}