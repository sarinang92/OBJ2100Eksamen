package kino.repository;

import kino.model.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    private final Connection conn;

    public FilmDAO(Connection conn) {
        this.conn = conn;
    }

    // Opprett en ny film og returner objektet
    public Film createFilm(String filmName) {
        String sql = "INSERT INTO kino.tblfilm (f_filmnavn) VALUES (?)";
        Film film = new Film();

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            film.setFilmnavn(filmName);
            pstmt.setString(1, film.getFilmnavn());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Opprettelse av film feilet, ingen rader påvirket.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    film.setFilmnr(generatedKeys.getInt(1));
                    System.out.println("Film '" + filmName + "' opprettet med ID: " + film.getFilmnr());
                } else {
                    throw new SQLException("Opprettelse av film feilet, ingen ID returnert.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return film;
    }

    // Hent alle filmer
    public List<Film> hentAlleFilmer() {
        List<Film> filmer = new ArrayList<>();
        String sql = "SELECT f_filmnr, f_filmnavn FROM kino.tblfilm";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Film film = new Film();
                film.setFilmnr(rs.getInt("f_filmnr"));
                film.setFilmnavn(rs.getString("f_filmnavn"));
                filmer.add(film);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filmer;
    }

    // Oppdater film
    public void oppdaterFilm(Film film) {
        String sql = "UPDATE kino.tblfilm SET f_filmnavn = ? WHERE f_filmnr = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, film.getFilmnavn());
            pstmt.setInt(2, film.getFilmnr());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Film oppdatert: " + film.getFilmnavn());
            } else {
                System.out.println("Ingen film ble oppdatert (sjekk ID).");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Slett film
    public void slettFilm(int filmnr) {
        String sql = "DELETE FROM kino.tblfilm WHERE f_filmnr = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmnr);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Film med ID " + filmnr + " er slettet.");
            } else {
                System.out.println("Ingen film funnet med ID: " + filmnr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Film hentFilm(int filmnr) {
        String sql = "SELECT f_filmnr, f_filmnavn FROM kino.tblfilm WHERE f_filmnr = ?";
        Film film = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, filmnr);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                film = new Film();
                film.setFilmnr(rs.getInt("f_filmnr"));
                film.setFilmnavn(rs.getString("f_filmnavn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return film;
    }

}
