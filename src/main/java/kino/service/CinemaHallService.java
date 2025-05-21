package kino.service;

import kino.repository.CinemaHallDAO;
import kino.model.CinemaHall;
import java.util.List;

// CinemaHallService-klassen håndterer forretningslogikk knyttet til kinosaler.
// Dette inkluderer operasjoner som å hente, legge til, oppdatere og slette kinosaler.
public class CinemaHallService {

    private CinemaHallDAO cinemaHallDAO; // Data Access Object for kinosaler, brukes for å interagere med kinosallagringen.

    // Konstruktør for CinemaHallService. Initialiserer CinemaHallDAO.
    public CinemaHallService() {
        this.cinemaHallDAO = new CinemaHallDAO(); // Oppretter en ny CinemaHallDAO-instans.
    }

    // Metode for å hente en spesifikk kinosal basert på dens ID.
    // Delegerer oppgaven til CinemaHallDAO og returnerer CinemaHall-objektet.
    public CinemaHall getCinemaHallById(int cinemaHallId) {
        return cinemaHallDAO.getCinemaHallById(cinemaHallId);
    }

    // Metode for å hente en liste over alle kinosaler.
    // Delegerer oppgaven til CinemaHallDAO og returnerer listen av CinemaHall-objekter.
    public List<CinemaHall> getAllCinemaHalls() {
        return cinemaHallDAO.getAllCinemaHalls();
    }

    // Metode for å legge til en ny kinosal.
    // Mottar et CinemaHall-objekt og delegerer oppgaven til CinemaHallDAO for opprettelse.
    public void addCinemaHall(CinemaHall cinemaHall) {
        cinemaHallDAO.createCinemaHall(cinemaHall);
    }

    // Metode for å oppdatere en eksisterende kinosal.
    // Mottar et CinemaHall-objekt med oppdaterte detaljer og delegerer oppgaven til CinemaHallDAO.
    public void updateCinemaHall(CinemaHall cinemaHall) {
        cinemaHallDAO.updateCinemaHall(cinemaHall);
    }

    // Metode for å slette en kinosal basert på dens ID.
    // Mottar kinosal-ID og delegerer oppgaven til CinemaHallDAO for sletting.
    public void deleteCinemaHall(int cinemaHallId) {
        cinemaHallDAO.deleteCinemaHall(cinemaHallId);
    }

    // Ytterligere tjenestemetoder kan legges til her ved behov.
}