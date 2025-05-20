package model;

/**
 * Representerer en kinobillett.
 */
public class Billett {
    private String billettkode;
    private int visningsnr;
    private boolean erBetalt;

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

    public int getVisningsnr() {
        return visningsnr;
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
                "kode='" + billettkode + '\'' +
                ", visningsnr=" + visningsnr +
                ", erBetalt=" + erBetalt +
                '}';
    }
}
