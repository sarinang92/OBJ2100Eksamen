package kino.service;

import kino.repository.FilmDAO;
import kino.model.Film;
import java.util.List;

// FilmService-klassen håndterer forretningslogikk knyttet til filmer.
// Dette inkluderer operasjoner som å hente, opprette, redigere og slette filmer.
public class FilmService {

    private FilmDAO filmDAO; // Data Access Object for filmer, brukes for å interagere med filmlagringen.

    // Konstruktør for FilmService. Initialiserer FilmDAO.
    public FilmService() {
        this.filmDAO = new FilmDAO(); // Oppretter en ny FilmDAO-instans.
    }

    // Metode for å hente en spesifikk film basert på dens ID.
    // Delegerer oppgaven til FilmDAO og returnerer Film-objektet.
    public Film getFilm(int filmId) {
        return filmDAO.getFilm(filmId);
    }

    // Metode for å hente en liste over alle filmer.
    // Delegerer oppgaven til FilmDAO og returnerer listen av Film-objekter.
    public List<Film> getAllFilms() {
        return filmDAO.getAllFilms();
    }

    // Metode for å opprette en ny film.
    // Mottar filmtittelen og delegerer oppgaven til FilmDAO for opprettelse.
    public void createFilm(String title) {
        filmDAO.createFilm(title);
    }

    // Metode for å redigere en eksisterende film.
    // Henter filmen, oppdaterer tittelen, og delegerer oppdateringen til FilmDAO.
    public void editFilm(int filmId, String newTitle) {
        Film film = filmDAO.getFilm(filmId); // Henter filmen basert på ID.
        if (film != null) { // Sjekker om filmen eksisterer.
            film.setF_filmnavn(newTitle); // Setter ny tittel på filmobjektet.
            // film.setF_filmnr(filmId); // Denne linjen var redundant og er fjernet.
            filmDAO.updateFilm(film); // Oppdaterer filmen i databasen via DAO.
        }
    }

    // Metode for å slette en film basert på dens ID.
    // Mottar film-ID og delegerer oppgaven til FilmDAO for sletting.
    public void deleteFilm(int filmId) {
        filmDAO.deleteFilm(filmId);
    }
}