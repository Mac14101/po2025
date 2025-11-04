package symylator;

public class SkrzyniaBiegow extends Komponent {
    private int aktualnyBieg;
    private int iloscBiegow;
    private double aktualnePrzelozenie;

    public SkrzyniaBiegow(int iloscBiegow, double waga, double cena) {
        super("Skrzynia biegów", waga, cena);
        this.iloscBiegow = iloscBiegow;
        this.aktualnyBieg = 0;
        this.aktualnePrzelozenie = 0;
    }

    void zwiekszBieg() {
        this.aktualnyBieg++;
    }

    void zmniejszBieg() {
        this.aktualnyBieg--;
    }

    public int getAktualnyBieg() {
        return this.aktualnyBieg;
    }

    public double getAktualnePrzelozenie() {
        return this.aktualnePrzelozenie;
    }
}
