package veritabani;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonBaglanti {

    private static String surucu = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String veriTabaniAdi = "ailebutcesi";
    private static String kullaniciAdi = "root";
    private static String parola = "root";

    private static Connection con;

    static {
        try {
            //Class.forName(surucu).newInstance(); // Olmasa???
            con = DriverManager.getConnection(url + veriTabaniAdi, kullaniciAdi, parola);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection instance() {
        return con;
    }
}
