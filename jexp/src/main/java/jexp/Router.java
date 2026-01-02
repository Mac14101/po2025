package jexp;

import java.util.ArrayList;
import java.util.Objects;

public class Router implements Handler {
    private ArrayList<Handler> handlers;
    private String route;

    Router() {
        this.handlers = new ArrayList<>();
        this.route = null;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRoute() {
        return this.route;
    }

    private Router findRoute(String route) {
        Router router=null;
        for (Handler handler : this.handlers) {
            if (handler instanceof Router && Objects.equals(((Router) handler).getRoute(), route)) {
                router = (Router) handler;
                break;
            }
        }
        return router;
    }

    public void use(String path, Handler handler){
        Route route = new Route(path);
        if(route.getRoute().length == 0){
            this.handlers.add(handler);
        }else{
            String actualRoute=route.getActualRoute();
            Router router=this.findRoute(actualRoute);
            if(router==null){
                Router newRouter=new Router();
                newRouter.setRoute(actualRoute);
                newRouter.use(route.getNextPath(), handler);
                this.handlers.add(newRouter);
            }else{
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

    }

}
