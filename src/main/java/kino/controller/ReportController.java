package kino.controller;

import kino.model.Statistics; 
import kino.model.FilmShowingStats; 
import kino.model.CinemaHallShowingStats; 
import kino.service.StatisticsService; 

import java.util.List;

// ReportController-klassen håndterer forespørsler relatert til rapporter og statistikk.
// Den fungerer som et mellomlag mellom visningslaget (View) og servicelaget (Service) for statistikk.
public class ReportController {

    private StatisticsService statisticsService; // En instans av StatisticsService for å utføre statistikkberegninger.

    // Konstruktør for ReportController. Initialiserer StatisticsService.
    public ReportController() {
        this.statisticsService = new StatisticsService(); // Oppretter en ny StatisticsService-instans.
    }

    // Genererer og skriver ut aggregerte statistikker for en spesifikk film.
    // Den henter statistikken fra StatisticsService og presenterer den for brukeren.
    public Statistics generateFilmStatistics(int filmId) {
        Statistics stats = statisticsService.generateFilmStatistics(filmId); // Henter aggregerte filmstatistikker.
        if (stats != null) { // Sjekker om statistikk ble funnet.
            System.out.println("Aggregerte filmstatistikker for Film ID " + filmId + ":"); // Skriver ut overskrift.
            System.out.println(stats.toString()); // Skriver ut statistikkdetaljer.
        } else {
            System.out.println("Ingen aggregerte statistikker funnet for Film ID " + filmId); // Melding hvis ingen statistikk finnes.
        }
        return stats; // Returnerer det genererte statistikkobjektet.
    }

     // Genererer og skriver ut aggregerte statistikker for en spesifikk kinosal.
     // Den henter statistikken fra StatisticsService og presenterer den for brukeren.
    public Statistics generateCinemaHallStatistics(int cinemaHallId) {
        Statistics stats = statisticsService.generateCinemaHallStatistics(cinemaHallId); // Henter aggregerte kinosalstatistikker.
        if (stats != null) { // Sjekker om statistikk ble funnet.
            System.out.println("Aggregerte kinosalstatistikker for Kinosal ID " + cinemaHallId + ":"); // Skriver ut overskrift.
            System.out.println(stats.toString()); // Skriver ut statistikkdetaljer.
        } else {
            System.out.println("Ingen aggregerte statistikker funnet for Kinosal ID " + cinemaHallId); // Melding hvis ingen statistikk finnes.
        }
        return stats; // Returnerer det genererte statistikkobjektet.
    }

    
    // Genererer og skriver ut detaljert visningsstatistikk for en spesifikk film.
    // Den henter en liste med detaljert statistikk fra StatisticsService og presenterer den for brukeren.
    public List<FilmShowingStats> generateFilmDetailedStatistics(int filmId) {
        List<FilmShowingStats> detailedStats = statisticsService.getFilmDetailedShowingStatistics(filmId); // Henter detaljerte visningsstatistikker for filmen.
        if (!detailedStats.isEmpty()) { // Sjekker om listen ikke er tom.
            System.out.println("\nDetaljert visningsstatistikk for Film ID " + filmId + ":"); // Skriver ut overskrift.
            for (FilmShowingStats stats : detailedStats) { // Itererer gjennom og skriver ut hver statistikk.
                System.out.println(stats.toString());
            }
        } else {
            System.out.println("Ingen detaljert visningsstatistikk funnet for Film ID " + filmId); // Melding hvis ingen statistikk finnes.
        }
        return detailedStats; // Returnerer listen med detaljerte statistikkobjekter.
    }


    // Genererer og skriver ut detaljert visningsstatistikk for en spesifikk kinosal.
    // Den henter en liste med detaljert statistikk fra StatisticsService og presenterer den for brukeren.
    public List<CinemaHallShowingStats> generateCinemaHallDetailedStatistics(int cinemaHallId) {
        List<CinemaHallShowingStats> detailedStats = statisticsService.getCinemaHallDetailedShowingStatistics(cinemaHallId); // Henter detaljerte visningsstatistikker for kinosalen.
        if (!detailedStats.isEmpty()) { // Sjekker om listen ikke er tom.
            System.out.println("\nDetaljert visningsstatistikk for Kinosal ID " + cinemaHallId + ":"); // Skriver ut overskrift.
            for (CinemaHallShowingStats stats : detailedStats) { // Itererer gjennom og skriver ut hver statistikk.
                System.out.println(stats.toString());
            }
        } else {
            System.out.println("Ingen detaljert visningsstatistikk funnet for Kinosal ID " + cinemaHallId); // Melding hvis ingen statistikk finnes.
        }
        return detailedStats; // Returnerer listen med detaljerte statistikkobjekter.
    }
}