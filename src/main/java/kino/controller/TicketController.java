package kino.controller;

import kino.service.TicketService;

// TicketController håndterer forespørsler relatert til billetter.
// Den fungerer som et mellomlag mellom brukergrensesnittet og TicketService.
public class TicketController {

    private TicketService ticketService;

    // Konstruktør for TicketController.
    // Initialiserer TicketService.
    public TicketController() {
        this.ticketService = new TicketService();
    }

    // Håndterer forespørselen om å slette alle ubetalte bookinger for en spesifikk visning.
    // Logger start av prosessen og eventuelle suksesser eller feil.
    public void deleteUnpaidBookingsForShowing(int showingId) {
        System.out.println("Forsøker å slette ubetalte bookinger for visning ID: " + showingId);
        try {
            // Kaller tjenestelaget for å utføre sletteoperasjonen.
            // Tjenesten vil returnere antall slettede billetter.
            int deletedCount = ticketService.deleteUnpaidBookingsForShowing(showingId);
            if (deletedCount > 0) {
                System.out.println("Vellykket slettet " + deletedCount + " ubetalte bookinger for visning ID " + showingId);
            } else {
                System.out.println("Ingen ubetalte bookinger ble slettet for visning ID " + showingId + " (enten ingen funnet eller feil oppstod).");
            }
        } catch (Exception e) {
            // Fanger opp generiske feil som kan komme fra tjenestelaget.
            // Skriver ut en feilmelding til System.err.
            System.err.println("Feil ved sletting av ubetalte bookinger for visning ID " + showingId + ": " + e.getMessage());
        }
    }

}