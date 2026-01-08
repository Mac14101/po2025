package symylator;

import static org.junit.Assert.assertEquals;

public class PozycjaTest {

    @org.junit.Test
    public void przemiesc() {
        double startX = 0d;
        double startY = 0d;
        double predkosc = 1;
        double dt = 0.01;
        Pozycja start = new Pozycja(startX, startY);
        Pozycja end = new Pozycja(1d, 1d);
        while (start.getX() != end.getX() && start.getY() != end.getY()) {
            start.przemiesc(end, predkosc, dt);
        }
        assertEquals(start.getX(), end.getX(), 0.005);
        assertEquals(start.getY(), end.getY(), 0.005);
        start = new Pozycja(startX, startY);
        end = new Pozycja(-1d, -1d);
        while (start.getX() != end.getX() && start.getY() != end.getY()) {
            start.przemiesc(end, predkosc, dt);
        }
        assertEquals(start.getX(), end.getX(), 0.005);
        assertEquals(start.getY(), end.getY(), 0.005);
        start = new Pozycja(startX, startY);
        end = new Pozycja(-1d, 1d);
        while (start.getX() != end.getX() && start.getY() != end.getY()) {
            start.przemiesc(end, predkosc, dt);
        }
        assertEquals(start.getX(), end.getX(), 0.005);
        assertEquals(start.getY(), end.getY(), 0.005);
        start = new Pozycja(startX, startY);
        end = new Pozycja(1d, -1d);
        while (start.getX() != end.getX() && start.getY() != end.getY()) {
            start.przemiesc(end, predkosc, dt);
        }
        assertEquals(start.getX(), end.getX(), 0.005);
        assertEquals(start.getY(), end.getY(), 0.005);
    }
}