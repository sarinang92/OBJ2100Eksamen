package kino.model;

// Statistics-klassen representerer aggregerte statistikker for filmer eller kinosaler.
// Den brukes til å holde oversiktsdata som totalt antall seere, gjennomsnittlig belegg, og kansellerte bookinger.
public class Statistics {

    private int filmId; // ID for filmen som statistikken gjelder for (eller relaterer seg til).
    private String filmTitle; // Tittelen på filmen.
    private int totalViewers; // Totalt antall seere (for filmen eller i salen).
    private double averageOccupancy; // Gjennomsnittlig belegg (i prosent, f.eks. for filmen eller salen).
    private int cancelledBookings; // Antall kansellerte bookinger (for filmen eller i salen).

    // Standard konstruktør for Statistics-klassen.
    public Statistics() {
    }

    // Konstruktør med alle felter for å initialisere et Statistics-objekt.
    public Statistics(int filmId, String filmTitle, int totalViewers, double averageOccupancy, int cancelledBookings) {
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.totalViewers = totalViewers;
        this.averageOccupancy = averageOccupancy;
        this.cancelledBookings = cancelledBookings;
    }

    // Gettere og Settere for klassefeltene.

    // Returnerer film-ID.
    public int getFilmId() {
        return filmId;
    }

    // Setter film-ID.
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    // Returnerer filmtittelen.
    public String getFilmTitle() {
        return filmTitle;
    }

    // Setter filmtittelen.
    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    // Returnerer totalt antall seere.
    public int getTotalViewers() {
        return totalViewers;
    }

    // Setter totalt antall seere.
    public void setTotalViewers(int totalViewers) {
        this.totalViewers = totalViewers;
    }

    // Returnerer gjennomsnittlig belegg.
    public double getAverageOccupancy() {
        return averageOccupancy;
    }

    // Setter gjennomsnittlig belegg.
    public void setAverageOccupancy(double averageOccupancy) {
        this.averageOccupancy = averageOccupancy;
    }

    // Returnerer antall kansellerte bookinger.
    public int getCancelledBookings() {
        return cancelledBookings;
    }

    // Setter antall kansellerte bookinger.
    public void setCancelledBookings(int cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    // Overskriver toString()-metoden for å gi en forbedret og lesbar strengrepresentasjon av objektet.
    // Bruker String.format for å formatere utskriften pent, inkludert prosenttegn for belegg.
    @Override
    public String toString() {
        return String.format(
            "   ID: %d%n" +
            "   Navn: %s%n" +
            "   Totalt antall seere: %d%n" +
            "   Gjennomsnittlig belegg: %.2f%%%n" + // Formaterer til 2 desimaler og legger til %
            "   Kansellerte bookinger: %d%n",
            filmId, filmTitle, totalViewers, averageOccupancy, cancelledBookings
        );
    }
}