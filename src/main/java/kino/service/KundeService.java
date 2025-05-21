package kino.service;

import kino.model.*;
import kino.repository.*;

import java.time.LocalDateTime;

import java.util.*;

public class KundeService {

    private final VisningRepo visningRepo = new VisningRepo();
    private final FilmRepo filmRepo = new FilmRepo();
    private final BillettRepo billettRepo = new BillettRepo();
    private final PlassBillettRepo plassBillettRepo = new PlassBillettRepo();

    private Visning valgtVisning;
    private List<Plass> valgtePlasser = new ArrayList<>();

    public enum Sortering { FILMNAVN, STARTTID }

    public List<Visning> hentTilgjengeligeVisninger(Sortering sortering) {
        List<Visning> visninger = visningRepo.hentAlleVisninger();
        LocalDateTime nå = LocalDateTime.now();

        List<Visning> gyldige = visninger.stream()
                .filter(v -> LocalDateTime.of(v.getDato(), v.getStarttid()).isAfter(nå.plusMinutes(30)))
                .toList();

        if (sortering == Sortering.FILMNAVN) {
            gyldige = gyldige.stream()
                    .sorted(Comparator.comparing(v -> filmRepo.hentFilm(v.getFilmnr()).getFilmnavn()))
                    .toList();
        } else {
            gyldige = gyldige.stream()
                    .sorted(Comparator.comparing(v -> LocalDateTime.of(v.getDato(), v.getStarttid())))
                    .toList();
        }

        return gyldige;
    }

    public void velgVisning(Visning v) {
        this.valgtVisning = v;
        this.valgtePlasser.clear();
    }

    public List<Plass> hentLedigePlasser() {
        return visningRepo.hentLedigePlasser(valgtVisning.getVisningnr(), valgtVisning.getKinosalnr());
    }

    public void togglePlass(Plass plass) {
        if (valgtePlasser.contains(plass)) {
            valgtePlasser.remove(plass);
        } else {
            valgtePlasser.add(plass);
        }
    }

    public void visBekreftelse() {
        System.out.println("----- BEKREFTELSE -----");
        Film film = filmRepo.hentFilm(valgtVisning.getFilmnr());
        System.out.println("Film: " + film.getFilmnavn());
        System.out.println("Tid: " + valgtVisning.getDato() + " kl. " + valgtVisning.getStarttid());
        System.out.println("Plasser: " + valgtePlasser.size());
        System.out.println("Pris: " + getTotalPris() + " kr");
        System.out.println("Billettkode: " + genererBillettkode());
        System.out.println("OBS: Hent og betal billettene senest 30 minutter før visningen, og oppgi billettkoden ved henting.");
        System.out.println("[1] Bekreft   [2] Avbryt");
    }

    public double getTotalPris() {
        return valgtVisning.getPris() * valgtePlasser.size();
    }

    public boolean bekreftBestilling() {
        String kode = genererBillettkode();
        Billett billett = new Billett();
        billett.setBillettkode(kode);
        billett.setVisningsnr(valgtVisning.getVisningnr());
        billett.setErBetalt(false);

        if (!billettRepo.opprettBillett(billett)) return false;

        for (Plass p : valgtePlasser) {
            PlassBillett pb = new PlassBillett();
            pb.setBillettkode(kode);
            pb.setRadnr(p.getRadnr());
            pb.setSetenr(p.getSetenr());
            pb.setKinosalnr(p.getKinosalnr());
            plassBillettRepo.opprettPlassBillett(pb);
        }

        tilbakestill();
        System.out.println("✅ Bestilling bekreftet!");
        return true;
    }

    public void avbryt() {
        tilbakestill();
        System.out.println("❌ Bestilling avbrutt.");
    }

    private String genererBillettkode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    private void tilbakestill() {
        valgtVisning = null;
        valgtePlasser.clear();
    }

    public List<VisningMedPlassinfo> hentVisningerMedPlassinfo(Sortering sortering) {
        List<Visning> visninger = hentTilgjengeligeVisninger(sortering);

        List<VisningMedPlassinfo> medInfo = new ArrayList<>();
        for (Visning v : visninger) {
            int ledige = visningRepo.tellLedigePlasser(v.getVisningnr(), v.getKinosalnr());
            String filmnavn = filmRepo.hentFilm(v.getFilmnr()).getFilmnavn();
            medInfo.add(new VisningMedPlassinfo(v, ledige, filmnavn));
        }

        return medInfo;
    }


}
