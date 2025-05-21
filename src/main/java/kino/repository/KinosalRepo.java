package kino.repository;

import kino.config.KinoDatabaseKobling;
import kino.model.Kinosal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KinosalRepo {

    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Hent en kinosal basert på kinosalnr
    public Kinosal hentKinosal(int kinosalnr) {
        String sql = "SELECT * FROM kino.tblkinosal WHERE k_kinosalnr = ?";
        Kinosal kinosal = null;

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, kinosalnr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                kinosal = new Kinosal(
                        rs.getInt("k_kinosalnr"),
                        rs.getString("k_kinonavn"),
                        rs.getString("k_kinosalnavn")
                );
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kinosal;
    }
}
