package sekiller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Daire extends Sekil {
    
    public Daire(Color icRenk, Color disRenk, int x, int y){
        this.icRenk = icRenk;
        this.disRenk = disRenk == null ? icRenk : disRenk; // Null göndermedik hiç ama böylesi daha kullanışlı.
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void cizimYap(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(disRenk); // Arkada kalması gerektiği için dış renk daha önce
        g2d.fillOval(x, y, 52, 52); // Fill yerine draw kullanınca istediğim sonucu alamadım
        g2d.setColor(icRenk);
        g2d.fillOval(x+1, y+1, 50, 50); // (0,0) noktası pencerenin değil, panelin sol üst noktası
    }
    
}
