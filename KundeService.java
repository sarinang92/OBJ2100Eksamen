package kunde.service;

import kunde.database.DatabaseConnection;
import kunde.model.Film;
import kunde.model.Visning;

import java.sql.*;
import java.util.*;

public class KundeService {
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    public void start() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            List<Film> filmer = hentFilmer(conn);
            Film valgtFilm = velgFilm(filmer);
            List<Visning> visninger = hentVisninger(conn, valgtFilm.getFilmnr());
            Visning valgtVisning = velgVisning(visninger);
            List<String> ledigePlasser = hentLedigePlasser(conn, valgtVisning.getVisningnr(), valgtVisning.getSalnr());
            String valgtPlass = velgPlass(ledigePlasser);
            String billettkode = genererBillettkode();
            registrerBillett(conn, valgtVisning.getVisningnr(), valgtPlass, valgtVisning.getSalnr(), billettkode);
            System.out.println("\n✅ Billett reservert!");
            System.out.println("Kode: " + billettkode + " | Pris: " + valgtVisning.getPris() + " kr | Plass: " + valgtPlass);
        } catch (SQLException e) {
            System.err.println("Feil under databaseoperasjon: " + e.getMessage());
        }
    }

    private List<Film> hentFilmer(Connection conn) throws SQLException {
        List<Film> filmer = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM kino.tblfilm");
        while (rs.next()) {
            filmer.add(new Film(rs.getInt("f_filmnr"), rs.getString("f_filmnavn")));
        }
        return filmer;
    }

    private Film velgFilm(List<Film> filmer) {
        System.out.println("Velg en film:");
        filmer.forEach(System.out::println);
        int valg = scanner.nextInt();
        return filmer.stream().filter(f -> f.getFilmnr() == valg).findFirst().orElseThrow();
    }

    private List<Visning> hentVisninger(Connection conn, int filmnr) throws SQLException {
        List<Visning> visninger = new ArrayList<>();
        String sql = "SELECT * FROM kino.tblvisning WHERE v_filmnr = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, filmnr);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            visninger.add(new Visning(
                    rs.getInt("v_visningnr"),
                    filmnr,
                    rs.getInt("v_kinosalnr"),
                    rs.getDate("v_dato").toLocalDate(),
                    rs.getTime("v_starttid").toLocalTime(),
                    rs.getDouble("v_pris")
            ));
        }
        return visninger;
    }

    private Visning velgVisning(List<Visning> visninger) {
        System.out.println("Velg visning:");
        visninger.forEach(System.out::println);
        int valg = scanner.nextInt();
        return visninger.stream().filter(v -> v.getVisningnr() == valg).findFirst().orElseThrow();
    }

    private List<String> hentLedigePlasser(Connection conn, int visningsnr, int salnr) throws SQLException {
        List<String> plasser = new ArrayList<>();
        String sql = """
            SELECT p_radnr, p_setenr FROM kino.tblplass
            WHERE p_kinosalnr = ?
            EXCEPT
            SELECT pb_radnr, pb_setenr FROM kino.tblplassbillett
            WHERE pb_billettkode IN (
                SELECT b_billettkode FROM kino.tblbillett WHERE b_visningsnr = ?
            ) AND pb_kinosalnr = ?
            """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, salnr);
        stmt.setInt(2, visningsnr);
        stmt.setInt(3, salnr);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            plasser.add(rs.getInt("p_radnr") + "-" + rs.getInt("p_setenr"));
        }
        return plasser;
    }

    private String velgPlass(List<String> plasser) {
        System.out.println("Ledige plasser:");
        plasser.stream().limit(10).forEach(System.out::println);
        System.out.print("Skriv inn ønsket plass (f.eks. 3-12): ");
        return scanner.next();
    }

    private String genererBillettkode() {
        return String.format("%06d", random.nextInt(1000000));
    }

    private void registrerBillett(Connection conn, int visningsnr, String plass, int salnr, String kode) throws SQLException {
        conn.setAutoCommit(false);
        try {
            PreparedStatement billettStmt = conn.prepareStatement(
                    "INSERT INTO kino.tblbillett (b_billettkode, b_visningsnr, b_erBetalt) VALUES (?, ?, FALSE)"
            );
            billettStmt.setString(1, kode);
            billettStmt.setInt(2, visningsnr);
            billettStmt.executeUpdate();

            String[] deler = plass.split("-");
            int rad = Integer.parseInt(deler[0]);
            int sete = Integer.parseInt(deler[1]);

            PreparedStatement plassStmt = conn.prepareStatement(
                    "INSERT INTO kino.tblplassbillett (pb_radnr, pb_setenr, pb_kinosalnr, pb_billettkode) VALUES (?, ?, ?, ?)"
            );
            plassStmt.setInt(1, rad);
            plassStmt.setInt(2, sete);
            plassStmt.setInt(3, salnr);
            plassStmt.setString(4, kode);
            plassStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}