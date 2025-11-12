package symylator;

import static org.junit.Assert.*;

public class PozycjaTest {

    @org.junit.Test
    public void przemiesc() {
        double startX = 0d;
        double startY = 0d;
        double predkosc = 1;
        double dt = 0.01;
        Pozycja pozycja = new Pozycja(startX, startY);
        double destinationX = 1d;
        double destinationY = 1d;
        pozycja.przemiesc(new Pozycja(destinationX, destinationY), predkosc, dt);
        assertEquals(0.01, pozycja.getX(), 0.005);
        assertEquals(0.01, pozycja.getY(), 0.005);
        pozycja = new Pozycja(startX, startY);
        destinationX = -1d;
        destinationY = -1d;
        pozycja.przemiesc(new Pozycja(destinationX, destinationY), predkosc, dt);
        assertEquals(-0.01, pozycja.getX(), 0.005);
        assertEquals(-0.01, pozycja.getY(), 0.005);
        pozycja = new Pozycja(startX, startY);
        destinationX = 1d;
        destinationY = -1d;
        pozycja.przemiesc(new Pozycja(destinationX, destinationY), predkosc, dt);
        assertEquals(0.01, pozycja.getX(), 0.005);
        assertEquals(-0.01, pozycja.getY(), 0.005);
        pozycja = new Pozycja(startX, startY);
        destinationX = -1d;
        destinationY = 1d;
        pozycja.przemiesc(new Pozycja(destinationX, destinationY), predkosc, dt);
        assertEquals(-0.01, pozycja.getX(), 0.005);
        assertEquals(0.01, pozycja.getY(), 0.005);
    }
}