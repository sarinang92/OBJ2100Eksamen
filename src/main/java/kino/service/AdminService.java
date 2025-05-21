package kino.service;

import kino.repository.FilmDAO;
import kino.repository.ShowingDAO;
import kino.repository.CinemaHallDAO;
import kino.model.Film;
import kino.model.Showing;
import kino.model.CinemaHall;

import java.util.List;

// AdminService-klassen håndterer forretningslogikk knyttet til administratoroppgaver.
// Dette inkluderer opprettelse, oppdatering og sletting av filmer, visninger og kinosaler.
public class AdminService {

    private FilmDAO filmDAO; // Data Access Object for filmer, brukes for å interagere med filmlagringen.
    private ShowingDAO showingDAO; // Data Access Object for visninger, brukes for å interagere med visningslagringen.
    private CinemaHallDAO cinemaHallDAO; // Data Access Object for kinosaler, brukes for å interagere med kinosallagringen.

    // Konstruktør for AdminService. Initialiserer DAO-objektene.
    public AdminService() {
        this.filmDAO = new FilmDAO(); // Oppretter en ny FilmDAO-instans.
        this.showingDAO = new ShowingDAO(); // Oppretter en ny ShowingDAO-instans.
        this.cinemaHallDAO = new CinemaHallDAO(); // Oppretter en ny CinemaHallDAO-instans.
    }

    // Metode for å legge til en ny film i systemet.
    // Mottar filmnavn og delegerer oppgaven til FilmDAO.
    public void addFilm(String filmName) {
        filmDAO.createFilm(filmName);
    }

    // Metode for å oppdatere tittelen på en eksisterende film.
    // Henter filmen, oppdaterer tittelen, og delegerer oppdateringen til FilmDAO.
    public void updateFilm(int filmId, String newTitle) {
        Film film = filmDAO.getFilm(filmId); // Henter filmen basert på ID.
        if (film != null) { // Sjekker om filmen eksisterer.
            film.setF_filmnavn(newTitle); // Setter ny tittel på filmobjektet.
            filmDAO.updateFilm(film); // Oppdaterer filmen i databasen via DAO.
        }
    }

    // Metode for å planlegge (opprette) en ny visning.
    // Mottar et Showing-objekt og delegerer oppgaven til ShowingDAO.
    public void scheduleShowing(Showing showing) {
        showingDAO.createShowing(showing);
    }

    // Metode for å oppdatere detaljene for en eksisterende visning.
    // Henter visningen, oppdaterer dens felter, og delegerer oppdateringen til ShowingDAO.
    public void updateShowing(int showingId, int filmId, int cinemaHallId, java.time.LocalDate date, java.time.LocalTime startTime, double price) {
        Showing showing = showingDAO.getShowing(showingId); // Henter visningen basert på ID.
        if (showing != null) { // Sjekker om visningen eksisterer.
            showing.setFilmId(filmId); // Oppdaterer film-ID.
            showing.setCinemaHallId(cinemaHallId); // Oppdaterer kinosal-ID.
            showing.setDate(date); // Oppdaterer dato.
            showing.setStartTime(startTime); // Oppdaterer starttidspunkt.
            showing.setPrice(price); // Oppdaterer pris.
            showingDAO.updateShowing(showing); // Oppdaterer visningen i databasen via DAO.
        }
    }

    // Metode for å legge til en ny kinosal i systemet.
    // Mottar et CinemaHall-objekt og delegerer oppgaven til CinemaHallDAO.
    public void addCinemaHall(CinemaHall cinemaHall) {
        cinemaHallDAO.createCinemaHall(cinemaHall);
    }

    // Metode for å slette en film fra systemet.
    // Mottar film-ID og delegerer oppgaven til FilmDAO.
    public void deleteFilm(int filmId) {
        filmDAO.deleteFilm(filmId);
    }

    // Metode for å slette en visning fra systemet.
    // Mottar visnings-ID og delegerer oppgaven til ShowingDAO.
    public void deleteShowing(int showingId) {
        showingDAO.deleteShowing(showingId);
    }

    // Metode for å hente en liste over alle filmer i systemet.
    // Delegerer oppgaven til FilmDAO og returnerer listen av Film-objekter.
    public List<Film> getAllFilms() {
        return filmDAO.getAllFilms();
    }

    // Metode for å hente alle visninger for en spesifikk film.
    // Mottar film-ID og delegerer oppgaven til ShowingDAO.
    // Returnerer en liste av Showing-objekter.
    public List<Showing> getShowingsForFilm(int filmId) {
        return showingDAO.getShowingsForFilm(filmId);
    }
}