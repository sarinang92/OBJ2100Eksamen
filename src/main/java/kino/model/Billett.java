package kino.model;

/**
 * Representerer en kinobillett.
 */
public class Billett {
    private String billettkode;
    private int visningsnr;
    private boolean erBetalt;

    /**
     * Tom konstruktør for bruk ved f.eks. serialisering eller rammeverk.
     */
    public Billett() {
    }

    /**
     * Oppretter en ny billett.
     * @param billettkode Koden til billetten
     * @param visningsnr Visningsnummeret denne billetten tilhører
     * @param erBetalt Om billetten er betalt eller ikke
     */
    public Billett(String billettkode, int visningsnr, boolean erBetalt) {
        this.billettkode = billettkode;
        this.visningsnr = visningsnr;
        this.erBetalt = erBetalt;
    }

    public String getBillettkode() {
        return billettkode;
    }

    public void setBillettkode(String billettkode) {
        this.billettkode = billettkode;
    }

    public int getVisningsnr() {
        return visningsnr;
    }

    public void setVisningsnr(int visningsnr) {
        this.visningsnr = visningsnr;
    }

    public boolean isErBetalt() {
        return erBetalt;
    }

    public void setErBetalt(boolean erBetalt) {
        this.erBetalt = erBetalt;
    }

    @Override
    public String toString() {
        return "Billett{" +
                "billettkode='" + billettkode + '\'' +
                ", visningsnr=" + visningsnr +
                ", erBetalt=" + erBetalt +
                '}';
    }
}
