package gui.symulator;

public class SkrzyniaBiegow extends Komponent {
    Sprzeglo sprzeglo;
    private int aktualnyBieg;
    private int iloscBiegow;
    private double aktualnePrzelozenie;

    public SkrzyniaBiegow(String nazwa, int iloscBiegow, Sprzeglo sprzeglo, double waga, double cena) {
        super(nazwa, waga, cena);
        this.aktualnyBieg = 0;
        this.iloscBiegow = iloscBiegow;
        this.aktualnePrzelozenie = 0;
        this.sprzeglo = sprzeglo;
    }

    void nacisnijSprzeglo() {
        this.sprzeglo.wcisnij();
    }

    public Sprzeglo getSprzeglo() {
        return sprzeglo;
    }

    void zwolnijSprzeglo() {
        this.sprzeglo.zwolnij();
    }

    void zwiekszBieg() {
        if (!this.sprzeglo.getStanSprzegla()) {
            if (this.aktualnyBieg < this.iloscBiegow) {
                this.aktualnyBieg++;
            }
        } else {/* TODO Wyrzuć błąd */}
    }

    void zmniejszBieg() {
        if (!this.sprzeglo.getStanSprzegla()) {
            if (this.aktualnyBieg > 0) {
                this.aktualnyBieg--;
            }
        } else {/* TODO Wyrzuć błąd */}
    }

    public int getAktualnyBieg() {
        return this.aktualnyBieg;
    }

    public double getAktualnePrzelozenie() {
        return this.aktualnePrzelozenie;
    }

    public double getWaga() {
        return this.waga + this.sprzeglo.getWaga();
    }
}
