package jexp;

import org.junit.Test;

import static org.junit.Assert.*;

public class RouteTest {

    @Test
    public void getPath() {
        Route route = new Route("/a/b/c");
        assertEquals("/a/b/c/", route.getPath());
        assertEquals("/b/c/", route.getNextPath());
        Route finalRoute = route;
        route = new Route("/");
        assertEquals("/", route.getPath());
        assertEquals("/", route.getNextPath());
    }

    @Test
    public void getRoute() {
        Route route = new Route("/a/b/c");
        assertArrayEquals(new String[]{"a", "b", "c"}, route.getRoute());
        assertEquals("a", route.getActualRoute());
        assertEquals("b", route.getNextRoute());
        Route finalRoute = route;
        route = new Route("/");
        assertArrayEquals(new String[]{}, route.getRoute());
        assertNull(route.getActualRoute());
        assertNull(route.getNextRoute());
    }
}