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

    public void addFilm(String filmName) {
        filmDAO.createFilm(filmName);
    }

    public void updateFilm(int filmId, String newTitle) {
        Film film = filmDAO.hentFilm(filmId);
        if (film != null) {
            film.setFilmnavn(newTitle);
            filmDAO.oppdaterFilm(film);
        }
    }

    public void scheduleShowing(Showing showing) {
        showingDAO.createShowing(showing);
    }

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

    public void addCinemaHall(CinemaHall cinemaHall) {
        cinemaHallDAO.createCinemaHall(cinemaHall);
    }

    public void deleteFilm(int filmId) {
        filmDAO.slettFilm(filmId);
    }

    public void deleteShowing(int showingId) {
        showingDAO.deleteShowing(showingId);
    }

    public List<Film> getAllFilms() {
        return filmDAO.hentAlleFilmer();
    }

    public List<Showing> getShowingsForFilm(int filmId) {
        return showingDAO.getShowingsForFilm(filmId);
    }
}
