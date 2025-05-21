package kino.view;

import kino.config.KinoDatabaseKobling;
import kino.dao.BillettDAO;
import kino.controller.KinobetjentController;
import kino.view.KinobetjentView;
import kino.view.KinoAdminConsoleView;
import java.sql.Connection;

import kino.controller.KundeController;
import kino.repository.LoginRepo;
import kino.model.Login;

import java.util.Scanner;

/**
 * MainProgramView fungerer som hovedmenyen for hele kinoapplikasjonen.
 * Den håndterer navigasjon til forskjellige roller: kunde, kinobetjent, planlegger og administrator.
 */
public class MainProgramView {

    private final Scanner scanner = new Scanner(System.in);
//Starter hovedmenyen og håndterer brukerens rollevalg.
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
//Starter kundemodulen
    private void kundeMeny() {
        System.out.println("Starter kundemodul...\n");
        KundeController kundeController = new KundeController();
        kundeController.start();  // Kjør kundeflyt
    }
//Håndterer innlogging og oppstart av kinobetjentmodulen.
//Kun brukere med rolle "betjent" eller "admin" har tilgang.
    private void betjentMeny() {
        System.out.println("Logg inn som kinobetjent eller administrator:");

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

        String rolle = login.getRole().toLowerCase();
        if (!rolle.equals("betjent") && !rolle.equals("admin")) {
            System.out.println("Tilgang nektet. Kun betjent eller admin har tilgang.");
            return;
        }

        boolean ok = repo.verifiserPinkode(brukernavn, pinkode);
        if (!ok) {
            System.out.println("Feil PIN-kode. Tilgang nektet.");
            return;
        }

        System.out.println("Innlogging godkjent. Velkommen, " + rolle + ".");

        try {
            Connection conn = KinoDatabaseKobling.getInstance().getForbindelse();
            BillettDAO billettDAO = new BillettDAO(conn);
            KinobetjentController controller = new KinobetjentController(billettDAO);
            KinobetjentView kinobetjentView = new KinobetjentView(controller);
            kinobetjentView.visMeny();
        } catch (Exception e) {
            System.out.println("Feil ved oppstart av kinobetjentmodulen: " + e.getMessage());
        }
    }


     //Håndterer innlogging og tilgang til planleggermodulen.
     //Kun brukere med rolle "planlegger" eller "admin" har tilgang.


    private void planleggerMeny() {
        System.out.println("Logg inn som administrator eller planlegger for å få tilgang til planleggermodulen:");

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

        String rolle = login.getRole().toLowerCase();
        if (!rolle.equals("admin") && !rolle.equals("planlegger")) {
            System.out.println("Tilgang nektet. Kun administrator eller planlegger har tilgang til planleggermodulen.");
            return;
        }

        boolean ok = repo.verifiserPinkode(brukernavn, pinkode);
        if (!ok) {
            System.out.println("Feil PIN-kode. Tilgang nektet.");
            return;
        }

        System.out.println("Innlogging godkjent. Velkommen til planleggermodulen, " + brukernavn + ".");

        // Start KinoAdminConsoleView
        KinoAdminConsoleView adminView = new KinoAdminConsoleView(scanner);
        adminView.start();
    }

    //Håndterer innlogging og tilgang til systemadministrasjon.
      //Kun brukere med rollen "admin" har tilgang.

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
