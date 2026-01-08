package gui.symulator;

public abstract class Komponent {
    protected double waga;
    private String nazwa;
    private double cena;

    public Komponent(String nazwa, double waga, double cena) {
        this.nazwa = nazwa;
        this.waga = waga;
        this.cena = cena;
    }

    public String getNazwa() {
        return this.nazwa;
    }

    public double getWaga() {
        return this.waga;
    }

    public double getCena() {
        return this.cena;
    }
}
