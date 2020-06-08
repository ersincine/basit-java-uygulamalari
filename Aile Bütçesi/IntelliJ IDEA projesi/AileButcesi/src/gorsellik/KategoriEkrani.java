package gorsellik;

import diger.Kategori;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import veritabani.KategoriIslemleri;

import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

public class KategoriEkrani extends Stage {

    final KategoriIslemleri kategoriIslemleri = new KategoriIslemleri();
    ListView<String> listView1 = new ListView<String>();
    ListView<String> listView2 = new ListView<String>();

    public KategoriEkrani() throws SQLException {
        setTitle("Kategoriler");
        setWidth(1340);
        setHeight(500);

        gelirKategorileriniGoster();

        listView1.setPrefHeight(200);
        listView1.setStyle("-fx-font: 20 arial; -fx-background-color: limegreen");

        giderKategorileriniGoster();

        listView2.setPrefHeight(200);
        listView2.setStyle("-fx-font: 20 arial; -fx-background-color: firebrick");

        Label label1 = new Label("Yeni gelir kategorisi:");
        label1.setStyle("-fx-font: 32 arial; -fx-text-fill: black");
        TextField textField1 = new TextField();
        textField1.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: aliceblue; -fx-border-color: limegreen");
        Button button1 = new Button("Oluştur");
        button1.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: limegreen; -fx-border-color: limegreen");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String kategoriAdi = textField1.getText();
                Kategori k = new Kategori(-1, kategoriAdi, true);
                try {
                    kategoriIslemleri.ekle(k);
                    gelirKategorileriniGoster();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Label label2 = new Label("Yeni gider kategorisi:");
        label2.setStyle("-fx-font: 32 arial; -fx-text-fill: black");
        TextField textField2 = new TextField();
        textField2.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: blanchedalmond; -fx-border-color: firebrick");
        Button button2 = new Button("Oluştur");
        button2.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: firebrick; -fx-border-color: firebrick");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String kategoriAdi = textField2.getText();
                Kategori k = new Kategori(-1, kategoriAdi, false);
                try {
                    kategoriIslemleri.ekle(k);
                    giderKategorileriniGoster();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        HBox hbox1 = new HBox(5, label1, textField1, button1);
        HBox hbox2 = new HBox(5, label2, textField2, button2);

        Label label3 = new Label("Gelir Kategorileri");
        label3.setStyle("-fx-font: 32 arial; -fx-text-fill: black");

        Label label4 = new Label("Gider Kategorileri");
        label4.setStyle("-fx-font: 32 arial; -fx-text-fill: black");

        Button button3 = new Button("Seçili gelir kategorisini sil");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String kategoriAdi = listView1.getSelectionModel().getSelectedItem();
                try {
                    int kategoriId = kategoriIslemleri.idGetir(kategoriAdi, "Gelir");
                    Kategori k = new Kategori(kategoriId, kategoriAdi, true);
                    kategoriIslemleri.sil(k);
                    gelirKategorileriniGoster();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        button3.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: limegreen; -fx-border-color: limegreen");

        Button button4 = new Button("Seçili gider kategorisini sil");
        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String kategoriAdi = listView2.getSelectionModel().getSelectedItem();
                try {
                    int kategoriId = kategoriIslemleri.idGetir(kategoriAdi, "Gider");
                    Kategori k = new Kategori(kategoriId, kategoriAdi, false);
                    kategoriIslemleri.sil(k);
                    giderKategorileriniGoster();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        button4.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: firebrick; -fx-border-color: firebrick");

        VBox vbox1 = new VBox(10, new Label("   "), label3, listView1, button3, hbox1);
        vbox1.setStyle("-fx-background-color: mintcream; -fx-padding: 8");

        VBox vbox2 = new VBox(10, new Label("   "), label4, listView2, button4, hbox2);
        vbox2.setStyle("-fx-background-color: antiquewhite; -fx-padding: 8");

        HBox hbox = new HBox(vbox1, vbox2);

        Scene scene = new Scene(hbox);
        setScene(scene);

        setResizable(false);
    }

    private void gelirKategorileriniGoster() throws SQLException {
        Set<String> gelirKategorileri = new TreeSet<>();        // Koleksiyon kullanılmış oldu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for (Kategori kategori : kategoriIslemleri.gelirKategorileriniGetir()) {
            gelirKategorileri.add(kategori.getAd());
        }
        ObservableList<String> items1 = FXCollections.observableArrayList(gelirKategorileri);
        listView1.setItems(items1);
    }

    private void giderKategorileriniGoster() throws SQLException {
        Set<String> giderKategorileri = new TreeSet<>();        // Koleksiyon kullanılmış oldu!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        for (Kategori kategori : kategoriIslemleri.giderKategorileriniGetir()) {
            giderKategorileri.add(kategori.getAd());
        }
        ObservableList<String> items2 = FXCollections.observableArrayList(giderKategorileri);
        listView2.setItems(items2);
    }

}
