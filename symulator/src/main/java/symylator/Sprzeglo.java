package symylator;

public class Sprzeglo extends Komponent {
    // Wartość 'false' oznacza sprzęgło wciścięte, 'true' oznacza sprzęgło zwolnione
    private boolean stanSprzegla;

    public Sprzeglo(String nazwa, boolean stanSprzegla, double waga, double cena) {
        super(nazwa, waga, cena);
        this.stanSprzegla = stanSprzegla;
    }

    public Sprzeglo(double waga, double cena) {
        super(nazwa, waga, cena);
        this.stanSprzegla = true;
    }

    public void wcisnij() {
        this.stanSprzegla = false;
    }

    public void zwolnij() {
        this.stanSprzegla = true;
    }

    public boolean getStanSprzegla() {
        return this.stanSprzegla;
    }
}
