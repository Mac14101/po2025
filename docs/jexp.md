# Moduł JExp
 JExp to moduł wzorowany na frameworku express.js dla środowiska Node.js. Moduł opakowuje wbudowany serwer HTTP, aby zapewnić szybkie i wygodne budowanie serwerów, a zwłaszcza REST API. Klasa **Server** posiada główny router aplikacji odpowiedzialny za obsługę wszystkich tras. Do głównego routera mogą zostać inne routery obsługujące inne trasy, tworząc drzewiastą strukturę obsługującą trasy oraz zapewniającą przetwarzanie potokowe (przez co ważna jest kolejność dodawania tras). Taka struktura aplikacji jest możliwa dzięki użyciu wzorca Composite, każdy obiekt obsługujący żądanie rozszerza interfejs **Handler**, przez co musi posiadać metodę **handle()**, która jest wywoływana stopniowo przez kolejne elementy, aż przetwarzanie żądania zostanie zakończone.

## Diagram UML klas

## Klasy i interfejsy
1. Request
   Klasa reprezentująca żądanie HTTP.
   Atrybuty:
   * private final **Headers** headers - obiekt nagłówków HTTP
   * private final **String[]** path - tablica ciągów znaków ułatwiająca dopasowanie żądania do trasy
   * private final **String** protocol - wersja protokołu
   * private final **String** url - adres URL razem z query string
   * private final **String** method - metoda użyta w żądaniu HTTP
   Metody:
   * public **Headers** getHeaders() - metoda zwracająca obiekt nagłówków HTTP
   * public **String[]** getPath() - metoda zwracająca tablicę ciągów znaków ułatwiających dopasowanie trasy
   * public **String** getProtocol() - metoda zwracająca wersję protokołu
   * public **String** getUrl() - metoda zwracająca adres URL razem z query string
   * public **String** getMethod() - metoda zwracająca metodę użytą w żądaniu HTTP
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
6. RoutingList
   Klasa odpowiedzialna za przechowywanie dopasowania tras do obiektów obsługujących trasę. Zapewnia automatyczną inicjalizację listy obiektów obsługi trasy.
   Atrybuty:
   * private **HashMap<String, ArrayList<Handler>>** pathList;
   Metody:
   * public **void** addHandler() - metoda dodająca obiekt obsługi trasy
   * public **ArrayList<Handler>** getHandlers() - metoda pobierająca listę obiektów obsługi trasy
   * public **boolean** hasPath() - metoda sprawdzająca czy trasa jest zapisana, **true** - trasa zapisana / **false** - brak trasy
7. Router
   Klasa odpowiedzialna za dopasowanie odpowiedniej trasy obsługującej żądanie.
   Atrybuty:
   * private finale **RoutingList** routingList - obiekt przechowujący obiekty obsługi trasy wraz z dopasowaną trasę
   * private **int** depth - liczba informujący o kolejności obsługi trasy, informuje router, za który człon ścieżki odpowiada
   Metody:
   * public **int** getDepth() - zwraca głębokość routera
   * public **void** setDepth() - ustawia głębokość routera
   * public **RoutingList** getRoutingList() - zwraca listę routingu routera
   * public **void** handle() throws **Response.ResponseError**, **Next.NextError** - obsługuje

## Wyjątki
1. JExpError - ogólna klasa wyjątku
2. ResponseError - wyjątek obiektu odpowiedzi
3. NextError - wyjątek obiektu odpowiedzialnego za przetwarzanie potokowe

## Diagramy sekwencji

## TODO