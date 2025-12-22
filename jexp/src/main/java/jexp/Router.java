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
    public void handle(Request request, Response response, Next next) throws Response.ResponseError, Next.NextError {
        if (this.routingList.hasPath(request.getPath()[this.depth])) {
            ArrayList<Handler> handlers = this.routingList.getHandlers(request.getPath()[this.depth]);
            for (Handler handler : handlers) {
                Next nextHandler = new Next();
                handler.handle(request, response, nextHandler);
                if (nextHandler.getNextRoute()) {
                    next.next();
                    break;
                } else if (!nextHandler.getNext()) {
                    break;
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
        String[] paths = path.split("/");
        if (paths.length == 0) {
            this.routingList.addHandler("", handler);
        } else if (paths.length == 2) {
            this.routingList.addHandler(paths[1], handler);
        } else {
            Router routerNew = new Router();
            String newPath = "/";
            for (int i = 2; i < paths.length; i++) {
                newPath += paths[i] + "/";
            }
            routerNew.use(newPath, handler);
            this.routingList.addHandler(paths[1], routerNew);
        }
    }
}
