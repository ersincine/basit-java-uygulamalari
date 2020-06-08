package gorsellik;

import diger.ADegisim;
import diger.Gelir;
import diger.Gider;
import diger.Kategori;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import veritabani.DegisimIslemleri;
import veritabani.KategoriIslemleri;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnaEkran extends Stage {

    // Günler, günlere göre çizgi grafiği !!!!???????

    private TableView table = new TableView();

// Değişimler listesi
// Ekle butonu
// Seçili

    public AnaEkran() throws SQLException {

        setTitle("Aile Bütçesi");
        setWidth(1340);
        setHeight(500);

        Button button1 = new Button("Kategorileri Düzenle");
        button1.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: lightskyblue; -fx-border-color: lightskyblue");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new KategoriEkrani().show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button2 = new Button("Çizgi Grafiği");
        button2.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: darkorange; -fx-border-color: palevioletred");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new CizgiGrafigi().show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button3 = new Button("Daire Grafiği");
        button3.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: lightgreen; -fx-border-color: seagreen");
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    new PastaGrafigi().show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button5 = new Button("Seçili değişimi sil");
        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Satir s = ((Satir)table.getSelectionModel().getSelectedItem());
                if(s==null) return;
                int id = s.getId();
                ADegisim a = new Gelir(id, 0, "", "", -1, -1, null); // Veya Gider
                DegisimIslemleri degisimIslemleri = new DegisimIslemleri();
                try {
                    degisimIslemleri.sil(a);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bilgi Penceresi");
                    alert.setHeaderText("Değişim silindi!");
                    alert.showAndWait();
                    guncelle();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        button5.setStyle("-fx-font: 20 arial; -fx-text-fill: black; -fx-background-color: salmon; -fx-border-color: salmon");

        HBox hBox1 = new HBox(10, button1, button5, new Label("                                                                  " +
                "                                                                                                                     "), button2, button3);
        hBox1.setStyle("-fx-background-color: antiquewhite; -fx-padding: 8");


        table.setEditable(true);

        TableColumn col0 = new TableColumn("ID");
        col0.setPrefWidth(120);

        TableColumn colSinif = new TableColumn("Sınıf");
        TableColumn col1 = new TableColumn("Tip");
        col1.setPrefWidth(140);
        TableColumn col2 = new TableColumn("Kategori");
        col2.setPrefWidth(230);

        colSinif.getColumns().addAll(col1, col2);

        TableColumn col3 = new TableColumn("Miktar");
        col3.setPrefWidth(160);

        TableColumn col4 = new TableColumn("Açıklama");
        col4.setPrefWidth(466);


        TableColumn colSeneAy = new TableColumn("Dönem");
        TableColumn col5 = new TableColumn("Sene");
        col5.setPrefWidth(100);
        TableColumn col6 = new TableColumn("Ay");
        col6.setPrefWidth(100);

        colSeneAy.getColumns().addAll(col5, col6);

        //TableColumn col7 = new TableColumn("Ekleme Tarihi");
        //col7.setPrefWidth(220);

        table.getColumns().addAll(col0, colSinif, col3, col4, colSeneAy); //, col7);
        table.setStyle("-fx-font: 20 arial");

        table.setEditable(false);








        col0.setCellValueFactory(
                new PropertyValueFactory<Satir,Integer>("id")
        );
        col1.setCellValueFactory(
                new PropertyValueFactory<Satir,String>("tip")
        );
        col2.setCellValueFactory(
                new PropertyValueFactory<Satir,String>("kategori")
        );
        col3.setCellValueFactory(
                new PropertyValueFactory<Satir,Double>("miktar")
        );
        col4.setCellValueFactory(
                new PropertyValueFactory<Satir,String>("açıklama")
        );
        col5.setCellValueFactory(
                new PropertyValueFactory<Satir,Integer>("sene")
        );
        col6.setCellValueFactory(
                new PropertyValueFactory<Satir,Integer>("ay")
        );


        guncelle();





        VBox vBox2 = new VBox(10, table);
        vBox2.setStyle("-fx-background-color: antiquewhite; -fx-padding: 8");



        List<String> liste1 = new ArrayList<>();
        liste1.add("Gelir");
        liste1.add("Gider");
        ObservableList<String> observableList1 = FXCollections.observableList(liste1);
        ComboBox comboBox1 = new ComboBox<>(observableList1);
        comboBox1.getSelectionModel().select(0);
        comboBox1.setStyle("-fx-font: 18 arial; -fx-background-color: gold");
        comboBox1.setPrefWidth(150);

        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.setStyle("-fx-font: 18 arial; -fx-background-color: gold");
        KategoriIslemleri kategoriIslemleri = new KategoriIslemleri();
        Kategori[] kategoriler = kategoriIslemleri.gelirKategorileriniGetir();
        List<String> liste2 = new ArrayList<>();
        for(Kategori k: kategoriler) {
            liste2.add(k.getAd());
        }
        ObservableList<String> observableList2 = FXCollections.observableList(liste2);
        comboBox2.setItems(observableList2);
        comboBox2.getSelectionModel().select(0);
        comboBox2.setPrefWidth(150);

        comboBox1.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    KategoriIslemleri kategoriIslemleri = new KategoriIslemleri();
                    Kategori[] kategoriler;
                    if(newValue.equals("Gelir")) {
                        kategoriler = kategoriIslemleri.gelirKategorileriniGetir();
                    } else {
                        kategoriler = kategoriIslemleri.giderKategorileriniGetir();
                    }

                    List<String> liste2 = new ArrayList<>();
                    for(Kategori k: kategoriler) {
                        liste2.add(k.getAd());
                    }
                    ObservableList<String> observableList2 = FXCollections.observableList(liste2);
                    comboBox2.setItems(observableList2);
                    comboBox2.getSelectionModel().select(0);
                } catch (SQLException e) { }
            }
        });

        TextField textField1 = new TextField("Miktar");
        textField1.setPrefWidth(100);
        textField1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textField1.setText("");
            }
        });
        textField1.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    if(textField1.getText().isEmpty()) {
                        textField1.setText("Miktar");
                    }
                }
            }
        });
        textField1.setStyle("-fx-font: 18 arial; -fx-background-color: lightsteelblue");
        textField1.setPrefWidth(150);

        TextField textField2 = new TextField("Açıklama");
        textField2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textField2.setText("");
            }
        });
        textField2.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    if(textField2.getText().isEmpty()) {
                        textField2.setText("Açıklama");
                    }
                }
            }
        });
        textField2.setPrefWidth(315);
        textField2.setStyle("-fx-font: 18 arial; -fx-background-color: lightsteelblue");
        comboBox2.setPrefWidth(250);

        TextField textField3 = new TextField("2015");
        textField3.setPrefWidth(200);
        textField3.setStyle("-fx-font: 18 arial; -fx-background-color: palegoldenrod");

        ComboBox<String> comboBox3 = new ComboBox<>();
        List<String> liste3 = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            liste3.add(ADegisim.ayinAdi(i));
        }
        ObservableList<String> observableList3 = FXCollections.observableList(liste3);
        comboBox3.setItems(observableList3);
        comboBox3.getSelectionModel().select(0);
        comboBox3.setStyle("-fx-font: 18 arial; -fx-background-color: palegoldenrod");
        comboBox3.setPrefWidth(170);

        Button button6 = new Button("Değişimi ekle");
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DegisimIslemleri degisimIslemleri = new DegisimIslemleri();

                    int miktar = Integer.parseInt(textField1.getText());
                    String aciklama = textField2.getText();
                    String kategori = comboBox2.getSelectionModel().getSelectedItem();
                    int sene = Integer.parseInt(textField3.getText());
                    int ay = ADegisim.ayinNumarasi(comboBox3.getSelectionModel().getSelectedItem());

                    ADegisim a;
                    if (comboBox1.getSelectionModel().getSelectedItem().equals("Gelir")) {
                        a = new Gelir(-1, miktar, aciklama, kategori, sene, ay, null);
                    } else {
                        a = new Gider(-1, miktar, aciklama, kategori, sene, ay, null);
                    }

                    degisimIslemleri.ekle(a);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bilgi Penceresi");
                    alert.setHeaderText("Değişim eklendi!");
                    alert.showAndWait();

                    guncelle();
                } catch (Exception e) { }
            }
        });
        button6.setPrefWidth(270);
        button6.setStyle("-fx-font: 18 arial; -fx-text-fill: black; -fx-background-color: salmon; -fx-border-color: salmon");

        HBox hBox2 = new HBox(10, comboBox1, comboBox2, textField1, textField2, textField3, comboBox3, button6);
        hBox2.setStyle("-fx-background-color: antiquewhite; -fx-padding: 8");

        VBox vBox = new VBox(hBox1, vBox2, hBox2);

        Scene scene = new Scene(vBox);
        setScene(scene);

        setResizable(false);

    }

    private void guncelle() throws SQLException {

        DegisimIslemleri degisimIslemleri = new DegisimIslemleri();
        ADegisim[] degisimler = degisimIslemleri.hepsiniGetir();

        List<Satir> satirListesi = new ArrayList<>();
        for(ADegisim d: degisimler) {
            satirListesi.add(new Satir(d.getId(), (d instanceof Gelir ? "Gelir":"Gider"), d.getKategori(), d.getMiktar(), d.getAciklama(), d.getSene(), d.getAy()));
        }

        final ObservableList<Satir> data = FXCollections.observableArrayList(satirListesi);

        table.setItems(data);

    }

}
