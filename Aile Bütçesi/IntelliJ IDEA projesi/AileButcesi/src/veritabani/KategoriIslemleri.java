package veritabani;

import diger.Kategori;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategoriIslemleri implements IVeriTabaniIslemleri<Kategori> {

    // Connection close??


    @Override
    public boolean ekle(Kategori value) throws SQLException {
        String ad = value.getAd();
        String tip = value.isGelirKategorisiMi() ? "Gelir" : "Gider";

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("INSERT INTO kategoriler VALUES (NULL, ?, ?)");// kategori_id, ad, tip
        ps.setString(1, ad);
        ps.setString(2, tip);
        boolean sonuc = ps.execute();

        return sonuc;
    }

    @Override
    public boolean sil(Kategori value) throws SQLException {

        int id = value.getId();

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("DELETE FROM kategoriler WHERE kategori_id = ?");
        ps.setInt(1, id);
        boolean sonuc = ps.execute();

        return sonuc;
    }

    @Override
    public Kategori[] hepsiniGetir() throws SQLException {

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT * FROM kategoriler");
        ResultSet kayitlar = ps.executeQuery();
        List<Kategori> kategoriListesi = new ArrayList<>();
        while(kayitlar.next()) { // kategori_id, ad, tip
            int id = kayitlar.getInt(1);
            String ad = kayitlar.getString(2);
            String tip = kayitlar.getString(3);
            boolean gelirKategorisiMi = tip.equals("Gelir");
            Kategori kategori = new Kategori(id, ad, gelirKategorisiMi);
            kategoriListesi.add(kategori);
        }

        Kategori[] kategoriler = new Kategori[kategoriListesi.size()];
        kategoriListesi.toArray(kategoriler);
        return kategoriler;
    }

    public Kategori[] gelirKategorileriniGetir() throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT * FROM kategoriler WHERE tip = 'Gelir'");
        ResultSet kayitlar = ps.executeQuery();
        List<Kategori> kategoriListesi = new ArrayList<>();
        while(kayitlar.next()) { // kategori_id, ad, tip
            int id = kayitlar.getInt(1);
            String ad = kayitlar.getString(2);
            Kategori kategori = new Kategori(id, ad, true);
            kategoriListesi.add(kategori);
        }

        Kategori[] kategoriler = new Kategori[kategoriListesi.size()];
        kategoriListesi.toArray(kategoriler);
        return kategoriler;
    }

    public Kategori[] giderKategorileriniGetir() throws SQLException {

        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT * FROM kategoriler WHERE tip = 'Gider'");
        ResultSet kayitlar = ps.executeQuery();
        List<Kategori> kategoriListesi = new ArrayList<>();
        while(kayitlar.next()) { // kategori_id, ad, tip
            int id = kayitlar.getInt(1);
            String ad = kayitlar.getString(2);
            Kategori kategori = new Kategori(id, ad, false);
            kategoriListesi.add(kategori);
        }

        Kategori[] kategoriler = new Kategori[kategoriListesi.size()];
        kategoriListesi.toArray(kategoriler);
        return kategoriler;
    }

    public int idGetir(String kategoriAdi, String tip) throws SQLException {
        PreparedStatement ps = SingletonBaglanti.instance().prepareStatement("SELECT * FROM kategoriler WHERE ad = ? and tip = ?");
        ps.setString(1, kategoriAdi);
        ps.setString(2, tip);
        ResultSet kayitlar = ps.executeQuery();

        if(kayitlar.next()) {
            int id = kayitlar.getInt("kategori_id");
            return id;
        }

        return -1;
    }

}