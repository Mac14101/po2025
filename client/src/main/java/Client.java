import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class Client extends Thread {
    private final HttpClient client;
    private String token;
    private LocalDateTime tokenTime;

    public Client() {
        this.client = HttpClient.newHttpClient();
    }

    public HttpRequest.Builder request(String url) {
        return HttpRequest.newBuilder(URI.create(url));
    }

    public HttpResponse<String> fetch(HttpRequest.Builder request) throws ClientError {
        if (token != null) {
            request.headers("Authorization", "Basic" + token);
        }
        HttpResponse<String> response = null;
        try {
            response = this.client.send(request.build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400 && response.statusCode() < 500) {
                throw new RuntimeException("Failed to fetch response");
            }
            return response;
        } catch (IOException | InterruptedException | RuntimeException e) {
            throw new ClientError("Failed to fetch", response);
        }
    }

    public void setToken(String token) {
        this.token = token;
        this.tokenTime = LocalDateTime.now();
    }

    private void refreshToken() {
        try {
            HttpRequest.Builder request =
                    this.request("");
            HttpResponse<String> response = this.fetch(request);
            String newToken = response.body();
            this.setToken(newToken);
        } catch (Exception e) {
            return;
        }
    }

    public void run() {
        while (true) {
            if (this.token != null) {
                if (this.tokenTime.plusMinutes(10).isBefore(LocalDateTime.now())) {
                    this.token = null;
                    this.tokenTime = null;
                } else {
                    this.refreshToken();
                    try {
                        Thread.sleep(5 * 60 * 1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static class ClientError extends Exception {
        private final HttpResponse<String> response;

        public ClientError(String message, HttpResponse<String> response) {
            super(message);
            this.response = response;
        }

        public HttpResponse<String> getResponse() {
            return this.response;
        }
    }
}
