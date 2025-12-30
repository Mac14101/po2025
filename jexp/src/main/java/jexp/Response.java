package jexp;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private final Headers headers;
    private int statusCode;
    private String body;
    private boolean closed;

    public Response(Headers headers) {
        this.headers = headers;
        this.statusCode = 200;
        this.body = null;
        this.closed = false;
    }

    public Response() {
        this.headers = new Headers();
        this.statusCode = 200;
        this.body = null;
        this.closed = false;
    }

    public void status(int code) {
        this.statusCode = code;
    }

    public void header(String headerName, String headerValue) {
        this.headers.add(headerName, headerValue);
    }

    public void type(String type) {
        this.header("Content-Type", type);
    }

    public void send(String data) throws ResponseError {
        if (this.closed) {
            throw new ResponseError("Response has been closed!");
        }
        this.body = data;
        this.closed = true;
    }

    public void end() throws ResponseError {
        if (this.closed) {
            throw new ResponseError("Response has been closed!");
        }
        this.closed = true;
    }


    public Headers getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }


    public String getBody() {
        return body;
    }

    public boolean isClosed() {
        return closed;
    }


    public void cookie(String name, String value) {
        StringBuilder cookie = new StringBuilder();
        cookie.append(name).append("=").append(value).append(";");
        cookie.append("Path=/").append(";");
        cookie.append("HttpOnly").append(";");
        cookie.append("Secure=false").append(";");
        cookie.append("Max-Age=3600");
        this.headers.add("Set-Cookie", cookie.toString());
    }

    public void cookie(String name, String value, int maxAge) {
        StringBuilder cookie = new StringBuilder();
        cookie.append(name).append("=").append(value).append(";");
        cookie.append("Path=/").append(";");
        cookie.append("HttpOnly").append(";");
        cookie.append("Secure=false").append(";");
        cookie.append("Max-Age=").append(maxAge);
        this.headers.add("Set-Cookie", cookie.toString());
    }

    public void cookie(String name, String value, int maxAge, String path) {
        StringBuilder cookie = new StringBuilder();
        cookie.append(name).append("=").append(value).append(";");
        cookie.append("Path=").append(path).append(";");
        cookie.append("HttpOnly").append(";");
        cookie.append("Secure=false").append(";");
        cookie.append("Max-Age=").append(maxAge);
        this.headers.add("Set-Cookie", cookie.toString());
    }

    public void sendResponse(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().putAll(headers);
        exchange.sendResponseHeaders(statusCode, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
        exchange.close();
    }

    public static class ResponseError extends JExpError {
        public ResponseError(String message) {
            super(message);
        }
    }
}
