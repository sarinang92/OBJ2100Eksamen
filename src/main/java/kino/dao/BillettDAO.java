package kino.dao;

import kino.model.Billett;
import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Data Access Object for håndtering av billetter i databasen.
 */
public class BillettDAO {
    private final Connection conn;

    /**
     * Oppretter DAO med aktiv databaseforbindelse.
     * @param conn JDBC-tilkobling
     */
    public BillettDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Henter en billett basert på billettkode.
     * @param kode Billettkode
     * @return Billett-objekt eller null hvis ikke funnet
     */
    public Billett hentBillett(String kode) {
        String sql = "SELECT * FROM kino.tblbillett WHERE b_billettkode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Billett(
                        rs.getString("b_billettkode"),
                        rs.getInt("b_visningsnr"),
                        rs.getBoolean("b_erBetalt")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Marker en billett som betalt.
     * @param kode Billettkode
     * @return true hvis oppdateringen lyktes
     */
    public boolean markerSomBetalt(String kode) {
        String sql = "UPDATE kino.tblbillett SET b_erBetalt = TRUE WHERE b_billettkode = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kode);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sletter alle ubetalte billetter og tilhørende plassbillett-oppføringer, og logger dem til fil.
     * @return antall slettede billetter
     */
    public int slettAlleUbetalteOgLogg() {
        int antallSlettet = 0;
        String hentSql = "SELECT b_billettkode FROM kino.tblbillett WHERE b_erBetalt = false";
        String slettPlassbillettSql = "DELETE FROM kino.tblplassbillett WHERE pb_billettkode = ?";
        String slettBillettSql = "DELETE FROM kino.tblbillett WHERE b_billettkode = ?";

        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(hentSql);
                FileWriter logg = new FileWriter("slettinger.dat", true)
        ) {
            while (rs.next()) {
                String kode = rs.getString("b_billettkode");

                // 1. Slett tilhørende plassbillett(er)
                try (PreparedStatement slettPlassStmt = conn.prepareStatement(slettPlassbillettSql)) {
                    slettPlassStmt.setString(1, kode);
                    slettPlassStmt.executeUpdate();
                }

                // 2. Slett billett
                try (PreparedStatement slettBillettStmt = conn.prepareStatement(slettBillettSql)) {
                    slettBillettStmt.setString(1, kode);
                    int rader = slettBillettStmt.executeUpdate();
                    if (rader > 0) {
                        antallSlettet++;
                        logg.write("Slettet billett: " + kode + "\n");
                    }
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return antallSlettet;
    }

}
