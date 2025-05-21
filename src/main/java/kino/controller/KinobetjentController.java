package kino.controller;

import kino.dao.BillettDAO;
import kino.model.Billett;

/**
 * Controller-klasse som håndterer logikken mellom brukergrensesnitt og datatilgang.
 */
public class KinobetjentController {

    private final BillettDAO billettDAO;

    /**
     * Oppretter controller med tilkoblet billett-DAO.
     * @param billettDAO Data Access Object for billetter
     */
    public KinobetjentController(BillettDAO billettDAO) {
        this.billettDAO = billettDAO;
    }

    /**
     * Forsøker å registrere betaling for en billettkode.
     * @param billettkode Kode for billetten
     * @return true hvis betaling ble registrert
     */
    public boolean registrerBetaling(String billettkode) {
        Billett billett = billettDAO.hentBillett(billettkode);
        if (billett != null && !billett.isErBetalt()) {
            return billettDAO.markerSomBetalt(billettkode);
        }
        return false;
    }

    /**
     * Sletter alle ubetalte billetter og logger dem til fil.
     * @return antall slettede billetter
     */
    public int slettAlleUbetalteOgLogg() {
        return billettDAO.slettAlleUbetalteOgLogg();
    }
}
