package kino.controller;

import kino.model.*;
import kino.service.KundeService;
import kino.view.KundeView;

import java.util.List;

public class KundeController {
    //Oppretter service- og view-objekter
    private final KundeService service = new KundeService();
    private final KundeView view = new KundeView();

    //kundemeny: velge visning, velge plasser og bekrefte bestilling.
    public void start() {
        //Hent alle visninger med tilgjengelighetsinfo, sortert på filmnavn
        List<VisningMedPlassinfo> visninger = service.hentVisningerMedPlassinfo(KundeService.Sortering.FILMNAVN);
        // velge en visning
        Visning valgt = view.visValgAvVisning(visninger);

        if (valgt == null) {
            System.out.println("Tilbake til hovedmeny.");
            return;
        }
//hvilken visning som er valgt
        service.velgVisning(valgt);

//Hent ledige plasser for valgt visning
        List<Plass> ledige = service.hentLedigePlasser();

        //velge en eller flere plasser
        List<Plass> valgte = view.velgPlasser(ledige);

        if (valgte.isEmpty()) {
            System.out.println("Ingen plasser valgt. Avbryter bestilling.");
            return;
        }

        for (Plass p : valgte) {
            service.togglePlass(p);
        }
        //Vis oppsummering og spør om brukeren vil bekrefte
        service.visBekreftelse();
        int bekreft = view.hentBrukerValg();
        if (bekreft == 1) {
            service.bekreftBestilling();
        } else {
            service.avbryt();
        }
    }
}

