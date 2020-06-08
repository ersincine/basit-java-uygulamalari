package yardimcisiniflar;

import java.awt.Color;

public class Renkler {
    
    public static Color rengiGetir(String kelime){
        return rengiGetir(kelime, "siyah");
    }
    
    public static Color rengiGetir(String kelime, String varsayilan) {
        if(kelime.equalsIgnoreCase("sarı")) {
            return Color.YELLOW;
        } else if(kelime.equalsIgnoreCase("kırmızı")) {
            return Color.RED;
        } else if(kelime.equalsIgnoreCase("mavi")) {
            return Color.CYAN;
        } else if(kelime.equalsIgnoreCase("yeşil")) {
            return Color.GREEN;
        } else if(kelime.equalsIgnoreCase("turuncu")) {
            return Color.ORANGE;
        } else if(kelime.equalsIgnoreCase("pembe")) {
            return Color.PINK;
        } else if(kelime.equalsIgnoreCase("siyah")) {
            return Color.BLACK;
        } else if(kelime.equalsIgnoreCase("beyaz")) {
            return Color.WHITE;
        } else if(kelime.equalsIgnoreCase("gri")) {
            return Color.GRAY;
        }
        /* ... */
        else{
            switch (varsayilan) { // Tanımlanmamış bir renk istendiyse
                case "beyaz":
                    return new Color(255, 255, 255);
                case "siyah":
                    return new Color(0, 0, 0);
                default:
                    return new Color(100, 100, 100);
            }
        }   
    }
    
}
