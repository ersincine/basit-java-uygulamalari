package yardimcisiniflar;

public class MetinBolucu {
    
    public static String[] metniBol(String metin){
        String[] kelimeler = metin.split(" ");
        if(kelimeler.length == 2) {
            return ikiKelimeliTamlamayiCozumle(kelimeler);
        } else if(kelimeler.length == 4) {
            return dortKelimeliTamlamayiCozumle(kelimeler);
        } else {
            return null;
        }
        // return {İç renk, dış renk, şekil}
    }
    
    private static String[] ikiKelimeliTamlamayiCozumle(String[] tamlama){ // "mavi daire"
        String icRenk = tamlama[0]; // mavi
        String disRenk = icRenk;    // mavi
        String sekil = tamlama[1]; // daire
        return new String[] {icRenk, disRenk, sekil}; 
    }
    
    private static String[] dortKelimeliTamlamayiCozumle(String[] tamlama){ // "kırmızı çizgili sarı kare"
        String disRenk = tamlama[0]; // kırmızı
                    // = tamlama[1]; // çizgili
        String icRenk = tamlama[2]; // sarı
        String sekil = tamlama[3]; // kare
        return new String[] {icRenk, disRenk, sekil};
    }
    
}