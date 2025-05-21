package kino.service;

import kino.repository.StatisticsDAO;
import kino.model.Statistics;
import kino.model.FilmShowingStats;
import kino.model.CinemaHallShowingStats;

import java.util.List;

// StatisticsService-klassen håndterer forretningslogikk knyttet til ulike statistikker
// i kinosystemet. Den fungerer som et mellomlag for å hente og potensielt bearbeide
// statistikkdata fra datalagret (DAO) før det presenteres.
public class StatisticsService {

    private StatisticsDAO statisticsDAO; // Deklarerer StatisticsDAO-objektet som vil brukes for databasetilgang.

    // Konstruktør for StatisticsService. Initialiserer StatisticsDAO.
    public StatisticsService() {
        this.statisticsDAO = new StatisticsDAO(); // Initialiserer statisticsDAO-objektet.
    }

    // Genererer aggregerte statistikker for en spesifikk film.
    public Statistics generateFilmStatistics(int filmId) {
        return statisticsDAO.getFilmStatistics(filmId);
    }

    // Genererer aggregerte statistikker for en spesifikk kinosal.
    public Statistics generateCinemaHallStatistics(int cinemaHallId) {
        return statisticsDAO.getCinemaHallStatistics(cinemaHallId);
    }

    // Henter detaljerte visningsstatistikk for en spesifikk film.
    public List<FilmShowingStats> getFilmDetailedShowingStatistics(int filmId) {
        return statisticsDAO.getFilmDetailedShowingStatistics(filmId);
    }

    // Henter detaljerte visningsstatistikk for en spesifikk kinosal.
    public List<CinemaHallShowingStats> getCinemaHallDetailedShowingStatistics(int cinemaHallId) {
        return statisticsDAO.getCinemaHallDetailedShowingStatistics(cinemaHallId);
    }
}