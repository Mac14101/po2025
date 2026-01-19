import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import entities.*;
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

    /**
     * Pobiera listę przedmiotów zapisanych w bazie danych za pomocą żądanie HTTP (`/subject/` GET).
     *
     * @return lista przedmiotów
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public ArrayList<Subject> getAllSubjects() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/subject/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<Subject>>() {
        });
    }

    /**
     * Wstawia nowy przedmiot do bazy danych, za pomocą żądania HTTP (`/subject/` POST).
     *
     * @param subject obiekt reprezentujący nowy przedmiot
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public void createSubject(Subject subject) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/subject/");
        request.POST(HttpRequest.BodyPublishers.ofString(JSON.stringify(subject)));
        this.fetch(request);
    }

    /**
     * Pobiera listę klas zapisanych w bazie danych za pomocą żądanie HTTP (`/class/` GET).
     *
     * @return lista klas
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public ArrayList<SchoolGroup> getAllClass() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/class/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<SchoolGroup>>() {
        });
    }

    /**
     * Wstawia nową klasę do bazy danych, za pomocą żądania HTTP (`/class/` POST).
     *
     * @param schoolGroup obiekt reprezentujący nową klasę
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public void createClass(SchoolGroup schoolGroup) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/class/");
        request.POST(HttpRequest.BodyPublishers.ofString(JSON.stringify(schoolGroup)));
        this.fetch(request);
    }

    /**
     * Pobiera listę uczniów zapisanych w bazie danych za pomocą żądanie HTTP (`/student/` GET).
     *
     * @return lista uczniów
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public ArrayList<Student> getAllStudents() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/student/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<Student>>() {
        });
    }

    /**
     * Pobiera listę uczniów należących klasy o podanym numerze id za pomocą żądanie HTTP (`/student/:classId/` GET).
     *
     * @param classId numer id klasy
     * @return lista uczniów należących do klasy
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public ArrayList<Student> getClassStudents(int classId) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/student/" + String.valueOf(classId) + "/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<Student>>() {
        });
    }

    /**
     * Dodaje ucznia o podanym id do wybranej klasy, za pomocą żądania HTTP (`/student/` POST).
     *
     * @param student obiekt z zapisanym id ucznie i id klasy, do której ma zostać przypisany
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public void addStudent(Student student) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/student/");
        request.POST(HttpRequest.BodyPublishers.ofString(JSON.stringify(student)));
        this.fetch(request);
    }

    /**
     * Pobiera listę lekcji nauczyciela, które już się odbyły, wysyła żądanie HTTP (`/lesson/` GET).
     *
     * @return lista lekcji poprowadzonych przez nauczyciela
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public ArrayList<Lesson> getLessons() throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/lesson/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<Lesson>>() {
        });
    }

    /**
     * Dodaje nową lekcję, automatycznie ustawia obecność wszystkich uczniów klasy na wartość "undefined",
     * wysyła żądanie HTTP (`/lesson/` POST).
     *
     * @param lesson obiekt reprezentujący lekcję
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public void addLesson(Lesson lesson) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/lesson/");
        request.POST(HttpRequest.BodyPublishers.ofString(JSON.stringify(lesson)));
        this.fetch(request);
    }

    /**
     * Pobiera listę obecności uczniów na wybranej lekcji o numerze id lessonId, wysyła żądanie HTTP (`/attendance/:lessonId/` GET).
     *
     * @param lessonId numer id lekcji, której listę obecności należy pobrać
     * @return lista obecności uczniów na lekcji
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public ArrayList<Attendance> getAttendanceList(int lessonId) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/attendance/" + String.valueOf(lessonId) + "/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<Attendance>>() {
        });
    }

    /**
     * Aktualizuje status obecności ucznia na wybranej lekcji, wysyła żądanie HTTP (`/lesson/` POST).
     *
     * @param lessonId   numer id lekcji, na której należy zmienić obecność ucznia
     * @param attendance obiekt reprezentujący status obecności
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     * @
     */
    public void updateAttendance(int lessonId, Attendance attendance) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/attendance/" + String.valueOf(lessonId) + "/");
        request.PUT(HttpRequest.BodyPublishers.ofString(JSON.stringify(attendance)));
        this.fetch(request);
    }

    /**
     * Pobiera listę ocen wybranego ucznia o numerze studentId, oceny muszą być wystawione przez aktualnie
     * zalogowanego nauczyciela, wysyła żądanie HTTP (`/grades/:studentId/` GET).
     *
     * @param studentId numer id ucznia, którego listę ocen należy pobrać
     * @return lista ocen ucznia, które wystawił zalogowany nauczyciel
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public ArrayList<Grade> getGradesList(int studentId) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/grade/" + String.valueOf(studentId) + "/");
        request.GET();
        HttpResponse<String> response = this.fetch(request);
        return JSON.parse(response.body(), new TypeReference<ArrayList<Grade>>() {
        });
    }

    /**
     * Wstawia ocenę wybranemu uczniowi o numerze id studentId, wysyła żądanie HTTP (`/grades/:studentId/` GET).
     *
     * @param studentId numer id ucznia, któremu należy wstawić ocenę
     * @param grade     obiekt reprezentujący ocene do wstawienia
     * @throws ClientError             błąd wysłania żądania lub otrzymana odpowiedź ma status HTTP oznaczający błąd
     * @throws JsonProcessingException błąd przetwarzania obiektu danych na JSON
     */
    public void addGrade(int studentId, Grade grade) throws ClientError, JsonProcessingException {
        HttpRequest.Builder request = this.request("/grade/" + String.valueOf(studentId) + "/");
        request.POST(HttpRequest.BodyPublishers.ofString(JSON.stringify(grade)));
        this.fetch(request);
    }
}
