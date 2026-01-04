package jexp;

import java.util.ArrayList;
import java.util.Objects;

/**
 * {@inheritDoc Handler}
 * Klasa pozwalająca na dodawanie różnych tras dla żądań.
 * Implementuje przetwarzanie potokowe oraz routing.
 */
public class Router implements Handler {
    private final ArrayList<Handler> handlers;
    private String route;
    private int depth;

    Router() {
        this.handlers = new ArrayList<>();
        this.route = null;
        this.depth = -1;
    }

    /**
     * Zwraca nazwę węzła trasy.
     *
     * @return nazwa węzła trasy
     */
    public String getRoute() {
        return this.route;
    }

    /**
     * Ustawia nazwę węzła trasy.
     *
     * @param route nowa nazwa węzła trasy
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     * Ustawia nową głębokość węzła trasy.
     *
     * @param depth nowa głębokość węzła trasy
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Aktualizuje głębokość wszystkich węzłów podległych sobie.
     */
    public void updateDepth() {
        for (Handler handler : this.handlers) {
            if (handler instanceof Router) {
                ((Router) handler).setDepth(this.depth + 1);
                ((Router) handler).updateDepth();
            } else if (handler instanceof ParamHandler) {
                ((ParamHandler) handler).setDepth(this.depth + 1);
            }
        }
    }

    /**
     * Przeszukuje listę obiektów obsługi tras, w poszukiwaniu routera o podanej nazwie węzła trasy.
     *
     * @param route nazwa węzła trasy
     * @return instancja routera o podanej nazwie węzła trasy
     */
    private Router findRoute(String route) {
        Router router = null;
        for (int i = this.handlers.size() - 1; i >= 0; i--) {
            if (this.handlers.get(i) instanceof Router && Objects.equals(((Router) this.handlers.get(i)).getRoute(), route)) {
                router = (Router) this.handlers.get(i);
                break;
            }
        }
        return router;
    }

    /**
     * Dodaje nowy obiekt obsługi tras do listy obiektów obsługi tras.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void use(String path, Handler handler) {
        Route route = new Route(path);
        //Sprawdzenie złożoności ścieżki
        if (route.getRoute().length == 0) {
            //Ścieżka jest pusta
            //TODO  Rzuć wyjątek jeśli handler jest klasy Router
            //Dodaj obiekt do listy obiektów obsługi tras
            this.handlers.add(handler);
        } else if (route.getRoute().length == 1 && handler instanceof Router) {
            //Ścieżka posiada jeden węzeł, ale obiekt jest routerem
            //Ustaw odpowiednią nazwę węzła tras w dodawanym routerze
            ((Router) handler).setRoute(route.getActualRoute());
            //Dodaj router do listy obiektów obsługi tras
            this.handlers.add(handler);
        } else {
            //Ścieżka jest złożona
            //Pobierz nazwę następnego węzła trasy
            String actualRoute = route.getActualRoute();
            //Pobierz router o nazwie następnego węzła trasy
            Router router = this.findRoute(actualRoute);
            if (router == null) {
                //Router o takiej nazwie nie istnieje
                //Sprawdź, czy nazwa węzła trasy nie jest parametrem trasy
                if (actualRoute.matches("^:.*$")) {
                    //Nazwa węzła trasy jest parametrem trasy
                    //Dodaj odpowiedni obiekt przepisujący wartość z trasy do listy parametrów trasy
                    this.handlers.add(new ParamHandler(actualRoute));
                }
                //Utwórz nowy router
                Router newRouter = new Router();
                //Ustaw nazwę węzła trasy dla nowego routera
                newRouter.setRoute(actualRoute);
                //Dodaj obiekt obsługujący trasę do nowego routera
                newRouter.use(route.getNextPath(), handler);
                //Dodaj nowy router do listy obiektów obsługujących trasę
                this.handlers.add(newRouter);
            } else {
                router.use(route.getNextPath(), handler);
            }
        }
    }

    /**
     * Dodaje obiekt obsługi trasy, który jest aktywny, tylko gdy żądanie posiada metodę HTTP 'GET'.
     * Opakowuje obiekt w obiekt klasy 'MethodHandler'.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void get(String path, Handler handler) {
        this.use(path, new MethodHandler("get", handler));
    }

    /**
     * Dodaje obiekt obsługi trasy, który jest aktywny, tylko gdy żądanie posiada metodę HTTP 'POST'.
     * Opakowuje obiekt w obiekt klasy 'MethodHandler'.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void post(String path, Handler handler) {
        this.use(path, new MethodHandler("post", handler));
    }

    /**
     * Dodaje obiekt obsługi trasy, który jest aktywny, tylko gdy żądanie posiada metodę HTTP 'PUT'.
     * Opakowuje obiekt w obiekt klasy 'MethodHandler'.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void put(String path, Handler handler) {
        this.use(path, new MethodHandler("put", handler));
    }

    /**
     * Dodaje obiekt obsługi trasy, który jest aktywny, tylko gdy żądanie posiada metodę HTTP 'DELETE'.
     * Opakowuje obiekt w obiekt klasy 'MethodHandler'.
     *
     * @param path    ścieżka do obiektu obsługi trasy
     * @param handler obiekt obsługi trasy
     */
    public void delete(String path, Handler handler) {
        this.use(path, new MethodHandler("delete", handler));
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        //Sprawdzenie, czy nazwa węzła odpowiada oczekiwanej nazwie węzła trasy żądania
        if (this.route == null || (request.getRoute().length > this.depth && this.route.equals(request.getRoute()[this.depth]))) {
            //Nazwa węzła odpowiednia
            //Przetwarzanie obiektu żądania i odpowiedzi przez wszystkie zapisane w routerze obiekty obsługi trasy
            for (int i = 0; i < this.handlers.size(); i++) {
                Next newNext = new Next();
                //Przetwarzanie potokowe
                this.handlers.get(i).handle(request, response, newNext);
                //Sprawdzenie, czy następny obiekt ma przetworzyć obiekty żądania i odpowiedzi
                if (newNext.getNextRoute()) {
                    //Przekazano do następnej trasy
                    //Przekaż obiekty żądania i odpowiedzi do następnego obiektu obsługi trasy
                    next.next();
                    //Zakończ przetwarzanie
                    return;
                } else if (!newNext.getNext()) {
                    //Nie przekazano do następnego obiektu
                    //Zakończ przetwarzanie
                    return;
                }
            }
            //Przekaż obiekty żądania i odpowiedzi do następnego obiektu obsługi trasy
            next.next();
        } else {
            //Nazwa węzła nieodpowiednia
            //Przekaż obiekty żądania i odpowiedzi do następnego obiektu obsługi trasy
            next.next();
        }
    }

}
