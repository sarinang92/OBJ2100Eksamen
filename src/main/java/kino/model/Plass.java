package kino.model;

public class Plass {
    private final int radnr;
    private final int setenr;
    private final int kinosalnr;

    public Plass(int radnr, int setenr, int kinosalnr) {
        this.radnr = radnr;
        this.setenr = setenr;
        this.kinosalnr = kinosalnr;
    }

    public int getRadnr() {
        return radnr;
    }

    public int getSetenr() {
        return setenr;
    }

    public int getKinosalnr() {
        return kinosalnr;
    }
}
