package kino.model;

public class Login {

    private String brukernavn;
    private int pinkode;
    private boolean erPlanlegger;

    public String getBrukernavn() {
        return brukernavn;
    }

    public int getPinkode() {
        return pinkode;
    }

    public void setPinkode(int pinkode) {
        this.pinkode = pinkode;
    }

    public boolean isErPlanlegger() {
        return erPlanlegger;
    }

}
