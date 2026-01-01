package jexp;

import java.util.ArrayList;

public class Router implements Handler {
    private final RoutingList routingList;
    private int depth;

    public Router() {
        this.routingList = new RoutingList();
        this.depth = 0;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public RoutingList getRoutingList() {
        return routingList;
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (request.getRoute().length == 0 && this.routingList.hasRoute("")) {
            ArrayList<Handler> handlers = this.routingList.getHandlers("");
            for (int i = 0; i < handlers.size(); i++) {
                Next nextHandler = new Next();
                handlers.get(i).handle(request, response, nextHandler);
                if (nextHandler.getNextRoute()) {
                    next.next();
                    break;
                } else if (!nextHandler.getNext()) {
                    break;
                } else if (nextHandler.getNext() && i + 1 == handlers.size()) {
                    next.next();
                }
            }
        } else if (request.getRoute().length > 0 && this.routingList.hasRoute(request.getRoute()[this.depth])) {
            ArrayList<Handler> handlers = this.routingList.getHandlers(request.getRoute()[this.depth]);
            for (int i = 0; i < handlers.size(); i++) {
                Next nextHandler = new Next();
                handlers.get(i).handle(request, response, nextHandler);
                if (nextHandler.getNextRoute()) {
                    next.next();
                    break;
                } else if (!nextHandler.getNext()) {
                    break;
                } else if (nextHandler.getNext() && i + 1 == handlers.size()) {
                    next.next();
                }
            }
        } else {
            next.next();
        }
    }

    public void use(String path, Handler handler) {
        if (handler instanceof Router) {
            ((Router) handler).setDepth(this.depth + 1);
        }
        Route route = new Route(path);
        if (route.getDepth() == 0) {
            this.routingList.addHandler("", handler);
        } else {
            if (this.routingList.hasRoute(route.getActualRoute()) && this.routingList.getHandlers(route.getActualRoute()).getLast() instanceof Router) {
                ((Router) this.routingList.getHandlers(route.getActualRoute()).getLast()).use(route.getNextPath(), handler);
            } else {
                Router routerNew = new Router();
                routerNew.setDepth(this.depth + 1);
                routerNew.use(route.getNextPath(), handler);
                this.routingList.addHandler(route.getActualRoute(), routerNew);
            }
        }

    }

    public void get(String path, Handler handler) {
        this.use(path, new MethodHandler("get", handler));
    }

    public void post(String path, Handler handler) {
        this.use(path, new MethodHandler("post", handler));
    }

    public void put(String path, Handler handler) {
        this.use(path, new MethodHandler("put", handler));
    }

    public void delete(String path, Handler handler) {
        this.use(path, new MethodHandler("delete", handler));
    }
}
