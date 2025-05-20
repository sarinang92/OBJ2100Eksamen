package kino.service;

import kino.model.Login;
import kino.repository.LoginRepo;

public class BrukeradministrasjonService {

    private final LoginRepo loginRepo = new LoginRepo();

    // Opprett PIN-kode
    public boolean opprettPinkode(String brukernavn, String pinkode) {
        if (!isValidPin(pinkode)) {
            System.out.println("Ugyldig PIN. Må være nøyaktig 4 siffer.");
            return false;
        }

        Login login = new Login(brukernavn, null); // role not used here
        login.setPinkode(pinkode);
        return loginRepo.oppdaterEllerSettPinkode(login);
    }

    // Endre PIN-kode
    public boolean endrePinkode(String brukernavn, String nyPinkode) {
        return opprettPinkode(brukernavn, nyPinkode);
    }

    // Slett PIN-kode
    public boolean slettPinkode(String brukernavn) {
        return loginRepo.slettPinkode(brukernavn);
    }

    // Validerer at PIN er eksakt 4 siffer
    private boolean isValidPin(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }
}
