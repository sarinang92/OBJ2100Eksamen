package kino.model;

import java.time.LocalDate;
import java.time.LocalTime;

// Showing-klassen representerer en enkelt visning (forestilling) i kinosystemet.
// Den fungerer som et modellobjekt som holder detaljer om en spesifikk visning.
public class Showing {
    private int showingId; // Unik ID for visningen (f.eks. primærnøkkel).
    private int filmId; // ID for filmen som vises.
    private int cinemaHallId; // ID for kinosalen der visningen finner sted.
    private LocalDate date; // Datoen for visningen.
    private LocalTime startTime; // Starttidspunktet for visningen.
    private double price; // Prisen per billett for denne visningen.

    // Standard konstruktør for Showing-klassen.
    public Showing() {
    }

    // Konstruktør med alle felter for å initialisere et Showing-objekt.
    public Showing(int showingId, int filmId, int cinemaHallId, LocalDate date, LocalTime startTime, double price) {
        this.showingId = showingId;
        this.filmId = filmId;
        this.cinemaHallId = cinemaHallId;
        this.date = date;
        this.startTime = startTime;
        this.price = price;
    }

    // Gettere og Settere for klassefeltene.

    // Returnerer visnings-ID.
    public int getShowingId() {
        return showingId;
    }

    // Setter visnings-ID.
    public void setShowingId(int showingId) {
        this.showingId = showingId;
    }

    // Returnerer film-ID.
    public int getFilmId() {
        return filmId;
    }

    // Setter film-ID.
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    // Returnerer kinosal-ID.
    public int getCinemaHallId() {
        return cinemaHallId;
    }

    // Setter kinosal-ID.
    public void setCinemaHallId(int cinemaHallId) {
        this.cinemaHallId = cinemaHallId;
    }

    // Returnerer datoen for visningen.
    public LocalDate getDate() {
        return date;
    }

    // Setter datoen for visningen.
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Returnerer starttidspunktet for visningen.
    public LocalTime getStartTime() {
        return startTime;
    }

    // Setter starttidspunktet for visningen.
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // Returnerer prisen per billett.
    public double getPrice() {
        return price;
    }

    // Setter prisen per billett.
    public void setPrice(double price) {
        this.price = price;
    }
}