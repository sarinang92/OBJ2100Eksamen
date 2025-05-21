package kino.model;

public class Billett {
    private String billettkode;
    private int visningsnr;
    private boolean erBetalt;

    public Billett() {
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
}