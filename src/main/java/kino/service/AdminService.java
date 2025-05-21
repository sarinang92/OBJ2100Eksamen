package kino.service;

import kino.repository.FilmDAO;
import kino.repository.ShowingDAO;
import kino.repository.CinemaHallDAO;
import kino.model.Film;
import kino.model.Showing;
import kino.model.CinemaHall;
import kino.config.KinoDatabaseKobling;

import java.sql.Connection;
import java.util.List;

public class AdminService {

    private FilmDAO filmDAO;
    private ShowingDAO showingDAO;
    private CinemaHallDAO cinemaHallDAO;

    //Konstruktør
    public AdminService() {
        try {
            Connection conn = KinoDatabaseKobling.getInstance().getForbindelse();
            this.filmDAO = new FilmDAO(conn);
            this.showingDAO = new ShowingDAO(conn);
            this.cinemaHallDAO = new CinemaHallDAO(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Legger til en ny film med gitt navn
    public void addFilm(String filmName) {
        filmDAO.createFilm(filmName);
    }

    //Oppdaterer tittelen på en film
    public void updateFilm(int filmId, String newTitle) {
        Film film = filmDAO.hentFilm(filmId);
        if (film != null) {
            film.setFilmnavn(newTitle);
            filmDAO.oppdaterFilm(film);
        }
    }
//Planlegger en ny visning.
    public void scheduleShowing(Showing showing) {
        showingDAO.createShowing(showing);
    }
//Oppdaterer en eksisterende visning
    public void updateShowing(int showingId, int filmId, int cinemaHallId, java.time.LocalDate date, java.time.LocalTime startTime, double price) {
        Showing showing = showingDAO.getShowing(showingId);
        if (showing != null) {
            showing.setFilmId(filmId);
            showing.setCinemaHallId(cinemaHallId);
            showing.setDate(date);
            showing.setStartTime(startTime);
            showing.setPrice(price);
            showingDAO.updateShowing(showing);
        }
    }
//Legger til en ny kinosa
    public void addCinemaHall(CinemaHall cinemaHall) {
        cinemaHallDAO.createCinemaHall(cinemaHall);
    }
// Sletter en film
    public void deleteFilm(int filmId) {
        filmDAO.slettFilm(filmId);
    }
//Sletter en visning
    public void deleteShowing(int showingId) {
        showingDAO.deleteShowing(showingId);
    }
//Henter alle filmer
    public List<Film> getAllFilms() {
        return filmDAO.hentAlleFilmer();
    }
//Henter alle visninger knyttet til en bestemt film
    public List<Showing> getShowingsForFilm(int filmId) {
        return showingDAO.getShowingsForFilm(filmId);
    }
}
