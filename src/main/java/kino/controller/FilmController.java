package kino.controller;

import kino.model.Film; 
import kino.service.FilmService; 
import java.util.List; 

// FilmController-klassen fungerer som et mellomlag mellom visningslaget (View) og servicelaget (Service) for filmer.
// Den mottar forespørsler relatert til filmer fra brukergrensesnittet og delegerer dem til FilmService.
public class FilmController {

    private FilmService filmService; // En instans av FilmService for å utføre filmrelaterte operasjoner.

    // Konstruktør for FilmController. Initialiserer FilmService.
    public FilmController() {
        this.filmService = new FilmService(); // Oppretter en ny FilmService-instans.
    }

    // Metode for å hente en spesifikk film basert på dens ID.
    // Mottar film-ID fra visningslaget og videresender den til filmService.
    // Returnerer Film-objektet.
    public Film getFilm(int filmId) {
        return filmService.getFilm(filmId);
    }

    // Metode for å hente en liste over alle filmer.
    // Delegerer kallet til filmService og returnerer listen av Film-objekter.
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    // Metode for å opprette en ny film.
    // Mottar filmtittel fra visningslaget og videresender den til filmService.
    public void createFilm(String title) {
        filmService.createFilm(title);
    }

    // Metode for å redigere en eksisterende film.
    // Mottar film-ID og den nye tittelen fra visningslaget og videresender til filmService.
    public void editFilm(int filmId, String newTitle) {
        filmService.editFilm(filmId, newTitle);
    }

    // Metode for å slette en film.
    // Mottar film-ID fra visningslaget og videresender til filmService.
    public void deleteFilm(int filmId) {
        filmService.deleteFilm(filmId);
    }

}