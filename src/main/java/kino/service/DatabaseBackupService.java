package kino.service;

// Importer nødvendige biblioteker og klasser for filbehandling og prosesshåndtering
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseBackupService {

    // Databasekonfigurasjon
    private static final String DB_NAME = "kino";
    private static final String USERNAME = "Case";
    private static final String PASSWORD = "Esac";

    // Mappen hvor sikkerhetskopien skal lagres (kan overskrives av config-fil)
    private static final String BACKUP_FOLDER = "db_backups";

    // Metode for å utføre sikkerhetskopiering
    public void backupDatabase(String backupFileName) {
        try {
            // Last inn config.properties
            Properties props = new Properties();
            props.load(new FileInputStream("systemvedlikehold_ressurser/config.properties"));

            // Hent pg_dump-pathen fra config-fil
            String pgDumpPath = props.getProperty("pg_dump_path");
            if (pgDumpPath == null || pgDumpPath.isBlank()) {
                System.err.println("'pg_dump_path' mangler i config.properties");
                return;
            }

            // Sørg for at backup-mappen finnes
            File folder = new File(BACKUP_FOLDER);
            if (!folder.exists()) folder.mkdirs();

            // Lag full sti for sikkerhetskopifilen
            String backupFilePath = BACKUP_FOLDER + File.separator + backupFileName;

            // Bygg kommandoen for pg_dump
            ProcessBuilder pb = new ProcessBuilder(
                "cmd.exe", "/c",
                "\"" + pgDumpPath + "\" -U " + USERNAME + " -F c -f \"" + backupFilePath + "\" " + DB_NAME
            );

            // Send passordet som miljøvariabel
            pb.environment().put("PGPASSWORD", PASSWORD);
            pb.inheritIO(); // Vis output og feil i konsollen

            // Start prosessen
            Process process = pb.start();
            int exitCode = process.waitFor();

            // Sjekk om backupen var vellykket
            if (exitCode == 0) {
                System.out.println("Backup fullfort: " + backupFilePath);
            } else {
                System.err.println("Backup feilet med kode " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Backup feilet med unntak.");
            e.printStackTrace();
        }
    }
}