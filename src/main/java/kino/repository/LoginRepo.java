package kino.repository;

import kino.config.KinoDatabaseKobling;
import kino.model.Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class LoginRepo {

    KinoDatabaseKobling kinoDB = KinoDatabaseKobling.getInstance();

    // Opprett eller endre pinkode
    public boolean oppdaterEllerSettPinkode(Login login) {
        try (Connection forbindelse = kinoDB.getForbindelse()) {
            String sql = "UPDATE kino.tbllogin SET l_pinkode = ? WHERE l_brukernavn = ?";

            try (PreparedStatement stmt = forbindelse.prepareStatement(sql)) {
                String hashedPin = hashPinkode(login.getPinkode());
                stmt.setString(1, hashedPin);
                stmt.setString(2, login.getBrukernavn());

                int rows = stmt.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Slett pinkode
    public boolean slettPinkode(String brukernavn) {
        try (Connection forbindelse = kinoDB.getForbindelse()) {
            String sql = "UPDATE kino.tbllogin SET l_pinkode = NULL WHERE l_brukernavn = ?";

            try (PreparedStatement stmt = forbindelse.prepareStatement(sql)) {
                stmt.setString(1, brukernavn);
                int rows = stmt.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verifisere PIN mot lagret hash
    public boolean verifiserPinkode(String brukernavn, String inputPinkode) {
        try (Connection forbindelse = kinoDB.getForbindelse()) {
            String sql = "SELECT l_pinkode FROM kino.tbllogin WHERE l_brukernavn = ?";

            try (PreparedStatement stmt = forbindelse.prepareStatement(sql)) {
                stmt.setString(1, brukernavn);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String storedHash = rs.getString("l_pinkode");
                    String inputHash = hashPinkode(inputPinkode);
                    return storedHash != null && storedHash.equals(inputHash);
                }
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Hashing med SHA-256
    private String hashPinkode(String pinkode) throws NoSuchAlgorithmException {
        if (pinkode == null || pinkode.isEmpty()) return null;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(pinkode.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    // Hent en bruker basert på brukernavn
    public Login finnEnLogin(String brukernavn) {
        Login login = null;

        try (Connection forbindelse = kinoDB.getForbindelse()) {
            String sql = "SELECT l_brukernavn, l_role FROM kino.tbllogin WHERE l_brukernavn = ?";

            try (PreparedStatement stmt = forbindelse.prepareStatement(sql)) {
                stmt.setString(1, brukernavn);
                ResultSet result = stmt.executeQuery();

                if (result.next()) {
                    login = new Login(result.getString("l_brukernavn"), result.getString("l_role"));
                }

                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login;
    }

    // Hash alle eksisterende PIN-koder i databasen
    public void hashAllPlaintextPins() {
        try (Connection conn = kinoDB.getForbindelse()) {
            String sql = "SELECT l_brukernavn, l_pinkode, l_role FROM kino.tbllogin WHERE l_pinkode IS NOT NULL";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String brukernavn = rs.getString("l_brukernavn");
                String pinkode = rs.getString("l_pinkode");
                String role = rs.getString("l_role");

                if (pinkode.length() >= 64) continue;

                Login login = new Login(brukernavn, role);
                login.setPinkode(pinkode);

                boolean ok = oppdaterEllerSettPinkode(login);
                System.out.println(ok ? "Hashed PIN for " + brukernavn : "Failed for " + brukernavn);
            }


            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

