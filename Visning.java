package kunde.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visning {
    private int visningnr;
    private int filmnr;
    private int salnr;
    private LocalDate dato;
    private LocalTime starttid;
    private double pris;

    public Visning(int visningnr, int filmnr, int salnr, LocalDate dato, LocalTime starttid, double pris) {
        this.visningnr = visningnr;
        this.filmnr = filmnr;
        this.salnr = salnr;
        this.dato = dato;
        this.starttid = starttid;
        this.pris = pris;
    }

    public int getVisningnr() { return visningnr; }
    public int getSalnr() { return salnr; }
    public LocalDate getDato() { return dato; }
    public LocalTime getStarttid() { return starttid; }
    public double getPris() { return pris; }

    @Override
    public String toString() {
        return visningnr + ": " + dato + " " + starttid + " | Sal: " + salnr + " | Pris: " + pris + " kr";
    }
}