import com.fasterxml.jackson.core.JsonProcessingException;
import entities.JSON;
import entities.User;
import entities.UserCredentials;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApplicationClient extends Client {
    private static ApplicationClient instance;

    private ApplicationClient() {
        super();
    }

    private static ApplicationClient getInstance() {
        if (instance == null) {
            instance = new ApplicationClient();
        }
        return instance;
    }

    public void authenticate(UserCredentials credentials) throws JsonProcessingException, ClientError {
        HttpRequest.Builder request = this.request("/login/");
        String body = JSON.stringify(credentials);
        request.POST(HttpRequest.BodyPublishers.ofString(body));
        request.header("Content-Type", "application/json");
        HttpResponse<String> response = this.fetch(request);
        if (response.body() == null) {
            throw new ClientError("Response body is empty, no token", response);
        }
        String token = response.body();
        this.setToken(token);
    }

    public User getUserData() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/self/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), User.class);
    }
}
