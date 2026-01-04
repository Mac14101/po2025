package jexp;

/**
 * Interfejs dla obiektu obsługującego błędy serwera.
 */
public interface ErrorHandler {
    /**
     * Metoda obsługująca błąd.
     *
     * @param error    błąd powstały podczas działania serwera
     * @param request  obiekt żądania HTTP
     * @param response obiekt odpowiedzi HTTP
     * @throws JExpError wyjątki, które mogą powstać podczas korzystania z obiektów żądania lub odpowiedzi
     */
    public void handle(Exception error, Request request, Response response) throws JExpError;
}
