package jexp;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class JExpRequest {
    private final Headers headers;
    private final String[] path;
    private final String protocol;
    private final String url;

    public JExpRequest(HttpExchange exchange) {
        this.headers = exchange.getRequestHeaders();
        this.url = exchange.getRequestURI().getPath();
        String[] path = exchange.getRequestURI().getPath().split("[/]");
        if (path.length == 0) {
            this.path = new String[0];
        } else {
            this.path = new String[path.length - 1];
            for (int i = 1; i < path.length; i++) {
                this.path[i - 1] = path[i];
            }
        }
        this.protocol = exchange.getProtocol();
    }

    public Headers getHeaders() {
        return this.headers;
    }

    public String[] getPath() {
        return this.path;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getUrl() {
        return this.url;
    }
}
