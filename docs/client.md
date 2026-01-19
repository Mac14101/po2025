# Moduł client
Moduł client odpowiada za wysyłanie żądań HTTP oraz zrządzanie sesją. W skład modułu wchodzą dwie klasy, **Client** zawiera podstawowe metody pozwalające na wysyłanie żądań, odpowiada również za automatyczne utrzymywanie sesji, natomiast **ApplicationClient** jest klasą pochodną z klasy **Client** opartą o wzorzec singleton, która posiada złożone metody wysyłające żądania do konkretnych endpointów API. Moduł korzysta z modułu entities, który zawiera klasy encji.

## Sposób użycia

## Diagram UML klas

## Klasy i interfejsy
1. Client
Klasa implementująca klienta HTTP. Poprzez dziedziczenie z klasy **Thread** implementuje automatyczne zarządzanie sesją w tle. Po ustawieniu tokenu uwierzytelniania sprawdza czy token jest jeszcze ważny oraz automatycznie odświeża token co 5 minut.
Atrybuty:
   * private final **HttpClient** client - instancja klienta HTTP z modułu *java.net*
   * private String token - token uwierzytelniania
   * private LocalDateTime tokenTime - data ustawienia tokenu uwierzytelniania
   * private String baseUrl - bazowy adres URL serwera HTTP
   * private String refreshUrl - adres endpointu służącego do odświeżenia tokenu uwierzytelniania
Metody:
   * public **void** setBaseUrl() - ustawia bazowy adres URL serwera HTTP, adres ten jest początkiem adresu dla wszystkich żądań, np. dla baseUrl=`http://localhost:8080` żądanie `/user/` zostaje przekształcone w adres URL `http://localhost:8080/user/`
   * public **void** setRefreshUrl() - ustawia adres endpointu automatycznego odświeżania tokenu
   * public **HttpRequest.Builder** request() - zwraca builder żądania HTTP z zdefiniowanym docelowym adresem URL
   * public **HttpResponse<String>** fetch() throws **ClientError** - wysyła żądanie HTTP, po ustawieniu tokenu uwierzytelniania automatycznie dodaje nagłówek *"Authorization"*, jeśli status HTTP oznacza błąd rzuca wyjątek **ClientError** (statusy 400-499 i 500-599), **ClientError** może zostać rzucony, gdy wystąpi błąd podczas wysyłania żądania HTTP
   * public **void** setToken() - ustawia nowy token oraz zapisuje czas ustawienia tokenu
   * private **void** refreshToken() - wysyła żądanie na adres podany w **setRefreshUrl()** aby przedłużyć ważność sesji logowania

2. ClientError
Klasa błędu klienta HTTP.
Atrybuty:
   * private final **HttpResponse<String>** response - referencja do obiektu odpowiedzi przez który wystąpił wyjątek
Metody:
   * public **HttpResponse<String>** getResponse() - zwraca referencję do obiektu odpowiedzi przez który wystąpił wyjątek

3. ApplicationClient
Klasa rozszerzająca klasę **Client**. Posiada metody zaimplementowane specjalnie dla projektu. Implementuje wzorzec singleton, aby zapewnić klienta że nie powstanie inna instancja klienta, która będzie miała zapisany inny token uwierzytelniania.
Atrybuty:
   * private static **ApplicationClient** instance - jedyna istniejąca instancja obiektu
