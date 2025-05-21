package kino.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visning {
    private int visningnr;
    private int filmnr;
    private int kinosalnr;
    private LocalDate dato;
    private LocalTime starttid;
    private double pris;

    public Visning() {
    }

    public int getVisningnr() {
        return visningnr;
    }

    public void setVisningnr(int visningnr) {
        this.visningnr = visningnr;
    }

    public int getFilmnr() {
        return filmnr;
    }

    public void setFilmnr(int filmnr) {
        this.filmnr = filmnr;
    }

    public int getKinosalnr() {
        return kinosalnr;
    }

    public void setKinosalnr(int kinosalnr) {
        this.kinosalnr = kinosalnr;
    }

    public LocalDate getDato() {
        return dato;
    }

    public void setDato(LocalDate dato) {
        this.dato = dato;
    }

    public LocalTime getStarttid() {
        return starttid;
    }

    public void setStarttid(LocalTime starttid) {
        this.starttid = starttid;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }


    @Override
    public String toString() {
        return visningnr + ": " + dato + " " + starttid + " | Sal: " + kinosalnr + " | Pris: " + pris + " kr";
    }

}
