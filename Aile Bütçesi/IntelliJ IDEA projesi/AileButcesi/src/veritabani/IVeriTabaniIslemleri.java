package veritabani;

import java.sql.SQLException;

public interface IVeriTabaniIslemleri<V> {

    boolean ekle(V value) throws SQLException;  // insert

    boolean sil(V value) throws SQLException;   // delete

    V[] hepsiniGetir() throws SQLException;     // select

}
