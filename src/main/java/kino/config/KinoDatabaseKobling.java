package kino.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Singleton klasse som håndterer koblingen til databasen
public class KinoDatabaseKobling {
    private static volatile KinoDatabaseKobling instance;
    private Connection forbindelse;

    // Database-URL, brukernavn og passord
    private static final String url = "jdbc:postgresql://localhost:5432/kino";
    private static final String brukernavn = "Case";
    private static final String passord = "Esac";

    private KinoDatabaseKobling() {
        connect();
    }

// Etablerer en ny tilkobling til databasen
    private void connect() {
        try {
            this.forbindelse = DriverManager.getConnection(url, brukernavn, passord);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Kunne ikke koble til kino-databasen");
        }
    }
 //Returnerer singleton instansen av DatabaseKobling
    public static KinoDatabaseKobling getInstance() {
        KinoDatabaseKobling resultat = instance;
        if (resultat == null) {
            synchronized (KinoDatabaseKobling.class) {
                resultat = instance;
                if (resultat == null) {
                    instance = resultat = new KinoDatabaseKobling();
                }
            }
        }
        return resultat;
    }

    //Returnerer en aktiv databaseforbindelse
    public Connection getForbindelse() {
        try {
            if (forbindelse == null || forbindelse.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Kunne ikke sjekke tilkoblingsstatusen");
        }
        return forbindelse;
    }
}

