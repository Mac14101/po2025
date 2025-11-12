package symylator;

public class Sprzeglo extends Komponent {
    // Wartość 'false' oznacza sprzęgło wciścięte, 'true' oznacza sprzęgło zwolnione
    private boolean stanSprzegla;

    public Sprzeglo(boolean stanSprzegla, double waga, double cena) {
        super("Sprzęgło", waga, cena);
        this.stanSprzegla = stanSprzegla;
    }

    public Sprzeglo(double waga, double cena) {
        super("Sprzęgło", waga, cena);
        this.stanSprzegla = true;
    }

    public Sprzeglo() {
        super("Sprzęgło", 6d, 1800d);
        this.stanSprzegla = true;
    }

    public void wcisnij() {
        this.stanSprzegla = false;
    }

    public void zwolnij() {
        this.stanSprzegla = true;
    }
}
