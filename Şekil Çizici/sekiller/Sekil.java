package sekiller;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Sekil{
    
    protected Color icRenk;
    protected Color disRenk;
    protected int x;
    protected int y;
    
    public abstract void cizimYap(Graphics g);
    
}