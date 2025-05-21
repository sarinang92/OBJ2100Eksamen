package kino.repository;

import kino.config.KinoDatabaseKobling;
import kino.model.Visning;
import kino.model.Plass;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisningRepo {

    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Opprett en ny visning
    public boolean opprettVisning(Visning visning) {
        String sql = "INSERT INTO kino.tblvisning (v_visningnr, v_filmnr, v_kinosalnr, v_dato, v_starttid, v_pris) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, visning.getVisningnr());
            stmt.setInt(2, visning.getFilmnr());
            stmt.setInt(3, visning.getKinosalnr());
            stmt.setDate(4, Date.valueOf(visning.getDato()));
            stmt.setTime(5, Time.valueOf(visning.getStarttid()));
            stmt.setDouble(6, visning.getPris());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hent en visning
    public Visning hentVisning(int visningnr) {
        String sql = "SELECT * FROM kino.tblvisning WHERE v_visningnr = ?";
        Visning visning = null;

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, visningnr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                visning = new Visning();
                visning.setVisningnr(rs.getInt("v_visningnr"));
                visning.setFilmnr(rs.getInt("v_filmnr"));
                visning.setKinosalnr(rs.getInt("v_kinosalnr"));
                visning.setDato(rs.getDate("v_dato").toLocalDate());
                visning.setStarttid(rs.getTime("v_starttid").toLocalTime());
                visning.setPris(rs.getDouble("v_pris"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visning;
    }

    // Oppdater en visning
    public boolean oppdaterVisning(Visning visning) {
        String sql = "UPDATE kino.tblvisning SET v_filmnr = ?, v_kinosalnr = ?, v_dato = ?, v_starttid = ?, v_pris = ? WHERE v_visningnr = ?";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, visning.getFilmnr());
            stmt.setInt(2, visning.getKinosalnr());
            stmt.setDate(3, Date.valueOf(visning.getDato()));
            stmt.setTime(4, Time.valueOf(visning.getStarttid()));
            stmt.setDouble(5, visning.getPris());
            stmt.setInt(6, visning.getVisningnr());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Slett en visning
    public boolean slettVisning(int visningnr) {
        String sql = "DELETE FROM kino.tblvisning WHERE v_visningnr = ?";

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, visningnr);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Visning> hentAlleVisninger() {
        List<Visning> visninger = new ArrayList<>();
        String sql = "SELECT * FROM kino.tblvisning";

        try (Connection forbindelse = kinoDB.getForbindelse();
             Statement stmt = forbindelse.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Visning visning = new Visning();
                visning.setVisningnr(rs.getInt("v_visningnr"));
                visning.setFilmnr(rs.getInt("v_filmnr"));
                visning.setKinosalnr(rs.getInt("v_kinosalnr"));
                visning.setDato(rs.getDate("v_dato").toLocalDate());
                visning.setStarttid(rs.getTime("v_starttid").toLocalTime());
                visning.setPris(rs.getDouble("v_pris"));
                visninger.add(visning);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return visninger;
    }

    public List<Plass> hentLedigePlasser(int visningsnr, int kinosalnr) {
        List<Plass> plasser = new ArrayList<>();
        String sql = """
        SELECT * FROM kino.tblplass p
        WHERE p.p_kinosalnr = ?
        AND NOT EXISTS (
            SELECT 1 FROM kino.tblplassbillett pb
            JOIN kino.tblbillett b ON pb.pb_billettkode = b.b_billettkode
            WHERE pb.pb_radnr = p.p_radnr
              AND pb.pb_setenr = p.p_setenr
              AND pb.pb_kinosalnr = p.p_kinosalnr
              AND b.b_visningsnr = ?
        )
    """;

        try (Connection forbindelse = kinoDB.getForbindelse();
             PreparedStatement stmt = forbindelse.prepareStatement(sql)) {

            stmt.setInt(1, kinosalnr);
            stmt.setInt(2, visningsnr);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Plass plass = new Plass(
                        rs.getInt("p_radnr"),
                        rs.getInt("p_setenr"),
                        rs.getInt("p_kinosalnr")
                );
                plasser.add(plass);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plasser;
    }

    public int tellLedigePlasser(int visningsnr, int kinosalnr) {
        String sql = """
        SELECT COUNT(*) FROM kino.tblplass p
        WHERE p.p_kinosalnr = ?
        AND NOT EXISTS (
            SELECT 1 FROM kino.tblplassbillett pb
            JOIN kino.tblbillett b ON pb.pb_billettkode = b.b_billettkode
            WHERE pb.pb_radnr = p.p_radnr
              AND pb.pb_setenr = p.p_setenr
              AND pb.pb_kinosalnr = p.p_kinosalnr
              AND b.b_visningsnr = ?
        )
    """;

        try (Connection conn = kinoDB.getForbindelse();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, kinosalnr);
            stmt.setInt(2, visningsnr);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


}
