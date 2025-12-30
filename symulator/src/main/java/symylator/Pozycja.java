package symylator;

public class Pozycja {
    private double x;
    private double y;

    public Pozycja(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Pozycja() {
        this.x = 0d;
        this.y = 0d;
    }

    public void przemiesc(Pozycja cel, double predkosc, double dt) {
        double distance = Math.sqrt(Math.pow(cel.getX() - this.x, 2) + Math.pow(cel.getY() - this.y, 2));
        double dx = predkosc * dt * (cel.getX() - this.x) / distance;
        double dy = predkosc * dt * (cel.getY() - this.y) / distance;
        if (Math.abs(dx) > distance) {
            dx = cel.getX() - this.x;
        }
        if (Math.abs(dy) > distance) {
            dy = cel.getY() - this.y;
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
