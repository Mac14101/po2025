package jexp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jexp.defaultHandlers.NotFoundHandler;
import jexp.defaultHandlers.ServerErrorHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Klasa będąca wrapperem dla serwera HTTP.
 */
public class Server {
    private Router mainRouter;
    private HttpServer server;
    private ErrorHandler errorHandler;
    private Logger logger;

    public Server() {
        this.mainRouter = new Router();
        this.errorHandler = null;
    }

    public void use(Logger logger) {
        this.logger = logger;
    }

    /**
     * Dodaje nowy obiekt obsługi tras do głównego routera serwera.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void use(String path, Handler handler) throws Router.RouterError {
        this.mainRouter.use(path, handler);
    }

    /**
     * Dodaje obiekt obsługi trasy, który jest aktywny, tylko gdy żądanie posiada metodę HTTP 'GET'.
     * Opakowuje obiekt w obiekt klasy 'MethodHandler'.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void get(String path, Handler handler) throws Router.RouterError {
        this.mainRouter.get(path, handler);
    }

    /**
     * Dodaje obiekt obsługi trasy, który jest aktywny, tylko gdy żądanie posiada metodę HTTP 'POST'.
     * Opakowuje obiekt w obiekt klasy 'MethodHandler'.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void post(String path, Handler handler) throws Router.RouterError {
        this.mainRouter.post(path, handler);
    }

    /**
     * Dodaje obiekt obsługi trasy, który jest aktywny, tylko gdy żądanie posiada metodę HTTP 'PUT'.
     * Opakowuje obiekt w obiekt klasy 'MethodHandler'.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void put(String path, Handler handler) throws Router.RouterError {
        this.mainRouter.put(path, handler);
    }

    /**
     * Dodaje obiekt obsługi trasy, który jest aktywny, tylko gdy żądanie posiada metodę HTTP 'DELETE'.
     * Opakowuje obiekt w obiekt klasy 'MethodHandler'.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void delete(String path, Handler handler) throws Router.RouterError {
        this.mainRouter.delete(path, handler);
    }

    /**
     * Zapisuje obiekt obsługi błędów serwera.
     *
     * @param errorHandler obiekt obsługi błędów
     */
    public void error(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    /**
     * Aktywuje serwer HTTP, który zaczyna nasłuchiwać na podanym porcie.
     *
     * @param host nazwa hosta
     * @param port numer portu aplikacji
     * @throws IOException błąd występujący przy starcie serwera
     */
    public void listen(String host, int port) throws IOException, Router.RouterError {
        //Dodaje obiekt obsługujący niedopasowane trasy
        this.mainRouter.use("/", new NotFoundHandler());
        //Aktualizuje głębokości routerów
        this.mainRouter.updateDepth();
        //Uruchamia serwer
        this.server = HttpServer.create(new InetSocketAddress(host, port), 0);
        this.server.createContext("/", new ServerHandler(this.mainRouter, this.errorHandler, this.logger));
        this.server.start();
    }

    /**
     * Aktywuje serwer HTTP, który zaczyna nasłuchiwać na podanym porcie.
     *
     * @param port numer portu aplikacji
     * @throws IOException błąd występujący przy starcie serwera
     */

    public void listen(int port) throws IOException, Router.RouterError {
        this.listen("localhost", port);
    }

    /**
     * Klasa opakowująca interfejs HttpHandler, aby był zgodny z działaniem przetwarzania obiektów żądań i odpowiedzi w routerach.
     */
    public static class ServerHandler implements HttpHandler {
        Router router;
        ErrorHandler serverErrorHandler;
        Logger logger;

        public ServerHandler(Router router, ErrorHandler errorHandler, Logger logger) {
            this.router = router;
            if (errorHandler != null) {
                this.serverErrorHandler = errorHandler;
            } else {
                this.serverErrorHandler = new ServerErrorHandler();
            }
            this.logger = logger;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                Request request = new Request(exchange);
                Response response = new Response();
                Next next = new Next();
                try {
                    this.router.handle(request, response, next);
                    if (this.logger != null) {
                        logger.logResponse(request, response);
                    }
                    response.sendResponse(exchange);
                } catch (Exception error) {
                    request = new Request(exchange);
                    response = new Response();
                    this.serverErrorHandler.handle(error, request, response);
                    if (this.logger != null) {
                        logger.logError(error, request, response);
                    }
                    response.sendResponse(exchange);
                }
            } catch (Exception error) {
                if (this.logger != null) {
                    logger.criticalError(error);
                }
                exchange.sendResponseHeaders(500, 0);
            }
        }
    }
}
