package symylator;

public class Sprzeglo extends Komponent {
    // Wartość 'false' oznacza sprzęgło wciścięte, 'true' oznacza sprzęgło zwolnione
    private boolean stanSprzegla;

    public Sprzeglo(double waga, double cena) {
        super("Sprzęgło", waga, cena);
        this.stanSprzegla = true;
    }

    public void wcisnij() {
        this.stanSprzegla = true;
    }

    public void zwolnij() {
        this.stanSprzegla = false;
    }
}
