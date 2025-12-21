package jexp;

import java.util.ArrayList;
import java.util.HashMap;

public class RoutingList {
    private HashMap<String, ArrayList<Handler>> pathList;

    public RoutingList() {
        this.pathList = new HashMap<>();
    }

    public void addHandler(String path, Handler handler) {
        if (this.pathList.containsKey(path)) {
            this.pathList.get(path).add(handler);
        } else {
            this.pathList.put(path, new ArrayList<Handler>());
            this.pathList.get(path).add(handler);
        }
    }

    public ArrayList<Handler> getHandlers(String path) {
        return this.pathList.get(path);
    }

    public boolean hasPath(String path) {
        return this.pathList.containsKey(path);
    }
}
