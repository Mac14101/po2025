import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    private final HttpClient client;

    public Client() {
        this.client = HttpClient.newHttpClient();
    }

    public HttpRequest.Builder request(String url) {
        //TODO
        return null;
    }

    // Wybrać sposób sprawdzenia statusu odpowiedzi
    public HttpResponse<String> fetch(HttpRequest.Builder request) {
        //TODO
        return null;
    }
}
