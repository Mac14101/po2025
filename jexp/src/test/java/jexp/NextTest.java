package jexp;

import org.junit.Test;

import static org.junit.Assert.*;

public class NextTest {

    @Test
    public void next() throws Exception {
        Next next = new Next();
        assertFalse(next.getNext());
        assertFalse(next.getNextRoute());
        next.next();
        assertTrue(next.getNext());
        assertFalse(next.getNextRoute());
        assertThrows(Next.NextError.class, () -> {
            next.next();
        });
    }

    @Test
    public void nextRoute() throws Exception {
        Next next = new Next();
        assertFalse(next.getNext());
        assertFalse(next.getNextRoute());
        next.nextRoute();
        assertFalse(next.getNext());
        assertTrue(next.getNextRoute());
        assertThrows(Next.NextError.class, () -> {
            next.nextRoute();
        });
    }
}