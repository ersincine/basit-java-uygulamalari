package veritabani;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DigerIslemler {

    // rs.close'lar...!!! ps.close'lar !???

    public int[] seneleriGetir() throws SQLException { // Birbirinden farklı seneler getirilir

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT sene FROM degisimler GROUP BY sene");
        ResultSet rs = ps.executeQuery();
        List<Integer> senelerListesi = new ArrayList<>();
        while (rs.next()) {
            int sene = rs.getInt("sene");
            senelerListesi.add(sene);
        }

        int[] seneler = new int[senelerListesi.size()];
        for(int i = 0; i < seneler.length; i++) {
            seneler[i] = senelerListesi.get(i);
        }
        return seneler;
    }

}
