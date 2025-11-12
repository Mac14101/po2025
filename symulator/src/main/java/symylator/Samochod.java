package symylator;

public class Samochod {
    // Wartość 'false' oznacza samochód wyłączony, 'true' samochód włączony
    private boolean stanWlaczenia;
    private String nrRejestr;
    private String model;
    private double predkoscMax;
    private Silnik silnik;
    private SkrzyniaBiegow skrzyniaBiegow;
    private Pozycja pozycja;

    public Samochod(boolean stanWlaczenia, String nrRejestr, String model, double predkoscMax, Silnik silnik, SkrzyniaBiegow skrzyniaBiegow, Pozycja pozycja) {
        this.stanWlaczenia = stanWlaczenia;
        this.nrRejestr = nrRejestr;
        this.model = model;
        this.predkoscMax = predkoscMax;
        this.silnik = silnik;
        this.skrzyniaBiegow = skrzyniaBiegow;
        this.pozycja = pozycja;
    }

    public Samochod(String nrRejestr, String model, double predkoscMax, Silnik silnik, SkrzyniaBiegow skrzyniaBiegow, Pozycja pozycja) {
        this.stanWlaczenia = false;
        this.nrRejestr = nrRejestr;
        this.model = model;
        this.predkoscMax = predkoscMax;
        this.silnik = silnik;
        this.skrzyniaBiegow = skrzyniaBiegow;
        this.pozycja = pozycja;
    }

    public Samochod(String nrRejestr, String model, double predkoscMax, Pozycja pozycja) {
        this.nrRejestr = nrRejestr;
        this.model = model;
        this.predkoscMax = predkoscMax;
        this.pozycja = pozycja;
        this.silnik = new Silnik();
        this.skrzyniaBiegow = new SkrzyniaBiegow();
    }

    public Samochod(String nrRejestr, String model, double predkoscMax) {
        this.nrRejestr = nrRejestr;
        this.model = model;
        this.predkoscMax = predkoscMax;
        this.pozycja = new Pozycja(0d, 0d);
        this.skrzyniaBiegow = new SkrzyniaBiegow();
    }

    public void wlacz() {
        this.stanWlaczenia = true;
        this.silnik.uruchom();
    }

    public void wylacz() {
        this.stanWlaczenia = false;
        this.silnik.zatrzymaj();
    }

    public void jedzDo(Pozycja cel) {
        // TODO
    }

    public double getWaga() {
        // TODO
        return 0d;
    }

    public double getAktPredkosc() {
        // TODO
        return 0d;
    }

    public Pozycja getAktpozycja() {
        return this.pozycja;
    }
}
