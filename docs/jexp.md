# Moduł JExp
JExp to moduł wzorowany na frameworku express.js dla środowiska Node.js. Moduł opakowuje wbudowany serwer HTTP, aby zapewnić szybkie i wygodne budowanie serwerów, a zwłaszcza REST API. Klasa **JExpServer** posiada główny router aplikacji odpowiedzialny za obsługę wszystkich tras. Do głównego routera mogą zostać inne routery obsługujące inne trasy, tworząc drzewiastą strukturę obsługującą trasy oraz zapewniającą przetwarzanie potokowe (przez co ważna jest kolejność dodawania tras). Taka struktura aplikacji jest możliwa dzięki użyciu wzorca Composite, każdy obiekt obsługujący żądanie rozszerza interfejs **JExpHandler**, przez co musi posiadać metodę **handle()**, która jest wywoływana stopniowo przez kolejne elementy, aż przetwarzanie żądania zostanie zakończone.

## Diagram UML klas

```plantuml
@startuml
interface JExpHandler{
+ handle(): void
}
class JExpMethodHandler{
}
class JExpRouter{
}
class JExpServer{
}

class JExpRequest{
}
class JExpResponse{
}
class JExpNext{
}

JExpMethodHandler--|>JExpHandler
JExpMethodHandler--*JExpHandler
JExpRouter--|>JExpHandler
JExpRouter--*JExpHandler
JExpRouter--*JExpMethodHandler
JExpServer--*JExpRouter

JExpServer..|>JExpRequest
JExpServer..|>JExpResponse
JExpServer..|>JExpNext

JExpHandler..|>JExpRequest
JExpHandler..|>JExpResponse
JExpHandler..|>JExpNext

@enduml
```

## Klasy i interfejsy

1. JExpHandler
   Interfejs, którego rozszerzeniem są obiekty obsługujące określone trasy.
   Metody:
      * public **void** handle() - metoda obsługująca żądanie HTTP
2. JExpMethodHandler
   Klasa rozszerzająca interfejs **JExpHandler**, obsługująca tylko żądania o wybranej metodzie HTTP.
   Atrybuty:
      * private **String** method - metoda żądania HTTP, którą obiekt może obsłużyć
   Metody:
      * public **void** handle() - metoda obsługująca żądanie HTTP tylko o metodzie podanej w atrybucie **method**
3. JExpRouter
   Klasa rozszerzająca interfejs **JExpHandler**, implementująca routing.
   Atrybuty:
      * private **HashMap<String, ArrayList<JExpHandler>>** handlers - tablica zawierająca odwzorowanie tras na obiekty obsługujące trasę
   Metody:
      * public **void** use() - metoda dodająca nowy obiekt obsługujący trasę do routera, dowolna metoda HTTP
      * public **void** get() - metoda dodająca nowy obiekt obsługujący trasę do routera, metoda HTTP *GET*
      * public **void** post() - metoda dodająca nowy obiekt obsługujący trasę do routera, metoda HTTP *POST*
      * public **void** put() - metoda dodająca nowy obiekt obsługujący trasę do routera, metoda HTTP *PUT*
      * public **void** delete() - metoda dodająca nowy obiekt obsługujący trasę do routera, metoda HTTP *DELETE*
      * public **void** method() - metoda dodająca nowy obiekt obsługujący trasę do routera, wybrana metoda HTTP
