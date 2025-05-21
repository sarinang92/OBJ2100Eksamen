package kino.repository;

import kino.config.KinoDatabaseKobling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TicketDAO håndterer alle databaseoperasjoner relatert til billetter,
// som å opprette og slette billetter.
public class TicketDAO {

    // Finner og sletter alle ubetalte billetter for en gitt visning.
    // Dette er en transaksjonsbasert operasjon for å sikre dataintegritet.
    // Metoden returnerer antall ubetalte billetter som ble slettet.
    // Den returnerer 0 ved feil eller hvis ingen billetter ble funnet å slette.
    public int deleteUnpaidTicketsForShowing(int showingId) {
        int deletedCount = 0;
        Connection conn = null; // Tilkoblingen må deklareres utenfor try-with-resources for transaksjonskontroll

        try {
            // Henter databaseforbindelsen og starter transaksjonen
            conn = KinoDatabaseKobling.getInstance().getForbindelse();
            conn.setAutoCommit(false); // Starter transaksjonen (deaktiverer auto-commit)

            // Trinn 1: Teller antall ubetalte billetter for den spesifikke visningen
            String countSql = "SELECT COUNT(*) FROM kino.tblbillett WHERE b_visningsnr = ? AND b_erbetalt = FALSE";
            try (PreparedStatement countPstmt = conn.prepareStatement(countSql)) {
                countPstmt.setInt(1, showingId);
                try (ResultSet rs = countPstmt.executeQuery()) {
                    if (rs.next()) {
                        deletedCount = rs.getInt(1); // Henter antallet
                    }
                }
            }

            if (deletedCount > 0) {
                // Trinn 2: Sletter de ubetalte billettene for visningen
                String deleteSql = "DELETE FROM kino.tblbillett WHERE b_visningsnr = ? AND b_erbetalt = FALSE";
                try (PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {
                    deletePstmt.setInt(1, showingId);
                    int rowsAffected = deletePstmt.executeUpdate(); // Utfører sletteoperasjonen

                    // Sjekker om antallet slettede rader stemmer overens med det forventede antallet
                    if (rowsAffected == deletedCount) {
                        conn.commit(); // Bekrefter transaksjonen hvis alt er i orden
                        System.out.println("Vellykket slettet " + rowsAffected + " ubetalte billetter for visning ID " + showingId);
                    } else {
                        conn.rollback(); // Tilbakeruller transaksjonen hvis det er uoverensstemmelse
                        System.err.println("Uoverensstemmelse: Forsøkte å slette " + deletedCount + " billetter, men påvirket " + rowsAffected + " rader. Tilbakeruller.");
                        deletedCount = 0; // Indikerer feil i sletteprosessen
                    }
                }
            } else {
                System.out.println("Ingen ubetalte billetter funnet for visning ID " + showingId + " å slette.");
            }

        } catch (SQLException e) {
            // Håndterer SQL-feil og tilbakeruller transaksjonen
            try {
                if (conn != null) {
                    conn.rollback(); // Tilbakeruller ved SQL-feil
                }
            } catch (SQLException rbEx) {
                System.err.println("Feil ved tilbakerulling etter SQL-feil: " + rbEx.getMessage());
            }
            System.err.println("Feil ved sletting av ubetalte billetter for visning ID " + showingId + ": " + e.getMessage());
            deletedCount = 0; // Indikerer feil
        } finally {
            // Lukker ressurser i en finally-blokk for å sikre at de alltid blir lukket
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Tilbakestiller auto-commit til true
                    conn.close(); // Lukker databasetilkoblingen
                }
            } catch (SQLException e) {
                System.err.println("Feil ved lukking av tilkoblingsressurser: " + e.getMessage());
            }
        }
        return deletedCount;
    }

    // Oppretter en ny billett i databasen for en spesifikk visning, rad og sete.
    // Billetten kan opprettes som betalt eller ubetalt basert på 'paid' parameteret.
    public void createTicket(int showingId, int radnr, int setenr, boolean paid) {
        String sql = "INSERT INTO kino.tblbillett (b_visningsnr, b_radnr, b_setenr, b_erbetalt, b_billettkode) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = KinoDatabaseKobling.getInstance().getForbindelse();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showingId);
            pstmt.setInt(2, radnr);
            pstmt.setInt(3, setenr);
            pstmt.setBoolean(4, paid);

            // Genererer en enkel unik billettkode. I en ekte applikasjon kan du bruke UUID eller en mer robust strategi.
            String ticketCode = "TICKET_" + showingId + "_" + radnr + "_" + setenr + "_" + System.currentTimeMillis();
            pstmt.setString(5, ticketCode);

            pstmt.executeUpdate();
            System.out.println("Billett opprettet med kode " + ticketCode + " for visning " + showingId + " på rad " + radnr + ", sete " + setenr + " (Betalt: " + paid + ")");
        } catch (SQLException e) {
            System.err.println("Feil ved oppretting av billett for visning ID " + showingId + ", rad " + radnr + ", sete " + setenr + ": " + e.getMessage());
        }
    }
}