package kino.controller;

import kino.model.Showing;
import kino.service.ShowingService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// ShowingController-klassen fungerer som et mellomlag mellom visningslaget (View) og servicelaget (Service) for visninger.
// Den mottar forespørsler relatert til visninger fra brukergrensesnittet og delegerer dem til ShowingService.
public class ShowingController {

    private ShowingService showingService; // En instans av ShowingService for å utføre visningsrelaterte operasjoner.

    // Konstruktør for ShowingController. Initialiserer ShowingService.
    public ShowingController() {
        this.showingService = new ShowingService(); // Oppretter en ny ShowingService-instans.
    }

    // Metode for å hente en spesifikk visning basert på dens ID.
    // Mottar visnings-ID fra visningslaget og videresender den til showingService.
    // Returnerer Showing-objektet.
    public Showing getShowing(int showingId) {
        return showingService.getShowing(showingId);
    }

    // Metode for å hente en liste over alle visninger.
    // Delegerer kallet til showingService og returnerer listen av Showing-objekter.
    public List<Showing> getAllShowings() {
        return showingService.getAllShowings();
    }

    // Metode for å opprette en ny visning.
    // Mottar nødvendig informasjon om film, sal, dato, tid og pris fra visningslaget
    // og videresender dette til showingService for opprettelse.
    public void createShowing(int filmId, int cinemaHallId, LocalDate date, LocalTime startTime, double price) {
        showingService.createShowing(filmId, cinemaHallId, date, startTime, price);
    }

    // Metode for å redigere en eksisterende visning.
    // Mottar visnings-ID og nye detaljer (film, sal, dato, tid, pris) fra visningslaget
    // og videresender dette til showingService for oppdatering.
    public void editShowing(int showingId, int filmId, int cinemaHallId, LocalDate date, LocalTime startTime, double price) {
        showingService.editShowing(showingId, filmId, cinemaHallId, date, startTime, price);
    }

    // Metode for å slette en visning.
    // Mottar visnings-ID fra visningslaget og videresender til showingService for sletting.
    public void deleteShowing(int showingId) {
        showingService.deleteShowing(showingId);
    }

    // Ytterligere visningsrelaterte metoder kan legges til her ved behov.
}