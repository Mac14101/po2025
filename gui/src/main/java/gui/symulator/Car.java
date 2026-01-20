package gui.symulator;

import java.util.ArrayList;
import java.util.List;

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
    private List<Listener> listeners = new ArrayList<>();

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

    public void setDestination(Position destination) {
        System.out.println("Set target");
        this.destination = destination;
    }

    public double getSpeed() {
        if (this.destination.getX() == this.position.getX() && this.destination.getY() == this.position.getY()) {
            return 0;
        } else if (!this.status) {
            return 0;
        } else if (!this.gearbox.getClutch().getStatus()) {
            return 0;
        }
        return (double) (this.engine.getRPM() * this.gearbox.getGear()) / this.gearbox.getMaxGear() * 0.11d;
    }

    public double getWeight() {
        return this.weight + this.gearbox.getWeight() + this.engine.getWeight();
    }

    @Override
    public void run() {
        System.out.println("Car started");
        while (true) {
            if (this.destination.getX() != this.position.getX() || this.destination.getY() != this.position.getY()) {
                this.position.move(this.destination, this.getSpeed(), dt);
                notifyListeners();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Listener listener : listeners) {
            listener.update();
        }
    }
}
