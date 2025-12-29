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

    public Server() {
        this.mainRouter = new Router();
    }

    public void use(String path, Handler handler) throws Route.RouteError {
        mainRouter.use(path, handler);
    }

    public void listen(String host, int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        this.server.createContext("/", new ServerHandler(this.mainRouter));
    }

    public void listen(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        this.server.createContext("/", new ServerHandler(this.mainRouter));
        this.server.start();
    }

    public static class ServerHandler implements HttpHandler {
        Router router;
        NotFoundHandler notFoundHandler;
        ServerErrorHandler serverErrorHandler;

        public ServerHandler(Router router) {
            this.router = router;
            this.notFoundHandler = new NotFoundHandler();
            this.serverErrorHandler = new ServerErrorHandler();
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                Request request = new Request(exchange);
                Response response = new Response();
                Next next = new Next();
                try {
                    this.router.handle(request, response, next);
                    if (next.getNext() || next.getNextRoute()) {
                        next = new Next();
                        this.notFoundHandler.handle(request, response, next);
                    }
                    response.sendResponse(exchange);
                } catch (JExpError error) {
                    request = new Request(exchange);
                    response = new Response();
                    next = new Next();
                    this.serverErrorHandler.handle(request, response, next);
                    response.sendResponse(exchange);
                }
            } catch (JExpError error) {
                exchange.sendResponseHeaders(500, 0);
            }
        }
    }
}
