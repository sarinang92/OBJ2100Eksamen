package kino.service;

// Importer nødvendige biblioteker for filbehandling og databaseoperasjoner
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kino.config.KinoDatabaseKobling;

public class EksporterSlettelogg {

    // Metode for å eksportere slettelogg til en spesifisert sti
    public void eksporterLogg(String eksportSti) {
        File kildeFil = new File("systemvedlikehold_ressurser/slettinger.dat");  // Filen som skal kopieres
        File eksportFil = new File(eksportSti); // Målfilen for eksporten

        try {
            // Sørg for at mappen til eksportfilen eksisterer
            Files.createDirectories(eksportFil.getParentFile().toPath());

            // Kopier filen og overskriv hvis den eksisterer
            Files.copy(kildeFil.toPath(), eksportFil.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Logg eksportert til: " + eksportFil.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Klarte ikke å eksportere loggen.");
            e.printStackTrace();
        }
    }

    // Metode for å hente antall ubetalte bestillinger fra databasen
    public int hentAntallAktiveBestillinger() {

        // SQL-spørring som teller antall billetter der "er betalt" = false
        String sql = "SELECT COUNT(*) FROM kino.tblbillett WHERE b_erbetalt = false";

        try (
        // Oppretter tilkobling til databasen
        Connection conn = KinoDatabaseKobling.getInstance().getForbindelse();
        // Forbereder SQL-spørringen
        PreparedStatement stmt = conn.prepareStatement(sql);
        // Utfører spørringen og lagrer resultatet
        ResultSet rs = stmt.executeQuery()
        ) {
            // Hvis det finnes et resultat, les tallet og returner det
            if (rs.next()) {
                int antall = rs.getInt(1); // Henter tallet fra første kolonne i resultatet
                System.out.println("Antall aktive bestillinger: " + antall);
                return antall;
            }

        } catch (SQLException e) {
            // Feilmelding hvis noe går galt med databasespørringen
            System.err.println("Feil under henting av aktive bestillinger.");
            e.printStackTrace();
        }

        return -1; // Returnerer -1 dersom det oppstår en feil
    }
}