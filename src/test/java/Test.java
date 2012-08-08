import com.ildar.web.main.ShowImage;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import com.ildar.web.main.ImageWork;

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
        final String pass = "bl3000";
        boolean checkConnect = false;
        try {
            Class.forName(driver);
            System.out.println("The driver is loaded");
            con = DriverManager.getConnection(url + db, user, pass);
            System.out.println("The connection established");
            checkConnect = true;
        } catch (ClassNotFoundException e) {
            System.out.println("The driver isn't loaded!");
        } catch (SQLException e) {
            System.out.println("Connetion ERROR!");
        }
        Assert.assertTrue(checkConnect);
    }

    @org.junit.Test
    public void Search() {
        String dirPath = new File("").getAbsolutePath();
        HashMap<String, String> tests = new HashMap<String, String>();
        tests.put(dirPath +  "\\testImgs\\InDatabase.jpg", //  key-test,   value-right answer;
                dirPath + "\\res\\inImageBase\\242.jpg");
        tests.put(dirPath + "\\testImgs\\NotInDatabase.jpg",
                null);
        tests.put(dirPath + "\\testImgs\\inDatabase2.jpg",
                "\\res\\inImageBase\\17.jpg");
        boolean check = true;
        for (Map.Entry<String, String> test : tests.entrySet()) {
            Iterator<String> iter = ImageWork.testStartSearch(
                    new File(test.getKey()));
            if (iter == null) {
                if (test.getValue() != null) {
                    check = false;
                    break;
                }
            } else {
                String imagePath;
                boolean existInIter = false;
                while (iter.hasNext()) {
                    imagePath = iter.next();
                    if (imagePath.equals(test.getValue())) {
                        existInIter = true;
                    }
                }
                if (existInIter) {
                    check = existInIter;
                    System.out.println("The image is found)");
                }
            }
            System.out.println("---------------------------------------------------");
        }
        Assert.assertTrue(check);
    }
}