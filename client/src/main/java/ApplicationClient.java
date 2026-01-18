import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import entities.SchoolGroup;
import entities.Subject;
import entities.User;
import entities.UserCredentials;
import json.JSON;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApplicationClient extends Client {
    private static ApplicationClient instance;

    private ApplicationClient() {
        super();
    }

    public static ApplicationClient getInstance() {
        if (instance == null) {
            instance = new ApplicationClient();
        }
        return instance;
    }

    /**
     * Metoda służąca do uwierzytelniania użytkownika. Wysyła żądanie HTTP (`/login/` POST), jeżeli dane są poprawne,
     * serwer zwraca token uwierzytelniania, który jest automatycznie zapisywany i dodawany do każdego następnego żądania.
     *
     * @param credentials obiekt z danymi uwierzytelniania
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     */
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

    /**
     * Metoda służąca do pobrania danych o zalogowanym użytkowniku. Wysyła żądanie HTTP (`/self/` GET), jeżeli użytkownik jest zalogowany,
     * zostaje zwrócony ciąg znaków JSON zawierający dane użytkownika.
     *
     * @return Obiekt z danymi użytkownika
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public User getUserData() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/self/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), User.class);
    }

    /**
     * Metoda służąca do wylogowania. Wysyła żądanie HTTP (`/logout/` GET), jeżeli użytkownik jest zalogowany, niszczy sesję logowania.
     *
     * @throws ClientError błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     */
    public void logOut() throws ClientError {
        HttpRequest.Builder request = this.request("/logout/");
        request.GET();
        this.fetch(request);
    }

    /**
     * Pobiera listę użytkowników zapisanych w bazie danych za pomocą żądanie HTTP (`/user/` GET).
     *
     * @return lista użytkowników
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public ArrayList<User> getAllUsers() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/user/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<User>>() {
        });
    }

    /**
     * Wstawia nowego użytkownika do bazy danych, za pomocą żądania HTTP (`/user/` POST).
     *
     * @param user obiekt reprezentujący nowego użytkownika
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public void createUser(User user) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/user/");
        request.POST(HttpRequest.BodyPublishers.ofString(JSON.stringify(user)));
        this.fetch(request);
    }

    public ArrayList<Subject> getAllSubjects() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/subject/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<Subject>>() {
        });
    }

    public void createSubject(Subject subject) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/subject/");
        request.POST(HttpRequest.BodyPublishers.ofString(JSON.stringify(subject)));
        this.fetch(request);
    }

    public ArrayList<SchoolGroup> getAllClass() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/class/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<SchoolGroup>>() {
        });
    }

    public void createSubject(SchoolGroup schoolGroup) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/class/");
        request.POST(HttpRequest.BodyPublishers.ofString(JSON.stringify(schoolGroup)));
        this.fetch(request);
    }
}
