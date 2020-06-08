package diger;

import java.sql.Timestamp;

public class Gelir extends ADegisim {

    public Gelir() { }

    public Gelir(int id, double miktar, String aciklama, String kategori, int sene, int ay, Timestamp eklemeTarihi) {
        setId(id);
        setMiktar(miktar);
        setAciklama(aciklama);
        setKategori(kategori);
        setSene(sene);
        setAy(ay);
        setEklemeTarihi(eklemeTarihi);
    }

    @Override
    public double degerHesapla() {
        return getMiktar();
    }

}