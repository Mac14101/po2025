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