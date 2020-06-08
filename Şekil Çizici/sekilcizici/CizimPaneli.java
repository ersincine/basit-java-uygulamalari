package sekilcizici;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import sekiller.Daire;
import sekiller.Kare;
import sekiller.Sekil;
import sekiller.Ucgen;
import yardimcisiniflar.MetinBolucu;
import yardimcisiniflar.Renkler;
import yardimcisiniflar.Sabitler;

public class CizimPaneli extends JPanel{
    
    Sekil sekil;
    
    public CizimPaneli(){
        setBorder(new EtchedBorder());
        setBounds(Sabitler.CIZIM_PANELI_X, Sabitler.CIZIM_PANELI_Y, Sabitler.CIZIM_PANELI_GENISLIGI, Sabitler.CIZIM_PANELI_YUKSEKLIGI);
        setBackground(Color.white);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (sekil != null){
            sekil.cizimYap(g);
        }
    }
    
    public String sekilGoster(String metin, int x, int y){ // metin = jTextField1.getText();
        String[] ozellikler = MetinBolucu.metniBol(metin);
        // ozellikler[0] iç renk, ozellikler[1] dış renk, ozellikler[2] şekil
        if(ozellikler == null){
            return "Sorun var";
        }
        Color icRenk = Renkler.rengiGetir(ozellikler[0]); // String'i Color'a çevir.
        Color disRenk = Renkler.rengiGetir(ozellikler[1]); // String'i Color'a çevir.
        if (ozellikler[2].equalsIgnoreCase("kare")){
            sekil = new Kare(icRenk, disRenk, x, y);
        } else if (ozellikler[2].equalsIgnoreCase("daire")){
            sekil = new Daire(icRenk, disRenk, x, y);
        } else if (ozellikler[2].equalsIgnoreCase("üçgen")){
            sekil = new Ucgen(icRenk, disRenk, x, y);
        } else {
            return "Sorun var"; // Farklı bir şekil istenmişse
        }
        repaint();
        return "Şekil çizildi";
    }
    
}
