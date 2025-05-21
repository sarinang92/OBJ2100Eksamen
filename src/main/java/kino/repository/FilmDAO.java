package kino.repository;

import kino.model.Film;
import kino.config.KinoDatabaseKobling; // Beholder KinoDatabaseKobling som spesifisert
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// FilmDAO (Data Access Object) klassen håndterer all databaseinteraksjon
// for 'Film' objekter. Den isolerer databaselogikk fra forretningslogikk.
public class FilmDAO {

    // Oppretter en instans av KinoDatabaseKobling for å hente databaseforbindelser.
    // Dette objektet er en Singleton, så 'getInstance()' returnerer alltid den samme instansen.
    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Metode for å hente en spesifikk film fra databasen basert på dens ID.
    // Returnerer Film-objektet eller null hvis ikke funnet.
    public Film getFilm(int filmId) {
        Film film = null;
        String sql = "SELECT f_filmnavn FROM kino.tblfilm WHERE f_filmnr = ?";
        try (Connection conn = kinoDB.getForbindelse();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                film = new Film(filmId, rs.getString("f_filmnavn"));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av film med ID " + filmId + ": " + e.getMessage());
        }
        return film;
    }

    // Metode for å hente en liste over alle filmer fra databasen.
    // Returnerer en liste av Film-objekter. Listen vil være tom hvis ingen filmer er funnet.
    public List<Film> getAllFilms() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT f_filmnr, f_filmnavn FROM kino.tblfilm";
        try (Connection conn = kinoDB.getForbindelse();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                films.add(new Film(rs.getInt("f_filmnr"), rs.getString("f_filmnavn")));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av alle filmer: " + e.getMessage());
        }
        return films;
    }

    // Metode for å opprette en ny film i databasen.
    // Returnerer det nylig opprettede Film-objektet med den genererte ID-en.
    // Returnerer null ved feil.
    public Film createFilm(String filmName) {
        Film film = new Film();
        film.setF_filmnavn(filmName);
        String sql = "INSERT INTO kino.tblfilm (f_filmnavn) VALUES (?)";
        try (Connection conn = kinoDB.getForbindelse();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, film.getF_filmnavn());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                film.setF_filmnr(generatedKeys.getInt(1));
                System.out.println("Film '" + filmName + "' opprettet vellykket med ID: " + film.getF_filmnr());
            } else {
                System.err.println("Klarte ikke å hente generert film ID for film '" + filmName + "'.");
                return null; // Returnerer null hvis ID ikke ble generert
            }
            return film;

        } catch (SQLException e) {
            System.err.println("Feil ved oppretting av film '" + filmName + "': " + e.getMessage());
            return null;
        }
    }

    // Metode for å oppdatere en eksisterende film i databasen.
    public void updateFilm(Film film) {
        String sql = "UPDATE kino.tblfilm SET f_filmnavn = ? WHERE f_filmnr = ?";
        try (Connection conn = kinoDB.getForbindelse();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, film.getF_filmnavn());
            pstmt.setInt(2, film.getF_filmnr());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Film med ID " + film.getF_filmnr() + " oppdatert vellykket til navn: " + film.getF_filmnavn());
            } else {
                System.out.println("Ingen film funnet med ID " + film.getF_filmnr() + " å oppdatere.");
            }
        } catch (SQLException e) {
            System.err.println("Feil ved oppdatering av film med ID " + film.getF_filmnr() + ": " + e.getMessage());
        }
    }

    // Sletter en film basert på dens ID.
    // VIKTIG: Denne metoden sletter først alle assosierte visninger (ved hjelp av ShowingDAO)
    // for å forhindre brudd på fremmednøkkelbegrensningen.
    public void deleteFilm(int filmId) {
        // Trinn 1: Slett alle assosierte visninger (tblvisning poster) for denne filmen først.
        ShowingDAO showingDAO = new ShowingDAO(); // Oppretter en instans av ShowingDAO
        
        // Kall deleteShowingsForFilm. Denne metoden håndterer sine egne SQLException internt
        // basert på den nye strukturen din for ShowingDAO.
        showingDAO.deleteShowingsForFilm(filmId); 

        // Trinn 2: Slett nå selve filmen fra tblfilm.
        String sql = "DELETE FROM kino.tblfilm WHERE f_filmnr = ?";
        try (Connection conn = kinoDB.getForbindelse();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Film med ID " + filmId + " slettet vellykket.");
            } else {
                System.out.println("Ingen film funnet med ID " + filmId + " å slette.");
            }
        } catch (SQLException e) {
            System.err.println("Feil ved sletting av film med ID " + filmId + ": " + e.getMessage());
        }
    }
}