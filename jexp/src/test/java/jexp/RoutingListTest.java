package jexp;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class RoutingListTest {

    @Test
    public void addHandler() {
        RoutingList routingList = new RoutingList();
        assertFalse(routingList.hasRoute("a"));
        Handler handler1 = Mockito.mock(Handler.class);
        routingList.addHandler("a", handler1);
        assertTrue(routingList.hasRoute("a"));
        assertSame(handler1, routingList.getHandlers("a").getFirst());
        Handler handler2 = Mockito.mock(Handler.class);
        routingList.addHandler("a", handler2);
        assertSame(handler1, routingList.getHandlers("a").getFirst());
        assertSame(handler2, routingList.getHandlers("a").getLast());
    }
}