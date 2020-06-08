package gorsellik;

import diger.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import veritabani.DegisimIslemleri;
import veritabani.DigerIslemler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class PastaGrafigi extends Stage {

    ObservableList<PieChart.Data> pieChartData;

    ComboBox<String> comboBox1;
    ComboBox<String> comboBox2;
    ComboBox<String> comboBox3;
    Button gosterButonu;

    PieChart chart;

    DegisimIslemleri degisimIslemleri;

    public PastaGrafigi() throws SQLException {

        setTitle("Pasta Grafiği");

        degisimIslemleri = new DegisimIslemleri();
        DigerIslemler digerIslemler = new DigerIslemler();

        setWidth(1340);
        setHeight(500);

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
        liste2.add("Bütün aylar");
        for(int i = 1; i <= 12; i++) {
            liste2.add(ADegisim.ayinAdi(i));
        }
        ObservableList<String> aylar = FXCollections.observableList(liste2);
        comboBox2 = new ComboBox<>(aylar);
        comboBox2.getSelectionModel().select(0);
        comboBox2.setStyle("-fx-font: 20 arial; -fx-background-color: gold");

        List<String> liste3 = new ArrayList<>();
        liste3.add("Giderler");
        liste3.add("Gelirler");
        ObservableList<String> degisim = FXCollections.observableList(liste3);
        comboBox3 = new ComboBox<>(degisim);
        comboBox3.getSelectionModel().select(0);
        comboBox3.setStyle("-fx-font: 20 arial; -fx-background-color: gold");

        gosterButonu = new Button("Göster");
        gosterButonu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    guncelle();
                } catch (SQLException e) { }
            }
        });
        gosterButonu.setStyle("-fx-font: 20 arial; -fx-background-color: tomato");

        chart = new PieChart();
        chart.setPrefWidth(800);

        VBox vbox = new VBox(5, new Label("   "), comboBox1, comboBox2, comboBox3, gosterButonu);

        HBox hbox = new HBox(20, chart, vbox);

        Scene scene = new Scene(hbox, 800, 600);

        setResizable(false);
        setScene(scene);
        guncelle();
    }

    private void guncelle() throws SQLException {

        String sene = comboBox1.getSelectionModel().getSelectedItem(); // "2015", "2014", ...
        String ay = comboBox2.getSelectionModel().getSelectedItem(); // "Bütün aylar", "Ocak", "Şubat", ...
        boolean butunAylar = ay.equals("Bütün aylar");
        String giderlerVeyaGelirler = comboBox3.getSelectionModel().getSelectedItem(); // "Giderler", "Gelirler"

        List<ADegisim> degisimler;
        if(giderlerVeyaGelirler.equals("Giderler")) {

            if(butunAylar) {
                degisimler = Arrays.asList(degisimIslemleri.giderleriKategorilereGoreGruplayipGetir());
            } else {
                degisimler = Arrays.asList(degisimIslemleri.buAydakiGiderleriKategorilereGoreGruplayipGetirGetir(ADegisim.ayinNumarasi(ay)));
            }

        } else {                     // Gelirler

            if(butunAylar) {
                degisimler = Arrays.asList(degisimIslemleri.gelirleriKategorilereGoreGruplayipGetir());
            } else {
                degisimler = Arrays.asList(degisimIslemleri.buAydakiGelirleriKategorilereGoreGruplayipGetirGetir(ADegisim.ayinNumarasi(ay)));
            }

        }

        if(degisimler.isEmpty()) {
            pieChartData = FXCollections.observableArrayList(new PieChart.Data("Veri yok",1));

        } else {

            PieChart.Data[] dilimler = new PieChart.Data[degisimler.size()];
            for (int i = 0; i < dilimler.length; i++) {
                String kategori = degisimler.get(i).getKategori();  // "Maaş"
                double miktar = degisimler.get(i).getMiktar();      // 2000
                dilimler[i] = new PieChart.Data(kategori, miktar);
            }

            pieChartData = FXCollections.observableArrayList(dilimler);
        }

        chart.setData(pieChartData); //chart = new PieChart(pieChartData);
        chart.setTitle(sene + " : " + ay + " : " + giderlerVeyaGelirler);
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.LEFT);

        /*
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");
        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "%");  // ????
                            System.out.println(String.valueOf(data.getPieValue()) + "%");
                        }
                    });
        }
        */


    }

}