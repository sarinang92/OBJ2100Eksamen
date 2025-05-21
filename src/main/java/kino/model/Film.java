package kino.model;

public class Film {
    private int filmnr;
    private String filmnavn;

    public Film(int filmnr, String filmnavn) {
        this.filmnr = filmnr;
        this.filmnavn = filmnavn;
    }

    public int getFilmnr() {
        return filmnr;
    }

    public String getFilmnavn() {
        return filmnavn;
    }


    public String toString() {
        return filmnr + ": " + filmnavn;
    }
}