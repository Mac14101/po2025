package gui.symulator;

public class Samochod extends Thread {
    public Pozycja cel;
    // Wartość 'false' oznacza samochód wyłączony, 'true' samochód włączony
    private boolean stanWlaczenia;
    private String nrRejestr;
    private String model;
    private Silnik silnik;
    private SkrzyniaBiegow skrzyniaBiegow;
    private Pozycja pozycja;
    private double dt;

    public Samochod(String nrRejestr, String model, Silnik silnik, SkrzyniaBiegow skrzyniaBiegow, Pozycja pozycja) {
        this.stanWlaczenia = false;
        this.nrRejestr = nrRejestr;
        this.model = model;
        this.silnik = silnik;
        this.skrzyniaBiegow = skrzyniaBiegow;
        this.pozycja = pozycja;
        this.cel = pozycja;
        this.start();
    }

    public Silnik getSilnik() {
        return silnik;
    }

    public SkrzyniaBiegow getSkrzyniaBiegow() {
        return skrzyniaBiegow;
    }

    public String getModel() {
        return model;
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
        this.cel = cel;
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

    @Override
    public void run() {
        while (true) {
            while (this.cel.getX() != this.pozycja.getX() && this.cel.getY() != this.pozycja.getY()) {
                this.pozycja.przemiesc(this.cel, this.getAktPredkosc(), this.dt);
                System.out.println(this.getAktPredkosc());
            }
        }
    }

    public String getNrRejestr() {
        return this.nrRejestr;
    }
}
