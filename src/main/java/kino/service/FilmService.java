package kino.service;

import kino.repository.FilmDAO;
import kino.model.Film;
import kino.config.KinoDatabaseKobling;

import java.sql.Connection;
import java.util.List;

/**
 * FilmService-klassen håndterer forretningslogikk knyttet til filmer.
 * Dette inkluderer operasjoner som å hente, opprette, redigere og slette filmer.
 */
public class FilmService {

    private FilmDAO filmDAO; // Data Access Object for filmer, brukes for å interagere med filmlagringen.

    /**
     * Konstruktør for FilmService. Initialiserer FilmDAO.
     */
    public FilmService() {
        try {
            Connection conn = KinoDatabaseKobling.getInstance().getForbindelse();
            this.filmDAO = new FilmDAO(conn);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Feil ved opprettelse av FilmDAO i FilmService.");
        }
    }

    /**
     * Henter en spesifikk film basert på ID.
     */
    public Film hentFilm(int filmId) {
        return filmDAO.hentFilm(filmId);
    }

    /**
     * Henter en liste over alle filmer.
     */
    public List<Film> hentAlleFilmer() {
        return filmDAO.hentAlleFilmer();
    }

    /**
     * Oppretter en ny film med gitt tittel.
     */
    public void opprettFilm(String tittel) {
        filmDAO.createFilm(tittel);
    }

    /**
     * Oppdaterer tittel for en eksisterende film.
     */
    public void oppdaterFilm(int filmId, String nyTittel) {
        Film film = filmDAO.hentFilm(filmId);
        if (film != null) {
            film.setFilmnavn(nyTittel);
            filmDAO.oppdaterFilm(film);
        }
    }

    /**
     * Sletter en film basert på ID.
     */
    public void slettFilm(int filmId) {
        filmDAO.slettFilm(filmId);
    }
}
