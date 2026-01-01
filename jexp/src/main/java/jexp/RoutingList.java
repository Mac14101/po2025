package jexp;

import java.util.ArrayList;
import java.util.HashMap;

public class RoutingList {
    private HashMap<String, ArrayList<Handler>> routes;

    public RoutingList() {
        this.routes = new HashMap<>();
    }

    public void addHandler(String route, Handler handler) {
        if (this.routes.containsKey(route)) {
            this.routes.get(route).add(handler);
        } else {
            this.routes.put(route, new ArrayList<Handler>());
            this.routes.get(route).add(handler);
        }
    }

    public ArrayList<Handler> getHandlers(String route) {
        return this.routes.get(route);
    }

    public boolean hasRoute(String route) {
        return this.routes.containsKey(route);
    }
}
