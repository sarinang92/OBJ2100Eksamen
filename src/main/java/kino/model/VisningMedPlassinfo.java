package kino.model;

/**
 * Inneholder en visning sammen med antall ledige plasser og filmnavn.
 * Brukes i visningsoversikten i kundegrensesnittet.
 */
public class VisningMedPlassinfo {

    private Visning visning;
    private int antallLedige;
    private String filmnavn;

    public VisningMedPlassinfo(Visning visning, int antallLedige, String filmnavn) {
        this.visning = visning;
        this.antallLedige = antallLedige;
        this.filmnavn = filmnavn;
    }

    public Visning getVisning() {
        return visning;
    }

    public int getAntallLedige() {
        return antallLedige;
    }

    public String getFilmnavn() {
        return filmnavn;
    }

    public void setVisning(Visning visning) {
        this.visning = visning;
    }

    public void setAntallLedige(int antallLedige) {
        this.antallLedige = antallLedige;
    }

    public void setFilmnavn(String filmnavn) {
        this.filmnavn = filmnavn;
    }
}
