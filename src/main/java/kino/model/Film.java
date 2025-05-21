package kino.model;

// Film-klassen representerer en film i kinosystemet.
// Den fungerer som et modellobjekt som holder data om en film.
public class Film {

    private int f_filmnr;    // Primærnøkkel for filmen, unikt filmnummer.
    private String f_filmnavn; // Navnet/tittelen på filmen.

    // Standard konstruktør for Film-klassen.
    public Film() {
    }

    // Konstruktør for Film-klassen med alle felt.
    // Brukes for å opprette et Film-objekt med initialiserte verdier.
    public Film(int f_filmnr, String f_filmnavn) {
        this.f_filmnr = f_filmnr;
        this.f_filmnavn = f_filmnavn;
    }

    // Returnerer filmnummeret.
    public int getF_filmnr() {
        return f_filmnr;
    }

    // Setter filmnummeret.
    public void setF_filmnr(int f_filmnr) {
        this.f_filmnr = f_filmnr;
    }

    // Returnerer filmnavnet.
    public String getF_filmnavn() {
        return f_filmnavn;
    }

    // Setter filmnavnet.
    public void setF_filmnavn(String f_filmnavn) {
        this.f_filmnavn = f_filmnavn;
    }

    // Overskriver toString()-metoden for å gi en strengrepresentasjon av Film-objektet.
    // Dette er nyttig for feilsøking og logging.
    @Override
    public String toString() {
        return "Film{" +
                "f_filmnr=" + f_filmnr +
                ", f_filmnavn='" + f_filmnavn + '\'' +
                '}';
    }
}