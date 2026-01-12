package gui.symulator;

public class Engine extends Component {
    private int maxRPM;
    private int RPM;

    public Engine(String name, int maxRPM, double weight, double price) {
        super(name, weight, price);
        this.maxRPM = maxRPM;
        this.RPM = 0;
    }

    public int getRPM() {
        return RPM;
    }

    public int getMaxRPM() {
        return maxRPM;
    }

    public void turnOn() {
        if (this.RPM == 0) {
            this.RPM = 800;
        }
    }

    public void turnOff() {
        this.RPM = 0;
    }

    public void increaseRPM() {
        if (this.RPM + 10 < this.maxRPM && this.RPM > 0) {
            this.RPM += 10;
        }
    }

    public void decreaseRPM() {
        if (this.RPM - 10 >= 800) {
            this.RPM -= 10;
        }
    }
}