4. JExpServer
   Klasa zawierająca serwer HTTP oraz główny router serwera, do którego są dodawane obiekty obsługujące trasy.
   Atrybuty:
      * private **HttpServer** server
      * private **JExpRouter** mainRouter - główny router serwera
   Metody:
      * public **void** listen() - tworzy serwer HTTP, który rozpoczyna nasłuchiwanie na wybranym porcie
      * public **void** use() - metoda dodająca nowy obiekt obsługujący trasę do głównego routera, dowolna metoda HTTP
      * public **void** get() - metoda dodająca nowy obiekt obsługujący trasę do głównego routera, metoda HTTP *GET*
      * public **void** post() - metoda dodająca nowy obiekt obsługujący trasę do głównego routera, metoda HTTP *POST*
      * public **void** put() - metoda dodająca nowy obiekt obsługujący trasę do głównego routera, metoda HTTP *PUT*
      * public **void** delete() - metoda dodająca nowy obiekt obsługujący trasę do głównego routera, metoda HTTP *DELETE*
      * public **void** method() - metoda dodająca nowy obiekt obsługujący trasę do głównego routera, wybrana metoda HTTP
5. JExpRequest
   Klasa reprezentująca żądanie HTTP.
   Atrybuty:
   * private final **Headers** headers - obiekt nagłówków HTTP
   * private final **String[]** path - tablica ciągów znaków ułatwiająca dopasowanie żądania do trasy
   * private final **String** protocol - wersja protokołu
   * private final **String** url - adres URL razem z query string
   Metody:
   * public **Headers** getHeaders() - metoda zwracająca obiekt nagłówków HTTP
   * public **String[]** getPath() - metoda zwracająca tablicę ciągów znaków ułatwiających dopasowanie trasy
   * public **String** getProtocol() - metoda zwracająca wersję protokołu
   * public **String** getUrl() - metoda zwracająca adres URL razem z query string
6. JExpResponse
   Klasa reprezentująca odpowiedź HTTP.
   Atrybuty:
   Metody:

## Diagramy sekwencji

### Obsługa żądanie HTTP
1. Brak routerów zagnieżdżonych, jeden obiekt obsługi trasy
```plantuml
@startuml
actor "Użytkownik" as User
participant "JExpApp" as Server
participant "mainRouter" as Router
participant "JExpHandler" as Handler
User->Server : Wysłanie żądania Http
activate Server
Server->Server : Stworzenie obiektu żądania i obiektu odpowiedzi
Server->Router : handle()
activate Router
Router->Handler : handle()
activate Handler
Handler->Router
deactivate Handler
Router->Server
deactivate Router
Server->User : Wysłanie odpowiedzi Http
deactivate Server
@enduml
```

2. Router zagnieżdżony, jeden obiekt obsługujący trasę
```plantuml
@startuml
actor "Użytkownik" as User
participant "JExpApp" as Server
participant "mainRouter" as MainRouter
participant "Router(/a/)" as Router1
participant "Router(/a/b/)" as Router2
participant "JExpHandler" as Handler
User->Server : Wysłanie żądania Http
activate Server
Server->Server : Stworzenie obiektu żądania i obiektu odpowiedzi
Server->MainRouter : handle()
activate MainRouter
MainRouter->Router1 : handle()
activate Router1
Router1->Router2 : handle()
activate Router2
Router2->Handler : handle()
activate Handler
Handler->Router2
deactivate Handler
Router2->Router1
deactivate Router2
Router1->MainRouter
deactivate Router1
MainRouter->Server
deactivate MainRouter
Server->User : Wysłanie odpowiedzi
deactivate Server
@enduml
```

3. Brak routerów zagnieżdżonych, dwa obiekty obsługujące trasę trasy
```plantuml
@startuml
actor "Użytkownik" as User
participant "JExpApp" as Server
participant "mainRouter" as Router
participant "JExpHandler1" as Handler1
participant "JExpHandler2" as Handler2
User->Server : Wysłanie żądania Http
activate Server
Server->Server : Stworzenie obiektu żądania i obiektu odpowiedzi
Server->Router : handle()
activate Router
Router->Handler1 : handle()
Activate Handler1
Handler1->Router : JExpNext.next()
deactivate Handler1
Router->Handler2 : handle()
activate Handler2
Handler2->Router
deactivate Handler2
Router->Server
deactivate Router
Server->User : Wysłanie odpowiedzi
deactivate Server
@enduml
```