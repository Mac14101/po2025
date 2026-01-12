package gui.symulator;

public class Gearbox extends Component {
    Clutch clutch;
    private int gear;
    private int maxGear;

    public Gearbox(String name, int maxGear, Clutch clutch, double weight, double price) {
        super(name, weight, price);
        this.gear = 0;
        this.maxGear = maxGear;
        this.clutch = clutch;
    }

    void pressClutch() {
        this.clutch.press();
    }

    void releaseClutch() {
        this.clutch.release();
    }


    void increaseGear() throws GearboxError {
        if (!this.clutch.getStatus()) {
            if (this.gear < this.maxGear) {
                this.gear++;
            }
        } else {
            throw new GearboxError("Najpierw naciśnij sprzęgło.");
        }
    }

    void decreaseGear() throws GearboxError {
        if (!this.clutch.getStatus()) {
            if (this.gear > 0) {
                this.gear--;
            }
        } else {
            throw new GearboxError("Najpierw naciśnij sprzęgło.");
        }
    }


    public double getWeight() {
        return this.weight + this.clutch.getWeight();
    }

    public Clutch getClutch() {
        return this.clutch;
    }

    public static class GearboxError extends Exception {
        public GearboxError(String message) {
            super(message);
        }
    }
}
