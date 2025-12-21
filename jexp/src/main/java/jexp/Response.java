package jexp;

import com.sun.net.httpserver.Headers;

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

    public void send(String data) throws Exception {
        if (this.closed) {
            throw new Exception(); // TODO : Dodać klasę błędu odpowiedzi
        }
        this.body = data;
        this.closed = true;
    }

    public void end() throws Exception {
        if (this.closed) {
            throw new Exception(); // TODO : Dodać klasę błędu odpowiedzi
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
}
