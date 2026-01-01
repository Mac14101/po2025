package jexp;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RouteTest {

    @Test
    public void getPath() {
        Route route = new Route("/a/b/c");
        assertEquals("/a/b/c/", route.getPath());
        assertEquals("/b/c/", route.getNextPath());
        Route finalRoute = route;
        route = new Route("/");
        assertEquals("/", route.getPath());
    }

    @Test
    public void getRoute() {
        Route route = new Route("/a/b/c");
        assertArrayEquals(new String[]{"a", "b", "c"}, route.getRoute());
        assertEquals("b", route.getNextRoute());
        Route finalRoute = route;
        route = new Route("/");
        assertArrayEquals(new String[]{}, route.getRoute());
    }

    @Test
    public void getDepth() {
        Route route = new Route("/a/b/c");
        assertEquals(3, route.getDepth());
        route = new Route("/");
        assertEquals(0, route.getDepth());
    }
}