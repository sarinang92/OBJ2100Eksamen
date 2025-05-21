package kino.view;

import kino.controller.KundeController;
import kino.repository.LoginRepo;
import kino.model.Login;

import java.util.Scanner;

public class MainProgramView {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n=== Hovedmeny ===");
            System.out.println("1. Kunde");
            System.out.println("2. Kinobetjent");
            System.out.println("3. Planlegger");
            System.out.println("4. Systemadministrasjon");
            System.out.println("5. Avslutt");
            System.out.print("Velg en rolle: ");

            String valg = scanner.nextLine();

            switch (valg) {
                case "1" -> kundeMeny();
                case "2" -> betjentMeny();
                case "3" -> planleggerMeny();
                case "4" -> systemadministrasjon();
                case "5" -> {
                    System.out.println("Avslutter programmet.");
                    return;
                }
                default -> System.out.println("Ugyldig valg. Prøv igjen.");
            }
        }
    }

    private void kundeMeny() {
        System.out.println("Starter kundemodul...\n");
        KundeController kundeController = new KundeController();
        kundeController.start();  // Kjør kundeflyt
    }

    private void betjentMeny() {
        System.out.println("Starter kinobetjentmodul...");
        // TODO
    }

    private void planleggerMeny() {
        System.out.println("Starter planleggermodul...");
        // TODO
    }

    private void systemadministrasjon() {
        System.out.println("Logg inn som administrator:");

        System.out.print("Brukernavn: ");
        String brukernavn = scanner.nextLine();

        System.out.print("PIN-kode: ");
        String pinkode = scanner.nextLine();

        LoginRepo repo = new LoginRepo();
        Login login = repo.finnEnLogin(brukernavn);

        if (login == null) {
            System.out.println("Bruker ikke funnet.");
            return;
        }

        if (!"admin".equalsIgnoreCase(login.getRole())) {
            System.out.println("Tilgang nektet. Kun administratorer har tilgang.");
            return;
        }

        boolean ok = repo.verifiserPinkode(brukernavn, pinkode);
        if (!ok) {
            System.out.println("Feil PIN-kode. Tilgang nektet.");
            return;
        }

        System.out.println("Innlogging godkjent. Velkommen, admin.");
        BrukeradministrasjonView view = new BrukeradministrasjonView();
        view.visMeny();
    }
}
