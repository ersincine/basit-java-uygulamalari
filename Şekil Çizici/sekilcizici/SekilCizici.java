package sekilcizici;

import java.awt.EventQueue;

public class SekilCizici {
   
    public static void main(String[] args) {

        /* Ersin Çine, 2 Nisan 2013 */
        
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pencere().setVisible(true);
            }
        });

    }
    
}