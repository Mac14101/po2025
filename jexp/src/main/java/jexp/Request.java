package jexp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Request {
    private final Headers headers;
    private final String[] route;
    private final String path;
    private final String protocol;
    private final String url;
    private final String method;
    private final HashMap<String, String> query;
    private final String body;

    public Request(HttpExchange exchange) throws IOException {
        this.headers = exchange.getRequestHeaders();
        this.url = exchange.getRequestURI().toString();
        Route route = new Route(exchange.getRequestURI().getPath());
        this.route = route.getRoute();
        this.path = route.getPath();
        this.protocol = exchange.getProtocol();
        this.method = exchange.getRequestMethod();
        this.query = new HashMap<String, String>();
        String queryString = exchange.getRequestURI().getQuery();
        if (queryString != null) {
            String[] query = queryString.split("&");
            for (int i = 0; i < query.length; i++) {
                String[] currentQuery = query[i].split("=");
                this.query.put(currentQuery[0], currentQuery[1]);
            }
        }
        InputStream bodyStream = exchange.getRequestBody();
        if (bodyStream != null) {
            this.body = new String(bodyStream.readAllBytes(), StandardCharsets.UTF_8);
        } else {
            this.body = null;
        }
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

    public String getQuery(String key) {
        return this.query.get(key);
    }

    public <T> T getBody(Class<T> type) throws JsonProcessingException {
        return JSONParser.parse(this.body, type);
    }

    public <T> T getBody(TypeReference<T> type) throws JsonProcessingException {
        return JSONParser.parse(this.body, type);
    }
}
