package kino.model;

import java.time.LocalDate;
import java.time.LocalTime;

// CinemaHallShowingStats-klassen representerer statistikk for en spesifikk visning sett fra et kinosalperspektiv.
// Den brukes til å holde detaljerte data om en visning i en kinosal, inkludert solgte billetter.
public class CinemaHallShowingStats {
    private int showingId; // ID for visningen.
    private String cinemaHallName; // Navnet på kinosalen der visningen fant sted.
    private String filmTitle; // Tittelen på filmen som ble vist.
    private LocalDate date; // Datoen for visningen.
    private LocalTime startTime; // Starttidspunktet for visningen.
    private double price; // Prisen per billett for denne visningen.
    private int ticketsSold; // Antall solgte billetter for denne visningen.

    // Standard konstruktør.
    public CinemaHallShowingStats() {}

    // Konstruktør med alle felter for å initialisere et CinemaHallShowingStats-objekt.
    public CinemaHallShowingStats(int showingId, String cinemaHallName, String filmTitle, LocalDate date, LocalTime startTime, double price, int ticketsSold) {
        this.showingId = showingId;
        this.cinemaHallName = cinemaHallName;
        this.filmTitle = filmTitle;
        this.date = date;
        this.startTime = startTime;
        this.price = price;
        this.ticketsSold = ticketsSold;
    }

    // Gettere for alle felter.

    // Returnerer visnings-ID.
    public int getShowingId() {
        return showingId;
    }

    // Returnerer navnet på kinosalen.
    public String getCinemaHallName() {
        return cinemaHallName;
    }

    // Returnerer filmtittelen.
    public String getFilmTitle() {
        return filmTitle;
    }

    // Returnerer datoen for visningen.
    public LocalDate getDate() {
        return date;
    }

    // Returnerer starttidspunktet for visningen.
    public LocalTime getStartTime() {
        return startTime;
    }

    // Returnerer billettprisen.
    public double getPrice() {
        return price;
    }

    // Returnerer antall solgte billetter.
    public int getTicketsSold() {
        return ticketsSold;
    }

    // Settere for alle felter.

    // Setter visnings-ID.
    public void setShowingId(int showingId) {
        this.showingId = showingId;
    }

    // Setter navnet på kinosalen.
    public void setCinemaHallName(String cinemaHallName) {
        this.cinemaHallName = cinemaHallName;
    }

    // Setter filmtittelen.
    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    // Setter datoen for visningen.
    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Setter starttidspunktet for visningen.
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    // Setter billettprisen.
    public void setPrice(double price) {
        this.price = price;
    }

    // Setter antall solgte billetter.
    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    // Overskriver toString()-metoden for å gi en lesbar strengrepresentasjon av objektet.
    // Dette er nyttig for logging og visning i konsollen.
    @Override
    public String toString() {
        return "Visnings ID: " + showingId +
               ", Sal: " + cinemaHallName +
               ", Film: " + filmTitle +
               ", Dato: " + date +
               ", Tid: " + startTime +
               ", Pris: " + price +
               ", Solgte billetter: " + ticketsSold;
    }
}