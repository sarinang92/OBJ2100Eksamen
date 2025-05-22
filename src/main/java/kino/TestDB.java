// OBS: Kun for testing av systemvedlikehold funksjonene

package kino;

import kino.config.KinoDatabaseKobling;
// import kino.service.EksporterSlettelogg;

// Import DatabaseBackupService hvis det finnes i kino.service-pakken
// import kino.service.DatabaseBackupService;

// Import RyddMidlertidigeData hvis det finnes i kino.service-pakken
// import kino.service.RyddMidlertidigeData;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) {

        // Test tilkobling til databasen
        try {
            Connection conn = KinoDatabaseKobling.getInstance().getForbindelse();
            if (conn != null && !conn.isClosed()) {
                System.out.println("Tilkobling til databasen var vellykket");
            } else {
                System.out.println("Klarte ikke å koble til databasen.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Test eksport av sletteloggen (kopierer slettinger.dat til backup-mappe)
        // EksporterSlettelogg vedlikehold = new EksporterSlettelogg();
        // vedlikehold.eksporterLogg("systemvedlikehold_ressurser/exported_logs/slettinger_backup.dat");
        // vedlikehold.hentAntallAktiveBestillinger();

        // Test sikkerhetskopi av databasen (bruker pg_dump)
        // DatabaseBackupService backupService = new DatabaseBackupService();
        // backupService.backupDatabase("kino_backup.dump");

        // Test sletting av ubetalte billetter fra databasen
        // RyddMidlertidigeData rydder = new RyddMidlertidigeData(); 
        // rydder.slettUbetalteBilletter();
    }
}