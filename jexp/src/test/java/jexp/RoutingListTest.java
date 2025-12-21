package jexp;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class RoutingListTest {
    @Test
    public void testRoutingList() {
        RoutingList routingList = new RoutingList();
        Handler handler = Mockito.mock(Handler.class);
        assertFalse(routingList.hasPath("path"));
        assertNull(routingList.getHandlers("path"));
        routingList.addHandler("path", handler);
        assertTrue(routingList.hasPath("path"));
        assertSame(1, routingList.getHandlers("path").size());
        assertSame(handler, routingList.getHandlers("path").getFirst());
        Handler handlerNew1 = Mockito.mock(Handler.class);
        routingList.addHandler("path", handlerNew1);
        assertTrue(routingList.hasPath("path"));
        assertSame(2, routingList.getHandlers("path").size());
        assertSame(handlerNew1, routingList.getHandlers("path").get(1));
        assertFalse(routingList.hasPath("pathNew"));
        Handler handlerNew2 = Mockito.mock(Handler.class);
        routingList.addHandler("pathNew", handlerNew2);
        assertTrue(routingList.hasPath("pathNew"));
        assertSame(1, routingList.getHandlers("pathNew").size());
        assertSame(handlerNew2, routingList.getHandlers("pathNew").getFirst());
    }
}