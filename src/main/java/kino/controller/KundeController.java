package kino.controller;

import kino.model.*;
import kino.service.KundeService;
import kino.view.KundeView;

import java.util.List;

public class KundeController {
    private final KundeService service = new KundeService();
    private final KundeView view = new KundeView();

    public void start() {
        List<VisningMedPlassinfo> visninger = service.hentVisningerMedPlassinfo(KundeService.Sortering.FILMNAVN);
        Visning valgt = view.visValgAvVisning(visninger);

        if (valgt == null) {
            System.out.println("Tilbake til hovedmeny.");
            return;
        }

        service.velgVisning(valgt);

        List<Plass> ledige = service.hentLedigePlasser();
        List<Plass> valgte = view.velgPlasser(ledige);

        if (valgte.isEmpty()) {
            System.out.println("Ingen plasser valgt. Avbryter bestilling.");
            return;
        }

        for (Plass p : valgte) {
            service.togglePlass(p);
        }

        service.visBekreftelse();
        int bekreft = view.hentBrukerValg();
        if (bekreft == 1) {
            service.bekreftBestilling();
        } else {
            service.avbryt();
        }
    }
}
