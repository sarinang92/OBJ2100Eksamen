package kino.repository;

import kino.model.Statistics;
import kino.model.FilmShowingStats;
import kino.model.CinemaHallShowingStats;
import kino.config.KinoDatabaseKobling; // Endret til KinoDatabaseKobling som avtalt
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// StatisticsDAO (Data Access Object) klassen håndterer all databaseinteraksjon
// for statistikkrelaterte spørringer. Den isolerer databaselogikk fra forretningslogikk.
public class StatisticsDAO {

    // Oppretter en instans av KinoDatabaseKobling for å hente databaseforbindelser.
    // Dette objektet er en enhet (Singleton), så 'getInstance()' returnerer alltid den samme instansen.
    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Henter generell statistikk for en spesifikk film, inkludert solgte billetter
    // og gjennomsnittlig beleggsprosent.
    public Statistics getFilmStatistics(int filmId) {
        Statistics statistics = null;
        String sql = "SELECT " +
                     "    f.f_filmnr, " +
                     "    f.f_filmnavn, " +
                     "    COALESCE(COUNT(b.b_billettkode), 0) AS total_tickets_sold, " +
                     "    COALESCE(AVG(CASE WHEN b.b_billettkode IS NOT NULL THEN 1.0 ELSE 0.0 END), 0.0) AS average_occupancy_percentage, " +
                     "    0 AS cancelled_bookings " + // Denne kolonnen er satt til 0 da det ikke er data for kansellerte bookinger i dette uttrekket
                     "FROM kino.tblfilm f " +
                     "LEFT JOIN kino.tblvisning v ON f.f_filmnr = v.v_filmnr " +
                     "LEFT JOIN kino.tblbillett b ON v.v_visningnr = b.b_visningsnr " +
                     "WHERE f.f_filmnr = ? " +
                     "GROUP BY f.f_filmnr, f.f_filmnavn";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                statistics = new Statistics(
                        rs.getInt("f_filmnr"),
                        rs.getString("f_filmnavn"),
                        rs.getInt("total_tickets_sold"),
                        rs.getDouble("average_occupancy_percentage"),
                        rs.getInt("cancelled_bookings")
                );
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av filmstatistikk for film ID " + filmId + ": " + e.getMessage());
        }
        return statistics;
    }

    // Henter generell statistikk for en spesifikk kinosal, inkludert solgte billetter
    // og gjennomsnittlig beleggsprosent.
    public Statistics getCinemaHallStatistics(int cinemaHallId) {
        Statistics statistics = null;
        String sql = "SELECT " +
                     "    ks.k_kinosalnr, " +
                     "    ks.k_kinosalnavn, " +
                     "    COALESCE(COUNT(b.b_billettkode), 0) AS total_tickets_sold, " +
                     "    COALESCE(AVG(CASE WHEN b.b_billettkode IS NOT NULL THEN 1.0 ELSE 0.0 END), 0.0) AS average_occupancy_percentage, " +
                     "    0 AS cancelled_bookings " + // Denne kolonnen er satt til 0 da det ikke er data for kansellerte bookinger i dette uttrekket
                     "FROM kino.tblkinosal ks " +
                     "LEFT JOIN kino.tblvisning v ON ks.k_kinosalnr = v.v_kinosalnr " +
                     "LEFT JOIN kino.tblbillett b ON v.v_visningnr = b.b_visningsnr " +
                     "WHERE ks.k_kinosalnr = ? " +
                     "GROUP BY ks.k_kinosalnr, ks.k_kinosalnavn";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaHallId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                statistics = new Statistics(
                        rs.getInt("k_kinosalnr"), // Bruker kinosalnummer som ID
                        rs.getString("k_kinosalnavn"), // Bruker kinosalnavn som tittel
                        rs.getInt("total_tickets_sold"),
                        rs.getDouble("average_occupancy_percentage"),
                        rs.getInt("cancelled_bookings")
                );
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av kinosalstatistikk for kinosal ID " + cinemaHallId + ": " + e.getMessage());
        }
        return statistics;
    }

    // Henter detaljert statistikk for visninger av en spesifikk film.
    // Inkluderer informasjon om hver visning og antall solgte billetter for den.
    public List<FilmShowingStats> getFilmDetailedShowingStatistics(int filmId) {
        List<FilmShowingStats> detailedStats = new ArrayList<>();
        String sql = "SELECT " +
                     "    v.v_visningnr, " +
                     "    f.f_filmnavn, " +
                     "    ks.k_kinosalnavn, " +
                     "    v.v_dato, " +
                     "    v.v_starttid, " +
                     "    v.v_pris, " +
                     "    COUNT(b.b_billettkode) AS tickets_sold " +
                     "FROM kino.tblvisning v " +
                     "JOIN kino.tblfilm f ON v.v_filmnr = f.f_filmnr " +
                     "JOIN kino.tblkinosal ks ON v.v_kinosalnr = ks.k_kinosalnr " +
                     "LEFT JOIN kino.tblbillett b ON v.v_visningnr = b.b_visningsnr " +
                     "WHERE f.f_filmnr = ? " +
                     "GROUP BY v.v_visningnr, f.f_filmnavn, ks.k_kinosalnavn, v.v_dato, v.v_starttid, v.v_pris " +
                     "ORDER BY v.v_dato, v.v_starttid";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                detailedStats.add(new FilmShowingStats(
                        rs.getInt("v_visningnr"),
                        rs.getString("f_filmnavn"),
                        rs.getString("k_kinosalnavn"),
                        rs.getDate("v_dato").toLocalDate(),
                        rs.getTime("v_starttid").toLocalTime(),
                        rs.getDouble("v_pris"),
                        rs.getInt("tickets_sold")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av detaljert visningsstatistikk for film ID " + filmId + ": " + e.getMessage());
        }
        return detailedStats;
    }

    // Henter detaljert statistikk for visninger i en spesifikk kinosal.
    // Inkluderer informasjon om hver visning og antall solgte billetter for den.
    public List<CinemaHallShowingStats> getCinemaHallDetailedShowingStatistics(int cinemaHallId) {
        List<CinemaHallShowingStats> detailedStats = new ArrayList<>();
        String sql = "SELECT " +
                     "    v.v_visningnr, " +
                     "    ks.k_kinosalnavn, " +
                     "    f.f_filmnavn, " +
                     "    v.v_dato, " +
                     "    v.v_starttid, " +
                     "    v.v_pris, " +
                     "    COUNT(b.b_billettkode) AS tickets_sold " +
                     "FROM kino.tblvisning v " +
                     "JOIN kino.tblkinosal ks ON v.v_kinosalnr = ks.k_kinosalnr " +
                     "JOIN kino.tblfilm f ON v.v_filmnr = f.f_filmnr " +
                     "LEFT JOIN kino.tblbillett b ON v.v_visningnr = b.b_visningsnr " +
                     "WHERE ks.k_kinosalnr = ? " +
                     "GROUP BY v.v_visningnr, ks.k_kinosalnavn, f.f_filmnavn, v.v_dato, v.v_starttid, v.v_pris " +
                     "ORDER BY v.v_dato, v.v_starttid";
        try (Connection conn = kinoDB.getForbindelse(); // Bruker kinoDB-instansen
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cinemaHallId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                detailedStats.add(new CinemaHallShowingStats(
                        rs.getInt("v_visningnr"),
                        rs.getString("k_kinosalnavn"),
                        rs.getString("f_filmnavn"),
                        rs.getDate("v_dato").toLocalDate(),
                        rs.getTime("v_starttid").toLocalTime(),
                        rs.getDouble("v_pris"),
                        rs.getInt("tickets_sold")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Feil ved henting av detaljert visningsstatistikk for kinosal ID " + cinemaHallId + ": " + e.getMessage());
        }
        return detailedStats;
    }
}