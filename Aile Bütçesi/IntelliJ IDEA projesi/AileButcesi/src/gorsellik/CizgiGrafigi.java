package gorsellik;

import diger.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import veritabani.DegisimIslemleri;
import veritabani.DigerIslemler;
import veritabani.KategoriIslemleri;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CizgiGrafigi extends Stage {

    // Sene değiştir
    // Kategori değiştir / Tüm kategoriler
    // Şekil ekle kaldır

    private ComboBox<String> comboBox1;
    private ComboBox<String> comboBox2;
    private ComboBox<String> comboBox3;
    private ComboBox<String> comboBox4;

    private DegisimIslemleri degisimIslemleri;

    private LineChart<String,Number> lineChart;

    public CizgiGrafigi() throws SQLException {

        degisimIslemleri = new DegisimIslemleri();

        setWidth(1340);
        setHeight(500);

        setTitle("Çizgi Grafiği");

        CategoryAxis xAxis = new CategoryAxis(); //xAxis.setLabel("...");
        NumberAxis yAxis = new NumberAxis();
        lineChart = new LineChart<String, Number>(xAxis,yAxis);
        lineChart.setPrefWidth(800);

        DigerIslemler digerIslemler = new DigerIslemler();
        int[] seneDizisi = digerIslemler.seneleriGetir();
        List<String> liste1 = new ArrayList<>();
        for(int sene: seneDizisi) {
            liste1.add("" + sene);
        }
        ObservableList<String> seneler = FXCollections.observableList(liste1);
        comboBox1 = new ComboBox<>(seneler);
        comboBox1.getSelectionModel().select(0);
        comboBox1.setStyle("-fx-font: 20 arial; -fx-background-color: gold");

        List<String> liste2 = new ArrayList<>();
        liste2.add("Gelir ve Gider");
        liste2.add("Gelir");
        liste2.add("Gider");
        liste2.add("Toplam");
        ObservableList<String> degisimTurleri = FXCollections.observableList(liste2);
        comboBox2 = new ComboBox<>(degisimTurleri);
        comboBox2.getSelectionModel().select(0);
        comboBox2.setStyle("-fx-font: 20 arial; -fx-background-color: gold");

        KategoriIslemleri kategoriIslemleri = new KategoriIslemleri();

        List<String> liste3 = new ArrayList<>();
        liste3.add("Bütün gelirler");
        Kategori[] kategoriler1 = kategoriIslemleri.gelirKategorileriniGetir();
        for(Kategori k: kategoriler1) {
            liste3.add(k.getAd());
        }
        ObservableList<String> gelirKategorileri = FXCollections.observableList(liste3);
        comboBox3 = new ComboBox<>(gelirKategorileri);
        comboBox3.getSelectionModel().select(0);
        comboBox3.setStyle("-fx-font: 20 arial; -fx-background-color: gold");

        List<String> liste4 = new ArrayList<>();
        liste4.add("Bütün giderler");
        Kategori[] kategoriler2 = kategoriIslemleri.giderKategorileriniGetir();
        for(Kategori k: kategoriler2) {
            liste4.add(k.getAd());
        }
        ObservableList<String> giderKategorileri = FXCollections.observableList(liste4);
        comboBox4 = new ComboBox<>(giderKategorileri);
        comboBox4.getSelectionModel().select(0);
        comboBox4.setStyle("-fx-font: 20 arial; -fx-background-color: gold");

        comboBox2.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch(newValue) {
                    case "Gelir ve Gider":
                        comboBox3.setDisable(false);
                        comboBox4.setDisable(false);
                        break;
                    case "Gelir":
                        comboBox3.setDisable(false);
                        comboBox4.setDisable(true);
                        break;
                    case "Gider":
                        comboBox3.setDisable(true);
                        comboBox4.setDisable(false);
                        break;
                    case "Toplam":
                        comboBox3.setDisable(true);
                        comboBox4.setDisable(true);
                        break;
                }
            }
        });

        Button gosterButonu = new Button("Göster");
        gosterButonu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    guncelle();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        gosterButonu.setStyle("-fx-font: 20 arial; -fx-background-color: tomato");

        VBox vbox = new VBox(5, new Label(""), comboBox1, comboBox2, comboBox3, comboBox4, gosterButonu);

        HBox hbox = new HBox(20, lineChart, vbox);

        Scene scene = new Scene(hbox,1340,500);

        setScene(scene);
        setResizable(false);

        setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");

            }
        });
    }

    public void guncelle() throws SQLException {



        int sene = Integer.parseInt(comboBox1.getSelectionModel().getSelectedItem()); // 2015, 2014, ...
        String gelirVeyaGider = comboBox2.getSelectionModel().getSelectedItem(); // "Gelir ve Gider", "Gelir", "Gider", "Toplam"
        String gelirKategorisi = comboBox3.getSelectionModel().getSelectedItem(); // "Bütün gelirler", "Maaş", ...
        String giderKategorisi = comboBox4.getSelectionModel().getSelectedItem(); // "Bütün giderler", "Ulaşım", ...

        lineChart.setTitle(sene + " : " + gelirKategorisi + " , " + giderKategorisi);
        lineChart.getData().clear();

        List<Gelir> gelirListesi = null;
        if(!gelirVeyaGider.equals("Gider")) { // "Gider" dışında bütün tercihlerde gelirler hesaplanır

            gelirListesi = new ArrayList<>();
            if(gelirKategorisi.equals("Bütün gelirler")) {
                gelirListesi = Arrays.asList(degisimIslemleri.buSenedekiGelirleriAylaraGoreGruplayipGetir(sene));
            } else {
                gelirListesi = Arrays.asList(degisimIslemleri.buSenedekiGelirleriAylaraGoreGruplayipGetir(sene, gelirKategorisi));
            }

            if(!gelirVeyaGider.equals("Toplam")) {
                XYChart.Series series = new XYChart.Series();
                series.setName("Gelirler");
                for (int i = 1; i <= 12; i++) {
                    boolean bulunduMu = false;
                    for (Gelir g : gelirListesi) {
                        if (g.getAy() == i) {
                            series.getData().add(new XYChart.Data(ADegisim.ayinAdi(i), g.getMiktar()));
                            bulunduMu = true;
                            break;
                        }
                    }
                    if (!bulunduMu) {
                        series.getData().add(new XYChart.Data(ADegisim.ayinAdi(i), 0));
                    }
                }
                lineChart.getData().add(series);
            }

        }

        List<Gider> giderListesi = null;
        if(!gelirVeyaGider.equals("Gelir")) { // "Gelir" dışında bütün tercihlerde giderler hesaplanır

            giderListesi = new ArrayList<>();
            if(giderKategorisi.equals("Bütün giderler")) {
                giderListesi = Arrays.asList(degisimIslemleri.buSenedekiGiderleriAylaraGoreGruplayipGetir(sene));
            } else {
                giderListesi = Arrays.asList(degisimIslemleri.buSenedekiGiderleriAylaraGoreGruplayipGetir(sene, giderKategorisi));
            }

            if(!gelirVeyaGider.equals("Toplam")) {
                XYChart.Series series = new XYChart.Series();
                series.setName("Giderler");
                for (int i = 1; i <= 12; i++) {
                    boolean bulunduMu = false;
                    for (Gider g : giderListesi) {
                        if (g.getAy() == i) {
                            series.getData().add(new XYChart.Data(ADegisim.ayinAdi(i), g.getMiktar()));
                            bulunduMu = true;
                            break;
                        }
                    }
                    if (!bulunduMu) {
                        series.getData().add(new XYChart.Data(ADegisim.ayinAdi(i), 0));
                    }
                }
                lineChart.getData().add(series);
            }
        }


        if(gelirVeyaGider.equals("Toplam")) {
            XYChart.Series series = new XYChart.Series();
            series.setName("Toplam");
            for (int i = 1; i <= 12; i++) {
                Gider gider = null;
                for (Gider g : giderListesi) {
                    if (g.getAy() == i) {
                        gider = g;
                        break;
                    }
                }
                Gelir gelir = null;
                for (Gelir g : gelirListesi) {
                    if (g.getAy() == i) {
                        gelir = g;
                        break;
                    }
                }
                if(gider == null && gelir == null) {
                    series.getData().add(new XYChart.Data(ADegisim.ayinAdi(i), 0));
                } else if(gider != null && gelir != null) {
                    series.getData().add(new XYChart.Data(ADegisim.ayinAdi(i), gelir.degerHesapla() + gider.degerHesapla()));
                } else if(gider != null) {
                    series.getData().add(new XYChart.Data(ADegisim.ayinAdi(i), gider.degerHesapla()));
                } else {
                    series.getData().add(new XYChart.Data(ADegisim.ayinAdi(i), gelir.degerHesapla()));
                }

            }
            lineChart.getData().add(series);
        }


    }

}



