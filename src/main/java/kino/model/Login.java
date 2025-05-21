package kino.model;

public class Login {

    private final String brukernavn;
    private final String role;
    private String pinkode;

    // Constructor
    public Login(String brukernavn, String role) {
        this.brukernavn = brukernavn;
        this.role = role;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public String getRole() {
        return role;
    }

    public String getPinkode() {
        return pinkode;
    }

    public void setPinkode(String pinkode) {
        this.pinkode = pinkode;
    }
}
