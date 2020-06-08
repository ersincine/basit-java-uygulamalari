package yardimcisiniflar;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TasinabilirMetinKutusu extends JTextField{

    private Point fareKoordinatlari;

    public TasinabilirMetinKutusu() {
        setSize(Sabitler.TASINABILIR_KUTU_GENISLIGI, Sabitler.TASINABILIR_KUTU_YUKSEKLIGI);
        sifirla();
        fareDinleyiciyiEkle();
        klavyeDinleyiciyiEkle();
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    private void fareDinleyiciyiEkle() {

        MouseAdapter fareEylemleri = new MouseAdapter() {
            
            @Override
            public void mouseMoved(MouseEvent e) {
                fareKoordinatlari = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point ekrandakiYer = getParent().getLocationOnScreen();
                Point fareninEkrandakiYeri = e.getLocationOnScreen();
                Point yeniYer = new Point(fareninEkrandakiYeri.x - ekrandakiYer.x - fareKoordinatlari.x, fareninEkrandakiYeri.y - ekrandakiYer.y - fareKoordinatlari.y);
                setLocation(yeniYer);
                
                if(pencereSinirlarininDisindaMi()){
                    sifirla();
                }
            }

            private boolean pencereSinirlarininDisindaMi() {
                if (getLocation().x < 0 || getLocation().y < 0 || getLocation().x > Sabitler.PENCERE_GENISLIGI - Sabitler.TASINABILIR_KUTU_GENISLIGI || getLocation().y > Sabitler.PENCERE_YUKSEKLIGI - Sabitler.TASINABILIR_KUTU_YUKSEKLIGI){
                    return true;
                }
                return false;
            }
            
        };

        addMouseMotionListener(fareEylemleri);
        
    }

    private void klavyeDinleyiciyiEkle() {
        
        KeyAdapter klavyeEylemleri = new KeyAdapter() {
        
            public void keyReleased(KeyEvent e) {

                String yazi = getText().trim(); // Başındaki ve sonundaki boşluklara engel ol. Çünkü kelimeler boşluk ile ayrılıyor.
                String[] kelimeler = yazi.split(" ");
                int kelimeSayisi = kelimeler.length;

                if(yazi.isEmpty()) {
                    // Kutucukta yazı yoksa (boşsa veya boşluk varsa, çünkü trim'ledik) önizlemeyi orijinal haline çevir.
                    setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
                    setBackground(Color.WHITE);
                } else if(kelimeSayisi == 1 || !kelimeler[1].equals("çizgili")) {
                    // Çizgi bilgisi verilmediği müddetçe kutu arkaplan rengini ilk kelimeden alsın
                    String ilkKelime = kelimeler[0];
                    Color icRenk = Renkler.rengiGetir(ilkKelime, "beyaz");
                    setBackground(icRenk);
                    setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
                } else {
                    String ikinciKelime = kelimeler[1];
                    // Çerçeve rengi:
                    if(ikinciKelime.equalsIgnoreCase("çizgili")) {
                        String ilkKelime = kelimeler[0];
                        Color disRenk = Renkler.rengiGetir(ilkKelime, "gri");
                        setBorder(BorderFactory.createLineBorder(disRenk));
                    } else {
                        setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
                    }
                    // Arka plan rengi:
                    if(kelimeSayisi >= 3){
                        String ucuncuKelime = kelimeler[2];
                        Color icRenk = Renkler.rengiGetir(ucuncuKelime, "beyaz");
                        setBackground(icRenk);
                    } else {
                        setBackground(Color.WHITE);
                    }
                }
                
            }
        
        };
        
        addKeyListener(klavyeEylemleri);
        
    }

    public void sifirla(){ // Pencere sınıfı da kullandığı için public
        setText("");
        setLocation(Sabitler.TASINABILIR_KUTU_X, Sabitler.TASINABILIR_KUTU_Y);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
    }
    
}