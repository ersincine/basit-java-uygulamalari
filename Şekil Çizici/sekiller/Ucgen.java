package sekiller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Ucgen extends Sekil {
 
    public Ucgen(Color icRenk, Color disRenk, int x, int y){
        this.icRenk = icRenk;
        this.disRenk = disRenk == null ? icRenk : disRenk;
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void cizimYap(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        int[] xler = {x, x+26, x+52};
        int[] yler = {y+52, y, y+52};
        int n = 3;
        Polygon p = new Polygon(xler, yler, n);
        g2d.setColor(icRenk);
        g2d.fillPolygon(p); 
        g2d.setColor(disRenk);
        g2d.drawPolygon(p);
    }
    
}
