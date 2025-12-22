package jexp;

import org.junit.Test;

import static org.junit.Assert.*;

public class RouteTest {

    @Test
    public void getPath() throws Route.RouteError {
        Route route = new Route("/a/b/c");
        assertEquals("/a/b/c/", route.getPath());
        assertEquals("/b/c/", route.getNextPath(1));
        assertEquals("/c/", route.getNextPath(2));
        Route finalRoute = route;
        assertThrows(Route.RouteError.class, () -> {
            finalRoute.getNextPath(3);
        });
        route = new Route("/");
        assertEquals("/", route.getPath());
    }

    @Test
    public void getRoute() throws Route.RouteError {
        Route route = new Route("/a/b/c");
        assertArrayEquals(new String[]{"a", "b", "c"}, route.getRoute());
        assertEquals("b", route.getNextRoute(1));
        assertEquals("c", route.getNextRoute(2));
        Route finalRoute = route;
        assertThrows(Route.RouteError.class, () -> {
            finalRoute.getNextRoute(3);
        });
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