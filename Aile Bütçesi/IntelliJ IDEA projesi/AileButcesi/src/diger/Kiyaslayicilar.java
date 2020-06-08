package diger;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Kiyaslayicilar {

    public static final boolean KUCUKTEN_BUYUGE = true;
    public static final boolean BUYUKTEN_KUCUGE = false;

    public static Comparator<ADegisim> kiyaslayiciGetir(Alanlar alan, boolean kucuktenBuyugeMi) {

        int katsayi = kucuktenBuyugeMi ? 1 : -1;
        Comparator<ADegisim> comparator = null;
        switch(alan) {
            case DEGER: // double
                comparator = (o1, o2) -> katsayi * Double.compare(o1.degerHesapla(), o2.degerHesapla());
                break;

            case ID: // int
                comparator = (o1, o2) -> katsayi * Integer.compare(o1.getId(), o2.getId());
                break;
            case MIKTAR: // double
                comparator = (o1, o2) -> katsayi * Double.compare(o1.getMiktar(), o2.getMiktar());
                break;
            case ACIKLAMA: // String
                comparator = (o1, o2) -> katsayi * o1.getAciklama().compareTo(o2.getAciklama());
                break;
            case KATEGORİ: // String
                comparator = (o1, o2) -> katsayi * o1.getKategori().compareTo(o2.getKategori());
                break;
            case SENE: // int
                comparator = (o1, o2) -> katsayi * Integer.compare(o1.getSene(), o2.getSene());
                break;
            case AY: // int
                comparator = (o1, o2) -> katsayi * Integer.compare(o1.getAy(), o2.getAy());
                break;
            case EKLEME_TARIHI: // Timestamp
                comparator = (o1, o2) -> katsayi * o1.getEklemeTarihi().compareTo(o2.getEklemeTarihi());
                break;

            // İlkel tipler için Wrapper.compare(bir, iki)
            // Sınıflar için bir.compareTo(iki)
        }

        return comparator;
    }

    public static void sirala(List<ADegisim> degisimler, Comparator<ADegisim> kiyaslayici) {
        Collections.sort(degisimler, kiyaslayici);
    }

}
