package kino.repository;

import kino.config.KinoDatabaseKobling;
import kino.model.PlassBillett;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlassBillettRepo {

    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Opprett en ny plassbillett
    public boolean opprettPlassBillett(PlassBillett pb) {
        String sql = "INSERT INTO kino.tblplassbillett (pb_billettkode, pb_radnr, pb_setenr, pb_kinosalnr) VALUES (?, ?, ?, ?)";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setString(1, pb.getBillettkode());
            stmt.setInt(2, pb.getRadnr());
            stmt.setInt(3, pb.getSetenr());
            stmt.setInt(4, pb.getKinosalnr());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hent en plassbillett
    public PlassBillett hentPlassBillett(String billettkode, int radnr, int setenr, int kinosalnr) {
        String sql = "SELECT * FROM kino.tblplassbillett WHERE pb_billettkode = ? AND pb_radnr = ? AND pb_setenr = ? AND pb_kinosalnr = ?";
        PlassBillett pb = null;

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setString(1, billettkode);
            stmt.setInt(2, radnr);
            stmt.setInt(3, setenr);
            stmt.setInt(4, kinosalnr);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pb = new PlassBillett();
                pb.setBillettkode(rs.getString("pb_billettkode"));
                pb.setRadnr(rs.getInt("pb_radnr"));
                pb.setSetenr(rs.getInt("pb_setenr"));
                pb.setKinosalnr(rs.getInt("pb_kinosalnr"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pb;
    }

    // Oppdater en plassbillett
    public boolean oppdaterPlassBillett(PlassBillett pb, String gammelBillettkode) {
        String sql = "UPDATE kino.tblplassbillett SET pb_billettkode = ? WHERE pb_billettkode = ? AND pb_radnr = ? AND pb_setenr = ? AND pb_kinosalnr = ?";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setString(1, pb.getBillettkode());
            stmt.setString(2, gammelBillettkode);
            stmt.setInt(3, pb.getRadnr());
            stmt.setInt(4, pb.getSetenr());
            stmt.setInt(5, pb.getKinosalnr());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Slett en plassbillett
    public boolean slettPlassBillett(String billettkode, int radnr, int setenr, int kinosalnr) {
        String sql = "DELETE FROM kino.tblplassbillett WHERE pb_billettkode = ? AND pb_radnr = ? AND pb_setenr = ? AND pb_kinosalnr = ?";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setString(1, billettkode);
            stmt.setInt(2, radnr);
            stmt.setInt(3, setenr);
            stmt.setInt(4, kinosalnr);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
