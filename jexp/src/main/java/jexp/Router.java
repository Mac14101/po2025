package jexp;

import java.util.ArrayList;
import java.util.Objects;

public class Router implements Handler {
    private final ArrayList<Handler> handlers;
    private String route;
    private int depth;

    Router() {
        this.handlers = new ArrayList<>();
        this.route = null;
        this.depth = -1;
    }

    public String getRoute() {
        return this.route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void updateDepth() {
        for (Handler handler : this.handlers) {
            if (handler instanceof Router) {
                ((Router) handler).setDepth(this.depth + 1);
                ((Router) handler).updateDepth();
            } else if (handler instanceof ParamHandler) {
                ((ParamHandler) handler).setDepth(this.depth + 1);
            }
        }
    }

    private Router findRoute(String route) {
        Router router = null;
        for (int i = this.handlers.size() - 1; i >= 0; i--) {
            if (this.handlers.get(i) instanceof Router && Objects.equals(((Router) this.handlers.get(i)).getRoute(), route)) {
                router = (Router) this.handlers.get(i);
                break;
            }
        }
        return router;
    }

    public void use(String path, Handler handler) {
        Route route = new Route(path);
        if (route.getRoute().length == 0) {
            this.handlers.add(handler);
        } else if (route.getRoute().length == 1 && handler instanceof Router) {
            ((Router) handler).setRoute(route.getActualRoute());
            this.handlers.add(handler);
        } else {
            String actualRoute = route.getActualRoute();
            Router router = this.findRoute(actualRoute);
            if (router == null) {
                if (actualRoute.matches("^:.*$")) {
                    this.handlers.add(new ParamHandler(actualRoute));
                }
                Router newRouter = new Router();
                newRouter.setRoute(actualRoute);
                newRouter.use(route.getNextPath(), handler);
                this.handlers.add(newRouter);
            } else {
                router.use(route.getNextPath(), handler);
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

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (this.route == null || this.route.equals(request.getRoute()[this.depth])) {
            for (int i = 0; i < this.handlers.size(); i++) {
                Next newNext = new Next();
                this.handlers.get(i).handle(request, response, newNext);
                if (newNext.getNextRoute()) {
                    next.next();
                    return;
                } else if (!newNext.getNext()) {
                    return;
                }
            }
            next.next();
        } else {
            next.next();
        }
    }

}
