package kino.service;

import kino.config.KinoDatabaseKobling;
import kino.repository.ShowingDAO;
import kino.model.Showing;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * ShowingService-klassen håndterer forretningslogikk knyttet til visninger (forestillinger).
 * Den fungerer som et mellomlag mellom kontrollerlaget og datalagret (DAO) for visningsrelaterte operasjoner.
 */
public class ShowingService {

    private ShowingDAO showingDAO; // Data Access Object for visninger, brukes for å interagere med visningslagringen.
    private FilmService filmService; // For å validere film-ID
    private CinemaHallService cinemaHallService; // For å validere kinosal-ID

    /**
     * Konstruktør for ShowingService. Initialiserer DAO og andre tjenester.
     */
    public ShowingService() {
        try {
            Connection conn = KinoDatabaseKobling.getInstance().getForbindelse();
            this.showingDAO = new ShowingDAO(conn);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Feil ved opprettelse av ShowingDAO i ShowingService.");
        }

        this.filmService = new FilmService(); // Brukes til å validere film-ID
        this.cinemaHallService = new CinemaHallService(); // Brukes til å validere kinosal-ID
    }

    /**
     * Metode for å hente en spesifikk visning basert på dens ID.
     * Delegerer oppgaven til ShowingDAO og returnerer Showing-objektet.
     */
    public Showing getShowing(int showingId) {
        return showingDAO.getShowing(showingId);
    }

    /**
     * Metode for å hente en liste over alle visninger.
     * Delegerer oppgaven til ShowingDAO og returnerer listen av Showing-objekter.
     */
    public List<Showing> getAllShowings() {
        return showingDAO.getAllShowings();
    }

    /**
     * Metode for å opprette en ny visning.
     * Inkluderer valideringslogikk for å sjekke om filmId og cinemaHallId eksisterer.
     */
    public void createShowing(int filmId, int cinemaHallId, LocalDate date, LocalTime startTime, double price) {
        if (filmService.hentFilm(filmId) == null) {
            System.out.println("Feil ved opprettelse av visning: Film med ID " + filmId + " eksisterer ikke.");
            return;
        }

        if (cinemaHallService.getCinemaHallById(cinemaHallId) == null) {
            System.out.println("Feil ved opprettelse av visning: Kinosal med ID " + cinemaHallId + " eksisterer ikke.");
            return;
        }

        Showing showing = new Showing();
        showing.setFilmId(filmId);
        showing.setCinemaHallId(cinemaHallId);
        showing.setDate(date);
        showing.setStartTime(startTime);
        showing.setPrice(price);
        showingDAO.createShowing(showing);
    }

    /**
     * Metode for å redigere en eksisterende visning.
     * Inkluderer valideringslogikk for å sjekke om oppdatert filmId og cinemaHallId eksisterer.
     */
    public void editShowing(int showingId, int filmId, int cinemaHallId, LocalDate date, LocalTime startTime, double price) {
        Showing showing = showingDAO.getShowing(showingId);
        if (showing == null) {
            System.out.println("Visning med ID " + showingId + " ble ikke funnet for oppdatering.");
            return;
        }

        if (filmService.hentFilm(filmId) == null) {
            System.out.println("Feil ved oppdatering av visning: Film med ID " + filmId + " eksisterer ikke.");
            return;
        }

        if (cinemaHallService.getCinemaHallById(cinemaHallId) == null) {
            System.out.println("Feil ved oppdatering av visning: Kinosal med ID " + cinemaHallId + " eksisterer ikke.");
            return;
        }

        showing.setFilmId(filmId);
        showing.setCinemaHallId(cinemaHallId);
        showing.setDate(date);
        showing.setStartTime(startTime);
        showing.setPrice(price);
        showingDAO.updateShowing(showing);
    }

    /**
     * Sletter en visning kun hvis det ikke er solgt billetter til den.
     * Implementerer forretningsregelen for sletting av visning.
     */
    public void deleteShowing(int showingId) {
        if (showingDAO.hasTicketsSold(showingId)) {
            System.out.println("Kan ikke slette visning " + showingId + ": Billetter er solgt til denne visningen.");
            return;
        }

        showingDAO.deleteShowing(showingId);
    }
}
