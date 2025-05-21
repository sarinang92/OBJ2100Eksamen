package kino.controller;

import kino.service.BrukeradministrasjonService;

public class BrukeradministrasjonController {

    //Opprette et BrukeradministrasjonService-objekt
    private final BrukeradministrasjonService service = new BrukeradministrasjonService();

    public boolean opprettPin(String brukernavn, String pin) {
        return service.opprettPinkode(brukernavn, pin);
    }

    public boolean endrePin(String brukernavn, String nyPin) {
        return service.endrePinkode(brukernavn, nyPin);
    }

    public boolean slettPin(String brukernavn) {
        return service.slettPinkode(brukernavn);
    }
}
