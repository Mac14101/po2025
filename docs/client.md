# Moduł client
Moduł client odpowiada za wysyłanie żądań HTTP oraz zrządzanie sesją. W skład modułu wchodzą dwie klasy, **Client** zawiera podstawowe metody pozwalające na wysyłanie żądań, odpowiada również za automatyczne utrzymywanie sesji, natomiast **ApplicationClient** jest klasą pochodną z klasy **Client** opartą o wzorzec singleton, która posiada złożone metody wysyłające żądania do konkretnych endpointów API. Moduł korzysta z modułu entities, który zawiera klasy encji.

## Diagram UML klas
```plantuml
@startuml
class Client{
    - final HttpClient client;
    - String token;
    - LocalDateTime tokenTime;
    - String baseUrl;
    - String refreshUrl;
	+ void setBaseUrl();
	+ void setRefreshUrl();
	+ HttpRequest.Builder request();
	+ HttpResponse<String> fetch();
	+ void setToken();
	- void refreshToken();
	+ void run();
}
class ClientError as "Client.ClientError"{
    - HttpResponse<String> response;
	+ HttpResponse<String> getResponse();
}
class ApplicationClient{
    + void authenticate();
    + User getUserData();
    + void logOut();
    + ArrayList<User> getAllUsers();
    + void createUser();
    + User getUserCredentials();
    + ArrayList<Subject> getAllSubjects();
    + void createSubject();
    + ArrayList<SchoolGroup> getAllClass();
    + void createClass();
    + ArrayList<Student> getAllStudents();
    + ArrayList<Student> getClassStudents();
    + void addStudent();
    + ArrayList<SchoolClass> getClassSchedule();
    + void addClassSchedule();
    + ArrayList<Lesson> getLessons();
    + void addLesson();
    + ArrayList<Attendance> getAttendanceList();
    + void updateAttendance();
    + ArrayList<Grade> getGradesList();
    + void addGrade();
    + ArrayList<SchoolClass> getUserSchedule();
    + ArrayList<Attendance> getUserAttendance();
    + ArrayList<Grade> getUserGrades();
    + void updateUserPassword();
}
ApplicationClient--*Client
Client..>ClientError
@enduml
```