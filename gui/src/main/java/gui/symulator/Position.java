package gui.symulator;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position() {
        this.x = 0d;
        this.y = 0d;
    }

    public void move(Position destination, double speed, double dt) {
        double distance = Math.sqrt(Math.pow(destination.getX() - this.x, 2) + Math.pow(destination.getY() - this.y, 2));
        double dx = speed * dt * (destination.getX() - this.x) / distance;
        double dy = speed * dt * (destination.getY() - this.y) / distance;
        if (Math.abs(dx) > distance) {
            dx = destination.getX() - this.x;
        }
        if (Math.abs(dy) > distance) {
            dy = destination.getY() - this.y;
        }
        this.x += dx;
        this.y += dy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
