package diger;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Locale;

public abstract class ADegisim {

    private int id;
    private double miktar;
    private String aciklama;
    private String kategori;
    private int sene;
    private int ay;
    private Timestamp eklemeTarihi;

    // Bazı gelir ve gider nesneleri aslında bir kategoridekilerin toplamı
    // O yüzden belli bir id'leri (-1), açıklamaları (null), ayları (-1) ve ekleme tarihleri (null) olmayabilir.

    /**
     * @return işaretli miktar
     */
    public abstract double degerHesapla();

    /******************************************************************************************************************/

    public static int ayinNumarasi(String ay) {
        String[] aylar = {"Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
        for(int i = 0; i < aylar.length; i++) {
            if(aylar[i].equals(ay)) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Uygun olmayan bir ay");
    }

    public static String ayinAdi(int ay) {
        String[] aylar = {"Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
        return aylar[ay-1];
    }

    /******************************************************************************************************************/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMiktar() {
        return miktar;
    }

    public void setMiktar(double miktar) {
        this.miktar = Math.abs(miktar);
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) { // Sonuçlar kategorilere göre gruplandıysa açıklama null gelir
        if(aciklama != null) {
            this.aciklama = aciklama.toLowerCase(Locale.forLanguageTag("tr-TR"));
        }
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        if(kategori != null) {
            this.kategori = kategori.toLowerCase(Locale.forLanguageTag("tr-TR"));
        }
    }

    public int getSene() {
        return sene;
    }

    public void setSene(int sene) {
        this.sene = sene;
    }

    public int getAy() {
        return ay;
    }

    public void setAy(int ay) {
        this.ay = ay;
    }

    public Timestamp getEklemeTarihi() {
        return eklemeTarihi;
    }

    public void setEklemeTarihi(Timestamp eklemeTarihi) {
        this.eklemeTarihi = eklemeTarihi;
    }

}
