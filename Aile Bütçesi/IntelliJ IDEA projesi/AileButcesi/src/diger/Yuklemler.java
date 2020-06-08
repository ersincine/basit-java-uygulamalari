package diger;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Yuklemler {

    public static final int KUCUKTUR = -1;
    public static final int ESITTIR = 0;
    public static final int BUYUKTUR = 1;

    public static <V> Predicate<ADegisim> yuklemGetir(Alanlar alan, int operator, V veri) {

        ADegisim o2;
        if(alan == Alanlar.DEGER) {
            double deger = (Double) veri;
            o2 = deger > 0 ? new Gelir() : new Gider();
        } else {
            o2 = new Gelir(); // Gider de olabilir, fark etmez.
        }

        switch(alan) {
            case DEGER: // double
                double deger = (Double) veri;
                double miktar = Math.abs(deger);
                o2.setMiktar(miktar);
                break;

            case ID: // int
                int id = (Integer) veri;
                o2.setId(id);
                break;
            case MIKTAR: // double
                double miktar2 = (Double) veri;
                o2.setMiktar(miktar2);
                break;
            case ACIKLAMA: // String
                String aciklama = (String) veri;
                o2.setAciklama(aciklama);
                break;
            case KATEGORİ: // String
                String kategori = (String) veri;
                o2.setKategori(kategori);
                break;
            case SENE: // int
                int sene = (Integer) veri;
                o2.setSene(sene);
                break;
            case AY: // int
                int ay = (Integer) veri;
                o2.setAy(ay);
                break;
            case EKLEME_TARIHI: // Timestamp
                Timestamp eklemeTarihi = (Timestamp) veri;
                o2.setEklemeTarihi(eklemeTarihi);
                break;
        }

        Comparator<ADegisim> kiyaslayici = Kiyaslayicilar.kiyaslayiciGetir(alan, Kiyaslayicilar.KUCUKTEN_BUYUGE);
        Predicate<ADegisim> yuklem = o1 -> Math.signum(kiyaslayici.compare(o1, o2)) == operator;

        return yuklem;
    }

    public static void filtrele(List<? extends ADegisim> degisimler, Predicate<ADegisim> yuklem) {
        List<ADegisim> collect = degisimler.stream().filter(yuklem).collect(Collectors.toList());
        if(collect.isEmpty()) {
            degisimler.clear();
        } else {
            degisimler.retainAll(collect);
        }
    }

    public static void gelirlerKalsin(List<? extends ADegisim> degisimler) { // Giderler silinsin
        List<ADegisim> collect = degisimler.stream().filter(d -> d instanceof Gelir).collect(Collectors.toList());
        if(collect.isEmpty()) {
            degisimler.clear();
        } else {
            degisimler.retainAll(collect);
        }
    }

    public static void giderlerKalsin(List<? extends ADegisim> degisimler) { // Gelirler silinsin
        List<ADegisim> collect = degisimler.stream().filter(d -> d instanceof Gider).collect(Collectors.toList());
        if(collect.isEmpty()) {
            degisimler.clear();
        } else {
            degisimler.retainAll(collect);
        }
    }

}
