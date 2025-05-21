package kino.model;

// CinemaHall-klassen representerer en kinosal i systemet.
// Den fungerer som et modellobjekt som holder data om en kinosal.
public class CinemaHall {

    private int k_kinosalnr;    // Primærnøkkel fra tblkinosal, unikt nummer for kinosalen.
    private String k_kinonavn;  // Navnet på kinoen som salen tilhører.
    private String k_kinosalnavn; // Navnet på selve kinosalen (f.eks. "Sal 1", "Hovedsalen").

    // Standard konstruktør for CinemaHall-klassen.
    public CinemaHall() {
    }

    // Konstruktør for CinemaHall-klassen med alle felt.
    // Brukes for å opprette et CinemaHall-objekt med initialiserte verdier.
    public CinemaHall(int k_kinosalnr, String k_kinonavn, String k_kinosalnavn) {
        this.k_kinosalnr = k_kinosalnr;
        this.k_kinonavn = k_kinonavn;
        this.k_kinosalnavn = k_kinosalnavn;
    }

    // Gettere og Settere for klassefeltene.

    // Returnerer kinosalnummeret.
    public int getK_kinosalnr() {
        return k_kinosalnr;
    }

    // Setter kinosalnummeret.
    public void setK_kinosalnr(int k_kinosalnr) {
        this.k_kinosalnr = k_kinosalnr;
    }

    // Returnerer navnet på kinoen.
    public String getK_kinonavn() {
        return k_kinonavn;
    }

    // Setter navnet på kinoen.
    public void setK_kinonavn(String k_kinonavn) {
        this.k_kinonavn = k_kinonavn;
    }

    // Returnerer navnet på kinosalen.
    public String getK_kinosalnavn() {
        return k_kinosalnavn;
    }

    // Setter navnet på kinosalen.
    public void setK_kinosalnavn(String k_kinosalnavn) {
        this.k_kinosalnavn = k_kinosalnavn;
    }

    // Overskriver toString()-metoden for å gi en strengrepresentasjon av CinemaHall-objektet.
    // Dette er nyttig for feilsøking og logging.
    @Override
    public String toString() {
        return "CinemaHall{" +
                "k_kinosalnr=" + k_kinosalnr +
                ", k_kinonavn='" + k_kinonavn + '\'' +
                ", k_kinosalnavn='" + k_kinosalnavn + '\'' +
                '}';
    }
}