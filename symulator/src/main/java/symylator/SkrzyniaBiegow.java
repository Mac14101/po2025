package symylator;

public class SkrzyniaBiegow extends Komponent {
    private int aktualnyBieg;
    private int iloscBiegow;
    private double aktualnePrzelozenie;
    Sprzeglo sprzeglo;

    public SkrzyniaBiegow(int aktualnyBieg, int iloscBiegow, double aktualnePrzelozenie, Sprzeglo sprzeglo, double waga, double cena) {
        super("Skrzynia biegów", waga, cena);
        this.aktualnyBieg = aktualnyBieg;
        this.iloscBiegow = iloscBiegow;
        this.aktualnePrzelozenie = aktualnePrzelozenie;
        this.sprzeglo = sprzeglo;
    }

    public SkrzyniaBiegow(int iloscBiegow, Sprzeglo sprzeglo, double waga, double cena) {
        super("Skrzynia biegów", waga, cena);
        this.iloscBiegow = iloscBiegow;
        this.aktualnyBieg = 0;
        this.aktualnePrzelozenie = 0;
        this.sprzeglo = sprzeglo;
    }

    public SkrzyniaBiegow() {
        super("Skrzynia biegów", 45d, 16000d);
        this.iloscBiegow = 5;
        this.aktualnyBieg = 0;
        this.sprzeglo = new Sprzeglo();
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
