# Moduł JExp
 JExp to moduł wzorowany na frameworku express.js dla środowiska Node.js. Moduł opakowuje wbudowany serwer HTTP, aby zapewnić szybkie i wygodne budowanie serwerów, a zwłaszcza REST API.

## Diagram UML klas

```planuml
@startuml
class Request{
    - Headers headers;
    - String[] route;
    - String path;
    - String protocol;
    - String url;
    - String method;
    - HashMap<String, String> query;
    - String body;
    - HashMap<String, String> cookies;
   + Headers getHeaders();
   + String[] getRoute();
   + String getPath();
   + String getProtocol();
   + String getUrl();
   + String getMethod();
   + String getQuery();
   + T getBody();
   + String getCookie();
   + HashMap<String, String> getCookies();
}
class Response{
   - Headers headers;
   - int statusCode;
   - String body;
   - boolean closed;
   + void status();
   + void header(); 
   + void type();
   + void send();
   + void end();
   + void cookie();
   + void sendResponse();
}
class Next{
   - boolean nextHandler;
   - boolean nextRoute;
   + void next();
   + void nextRoute();
}
class Handler{
    + void handle();
}
class MethodHandler{
    - String method;
    + void handle();
}
class JSON{
    - ObjectMapper mapper;
    + <T> parse();
}
Handler..>Request
Handler..>Response
Handler..>Next
Request..>JSON
MethodHandler--|>Handler
@enduml
```

## Klasy i interfejsy
1. Request
   Klasa reprezentująca żądanie HTTP.
   Atrybuty:
   private final **Headers** headers - obiekt przechowujący nagłówki żądania
   private final **String[]** route - tablica odpowiadająca za wybranie odpowiedniej trasy
   private final **String** path - ścieżka żądania
   private final **String** protocol - protokół żądania
   private final **String** url - adres URL żądania
   private final **String** method - metoda HTTP żądania
   private final **HashMap<String, String>** query - query string
   private final **String** body - ciało żądania
   private final **HashMap<String, String>** cookies - pliki cookie żądania
   Metody:
   * public **Headers** getHeaders() - metoda zwracająca obiekt nagłówków HTTP
   * public **String[]** getRoute() - metoda zwracająca trasę żądania
   * public **String** getPath() - metoda zwracająca ścieżkę żądania
   * public **String** getProtocol() - metoda zwracająca wersję protokołu
   * public **String** getUrl() - metoda zwracająca adres URL razem z query string
   * public **String** getMethod() - metoda zwracająca metodę użytą w żądaniu HTTP
   * public **String** getQuery() - metoda zwracająca wartość wybranego atrybutu z query string
   * public **T** getBody() throws **RequestError** - metoda przekształcająca ciało żądania na obiekt java
   * public **String** getCookie() - metoda zwracająca wybrany plik cookie
   * public **HashMap<String, String>** getCookies() - metoda zwracająca wszystkie pliki cookie
2. Response
   Klasa reprezentująca odpowiedź HTTP.
   Atrybuty:
   * private final Headers headers - obiekt nagłówków HTTP
   * private int statusCode - kod odpowiedzi HTTP
   * private String body - ciało odpowiedzi
   * private boolean closed - atrybut 
   Metody:
   * public **void** status() - ustawia status HTTP odpowiedzi
   * public **void** header() - dodaje nowy nagłówek HTTP
   * public **void** type() - ustawia typ odpowiedzi
   * public **void** send() throws **Response.ResponseError** - wysyła dane, blokuje wysłanie następnych danych, jeśli już zablokowane rzuca wyjątek rzuca wyjątek
   * public **void** end() throws **Response.ResponseError** - blokuje wysyłanie danych, jeśli już zablokowane rzuca wyjątek
   * public **void** cookie() - zapisuje nowy plik cookie
   * public **void** sendResponse() throws **IOException** - wysyła odpowiedź HTTP
3. Next
   Klasa pozwalająca pomijać funkcje obsługi tras.
   Atrybuty:
   * private **boolean** nextHandler - jeśli prawda przechodzi do następnego obiektu obsługującego trasę
   * private **boolean** nextRoute - jeśli prawda przechodzi do nastepnej dopasowanej trasy
   Metody:
   * public **void** next() throws **Next.NextError** - przechodzi do następnego obiektu obsługującego trasę, wyrzuca wyjątek po drugim wywołaniu
   * public **void** nextRoute() throws **Next.NextError** - przechodzi do nastepnej dopasowanej trasy, wyrzuca wyjątek po drugim wywołaniu
4. Handler
   Interfejs, którego rozszerzeniem są obiekty obsługujące określone trasy.
   Metody:
      * public **void** handle() - metoda obsługująca żądanie HTTP
5. MethodHandler
   Klasa rozszerzająca interfejs **Handler**, obsługująca tylko żądania o wybranej metodzie HTTP.
   Atrybuty:
      * private **String** method - metoda żądania HTTP, którą obiekt może obsłużyć
      * private **Handler** handler - obiekt obsługujący trasę o podanej metodzie
   Metody:
      * public **void** handle() - metoda obsługująca żądanie HTTP tylko o metodzie podanej w atrybucie **method**
6. JSON
   Klasa odpowiedzialna za przetwarzanie JSON, oparta o wzorzec singleton
   Atrybuty:
   * private static **ObjectMapper** mapper - pojedyncza instancja mappera Jackson
   Metody:
   * public static **T** parse throws **JsonProcessingException** - metoda przekształcająca ciąg znaków JSON na obiekt Java


## Wyjątki
1. JExpError - ogólna klasa wyjątku
2. Request - wyjątek obiektu żądania
2. ResponseError - wyjątek obiektu odpowiedzi
3. NextError - wyjątek obiektu odpowiedzialnego za przetwarzanie potokowe

## Diagramy sekwencji

## TODO