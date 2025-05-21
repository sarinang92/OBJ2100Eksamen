package kino.model;

public class PlassBillett {
    private int radnr;
    private int setenr;
    private int kinosalnr;
    private String billettkode;

    public PlassBillett() {
    }

    public int getRadnr() {
        return radnr;
    }

    public void setRadnr(int radnr) {
        this.radnr = radnr;
    }

    public int getSetenr() {
        return setenr;
    }

    public void setSetenr(int setenr) {
        this.setenr = setenr;
    }

    public int getKinosalnr() {
        return kinosalnr;
    }

    public void setKinosalnr(int kinosalnr) {
        this.kinosalnr = kinosalnr;
    }

    public String getBillettkode() {
        return billettkode;
    }

    public void setBillettkode(String billettkode) {
        this.billettkode = billettkode;
    }
}
