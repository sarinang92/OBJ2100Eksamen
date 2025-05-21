package kino.repository;

import kino.config.KinoDatabaseKobling;
import kino.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmRepo {

    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Opprett en ny film
    public boolean opprettFilm(Film film) {
        String sql = "INSERT INTO kino.tblfilm (f_filmnr, f_filmnavn) VALUES (?, ?)";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, film.getFilmnr());
            stmt.setString(2, film.getFilmnavn());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hent en film
    public Film hentFilm(int filmnr) {
        String sql = "SELECT * FROM kino.tblfilm WHERE f_filmnr = ?";
        Film film = null;

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, filmnr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                film = new Film(rs.getInt("f_filmnr"), rs.getString("f_filmnavn"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return film;
    }

    // Oppdater en film
    public boolean oppdaterFilm(Film film) {
        String sql = "UPDATE kino.tblfilm SET f_filmnavn = ? WHERE f_filmnr = ?";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setString(1, film.getFilmnavn());
            stmt.setInt(2, film.getFilmnr());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Slett en film
    public boolean slettFilm(int filmnr) {
        String sql = "DELETE FROM kino.tblfilm WHERE f_filmnr = ?";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, filmnr);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
