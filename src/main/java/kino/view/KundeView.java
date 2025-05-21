package kino.view;

import kino.model.*;

import java.util.*;

public class KundeView {

    private final Scanner scanner = new Scanner(System.in);

    // vise filmnavn, pris og ledige plasser
    public Visning visValgAvVisning(List<VisningMedPlassinfo> visninger) {
        if (visninger == null || visninger.isEmpty()) {
            System.out.println("⚠️  Ingen tilgjengelige visninger.");
            return null;
        }

        System.out.println("\n=== Kommende visninger ===");
        for (int i = 0; i < visninger.size(); i++) {
            Visning v = visninger.get(i).getVisning();
            int ledige = visninger.get(i).getAntallLedige();
            String filmnavn = visninger.get(i).getFilmnavn();
            System.out.printf("[%d] %s – %s kl %s | %.0f kr | %d ledige plasser\n",
                    i + 1, filmnavn, v.getDato(), v.getStarttid(), v.getPris(), ledige);
        }

        System.out.print("Velg visning: ");
        int valg = scanner.nextInt() - 1;

        if (valg < 0 || valg >= visninger.size()) {
            System.out.println("Ugyldig valg.");
            return null;
        }

        return visninger.get(valg).getVisning();
    }


    public List<Plass> velgPlasser(List<Plass> ledige) {
        List<Plass> valgte = new ArrayList<>();

        if (ledige == null || ledige.isEmpty()) {
            System.out.println("⚠️  Ingen ledige plasser for valgt visning.");
            return valgte;
        }

        System.out.println("\n=== Tilgjengelige plasser ===");
        for (int i = 0; i < ledige.size(); i++) {
            Plass p = ledige.get(i);
            System.out.printf("[%d] Rad %d Sete %d\n", i + 1, p.getRadnr(), p.getSetenr());
        }

        System.out.print("Velg seter (kommaseparert): ");
        scanner.nextLine(); // flush
        String input = scanner.nextLine();

        for (String s : input.split(",")) {
            try {
                int index = Integer.parseInt(s.trim()) - 1;
                if (index >= 0 && index < ledige.size()) {
                    valgte.add(ledige.get(index));
                }
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig tall: " + s);
            }
        }

        return valgte;
    }

    public int hentBrukerValg() {
        System.out.print("Skriv 1 for å bekrefte, 2 for å avbryte: ");
        return scanner.nextInt();
    }
}
