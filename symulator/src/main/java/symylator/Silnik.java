package symylator;

public class Silnik extends Komponent {
    private int maxObroty;
    private int obroty;

    public Silnik(String nazwa, int maxObroty, int obroty, double waga, double cena) {
        super(nazwa, waga, cena);
        this.maxObroty = maxObroty;
        this.obroty = obroty;
    }

    public Silnik(String nazwa, double waga, double cena) {
        super(nazwa, waga, cena);
        this.maxObroty = 8000;
        this.obroty = 0;
    }

    public void uruchom() {
        if (this.obroty == 0) {
            this.obroty = 800;
        }
    }

    public void zatrzymaj() {
        this.obroty = 0;
    }

    public void zwiekszObroty() {
        if (this.obroty + 10 < this.maxObroty) {
            this.obroty += 10;
        }
    }

    public void zmniejszObroty() {
        if (this.obroty - 10 < 800) {
            this.obroty -= 10;
        }
    }
}
