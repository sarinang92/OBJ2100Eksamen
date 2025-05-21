package kino.view;

// Importerer nødvendige klasser for å håndtere brukerinput, dato/tid, og kontrollerlogikk.
import kino.controller.AdminController;
import kino.controller.ReportController; // Importerer den nye ReportControlleren for rapportfunksjonalitet.
import kino.model.Film; // Modellen for filmer, brukes til dataoverføring.
import java.time.LocalDate; // For å håndtere datoer.
import java.time.LocalTime; // For å håndtere tidspunkter.
import java.time.format.DateTimeFormatter; // For å formatere og parse dato/tid-strenger.
import java.time.format.DateTimeParseException; // For å fange opp feil ved parsing av dato/tid.
import java.util.InputMismatchException; // For å fange opp feil ved feil type brukerinput.
import java.util.Scanner; // For å lese input fra konsollen.

// KinoAdminConsoleView-klassen håndterer all interaksjon med brukeren i administratorkonsollen.
public class KinoAdminConsoleView {

    private Scanner scanner; // Objekt for å lese brukerinput.
    private AdminController adminController; // Kontroller for administrative oppgaver (filmer, visninger).
    private ReportController reportController; // Instans av ReportController for statistikkgenerering.

    // Konstruktør for KinoAdminConsoleView. Initialiserer Scanner og alle kontrollere.
    public KinoAdminConsoleView(Scanner scanner) {
        this.scanner = scanner;
        this.adminController = new AdminController(); // Oppretter en ny AdminController.
        this.reportController = new ReportController(); // Initialiserer ReportController.
    }

    // start()-metoden er hovedløkken for administratorkonsollen.
    public void start() {
        while (true) { // Løkke som kjører uendelig til brukeren velger å avslutte.
            printAdminMenu(); // Skriver ut hovedmenyen for administrator.
            int choice = getValidIntegerInput(); // Leser og validerer brukerens valg.
            scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.

            // Bruker en switch-setning for å utføre handling basert på brukerens valg.
            switch (choice) {
                case 1:
                    addFilm(); // Kall for å legge til en film.
                    break;
                case 2:
                    updateFilm(); // Kall for å oppdatere en film.
                    break;
                case 3:
                    deleteFilm(); // Kall for å slette en film.
                    break;
                case 4:
                    scheduleShowing(); // Kall for å sette opp en visning.
                    break;
                case 5:
                    updateShowing(); // Kall for å oppdatere en visning.
                    break;
                case 6:
                    deleteShowing(); // Kall for å slette en visning.
                    break;
                case 7:
                    generateFilmStatistics(); // Kall for å generere filmstatistikk.
                    break;
                case 8:
                    generateCinemaHallStatistics(); // Kall for å generere kinosalstatistikk.
                    break;
                case 0:
                    System.out.println("Avslutter administratorkonsoll."); // Melding ved avslutning.
                    return; // Avslutter start-metoden og dermed programmet.
                default:
                    System.out.println("Ugyldig valg. Vennligst prøv igjen."); // Feilmelding for ugyldig input.
            }
            System.out.println("\nTrykk Enter for å fortsette..."); // Ber brukeren om å trykke Enter for å fortsette.
            scanner.nextLine(); // Venter på at brukeren skal trykke Enter.
        }
    }

    // printAdminMenu()-metoden skriver ut hovedmenyen for administratoren til konsollen.
    private void printAdminMenu() {
        System.out.println("\n--- Kino Administrator Meny ---");
        System.out.println("1. Legg til film");
        System.out.println("2. Oppdater film");
        System.out.println("3. Slett film");
        System.out.println("4. Sett opp visning");
        System.out.println("5. Oppdater visning");
        System.out.println("6. Slett visning");
        System.out.println("7. Generer filmstatistikk");
        System.out.println("8. Generer salgsstatistikk for kinosal");
        System.out.println("0. Avslutt");
        System.out.print("Skriv inn ditt valg: "); // Ber brukeren om å velge et alternativ.
    }

    // addFilm()-metoden tar input fra brukeren for å legge til en ny film.
    private void addFilm() {
        System.out.println("\n--- Legg til film ---");
        System.out.print("Skriv inn filmtittel: ");
        String title = scanner.nextLine(); // Leser filmtittelen.
        adminController.addFilm(title); // Kaller controlleren for å legge til filmen i databasen.
    }

