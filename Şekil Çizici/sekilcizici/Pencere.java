package sekilcizici;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import yardimcisiniflar.Sabitler;
import yardimcisiniflar.TasinabilirMetinKutusu;

public class Pencere extends JFrame{
    
    public Pencere(){
        
        // Pencere ayarları
        setTitle("Şekil Çizici");
        setSize(Sabitler.PENCERE_GENISLIGI, Sabitler.PENCERE_YUKSEKLIGI);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Ana paneli pencereye ekle
        JPanel anaPanel = new JPanel();
        anaPanel.setLayout(null);
        anaPanel.setBackground(new Color(204,255,204));
        setContentPane(anaPanel);
        
        // Taşınabilir metin kutusunu ana panele ekle
        final TasinabilirMetinKutusu kutu = new TasinabilirMetinKutusu();
        anaPanel.add(kutu);
        
        // 1. açıklamayı ekle
        JLabel label = new JLabel("Şeklin niteliklerini giriniz:");
        label.setBounds(10, 15, 140, 30);
        anaPanel.add(label);
        
        // 2. açıklamayı ekle
        final JLabel label2 = new JLabel("Durum bilgisi");
        label2.setBounds(10, 230, 100, 30);
        anaPanel.add(label2);
        
        // Yardım düğmesini ekle
        JButton yardimDugmesi = new JButton("Yardım");
        yardimDugmesi.setBounds(270, 230, 100, 20);
        anaPanel.add(yardimDugmesi);
        
        // Çizim panelini ekle
        final CizimPaneli cizimPaneli = new CizimPaneli();
        anaPanel.add(cizimPaneli);
        
        // Yardım düğmesi eylemlerini ekle
        ActionListener butonEylemleri = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Yardim().setVisible(true);
            }
        };
        yardimDugmesi.addActionListener(butonEylemleri);
        
        // Fare eylemlerini ekle
        MouseAdapter fareEylemleri = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e){
                if(cizimPaneliIcindeMi() && !kutu.getText().isEmpty()){
                    String durum = cizimPaneli.sekilGoster(kutu.getText().trim(), kutu.getLocation().x, kutu.getLocation().y);
                    label2.setText(durum);
                    label2.setForeground( durum.equals("Şekil çizildi") ? Color.GREEN : Color.RED );
                }
                kutu.sifirla();
            }

            private boolean cizimPaneliIcindeMi() {
                if(kutu.getLocation().x >= Sabitler.CIZIM_PANELI_X && kutu.getLocation().y >= Sabitler.CIZIM_PANELI_Y && kutu.getLocation().x <= Sabitler.CIZIM_PANELI_X + Sabitler.CIZIM_PANELI_GENISLIGI && kutu.getLocation().y <= Sabitler.CIZIM_PANELI_Y + Sabitler.CIZIM_PANELI_YUKSEKLIGI){
                    return true;
                }
                return false;
            }
            
        };
        kutu.addMouseListener(fareEylemleri);

    }
    
}
