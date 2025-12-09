package symylator;

public class Silnik extends Komponent {
    private int maxObroty;
    private int obroty;

    public Silnik(String nazwa, int maxObroty, int obroty, double waga, double cena) {
        super(nazwa, waga, cena);
        this.maxObroty = maxObroty;
        this.obroty = obroty;
    }

    public Silnik(String nazwa, int maxObroty, double waga, double cena) {
        super(nazwa, waga, cena);
        this.maxObroty = maxObroty;
        this.obroty = 0;
    }

    public Silnik(Strina nazwa) {
        super(nazwa, 100d, 15000d);
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
