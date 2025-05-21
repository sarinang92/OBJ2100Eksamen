package kino.repository;

import kino.config.KinoDatabaseKobling;
import kino.model.Billett;

import java.sql.*;

public class BillettRepo {
    // Henter singleton-instans av databasekoblingen
    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Opprett en ny billett
    public boolean opprettBillett(Billett billett) {
        String sql = "INSERT INTO kino.tblbillett (b_billettkode, b_visningsnr, b_erBetalt) VALUES (?, ?, ?)";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            // Sett verdier i SQL-setningen
            stmt.setString(1, billett.getBillettkode());
            stmt.setInt(2, billett.getVisningsnr());
            stmt.setBoolean(3, billett.isErBetalt());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hent en billett
    public Billett hentBillett(String billettkode) {
        String sql = "SELECT * FROM kino.tblbillett WHERE b_billettkode = ?";
        Billett billett = null;

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setString(1, billettkode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                billett = new Billett();
                billett.setBillettkode(rs.getString("b_billettkode"));
                billett.setVisningsnr(rs.getInt("b_visningsnr"));
                billett.setErBetalt(rs.getBoolean("b_erBetalt"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billett;
    }

    // Oppdater en billett
    public boolean oppdaterBillett(Billett billett) {
        String sql = "UPDATE kino.tblbillett SET b_visningsnr = ?, b_erBetalt = ? WHERE b_billettkode = ?";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, billett.getVisningsnr());
            stmt.setBoolean(2, billett.isErBetalt());
            stmt.setString(3, billett.getBillettkode());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Slett en billett
    public boolean slettBillett(String billettkode) {
        String sql = "DELETE FROM kino.tblbillett WHERE b_billettkode = ?";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setString(1, billettkode);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

