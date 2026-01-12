package gui.symulator;

public class Car extends Thread {
    private static double dt = 0.01;
    private final String registerNumber;
    private final String model;
    private final double weight;
    private Position position;
    private Position destination;
    // Wartość 'false' oznacza samochód wyłączony, 'true' samochód włączony
    private boolean status;
    private Engine engine;
    private Gearbox gearbox;

    public Car(String registerNumber, String model, double weight, Engine engine, Gearbox gearbox) {
        this.position = new Position();
        this.destination = new Position();
        this.status = false;
        this.registerNumber = registerNumber;
        this.model = model;
        this.weight = weight;
        this.engine = engine;
        this.gearbox = gearbox;
    }

    public void turnOn() {
        this.status = true;
        this.engine.turnOn();
    }

    public void turnOff() {
        this.status = false;
        this.engine.turnOff();
    }

    public void decreaseGear() throws Gearbox.GearboxError {
        this.gearbox.decreaseGear();
    }

    public void increaseGear() throws Gearbox.GearboxError {
        this.gearbox.increaseGear();
    }

    public void pressClutch() {
        this.gearbox.pressClutch();
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getModel() {
        return model;
    }

    public Position getPosition() {
        return position;
    }

    public Engine getEngine() {
        return engine;
    }

    public Gearbox getGearbox() {
        return gearbox;
    }

    public void releaseClutch() {
        this.gearbox.releaseClutch();
    }

    public void increaseRPM() {
        this.engine.increaseRPM();
    }

    public void decraseRPM() {
        this.engine.decreaseRPM();
    }

    public double getWaga() {
        return this.weight + this.gearbox.getWeight() + this.engine.getWeight();
    }

    @Override
    public void run() {
        while (true) {
            while (this.destination.getX() != this.position.getX() && this.destination.getY() != this.destination.getY()) {
                this.position.przemiesc(this.destination, 0, this.dt);
            }
        }
    }
}
