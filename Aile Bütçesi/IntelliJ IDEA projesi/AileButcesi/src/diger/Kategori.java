package diger;

public class Kategori {

    private int id;
    private String ad;
    private boolean gelirKategorisiMi;

    public Kategori(int id, String ad, boolean gelirKategorisiMi) {
        this.id = id;
        this.ad = ad;
        this.gelirKategorisiMi = gelirKategorisiMi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public boolean isGelirKategorisiMi() {
        return gelirKategorisiMi;
    }

    public void setGelirKategorisiMi(boolean gelirKategorisiMi) {
        this.gelirKategorisiMi = gelirKategorisiMi;
    }
}
