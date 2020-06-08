package veritabani;

import diger.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DegisimIslemleri implements IVeriTabaniIslemleri<ADegisim> {

    @Override
    public boolean ekle(ADegisim value) throws SQLException {

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("INSERT INTO degisimler VALUES (null, ?, ?, ?, ?, ?, ?, ?)");

        String tip;
        if(value instanceof Gelir) {
            tip = "Gelir";
        } else {
            tip = "Gider";
        }
        ps.setString(1, tip);

        ps.setDouble(2, value.getMiktar());
        ps.setString(3, value.getAciklama());

        KategoriIslemleri kategoriIslemleri = new KategoriIslemleri();
        ps.setInt(4, kategoriIslemleri.idGetir(value.getKategori(), tip)); // ("Market", "Gider") --> 16



        System.out.println(kategoriIslemleri.idGetir(value.getKategori(), tip));



        ps.setInt(5, value.getSene());
        ps.setInt(6, value.getAy());
        ps.setTimestamp(7, new Timestamp(new Date().getTime()));

        boolean sonuc = ps.execute();
        return sonuc;
    }

    @Override
    public boolean sil(ADegisim value) throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("DELETE FROM degisimler WHERE degisim_id = ?");
        ps.setInt(1, value.getId());
        boolean sonuc = ps.execute();
        return sonuc;
    }

    @Override
    public ADegisim[] hepsiniGetir() throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT * FROM degisimler");
        ResultSet rs = ps.executeQuery();
        List<ADegisim> liste = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt(1);
            String tip = rs.getString(2);
            double miktar = rs.getDouble(3);
            String aciklama = rs.getString(4);
            int kategoriNo = rs.getInt(5);
            int sene = rs.getInt(6);
            int ay = rs.getInt(7);
            Timestamp eklemeTarihi = rs.getTimestamp(8);

            PreparedStatement ps2 = SingletonBaglanti.instance().prepareStatement("SELECT ad FROM kategoriler WHERE kategori_id = ?");
            ps2.setInt(1, kategoriNo);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            String kategori = rs2.getString(1);

            ADegisim d;
            if(tip.equals("Gelir")) {
                d = new Gelir(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            } else {
                d = new Gider(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            }
            liste.add(d);
        }

        ADegisim[] dizi = new ADegisim[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }

    public Gelir[] gelirleriGetir() throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT * FROM degisimler WHERE tip = 'Gelir'");
        ResultSet rs = ps.executeQuery();
        List<Gelir> liste = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt(1);
            //String tip = rs.getString(2);
            double miktar = rs.getDouble(3);
            String aciklama = rs.getString(4);
            int kategoriNo = rs.getInt(5);
            int sene = rs.getInt(6);
            int ay = rs.getInt(7);
            Timestamp eklemeTarihi = rs.getTimestamp(8);

            PreparedStatement ps2 = SingletonBaglanti.instance().prepareStatement("SELECT ad FROM kategoriler WHERE kategori_id = ?");
            ps2.setInt(1, kategoriNo);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            String kategori = rs2.getString(1);

            Gelir d = new Gelir(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gelir[] dizi = new Gelir[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }

    public Gelir[] gelirleriKategorilereGoreGruplayipGetir() throws SQLException { // Maaş diye bir tane gelecek mesela. group by kategori, sum miktar --> açıklama falan olmaz artık
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT sum(miktar), kategori_no FROM degisimler WHERE tip = 'Gelir' GROUP BY kategori_no");
        ResultSet rs = ps.executeQuery();
        List<Gelir> liste = new ArrayList<>();
        while(rs.next()) {
            int id = -1;
            double miktar = rs.getDouble(1);
            String aciklama = null;
            int kategoriNo = rs.getInt(2);
            int sene = -1;
            int ay = -1;
            Timestamp eklemeTarihi = null;

            PreparedStatement ps2 = SingletonBaglanti.instance().prepareStatement("SELECT ad FROM kategoriler WHERE kategori_id = ?");
            ps2.setInt(1, kategoriNo);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            String kategori = rs2.getString(1);

            Gelir d = new Gelir(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gelir[] dizi = new Gelir[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }

    public Gelir[] buSenedekiGelirleriAylaraGoreGruplayipGetir(int sene) throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT ay, sum(miktar) FROM degisimler WHERE tip = 'Gelir' AND sene = ? GROUP BY ay");
        ps.setInt(1, sene);
        ResultSet rs = ps.executeQuery();
        List<Gelir> liste = new ArrayList<>();
        while(rs.next()) {
            int id = -1;
            double miktar = rs.getDouble(2);
            String aciklama = null;
            int kategoriNo = -1;
            //int sene = sene;
            int ay = rs.getInt(1);
            Timestamp eklemeTarihi = null;

            String kategori = null;

            Gelir d = new Gelir(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gelir[] dizi = new Gelir[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }

    public Gelir[] buSenedekiGelirleriAylaraGoreGruplayipGetir(int sene, String kategoriAdi) throws SQLException {

        PreparedStatement ps0 = SingletonBaglanti.instance().prepareStatement("SELECT kategori_id FROM kategoriler WHERE tip = 'Gelir' AND ad = ?");
        ps0.setString(1, kategoriAdi);
        ResultSet rs0 = ps0.executeQuery();
        rs0.next();
        int kategoriId = rs0.getInt(1);

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT ay, sum(miktar) FROM degisimler WHERE tip = 'Gelir' AND sene = ? AND kategori_no = ? GROUP BY ay");
        ps.setInt(1, sene);
        ps.setInt(2, kategoriId);
        ResultSet rs = ps.executeQuery();
        List<Gelir> liste = new ArrayList<>();
        while(rs.next()) {
            int id = -1;
            double miktar = rs.getDouble(2);
            String aciklama = null;
            int kategoriNo = -1;
            //int sene = sene;
            int ay = rs.getInt(1);
            Timestamp eklemeTarihi = null;

            String kategori = null;

            Gelir d = new Gelir(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gelir[] dizi = new Gelir[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }



    public Gider[] giderleriGetir() throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT * FROM degisimler WHERE tip = 'Gider'");
        ResultSet rs = ps.executeQuery();
        List<Gider> liste = new ArrayList<>();
        while(rs.next()) {
            int id = rs.getInt(1);
            //String tip = rs.getString(2);
            double miktar = rs.getDouble(3);
            String aciklama = rs.getString(4);
            int kategoriNo = rs.getInt(5);
            int sene = rs.getInt(6);
            int ay = rs.getInt(7);
            Timestamp eklemeTarihi = rs.getTimestamp(8);

            PreparedStatement ps2 = SingletonBaglanti.instance().prepareStatement("SELECT ad FROM kategoriler WHERE kategori_id = ?");
            ps2.setInt(1, kategoriNo);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            String kategori = rs2.getString(1);

            Gider d = new Gider(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gider[] dizi = new Gider[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }

    public Gider[] giderleriKategorilereGoreGruplayipGetir() throws SQLException { // Market diye bir tane gelecek mesela. group by kategori, sum miktar --> açıklama falan olmaz artık
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT sum(miktar), kategori_no FROM degisimler WHERE tip = 'Gider' GROUP BY kategori_no");
        ResultSet rs = ps.executeQuery();
        List<Gider> liste = new ArrayList<>();
        while(rs.next()) {
            int id = -1;
            double miktar = rs.getDouble(1);
            String aciklama = null;
            int kategoriNo = rs.getInt(2);
            int sene = -1;
            int ay = -1;
            Timestamp eklemeTarihi = null;

            PreparedStatement ps2 = SingletonBaglanti.instance().prepareStatement("SELECT ad FROM kategoriler WHERE kategori_id = ?");
            ps2.setInt(1, kategoriNo);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            String kategori = rs2.getString(1);

            Gider d = new Gider(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gider[] dizi = new Gider[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }

    public Gider[] buSenedekiGiderleriAylaraGoreGruplayipGetir(int sene) throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT ay, sum(miktar) FROM degisimler WHERE tip = 'Gider' AND sene = ? GROUP BY ay");
        ps.setInt(1, sene);
        ResultSet rs = ps.executeQuery();
        List<Gider> liste = new ArrayList<>();
        while(rs.next()) {
            int id = -1;
            double miktar = rs.getDouble(2);
            String aciklama = null;
            int kategoriNo = -1;
            //int sene = sene;
            int ay = rs.getInt(1);
            Timestamp eklemeTarihi = null;

            String kategori = null;

            Gider d = new Gider(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gider[] dizi = new Gider[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }

    public Gider[] buSenedekiGiderleriAylaraGoreGruplayipGetir(int sene, String kategoriAdi) throws SQLException {

        PreparedStatement ps0 = SingletonBaglanti.instance().prepareStatement("SELECT kategori_id FROM kategoriler WHERE tip = 'Gider' AND ad = ?");
        ps0.setString(1, kategoriAdi);
        ResultSet rs0 = ps0.executeQuery();
        rs0.next();
        int kategoriId = rs0.getInt(1);

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT ay, sum(miktar) FROM degisimler WHERE tip = 'Gider' AND sene = ? AND kategori_no = ? GROUP BY ay");
        ps.setInt(1, sene);
        ps.setInt(2, kategoriId);
        ResultSet rs = ps.executeQuery();
        List<Gider> liste = new ArrayList<>();
        while(rs.next()) {
            int id = -1;
            double miktar = rs.getDouble(2);
            String aciklama = null;
            int kategoriNo = -1;
            //int sene = sene;
            int ay = rs.getInt(1);
            Timestamp eklemeTarihi = null;

            String kategori = null;

            Gider d = new Gider(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gider[] dizi = new Gider[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }

    public Gider[] buAydakiGiderleriKategorilereGoreGruplayipGetirGetir(int ay) throws SQLException {

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT sum(miktar), kategori_no FROM degisimler WHERE tip = 'Gider' AND ay = ? GROUP BY kategori_no");
        ps.setInt(1, ay);
        ResultSet rs = ps.executeQuery();
        List<Gider> liste = new ArrayList<>();
        while(rs.next()) {
            int id = -1;
            double miktar = rs.getDouble(1);
            String aciklama = null;
            int kategoriNo = rs.getInt(2);
            int sene = -1;
            //int ay = -1;
            Timestamp eklemeTarihi = null;

            PreparedStatement ps2 = SingletonBaglanti.instance().prepareStatement("SELECT ad FROM kategoriler WHERE kategori_id = ?");
            ps2.setInt(1, kategoriNo);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            String kategori = rs2.getString(1);

            Gider d = new Gider(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gider[] dizi = new Gider[liste.size()];
        liste.toArray(dizi);
        return dizi;

    }

    public Gelir[] buAydakiGelirleriKategorilereGoreGruplayipGetirGetir(int ay) throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT sum(miktar), kategori_no FROM degisimler WHERE tip = 'Gelir' AND ay = ? GROUP BY kategori_no");
        ps.setInt(1, ay);
        ResultSet rs = ps.executeQuery();
        List<Gelir> liste = new ArrayList<>();
        while(rs.next()) {
            int id = -1;
            double miktar = rs.getDouble(1);
            String aciklama = null;
            int kategoriNo = rs.getInt(2);
            int sene = -1;
            //int ay = -1;
            Timestamp eklemeTarihi = null;

            PreparedStatement ps2 = SingletonBaglanti.instance().prepareStatement("SELECT ad FROM kategoriler WHERE kategori_id = ?");
            ps2.setInt(1, kategoriNo);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            String kategori = rs2.getString(1);

            Gelir d = new Gelir(id, miktar, aciklama, kategori, sene, ay, eklemeTarihi);
            liste.add(d);
        }
        Gelir[] dizi = new Gelir[liste.size()];
        liste.toArray(dizi);
        return dizi;
    }
}