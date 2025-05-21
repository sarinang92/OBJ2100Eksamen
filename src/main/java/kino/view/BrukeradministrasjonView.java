package kino.view;

import kino.controller.BrukeradministrasjonController;

import java.util.Scanner;

/**
 * BrukeradministrasjonView håndterer visning og interaksjon med brukeren
 * for operasjoner relatert til PIN-koder: opprette, endre og slette.
 */
public class BrukeradministrasjonView {

    private final BrukeradministrasjonController controller = new BrukeradministrasjonController();
    private final Scanner scanner = new Scanner(System.in);

    public void visMeny() {
        while (true) {
            System.out.println("\n--- Brukeradministrasjon ---");
            System.out.println("1. Opprett PIN-kode");
            System.out.println("2. Endre PIN-kode");
            System.out.println("3. Slett PIN-kode");
            System.out.println("4. Avslutt");
            System.out.print("Velg et alternativ: ");

            String valg = scanner.nextLine();

            switch (valg) {
                case "1" -> opprettPin();
                case "2" -> endrePin();
                case "3" -> slettPin();
                case "4" -> {
                    System.out.println("Avslutter.");
                    return;
                }
                default -> System.out.println("Ugyldig valg.");
            }
        }
    }

    private void opprettPin() {
        System.out.print("Brukernavn: ");
        String brukernavn = scanner.nextLine();

        System.out.print("Ny PIN-kode (4 siffer): ");
        String pin = scanner.nextLine();

        boolean suksess = controller.opprettPin(brukernavn, pin);
        System.out.println(suksess ? "PIN-kode opprettet." : "Kunne ikke opprette PIN-kode.");
    }

    private void endrePin() {
        System.out.print("Brukernavn: ");
        String brukernavn = scanner.nextLine();

        System.out.print("Ny PIN-kode (4 siffer): ");
        String pin = scanner.nextLine();

        boolean suksess = controller.endrePin(brukernavn, pin);
        System.out.println(suksess ? "PIN-kode endret." : "Kunne ikke endre PIN-kode.");
    }

    private void slettPin() {
        System.out.print("Brukernavn: ");
        String brukernavn = scanner.nextLine();

        boolean suksess = controller.slettPin(brukernavn);
        System.out.println(suksess ? "PIN-kode slettet." : "Kunne ikke slette PIN-kode.");
    }
}
