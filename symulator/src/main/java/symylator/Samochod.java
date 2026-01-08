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

    public Samochod(String nrRejestr, String model, Silnik silnik, SkrzyniaBiegow skrzyniaBiegow, Pozycja pozycja) {
        this.stanWlaczenia = false;
        this.nrRejestr = nrRejestr;
        this.model = model;
        this.predkoscMax = 160;
        this.silnik = silnik;
        this.skrzyniaBiegow = skrzyniaBiegow;
        this.pozycja = pozycja;
    }

    public void wlacz() {
        this.stanWlaczenia = true;
        this.silnik.uruchom();
    }

    public void wylacz() {
        this.stanWlaczenia = false;
        this.silnik.zatrzymaj();
    }

    public void zmniejszBieg() {
        this.skrzyniaBiegow.zmniejszBieg();
    }

    public void zwiekszBieg() {
        this.skrzyniaBiegow.zwiekszBieg();
    }

    public void nacisnijSprzeglo() {
        this.skrzyniaBiegow.nacisnijSprzeglo();
    }

    public void zwolnijsprezglo() {
        this.skrzyniaBiegow.zwolnijSprzeglo();
    }

    public void dodajGazu() {
        this.silnik.zwiekszObroty();
    }

    public void ujmijGazu() {
        this.silnik.zmniejszObroty();
    }


    public void jedzDo(Pozycja cel) {
        // TODO
    }

    public double getWaga() {
        return this.silnik.getWaga() + this.skrzyniaBiegow.getWaga();
    }

    public double getAktPredkosc() {
        return 0d;
    }

    public Pozycja getAktpozycja() {
        return this.pozycja;
    }
}