    // updateFilm()-metoden tar input for å oppdatere en eksisterende film.
    private void updateFilm() {
        System.out.println("\n--- Oppdater film ---");
        System.out.print("Skriv inn Film ID for å oppdatere: ");
        int filmId = getValidIntegerInput(); // Leser film-IDen.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        System.out.print("Skriv inn ny filmtittel: ");
        String newTitle = scanner.nextLine(); // Leser den nye filmtittelen.
        adminController.updateFilm(filmId, newTitle); // Kaller controlleren for å oppdatere filmen.
    }

    // deleteFilm()-metoden tar input for å slette en film.
    private void deleteFilm() {
        System.out.println("\n--- Slett film ---");
        System.out.print("Skriv inn Film ID for å slette: ");
        int filmId = getValidIntegerInput(); // Leser film-IDen som skal slettes.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        adminController.deleteFilm(filmId); // Kaller controlleren for å slette filmen.
    }

    // scheduleShowing()-metoden tar input for å sette opp en ny visning.
    private void scheduleShowing() {
        System.out.println("\n--- Sett opp visning ---");
        System.out.print("Skriv inn Film ID: ");
        int filmId = getValidIntegerInput(); // Leser film-ID.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        System.out.print("Skriv inn Kinosal ID: ");
        int cinemaHallId = getValidIntegerInput(); // Leser kinosal-ID.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        LocalDate date = getValidLocalDateInput(); // Leser og validerer dato.
        LocalTime startTime = getValidLocalTimeInput(); // Leser og validerer starttid.
        System.out.print("Skriv inn pris: ");
        double price = getValidDoubleInput(); // Leser og validerer pris.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        adminController.scheduleShowing(filmId, cinemaHallId, date, startTime, price); // Kaller controlleren for å sette opp visningen.
    }

    // updateShowing()-metoden tar input for å oppdatere en eksisterende visning.
    private void updateShowing() {
        System.out.println("\n--- Oppdater visning ---");
        System.out.print("Skriv inn Visnings ID for å oppdatere: ");
        int showingId = getValidIntegerInput(); // Leser visnings-ID som skal oppdateres.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        System.out.print("Skriv inn ny Film ID: ");
        int filmId = getValidIntegerInput(); // Leser ny film-ID.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        System.out.print("Skriv inn ny Kinosal ID: ");
        int cinemaHallId = getValidIntegerInput(); // Leser ny kinosal-ID.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        LocalDate date = getValidLocalDateInput(); // Leser og validerer ny dato.
        LocalTime startTime = getValidLocalTimeInput(); // Leser og validerer ny starttid.
        System.out.print("Skriv inn ny pris: ");
        double price = getValidDoubleInput(); // Leser og validerer ny pris.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        adminController.updateShowing(showingId, filmId, cinemaHallId, date, startTime, price); // Kaller controlleren for å oppdatere visningen.
    }

    // deleteShowing()-metoden tar input for å slette en visning.
    private void deleteShowing() {
        System.out.println("\n--- Slett visning ---");
        System.out.print("Skriv inn Visnings ID for å slette: ");
        int showingId = getValidIntegerInput(); // Leser visnings-ID som skal slettes.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        adminController.deleteShowing(showingId); // Kaller controlleren for å slette visningen.
    }

    // generateFilmStatistics()-metoden genererer statistikk for en spesifikk film.
    private void generateFilmStatistics() {
        System.out.println("\n--- Generer filmstatistikk ---");
        System.out.print("Skriv inn Film ID for statistikk: ");
        int filmId = getValidIntegerInput(); // Leser film-ID for statistikk.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        reportController.generateFilmStatistics(filmId); // Kaller ReportController for å generere filmstatistikk.
    }

    // generateCinemaHallStatistics()-metoden genererer statistikk for en spesifikk kinosal.
    private void generateCinemaHallStatistics() {
        System.out.println("\n--- Generer salgsstatistikk for kinosal ---");
        System.out.print("Skriv inn Kinosal ID for statistikk: ");
        int cinemaHallId = getValidIntegerInput(); // Leser kinosal-ID for statistikk.
        scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter tallinput.
        reportController.generateCinemaHallStatistics(cinemaHallId); // Kaller ReportController for å generere kinosalstatistikk.
    }

