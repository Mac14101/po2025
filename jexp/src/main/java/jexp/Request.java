package jexp;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import entities.JSON;
import jexp.session.Manager;
import jexp.session.Session;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * Obiekt żądania HTTP.
 * Zawiera informacje o żądaniu takie jak:
 * <ul>
 *     <li>Nagłówki HTTP żądania</li>
 *     <li>Trasa żądania</li>
 *     <li>Ścieżka żądania</li>
 *     <li>Wersja protokołu</li>
 *     <li>adres URL</li>
 *     <li>Metoda HTTP żądania</li>
 *     <li>Query string</li>
 *     <li>Ciało żądania</li>
 *     <li>Pliki cookie</li>
 *     <li>Parametry trasy</li>
 * </ul>
 */
public class Request {
    private final Headers headers;
    private final String[] route;
    private final String path;
    private final String protocol;
    private final String url;
    private final String method;
    private final HashMap<String, String> query;
    private final String body;
    private final HashMap<String, String> cookies;
    private final HashMap<String, String> params;
    private final Manager sessionManager;
    private Session session;

    public Request(HttpExchange exchange) throws RequestError {
        try {
            //Odczytanie nagłówków żądania
            this.headers = exchange.getRequestHeaders();
            //Odczytanie adresu URL żądania
            this.url = exchange.getRequestURI().toString();
            Route route = new Route(exchange.getRequestURI().getPath());
            //Odczytanie trasy i ścieżki
            this.route = route.getRoute();
            this.path = route.getPath();
            //Odczytanie wersji protokołu
            this.protocol = exchange.getProtocol();
            //Odczytanie metody
            this.method = exchange.getRequestMethod();
            this.query = new HashMap<String, String>();
            //Odczytywanie query string
            String queryString = exchange.getRequestURI().getQuery();
            if (queryString != null) {
                //Żądanie posiada query string
                //Odczytywanie wartości query string
                String[] query = queryString.split("&");
                for (String s : query) {
                    String[] currentQuery = s.split("=");
                    this.query.put(currentQuery[0], currentQuery[1]);
                }
            }
            this.cookies = new HashMap<String, String>();
            //Odczytywanie plików cookie z nagłówka
            if (this.headers.get("Cookie") != null) {
                //Nagłówek posiada zapisane pliki cookie
                //Odczytywanie wartości plików cookie
                String[] cookieList = this.headers.get("Cookie").getFirst().split(";");
                for (String s : cookieList) {
                    String[] currentCookie = s.split("=");
                    this.cookies.put(currentCookie[0], currentCookie[1]);
                }
            }
            //Odczytywanie ciała żądania
            InputStream bodyStream = exchange.getRequestBody();
            if (bodyStream != null) {
                //Żądanie posiada ciało
                this.body = new String(bodyStream.readAllBytes(), StandardCharsets.UTF_8);
            } else {
                //Żądanie nie posiada ciała
                this.body = null;
            }
            //Utworzenie listy parametrów trasy, która będzie uzupełniana przez obiekty klasy ParamHandler
            this.params = new HashMap<>();
            this.sessionManager = Manager.getInstance();
        } catch (IOException e) {
            throw new RequestError(e.getMessage());
        }
    }

    /**
     * Metoda zwracająca nagłówki żądania.
     *
     * @return nagłówki żądania
     */
    public Headers getHeaders() {
        return this.headers;
    }

    /**
     * Metoda zwracająca trasę żądania.
     *
     * @return trasa żądania
     */
    public String[] getRoute() {
        return this.route;
    }

    /**
     * Metoda zwracająca ścieżkę żądania.
     *
     * @return ścieżka żądania
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Metoda zwracająca wersję protokołu żądania.
     *
     * @return wersja protokołu żądania
     */
    public String getProtocol() {
        return this.protocol;
    }

    /**
     * Metoda zwracająca adres URL żądania.
     *
     * @return adres URL żądania
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Metoda zwracająca metodę HTTP żądania.
     *
     * @return metoda HTTP żądania
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * Metoda zwracająca wartość dla klucza z query string żądania.
     *
     * @param key klucz, którego wartość ma zostać zwrócona
     * @return wartość odpowiadająca podanemu kluczowi
     */
    public String getQuery(String key) {
        return this.query.get(key);
    }

    /**
     * Metoda zwracająca ciało żądania jako podany obiekt Java.
     *
     * @return ciało żądania jako obiekt
     */
    public <T> T getBody(Class<T> type) throws RequestError {
        try {
            return JSON.parse(this.body, type);
        } catch (JsonProcessingException e) {
            throw new RequestError(e.getMessage());
        }
    }

    /**
     * Metoda zwracająca ciało żądania jako podany obiekt Java.
     *
     * @return ciało żądania jako obiekt
     */
    public <T> T getBody(TypeReference<T> type) throws RequestError {
        try {
            return JSON.parse(this.body, type);

        } catch (JsonProcessingException e) {
            throw new RequestError(e.getMessage());
        }
    }

    /**
     * Zwraca wartość wybranego pliku cookie.
     *
     * @param name nazwa pliku cookie
     * @return wartość pliku cookie
     */
    public String getCookie(String name) {
        return this.cookies.get(name);
    }

    /**
     * Zwraca listę wszystkich plików cookie.
     *
     * @return lista plików cookie
     */
    public HashMap<String, String> getCookies() {
        return this.cookies;
    }

    /**
     * Metoda wczytująca parametr trasy z trasy żądania.
     * Zamienia parametr podany w trasie żądania na nazwę parametru, aby żądanie zostało dopasowane do odpowiedniej trasy.
     *
     * @param name  nazwa parametru
     * @param depth głębokość parametru
     */
    public void readParam(String name, int depth) {
        this.params.put(name, this.route[depth]);
        this.route[depth] = name;
    }

    /**
     * Zwraca wartość wybranego parametru trasy.
     *
     * @param name nazwa parametru trasy
     * @return wartość parametru trasy
     */
    public String getParam(String name) {
        return this.params.get(":" + name);
    }

    public void setSessionObject(Session session) {
        this.session = session;
    }

    public boolean sessionEstabilished() {
        return this.session != null;
    }

    public String getSession(String key) {
        return this.session.getSession(key);
    }

    public void setSession(String key, String value) {
        this.session.setSession(key, value);
    }

    public String logIn(Integer userId, String username) {
        String token = this.sessionManager.addSession();
        this.session = this.sessionManager.getSession(token);
        this.session.setSession("userId", userId.toString());
        this.session.setSession("username", username);
        return token;
    }

    public void logOut() {
        String token = this.session.getToken();
        this.sessionManager.removeSession(token);
        this.session = null;
    }

    public String refreshSession() {
        String token = this.session.getToken();
        String newToken = this.sessionManager.refreshToken(token);
        this.session = this.sessionManager.getSession(newToken);
        return newToken;
    }

    public static class RequestError extends JExpError {
        public RequestError(String message) {
            super(message);
        }
    }
}
