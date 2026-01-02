package jexp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jexp.defaultHandlers.NotFoundHandler;
import jexp.defaultHandlers.ServerErrorHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private Router mainRouter;
    private HttpServer server;
    private ErrorHandler errorHandler;

    public Server() {
        this.mainRouter = new Router();
        this.errorHandler = null;
    }

    public void use(String path, Handler handler) {
        this.mainRouter.use(path, handler);
    }

    public void get(String path, Handler handler) {
        this.mainRouter.use(path, new MethodHandler("get", handler));
    }

    public void post(String path, Handler handler) {
        this.mainRouter.use(path, new MethodHandler("post", handler));
    }

    public void put(String path, Handler handler) {
        this.mainRouter.use(path, new MethodHandler("put", handler));
    }

    public void delete(String path, Handler handler) {
        this.mainRouter.use(path, new MethodHandler("delete", handler));
    }

    public void error(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void listen(String host, int port) throws IOException {
        this.mainRouter.use("/", new NotFoundHandler());
        this.mainRouter.updateDepth();
        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        this.server.createContext("/", new ServerHandler(this.mainRouter, this.errorHandler));
    }

    public void listen(int port) throws IOException {
        this.mainRouter.use("/", new NotFoundHandler());
        this.mainRouter.updateDepth();
        this.server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        this.server.createContext("/", new ServerHandler(this.mainRouter, this.errorHandler));
        this.server.start();
    }

    public static class ServerHandler implements HttpHandler {
        Router router;
        ErrorHandler serverErrorHandler;

        public ServerHandler(Router router, ErrorHandler errorHandler) {
            this.router = router;
            if (errorHandler != null) {
                this.serverErrorHandler = errorHandler;
            } else {
                this.serverErrorHandler = new ServerErrorHandler();
            }
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                Request request = new Request(exchange);
                Response response = new Response();
                Next next = new Next();
                try {
                    this.router.handle(request, response, next);
                    response.sendResponse(exchange);
                } catch (Exception error) {
                    request = new Request(exchange);
                    response = new Response();
                    next = new Next();
                    this.serverErrorHandler.handle(error, request, response, next);
                    response.sendResponse(exchange);
                }
            } catch (Exception error) {
                System.err.println(error.getMessage());
                exchange.sendResponseHeaders(500, 0);
            }
        }
    }
}
