package kino.repository;

import kino.model.CinemaHall;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CinemaHallDAO (Data Access Object) klassen håndterer all databaseinteraksjon
 * for 'CinemaHall' (kinosal) objekter. Den isolerer databaselogikk fra forretningslogikk.
 */
public class CinemaHallDAO {

    // Bruker en ekstern databaseforbindelse i stedet for å hente den selv
    private final Connection conn;

    /**
     * Konstruktør som mottar en eksisterende JDBC-tilkobling (dependency injection).
     * @param conn JDBC-tilkobling
     */
    public CinemaHallDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Metode for å hente en spesifikk kinosal fra databasen basert på dens ID.
     * Returnerer et CinemaHall-objekt hvis funnet, ellers null.
     */
    public CinemaHall getCinemaHallById(int hallId) {
        String sql = "SELECT * FROM kino.tblkinosal WHERE k_kinosalnr = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hallId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                CinemaHall hall = new CinemaHall();
                hall.setK_kinosalnr(rs.getInt("k_kinosalnr"));
                hall.setK_kinonavn(rs.getString("k_kinonavn"));
                hall.setK_kinosalnavn(rs.getString("k_kinosalnavn"));
                return hall;
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av kinosal med ID " + hallId + ": " + e.getMessage());
        }
        return null; // Returnerer null hvis ingen kinosal ble funnet eller ved feil
    }

    /**
     * Metode for å hente en liste over alle kinosaler fra databasen.
     * Returnerer en liste av CinemaHall-objekter. Listen vil være tom hvis ingen kinosaler er funnet.
     */
    public List<CinemaHall> getAllCinemaHalls() {
        List<CinemaHall> halls = new ArrayList<>();
        String sql = "SELECT * FROM kino.tblkinosal";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                CinemaHall hall = new CinemaHall();
                hall.setK_kinosalnr(rs.getInt("k_kinosalnr"));
                hall.setK_kinonavn(rs.getString("k_kinonavn"));
                hall.setK_kinosalnavn(rs.getString("k_kinosalnavn"));
                halls.add(hall);
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av alle kinosaler: " + e.getMessage());
        }
        return halls;
    }

    /**
     * Metode for å opprette en ny kinosal i databasen.
     * Mottar et CinemaHall-objekt og setter inn dets data i tblkinosal.
     */
    public void createCinemaHall(CinemaHall hall) {
        String sql = "INSERT INTO kino.tblkinosal (k_kinosalnr, k_kinonavn, k_kinosalnavn) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hall.getK_kinosalnr());
            pstmt.setString(2, hall.getK_kinonavn());
            pstmt.setString(3, hall.getK_kinosalnavn());
            pstmt.executeUpdate();
            System.out.println("Kinosal '" + hall.getK_kinosalnavn() + "' opprettet vellykket.");
        } catch (SQLException e) {
            System.err.println("Feil ved oppretting av kinosal '" + hall.getK_kinosalnavn() + "': " + e.getMessage());
        }
    }

    /**
     * Metode for å oppdatere en eksisterende kinosal i databasen.
     * Mottar et CinemaHall-objekt med oppdaterte detaljer.
     */
    public void updateCinemaHall(CinemaHall hall) {
        String sql = "UPDATE kino.tblkinosal SET k_kinonavn = ?, k_kinosalnavn = ? WHERE k_kinosalnr = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, hall.getK_kinonavn());
            pstmt.setString(2, hall.getK_kinosalnavn());
            pstmt.setInt(3, hall.getK_kinosalnr());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kinosal med ID " + hall.getK_kinosalnr() + " oppdatert vellykket.");
            } else {
                System.out.println("Ingen kinosal funnet med ID " + hall.getK_kinosalnr() + " å oppdatere.");
            }
        } catch (SQLException e) {
            System.err.println("Feil ved oppdatering av kinosal med ID " + hall.getK_kinosalnr() + ": " + e.getMessage());
        }
    }

    /**
     * Metode for å slette en kinosal fra databasen basert på dens ID.
     */
    public void deleteCinemaHall(int hallId) {
        String sql = "DELETE FROM kino.tblkinosal WHERE k_kinosalnr = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, hallId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kinosal med ID " + hallId + " slettet vellykket.");
            } else {
                System.out.println("Ingen kinosal funnet med ID " + hallId + " å slette.");
            }
        } catch (SQLException e) {
            System.err.println("Feil ved sletting av kinosal med ID " + hallId + ": " + e.getMessage());
        }
    }
}
