package kino.view;

import kino.controller.KinobetjentController;
import java.util.Scanner;

/**
 * Konsollbasert brukergrensesnitt for kinobetjent.
 */
public class KinobetjentView {
    private final KinobetjentController controller;
    private final Scanner scanner;

    /**
     * Oppretter visning med tilkoblet controller.
     * @param controller Kinobetjent-controller
     */
    public KinobetjentView(KinobetjentController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Viser hovedmenyen og leser brukerens valg.
     */
    public void visMeny() {
        while (true) {
            System.out.println("\n--- Kinobetjentmeny ---");
            System.out.println("1. Registrer betaling");
            System.out.println("2. Slett alle ubetalte og logg");
            System.out.println("0. Avslutt");
            System.out.print("Velg: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.print("Skriv inn billettkode: ");
                    String kode = scanner.nextLine();
                    boolean registrert = controller.registrerBetaling(kode);
                    if (registrert) {
                        System.out.println("Betaling registrert.");
                    } else {
                        System.out.println("Fant ikke billetten eller den er allerede betalt.");
                    }
                    break;

                case "2":
                    int slettet = controller.slettAlleUbetalteOgLogg();
                    System.out.println("Slettet " + slettet + " ubetalte billetter. Logget til slettinger.dat.");
                    break;

                case "0":
                    System.out.println("Avslutter programmet.");
                    return;

                default:
                    System.out.println("Ugyldig valg, prøv igjen.");
            }
        }
    }
}
