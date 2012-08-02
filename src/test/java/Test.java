import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ildar
 * Date: 07.05.12
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    static Connection con;
    static java.sql.Statement st;
    static ResultSet rs;

    private static void createConnetion() {

        final String url = "jdbc:mysql://localhost/";
        final String db = "ildarglasses";
        final String driver = "com.mysql.jdbc.Driver";
        final String user = "root";
        final String pass = "bl3000";
        try {
            Class.forName(driver);
            System.out.println("The driver is loaded");
            con = DriverManager.getConnection(url + db, user, pass);
            System.out.println("The connection established");
        } catch (ClassNotFoundException e) {
            System.out.println("The driver isn't loaded!");
        } catch (SQLException e) {
            System.out.println("Connetion ERROR!");
        }

    }

    public static void main(String[] args) throws IOException {
        //createConnetion();
        File file = new File("..");
        System.out.println(file.getAbsolutePath());
    }
}
