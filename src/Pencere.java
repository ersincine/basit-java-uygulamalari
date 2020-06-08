// 
// Decompiled by Procyon v0.5.36
// 

package altyazikelimeleri;

import java.awt.Container;
import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.JFrame;

public class Pencere extends JFrame
{
    File altyaziDosyasi;
    
    public Pencere() {
        this.altyaziDosyasi = null;
        final JPanel tepsi = new JPanel(new GridLayout(1, 2, 20, 20));
        final JButton btnGozat = new JButton("G\u00f6zat");
        btnGozat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
                final FileNameExtensionFilter srtFiltresi = new FileNameExtensionFilter("Yaln\u0131zca \u0130ngilizce SRT dosyalar\u0131 desteklenmektedir.", new String[] { "srt" });
                fc.setFileFilter(srtFiltresi);
                final int donenDeger = fc.showOpenDialog(null);
                if (donenDeger == 0) {
                    Pencere.this.altyaziDosyasi = fc.getSelectedFile();
                    JOptionPane.showMessageDialog(null, Pencere.this.altyaziDosyasi.getName() + " dosyas\u0131 se\u00e7ildi.", "Dosya se\u00e7ildi", 1);
                }
            }
        });
        tepsi.add(btnGozat);
        final JButton btnOlustur = new JButton("Olu\u015ftur!");
        btnOlustur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (Pencere.this.altyaziDosyasi != null) {
                    Altyazi.olustur(Pencere.this.altyaziDosyasi);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Dosya se\u00e7ilmedi!", "Hata", 0);
                }
            }
        });
        tepsi.add(btnOlustur);
        this.setContentPane(tepsi);
    }
}
