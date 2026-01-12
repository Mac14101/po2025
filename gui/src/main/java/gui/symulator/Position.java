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

    public void przemiesc(Position cel, double speed, double dt) {
        double distance = Math.sqrt(Math.pow(cel.getX() - this.x, 2) + Math.pow(cel.getY() - this.y, 2));
        double dx = speed * dt * (cel.getX() - this.x) / distance;
        double dy = speed * dt * (cel.getY() - this.y) / distance;
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
