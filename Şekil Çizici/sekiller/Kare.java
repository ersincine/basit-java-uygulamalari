package sekiller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Kare extends Sekil {
    
    public Kare(Color icRenk, Color disRenk, int x, int y){
        this.icRenk = icRenk;
        this.disRenk = disRenk == null ? icRenk : disRenk;
        this.x = x;
        this.y = y;
    }
    
    public void cizimYap(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(disRenk);
        g2d.fillRect(x, y, 52, 52);
        g2d.setColor(icRenk);
        g2d.fillRect(x+1, y+1, 50, 50);
    }
}
