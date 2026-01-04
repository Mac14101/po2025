package jexp;

/**
 * {@inheritDoc Handler}
 * Klasa odpowiedzialna za przetważanie tylko żądań o wybranej metodzie HTTP.
 */
public class MethodHandler implements Handler {
    String method;
    Handler handler;

    public MethodHandler(String method, Handler handler) {
        this.method = method;
        this.handler = handler;
    }

    public void handle(Request request, Response response, Next next) throws JExpError {
        if (request.getMethod().equalsIgnoreCase(this.method)) {
            // Obsługa żądania
            handler.handle(request, response, next);
        } else {
            // Przekazanie obiektów żądania i odpowiedzi dalej
            next.next();
        }
    }
}
