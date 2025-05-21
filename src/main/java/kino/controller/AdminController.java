package kino.controller;

import kino.service.AdminService;
import kino.model.Film;
import kino.model.Showing;
import java.time.LocalDate;
import java.time.LocalTime;

// AdminController-klassen fungerer som et mellomlag mellom visningslaget (View) og servicelaget (Service).
// Den mottar forespørsler fra brukergrensesnittet og delegerer dem til AdminService.
public class AdminController {

    private AdminService adminService; // En instans av AdminService for å utføre forretningslogikk.

    // Konstruktør for AdminController. Initialiserer AdminService.
    public AdminController() {
        this.adminService = new AdminService();
    }

    // Metode for å legge til en ny film.
    // Mottar tittel fra visningslaget og videresender den til adminService.
    public void addFilm(String title) {
        adminService.addFilm(title);
    }

    // Metode for å oppdatere en eksisterende film.
    // Mottar film-ID og ny tittel fra visningslaget og videresender til adminService.
    public void updateFilm(int filmId, String newTitle) {
        adminService.updateFilm(filmId, newTitle);
    }

    // Metode for å slette en film.
    // Mottar film-ID fra visningslaget og videresender til adminService.
    public void deleteFilm(int filmId) {
        adminService.deleteFilm(filmId);
    }

    // Metode for å sette opp en ny visning.
    // Mottar nødvendig informasjon fra visningslaget, oppretter et Showing-objekt,
    // og videresender det til adminService.
    public void scheduleShowing(int filmId, int cinemaHallId, LocalDate date, LocalTime startTime, double price) {
        Showing showing = new Showing(); // Oppretter et nytt Showing-objekt.
        showing.setFilmId(filmId); // Setter film-ID.
        showing.setCinemaHallId(cinemaHallId); // Setter kinosal-ID.
        showing.setDate(date); // Setter dato for visningen.
        showing.setStartTime(startTime); // Setter starttidspunkt for visningen.
        showing.setPrice(price); // Setter pris for visningen.
        adminService.scheduleShowing(showing); // Kaller service-laget for å lagre visningen.
    }

    // Metode for å oppdatere en eksisterende visning.
    // Mottar visnings-ID og nye detaljer fra visningslaget og videresender til adminService.
    public void updateShowing(int showingId, int filmId, int cinemaHallId, LocalDate date, LocalTime startTime, double price) {
        adminService.updateShowing(showingId, filmId, cinemaHallId, date, startTime, price);
    }

    // Metode for å slette en visning.
    // Mottar visnings-ID fra visningslaget og videresender til adminService.
    public void deleteShowing(int showingId) {
        adminService.deleteShowing(showingId);
    }
}