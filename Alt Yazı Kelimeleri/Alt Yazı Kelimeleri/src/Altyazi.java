// 
// Decompiled by Procyon v0.5.36
// 

package altyazikelimeleri;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Locale;
import java.io.IOException;
import java.awt.Component;
import javax.swing.JOptionPane;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.TreeSet;
import java.io.File;

public class Altyazi
{
    public static void olustur(final File altyaziDosyasi) {
        final String[] farkliKelimeler = farkliKelimeleriGetir(altyaziDosyasi);
        final String eskiDosyaAdresi = altyaziDosyasi.getAbsolutePath().substring(0, altyaziDosyasi.getAbsolutePath().lastIndexOf("."));
        final String yeniDosyaAdresi = eskiDosyaAdresi + ".txt";
        farkliKelimeleriYaz(farkliKelimeler, yeniDosyaAdresi);
    }
    
    private static String[] farkliKelimeleriGetir(final File altyaziDosyasi) {
        final TreeSet<String> farkliKelimeler = new TreeSet<String>();
        try (final BufferedReader br = new BufferedReader(new FileReader(altyaziDosyasi))) {
            for (String satir = br.readLine(); satir != null; satir = br.readLine()) {
                final String[] split;
                final String[] kelimeler = split = satir.split(" ");
                for (final String s : split) {
                    if (harfIceriyorMu(s)) {
                        farkliKelimeler.add(ayikla(s));
                    }
                }
            }
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Beklenmedik bir hata!", "Hata", 0);
        }
        final String[] farkliKelimelerDizi = farkliKelimeler.toArray(new String[farkliKelimeler.size()]);
        return farkliKelimelerDizi;
    }
    
    private static boolean harfIceriyorMu(final String satir) {
        return satir.matches(".*[A-Za-z].*");
    }
    
    private static String ayikla(String s) {
        s = s.replaceAll("\\s*(<[^<]*.*[^<]*>)\\s*", " ").toLowerCase(Locale.ENGLISH).trim();
        s = s.replaceAll("(\\W*)(.*)", "$2");
        s = s.replaceAll("(\\w*)(.*)", "$1");
        return s;
    }
    
    private static void farkliKelimeleriYaz(final String[] farkliKelimeler, final String dosyaAdresi) {
        try (final BufferedWriter bw = new BufferedWriter(new FileWriter(dosyaAdresi))) {
            int k = 0;
            for (int i = 0; i < farkliKelimeler.length; ++i) {
                bw.write(farkliKelimeler[i]);
                bw.write(" ");
                k += farkliKelimeler[i].length() + 1;
                if (k > 60) {
                    bw.newLine();
                    k = 0;
                }
            }
            bw.newLine();
            bw.flush();
            JOptionPane.showMessageDialog(null, dosyaAdresi + " dosyas\u0131 olu\u015fturuldu.", "Dosya olu\u015fturuldu", 1);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Beklenmedik bir hata!", "Hata", 0);
        }
    }
}
