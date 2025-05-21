package kino.controller;

import kino.model.Film;
import kino.service.FilmService;
import java.util.List;

/**
 * FilmController-klassen fungerer som et mellomlag mellom visningslaget (View)
 * og servicelaget (Service) for filmer. Den mottar forespørsler relatert til
 * filmer fra brukergrensesnittet og delegerer dem til FilmService.
 */
public class FilmController {

    private FilmService filmService; // En instans av FilmService for å utføre filmrelaterte operasjoner.

    /**
     * Konstruktør for FilmController. Initialiserer FilmService.
     */
    public FilmController() {
        this.filmService = new FilmService();
    }

    /**
     * Henter en spesifikk film basert på ID.
     */
    public Film hentFilm(int filmId) {
        return filmService.hentFilm(filmId);
    }

    /**
     * Henter alle filmer.
     */
    public List<Film> hentAlleFilmer() {
        return filmService.hentAlleFilmer();
    }

    /**
     * Oppretter en ny film.
     */
    public void opprettFilm(String tittel) {
        filmService.opprettFilm(tittel);
    }

    /**
     * Oppdaterer en eksisterende film.
     */
    public void oppdaterFilm(int filmId, String nyTittel) {
        filmService.oppdaterFilm(filmId, nyTittel);
    }

    /**
     * Sletter en film basert på ID.
     */
    public void slettFilm(int filmId) {
        filmService.slettFilm(filmId);
    }
}
