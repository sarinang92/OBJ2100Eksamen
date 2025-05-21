package main;

import controller.KinobetjentController;
import dao.BillettDAO;
import view.KinobetjentView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Main-klasse som starter kinobetjentprogrammet.
 */
public class Main {
    public static void main(String[] args) {
        final String url = "jdbc:postgresql://localhost:5432/kino";
        final String user = "Case";
        final String password = "Esac";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            BillettDAO billettDAO = new BillettDAO(conn);
            KinobetjentController controller = new KinobetjentController(billettDAO);
            KinobetjentView view = new KinobetjentView(controller);

            view.visMeny();

            conn.close();

        } catch (SQLException e) {
            System.err.println("Klarte ikke koble til databasen:");
            e.printStackTrace();
        }
    }
}
