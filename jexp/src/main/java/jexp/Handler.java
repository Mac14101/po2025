package jexp;

/**
 * Interfejs obiektu obsługującego trasę.
 */
public interface Handler {
    /**
     * Metoda obsługująca trasę.
     *
     * @param request  obiekt żądania HTTP
     * @param response obiekt odpowiedzi HTTP
     * @param next     obiekt odpowiedzialny za przejście do następnego obiektu/trasy
     *
     */
    public void handle(Request request, Response response, Next next) throws JExpError;
}