    // getValidIntegerInput()-metoden sikrer at brukeren skriver inn et gyldig positivt heltall.
    private int getValidIntegerInput() {
        int input = -1; // Initialiserer input til en ugyldig verdi.
        while (input < 0) { // Løkke som fortsetter til gyldig input er mottatt.
            try {
                input = scanner.nextInt(); // Forsøker å lese et heltall.
                if (input < 0) {
                    System.out.println("Ugyldig input. Vennligst skriv inn et positivt heltall."); // Feilmelding for negativt tall.
                }
            } catch (InputMismatchException e) { // Fanger opp unntak hvis input ikke er et heltall.
                System.out.println("Ugyldig input. Vennligst skriv inn et gyldig heltall."); // Feilmelding for feil type input.
                scanner.nextLine(); // Tømmer den ugyldige inputen fra skanneren for å unngå uendelig løkke.
                input = -1; // Setter input til ugyldig verdi for å fortsette løkken.
            }
        }
        return input; // Returnerer den gyldige inputen.
    }

    // getValidDoubleInput()-metoden sikrer at brukeren skriver inn et gyldig positivt desimaltall.
    private double getValidDoubleInput() {
        double input = -1.0; // Initialiserer input til en ugyldig verdi.
        while (input < 0) { // Løkke som fortsetter til gyldig input er mottatt.
            try {
                input = scanner.nextDouble(); // Forsøker å lese et desimaltall.
                if (input < 0) {
                    System.out.println("Ugyldig input. Vennligst skriv inn et positivt tall."); // Feilmelding for negativt tall.
                }
            } catch (InputMismatchException e) { // Fanger opp unntak hvis input ikke er et desimaltall.
                System.out.println("Ugyldig input. Vennligst skriv inn et gyldig tall."); // Feilmelding for feil type input.
                scanner.nextLine(); // Tømmer den ugyldige inputen fra skanneren.
                input = -1.0; // Setter input til ugyldig verdi for å fortsette løkken.
            }
        }
        return input; // Returnerer den gyldige inputen.
    }

    // getValidLocalDateInput()-metoden sikrer at brukeren skriver inn en gyldig dato i YYYY-MM-DD format.
    private LocalDate getValidLocalDateInput() {
        LocalDate date = null; // Initialiserer dato til null (ugyldig).
        while (date == null) { // Løkke som fortsetter til gyldig dato er mottatt.
            System.out.print("Skriv inn dato (YYYY-MM-DD): ");
            String dateString = scanner.next(); // Leser datoen som en streng.
            scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter strenginput.
            try {
                date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE); // Forsøker å parse strengen til en LocalDate.
            } catch (DateTimeParseException e) { // Fanger opp unntak hvis datoformatet er feil.
                System.out.println("Ugyldig datoformat. Vennligst bruk YYYY-MM-DD."); // Feilmelding for ugyldig format.
            }
        }
        return date; // Returnerer den gyldige datoen.
    }

    // getValidLocalTimeInput()-metoden sikrer at brukeren skriver inn en gyldig tid i HH:MM format.
    private LocalTime getValidLocalTimeInput() {
        LocalTime time = null; // Initialiserer tid til null (ugyldig).
        while (time == null) { // Løkke som fortsetter til gyldig tid er mottatt.
            System.out.print("Skriv inn tid (HH:MM): ");
            String timeString = scanner.next(); // Leser tiden som en streng.
            scanner.nextLine(); // Leser opp det gjenstående linjeskiftet etter strenginput.
            try {
                time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm")); // Forsøker å parse strengen til en LocalTime.
            } catch (DateTimeParseException e) { // Fanger opp unntak hvis tidsformatet er feil.
                System.out.println("Ugyldig tidsformat. Vennligst bruk HH:MM."); // Feilmelding for ugyldig format.
            }
        }
        return time; // Returnerer den gyldige tiden.
    }

    // main-metoden er inngangspunktet for programmet.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Oppretter en Scanner for konsollinput.
        KinoAdminConsoleView view = new KinoAdminConsoleView(scanner); // Oppretter en instans av KinoAdminConsoleView.
        view.start(); // Starter administratorkonsollen.
        scanner.close(); // Lukker scanneren når programmet avsluttes for å frigi ressurser.
    }
}