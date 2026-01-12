package gui.symulator;

public class Clutch extends Component {
    // Wartość 'false' oznacza sprzęgło wciścięte, 'true' oznacza sprzęgło zwolnione
    private boolean status;

    public Clutch(String name, double weight, double price) {
        super(name, weight, price);
        this.status = true;
    }

    public boolean getStatus() {
        return status;
    }

    public void press() {
        this.status = false;
    }

    public void release() {
        this.status = true;
    }

}
