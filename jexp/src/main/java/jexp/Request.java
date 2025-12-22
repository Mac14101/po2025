package jexp;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class Request {
    private final Headers headers;
    private final String[] route;
    private final String path;
    private final String protocol;
    private final String url;
    private final String method;

    public Request(HttpExchange exchange) {
        this.headers = exchange.getRequestHeaders();
        this.url = exchange.getRequestURI().getPath();
        Route route = new Route(exchange.getRequestURI().getPath());
        this.route = route.getRoute();
        this.path = route.getPath();
        this.protocol = exchange.getProtocol();
        this.method = exchange.getRequestMethod();
    }

    public Headers getHeaders() {
        return this.headers;
    }

    public String[] getRoute() {
        return this.route;
    }

    public String getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getUrl() {
        return this.url;
    }

    public String getMethod() {
        return this.method;
    }
}
