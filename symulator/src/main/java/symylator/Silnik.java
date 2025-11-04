package symylator;

public class Silnik extends Komponent {
    private int maxObroty;
    private int obroty;

    public Silnik(int maxObroty, int obroty, double waga, double cena) {
        super("Silnik", waga, cena);
        this.maxObroty = maxObroty;
        this.obroty = obroty;
    }

    public Silnik(int maxObroty, double waga, double cena) {
        super("Silnik", waga, cena);
        this.maxObroty = maxObroty;
        this.obroty = 0;
    }

    public Silnik() {
        super("Silnik", 100d, 15000d);
        this.maxObroty = 8000;
        this.obroty = 0;
    }

    public void uruchom() {
        this.obroty = 1000;
    }

    public void zatrzymaj() {
        this.obroty = 0;
    }

    public void zwiekszObroty() {
        this.obroty += 10;
    }

    public void zmniejszObroty() {
        this.obroty -= 10;
    }
}
