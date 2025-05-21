package kino.repository;

import kino.config.KinoDatabaseKobling;
import kino.model.Plass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlassRepo {

    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Hent en plass basert på radnr, setenr og kinosalnr
    public Plass hentPlass(int radnr, int setenr, int kinosalnr) {
        String sql = "SELECT * FROM kino.tblplass WHERE p_radnr = ? AND p_setenr = ? AND p_kinosalnr = ?";
        Plass plass = null;

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, radnr);
            stmt.setInt(2, setenr);
            stmt.setInt(3, kinosalnr);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                plass = new Plass(
                        rs.getInt("p_radnr"),
                        rs.getInt("p_setenr"),
                        rs.getInt("p_kinosalnr")
                );
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plass;
    }
}
