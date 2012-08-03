import junit.framework.TestCase;
import org.junit.Assert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Ildar
 * Date: 07.05.12
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    static Connection con;

    @org.junit.Test
    public void createConnetion() {

        final String url = "jdbc:mysql://localhost/";
        final String db = "ildarglasses";
        final String driver = "com.mysql.jdbc.Driver";
        final String user = "root";
        final String pass = "bl300";
        boolean connect = false;
        try {
            Class.forName(driver);
            System.out.println("The driver is loaded");
            con = DriverManager.getConnection(url + db, user, pass);
            System.out.println("The connection established");
            connect = true;
        } catch (ClassNotFoundException e) {
            System.out.println("The driver isn't loaded!");
        } catch (SQLException e) {
            System.out.println("Connetion ERROR!");
        }
        Assert.assertTrue(connect);
    }


}


/*
    static java.sql.Statement st;
    static ResultSet rs;*/