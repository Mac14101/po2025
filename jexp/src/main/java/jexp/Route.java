package jexp;

public class Route {
    private final String[] route;

    public Route(String path) {
        String[] route = path.split("/");
        if (route.length == 0) {
            this.route = new String[0];
        } else {
            this.route = new String[route.length - 1];
            System.arraycopy(route, 1, this.route, 0, route.length - 1);
        }
    }

    public String getPath() {
        StringBuilder path = new StringBuilder("/");
        for (String s : route) {
            path.append(s).append("/");
        }
        return path.toString();
    }

    public String getNextPath(int depth) throws RouteError {
        if (depth >= route.length) {
            throw new RouteError("Cannot resolve next path with depth " + depth);
        }
        StringBuilder path = new StringBuilder("/");
        for (int i = depth; i < route.length; i++) {
            path.append(route[i]).append("/");
        }
        return path.toString();
    }


    public String[] getRoute() {
        return this.route;
    }

    public String getNextRoute(int depth) throws RouteError {
        if (depth >= route.length) {
            throw new RouteError("Cannot resolve next route with depth " + depth);
        }
        return route[depth];
    }

    public static class RouteError extends Exception {
        public RouteError(String message) {
            super(message);
        }
    }
}
