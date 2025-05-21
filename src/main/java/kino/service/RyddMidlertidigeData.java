// Rydding av midlertidige data i kino-databasen (Maksim)
// Funksjon: Sletter alle ubetalte billetter fra databasen
package kino.service;

// Importer nødvendige biblioteker for filbehandling og databaseoperasjoner
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import kino.config.KinoDatabaseKobling;

public class RyddMidlertidigeData {

    // Metode for å slette ubetalte billetter fra databasen
    // Denne metoden fjerner alle billetter der "b_erbetalt" er false
    public void slettUbetalteBilletter() {
        String sql = "DELETE FROM kino.tblbillett WHERE b_erbetalt = false";

        try (
            // Oppretter tilkobling til databasen og forbereder SQL-spørringen
            Connection conn = KinoDatabaseKobling.getInstance().getForbindelse();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // Utfører DELETE-spørringen og lagrer antall slettede rader
            int slettetAntall = stmt.executeUpdate();
            System.out.println("Slettet " + slettetAntall + " ubetalte billetter.");

        } catch (SQLException e) {
            // Feilmelding dersom slettingen feiler
            System.err.println("Klarte ikke å slette ubetalte billetter.");
            e.printStackTrace();
        }
    }
}