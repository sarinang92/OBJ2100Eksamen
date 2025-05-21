package kino.repository;

import kino.model.Showing;
import kino.config.KinoDatabaseKobling; // Endret til KinoDatabaseKobling som avtalt

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// ShowingDAO (Data Access Object) klassen håndterer all databaseinteraksjon for 'Showing' (visning) objekter.
// Den isolerer databaselogikk fra forretningslogikk.
public class ShowingDAO {

    // Oppretter en instans av KinoDatabaseKobling for å hente databaseforbindelser.
    // Dette objektet er en enhet (Singleton), så 'getInstance()' returnerer alltid den samme instansen.
    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Henter en spesifikk visning fra databasen basert på ID-en.
    // Returnerer et Showing-objekt, eller null hvis visningen ikke ble funnet.
    public Showing getShowing(int showingId) {
        Showing showing = null;
        String sql = "SELECT v_filmnr, v_kinosalnr, v_dato, v_starttid, v_pris " +
                     "FROM kino.tblvisning WHERE v_visningnr = ?";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                showing = new Showing(showingId, rs.getInt("v_filmnr"), rs.getInt("v_kinosalnr"),
                                      rs.getDate("v_dato").toLocalDate(), rs.getTime("v_starttid").toLocalTime(),
                                      rs.getDouble("v_pris"));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av visning med ID " + showingId + ": " + e.getMessage());
        }
        return showing;
    }

    // Henter en liste over alle visninger fra databasen.
    // Returnerer en liste av Showing-objekter. Listen vil være tom hvis ingen visninger er funnet.
    public List<Showing> getAllShowings() {
        List<Showing> showings = new ArrayList<>();
        String sql = "SELECT v_visningnr, v_filmnr, v_kinosalnr, v_dato, v_starttid, v_pris FROM kino.tblvisning";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                showings.add(new Showing(rs.getInt("v_visningnr"), rs.getInt("v_filmnr"),
                                         rs.getInt("v_kinosalnr"), rs.getDate("v_dato").toLocalDate(),
                                         rs.getTime("v_starttid").toLocalTime(), rs.getDouble("v_pris")));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av alle visninger: " + e.getMessage());
        }
        return showings;
    }

    // Oppretter en ny visning i databasen basert på et gitt Showing-objekt.
    public void createShowing(Showing showing) {
        String sql = "INSERT INTO kino.tblvisning (v_filmnr, v_kinosalnr, v_dato, v_starttid, v_pris) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showing.getFilmId());
            pstmt.setInt(2, showing.getCinemaHallId());
            pstmt.setDate(3, java.sql.Date.valueOf(showing.getDate()));
            pstmt.setTime(4, java.sql.Time.valueOf(showing.getStartTime()));
            pstmt.setDouble(5, showing.getPrice());
            pstmt.executeUpdate();
            System.out.println("Visning opprettet vellykket for film ID: " + showing.getFilmId() + ", sal ID: " + showing.getCinemaHallId() + " den " + showing.getDate());
        } catch (SQLException e) {
            System.err.println("Feil ved oppretting av visning: " + e.getMessage());
        }
    }

    // Oppdaterer en eksisterende visning i databasen basert på et Showing-objekt.
    public void updateShowing(Showing showing) {
        String sql = "UPDATE kino.tblvisning SET v_filmnr = ?, v_kinosalnr = ?, v_dato = ?, v_starttid = ?, v_pris = ? WHERE v_visningnr = ?";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showing.getFilmId());
            pstmt.setInt(2, showing.getCinemaHallId());
            pstmt.setDate(3, java.sql.Date.valueOf(showing.getDate()));
            pstmt.setTime(4, java.sql.Time.valueOf(showing.getStartTime()));
            pstmt.setDouble(5, showing.getPrice());
            pstmt.setInt(6, showing.getShowingId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Visning med ID " + showing.getShowingId() + " oppdatert vellykket.");
            } else {
                System.out.println("Ingen visning funnet med ID " + showing.getShowingId() + " å oppdatere.");
            }
        } catch (SQLException e) {
            System.err.println("Feil ved oppdatering av visning med ID " + showing.getShowingId() + ": " + e.getMessage());
        }
    }

    // Sjekker om det er solgt billetter for en spesifikk visning.
    // Returnerer 'true' hvis billetter er solgt, 'false' ellers.
    // Returnerer også 'true' ved feil for å forhindre utilsiktet sletting.
    public boolean hasTicketsSold(int showingId) {
        String sql = "SELECT COUNT(*) FROM kino.tblbillett WHERE b_visningsnr = ?";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Hvis antall er større enn 0, eksisterer det billetter
            }
        } catch (SQLException e) {
            System.err.println("Feil ved sjekk av solgte billetter for visning ID " + showingId + ": " + e.getMessage());
            return true; // Returnerer true som en sikkerhetsstandard for å forhindre utilsiktet sletting
        }
        return false; // Ingen billetter funnet hvis ResultSet er tom eller spørring returnerer 0.
    }

    // Sletter en spesifikk visning.
    // Denne metoden bør kun kalles etter å ha sjekket for solgte billetter (typisk i tjenestelaget).
    public void deleteShowing(int showingId) {
        String sql = "DELETE FROM kino.tblvisning WHERE v_visningnr = ?";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, showingId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Visning med ID " + showingId + " slettet vellykket.");
            } else {
                System.out.println("Ingen visning funnet med ID " + showingId + " å slette.");
            }
        } catch (SQLException e) {
            System.err.println("Feil ved sletting av visning med ID " + showingId + ": " + e.getMessage());
        }
    }

    // Sletter alle visninger tilknyttet en spesifikk film-ID.
    // Denne metoden kalles av FilmDAO før en film slettes for å opprettholde referanseintegritet.
    public void deleteShowingsForFilm(int filmId) {
        String sql = "DELETE FROM kino.tblvisning WHERE v_filmnr = ?";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmId);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Slettet " + rowsAffected + " visninger for film ID: " + filmId);
        } catch (SQLException e) {
            System.err.println("Feil ved sletting av visninger for film ID " + filmId + ": " + e.getMessage());
        }
    }

    // Henter en liste over alle visninger for en spesifikk film.
    // Returnerer en liste av Showing-objekter. Listen vil være tom hvis ingen visninger er funnet for filmen.
    public List<Showing> getShowingsForFilm(int filmId) {
        List<Showing> showings = new ArrayList<>();
        String sql = "SELECT v_visningnr, v_filmnr, v_kinosalnr, v_dato, v_starttid, v_pris FROM kino.tblvisning WHERE v_filmnr = ?";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                showings.add(new Showing(rs.getInt("v_visningnr"), rs.getInt("v_filmnr"),
                                         rs.getInt("v_kinosalnr"), rs.getDate("v_dato").toLocalDate(),
                                         rs.getTime("v_starttid").toLocalTime(), rs.getDouble("v_pris")));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av visninger for film ID " + filmId + ": " + e.getMessage());
        }
        return showings;
    }
}