Metody:
   * private static **ApplicationClient** getInstance() - zwraca jedyną instancję obiektu

   * public **void** authenticate() throws **JsonProcessingException**, **ClientError** - metoda implementująca mechanizm uwierzytelniania, wysyła żądanie HTTP (`/login/` POST) zawierające obiekt **UserCredentials**, jeżeli dane uwierzytelniania są poprawne w odpowiedzi zostaje zwrócony token uwierzytelniania, który automatycznie zostaje zapisany w obiekcie poprzez wywołanie metody **setToken()**, metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania obiektu **UserCredentials** do JSON

   * public **void** getUserData() throws **JsonProcessingException**, **ClientError** - zwraca dane o zalogowanym użytkowniku jako obiekt **User**, wysyła żądanie HTTP (`/self/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **User**, ***należy pamiętać że endpoint `/self/` jest dostępny tylko dla zalogowanych użytkowników!!!***

   * public **void** logOut() throws **ClientError** - wylogowuje zalogowanego użytkownika, wysyła żądanie HTTP (`/logout/` GET), ***należy pamiętać że endpoint `/logout/` jest dostępny tylko dla zalogowanych użytkowników!!!***
   * public **ArrayList<User>** getAllUsers() throws **ClientError**, **JsonProcessingException** - zwraca listę użytkowników zapisanych w bazie danych, wysyła żądanie HTTP (`/user/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **ArrayList<User>**, ***należy pamiętać że endpoint `/user/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora!!!***

   * public **void** createUser() throws **ClientError**, **JsonProcessingException** - dodaje nowe konto użytkownika do bazy danych, wysyła żądanie HTTP (`/user/` POST), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania obiektu **User** do JSON, ***należy pamiętać że endpoint `/user/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora!!!***
   
   * public **ArrayList<Subject>** getAllSubjects() throws **ClientError**, **JsonProcessingException** - zwraca listę przedmiotów zapisanych w bazie danych, wysyła żądanie HTTP (`/subject/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **ArrayList<Subject>**, ***należy pamiętać że endpoint `/subject/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora!!!***

   * public **void** createSubject() throws **ClientError**, **JsonProcessingException** - dodaje nowy przedmiot do bazy danych, wysyła żądanie HTTP (`/subject/` POST), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania obiektu **Subject** do JSON, ***należy pamiętać że endpoint `/subject/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora!!!***
   * public **ArrayList<SchoolGroup>** getAllClass() throws **ClientError**, **JsonProcessingException** - zwraca listę klas zapisanych w bazie danych, wysyła żądanie HTTP (`/class/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **ArrayList<SchoolGroup>**, ***należy pamiętać że endpoint `/class/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora!!!***

   * public **void** createClass() throws **ClientError**, **JsonProcessingException** - dodaje nową klasę do bazy danych, wysyła żądanie HTTP (`/class/` POST), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania obiektu **SchoolGroup** do JSON, ***należy pamiętać że endpoint `/class/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora!!!***
   * public **ArrayList<Student>** getAllClass() throws **ClientError**, **JsonProcessingException** - zwraca listę uczniów zapisanych w bazie danych, wysyła żądanie HTTP (`/student/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **ArrayList<Student>**, ***należy pamiętać że endpoint `/student/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora!!!***

   * public **ArrayList<Student>** getAllClass() throws **ClientError**, **JsonProcessingException** - zwraca listę uczniów w klasie o wybranym numerze id, wysyła żądanie HTTP (`/student/:classId/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **ArrayList<Student>**, ***należy pamiętać że endpoint `/student/:classId/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora lub nauczyciela!!!***

   * public **void** addStudent() throws **ClientError**, **JsonProcessingException** - dodaje ucznia o wybranym id do podanej klasy, najważniejsze jest aby podać numery id, ponieważ to gwarantuje właściwe odwzorowanie uczniów w klasach, wysyła żądanie HTTP (`/student/` POST), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania obiektu **Student** do JSON, ***należy pamiętać że endpoint `/student/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami administratora!!!***

   * public ArrayList<Lesson> getLessons() throws **ClientError**, **JsonProcessingException** - zwraca listę lekcji nauczyciela, które już się odbyły, wysyła żądanie HTTP (`/lesson/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **ArrayList<Lesson>**, ***należy pamiętać że endpoint `/lesson/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami nauczyciela!!!***

   * public void addLesson() throws **ClientError**, **JsonProcessingException** - dodaje nową lekcję, automatycznie ustawia obecność wszystkich uczniów klasy na wartość *"undefined"*, wysyła żądanie HTTP (`/lesson/` POST), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania obiektu **Lesson** do JSON, ***należy pamiętać że endpoint `/lesson/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami nauczyciela!!!***

   * public ArrayList<Attendance> getAttendanceList() throws **ClientError**, **JsonProcessingException** - pobiera listę obecności uczniów na wybranej lekcji o numerze id `lessonId`, wysyła żądanie HTTP (`/attendance/:lessonId/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **ArrayList<Attendance>**, ***należy pamiętać że endpoint `/attendance/:lessonId/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami nauczyciela!!!***

   * public void updateAttendance() throws **ClientError**, **JsonProcessingException** - aktualizuje status obecności ucznia na wybranej lekcji o numerze id `lessonId`, wysyła żądanie HTTP (`/attendance/:lessonId/` POST), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania obiektu **Attendance** do JSON, ***należy pamiętać że endpoint `/attendance/:lessonId/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami nauczyciela!!!***
   
   * public ArrayList<Grade> getGradesList() throws **ClientError**, **JsonProcessingException** - obiera listę ocen wybranego ucznia o numerze `studentId`, oceny muszą być wystawione przez aktualnie zalogowanego nauczyciela, wysyła żądanie HTTP (`/grades/:studentId/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania odpowiedzi do obiektu **ArrayList<Grade>**, ***należy pamiętać że endpoint `/grades/:studentId/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami nauczyciela!!!***

   * public void addGrade() throws **ClientError**, **JsonProcessingException** - Wstawia ocenę wybranemu uczniowi o numerze id `studentId`, wysyła żądanie HTTP (`/grades/:studentId/` GET), metoda może rzucić wyjątek **JsonProcessingException** podczas przetwarzania obiektu **Grade** do JSON, ***należy pamiętać że endpoint `/grades/:studentId/` jest dostępny tylko dla zalogowanych użytkowników z uprawnieniami nauczyciela!!!***

## Błędy i statusy HTTP
* status `200` - przetwarzanie żądania przebiegło pomyślnie
* status `201` - wprowadzono podane dane do bazy danych
* status `400` - podczas przetwarzania żądania znaleziono błędne lub niepoprawne dane, odpowiedź powinna posiadać obiekt typu **Message** z opisem błędu lub błędów
* status `401` - odmówiono dostępu do zasobów, brak uwierzytelnienia lub potrzebne inne uprawnienia
* status `404` - nie znaleziono żądanego zasobu
* status `500` - wystąpił błąd po stronie serwera