package jexp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import json.JSON;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Obiekt odpowiedzi HTTP.
 * Zawiera informacje o odpowiedzi takie jak:
 * <ul>
 *     <li>Nagłówki HTTP odpowiedzi</li>
 *     <li>Kod odpowiedzi HTTP</li>
 *     <li>Ciało odpowiedzi</li>
 *     <li>Flaga zamknięcia odpowiedzi</li>
 * </ul>
 */
public class Response {
    private final Headers headers;
    private int statusCode;
    private String body;
    private boolean closed;

    public Response() {
        this.headers = new Headers();
        this.statusCode = 200;
        this.body = null;
        this.closed = false;
    }

    /**
     * Ustawia kod odpowiedzi HTTP.
     *
     * @param code nowy kod odpowiedzi
     */
    public void status(int code) {
        this.statusCode = code;
    }

    /**
     * Dodaje nagłówek do nagłówków odpowiedzi HTTP.
     *
     * @param headerName  nazwa nagłówka
     * @param headerValue wartość nagłówka
     */
    public void header(String headerName, String headerValue) {
        this.headers.add(headerName, headerValue);
    }

    /**
     * Ustawia nagłówek 'Content-Type'.
     *
     * @param type typ danych w ciele odpowiedzi
     */
    public void type(String type) {
        this.header("Content-Type", type);
    }

    /**
     * Ustawia ciało odpowiedzi oraz blokuje wysyłanie następnych danych w ciele odpowiedzi.
     *
     * @param data ciało odpowiedzi
     * @throws ResponseError błąd, gdy ciało odpowiedzi zostało zablokowane
     */
    public void send(String data) throws ResponseError {
        if (this.closed) {
            throw new ResponseError("Response has been closed!");
        }
        this.body = data;
        this.closed = true;
    }

    /**
     * Blokuje wysyłanie następnych danych w ciele odpowiedzi
     *
     * @throws ResponseError błąd, gdy ciało odpowiedzi zostało zablokowane
     */
    public void end() throws ResponseError {
        if (this.closed) {
            throw new ResponseError("Response has been closed!");
        }
        this.closed = true;
    }

    /**
     * Dodaje nagłówek 'Set-Cookie' o podanych parametrach, który wysyła pliki cookie do użytkownika.
     *
     * @param name  nazwa pliku cookie
     * @param value wartość pliku cookie
     */
    public void cookie(String name, String value) {
        this.cookie(name, value, 3600, "/");
    }

    /**
     * Dodaje nagłówek 'Set-Cookie' o podanych parametrach, który wysyła pliki cookie do użytkownika.
     *
     * @param name   nazwa pliku cookie
     * @param value  wartość pliku cookie
     * @param maxAge czas życia pliku cookie
     */
    public void cookie(String name, String value, int maxAge) {
        this.cookie(name, value, maxAge, "/");
    }

    /**
     * Dodaje nagłówek 'Set-Cookie' o podanych parametrach, który wysyła pliki cookie do użytkownika.
     *
     * @param name   nazwa pliku cookie
     * @param value  wartość pliku cookie
     * @param maxAge czas życia pliku cookie
     * @param path   ścieżka pliku cookie
     */
    public void cookie(String name, String value, int maxAge, String path) {
        StringBuilder cookie = new StringBuilder();
        cookie.append(name).append("=").append(value).append(";");
        cookie.append("Path=").append(path).append(";");
        cookie.append("HttpOnly").append(";");
        cookie.append("Secure=false").append(";");
        cookie.append("Max-Age=").append(maxAge);
        this.headers.add("Set-Cookie", cookie.toString());
    }

    /**
     * Ustawia ciało odpowiedzi na json.JSON podanego obiektu, ustawia nagłówek 'Content-Type' oraz blokuje wysyłanie następnych danych w ciele odpowiedzi.
     *
     * @param object obiekt, który ma zostać wysłany
     * @throws ResponseError błąd obiektu odpowiedzi
     */
    public void json(Object object) throws ResponseError {
        try {
            this.type("application/json");
            this.send(JSON.stringify(object));
        } catch (JsonProcessingException error) {
            throw new ResponseError(error.getMessage());
        }
    }

    /**
     * Wysyła obiekt odpowiedzi.
     *
     * @param exchange obiekt wymiany HTTP
     * @throws IOException błąd przypisania ciała odpowiedzi
     */
    public void sendResponse(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().putAll(headers);
        if (this.body != null) {
            exchange.sendResponseHeaders(statusCode, body.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(statusCode, 0);
        }
        exchange.close();
    }

    public static class ResponseError extends JExpError {
        public ResponseError(String message) {
            super(message);
        }
    }
}
