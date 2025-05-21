package kino.model;

public class Kinosal {
    private final int kinosalnr;
    private final String kinonavn;
    private final String kinosalnavn;

    public Kinosal(int kinosalnr, String kinonavn, String kinosalnavn) {
        this.kinosalnr = kinosalnr;
        this.kinonavn = kinonavn;
        this.kinosalnavn = kinosalnavn;
    }

    public int getKinosalnr() {
        return kinosalnr;
    }

    public String getKinonavn() {
        return kinonavn;
    }

    public String getKinosalnavn() {
        return kinosalnavn;
    }
}
