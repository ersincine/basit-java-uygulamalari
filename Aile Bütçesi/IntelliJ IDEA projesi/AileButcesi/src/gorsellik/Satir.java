package gorsellik;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Satir {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty tip;
    private final SimpleStringProperty kategori;
    private final SimpleDoubleProperty miktar;
    private final SimpleStringProperty aciklama;
    private final SimpleIntegerProperty sene;
    private final SimpleIntegerProperty ay;

    public Satir(int id, String tip, String kategori, double miktar, String aciklama, int sene, int ay) {
        this.id = new SimpleIntegerProperty(id);
        this.tip = new SimpleStringProperty(tip);
        this.kategori = new SimpleStringProperty(kategori);
        this.miktar = new SimpleDoubleProperty(miktar);
        this.aciklama = new SimpleStringProperty(aciklama);
        this.sene = new SimpleIntegerProperty(sene);
        this.ay = new SimpleIntegerProperty(ay);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTip() {
        return tip.get();
    }

    public SimpleStringProperty tipProperty() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip.set(tip);
    }

    public String getKategori() {
        return kategori.get();
    }

    public SimpleStringProperty kategoriProperty() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori.set(kategori);
    }

    public double getMiktar() {
        return miktar.get();
    }

    public SimpleDoubleProperty miktarProperty() {
        return miktar;
    }

    public void setMiktar(double miktar) {
        this.miktar.set(miktar);
    }

    public String getAciklama() {
        return aciklama.get();
    }

    public SimpleStringProperty aciklamaProperty() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama.set(aciklama);
    }

    public int getSene() {
        return sene.get();
    }

    public SimpleIntegerProperty seneProperty() {
        return sene;
    }

    public void setSene(int sene) {
        this.sene.set(sene);
    }

    public int getAy() {
        return ay.get();
    }

    public SimpleIntegerProperty ayProperty() {
        return ay;
    }

    public void setAy(int ay) {
        this.ay.set(ay);
    }
}
