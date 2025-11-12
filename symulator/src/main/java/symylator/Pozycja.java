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
        double distance = Math.sqrt(Math.pow(this.x - cel.getX(), 2) + Math.pow(this.y - cel.getY(), 2));
        double deltaDistance = predkosc * dt;
        this.x = this.x + deltaDistance * (cel.getX() - this.x) / distance;
        this.y = this.y + deltaDistance * (cel.getY() - this.y) / distance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
