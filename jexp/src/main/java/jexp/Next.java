package jexp;

/**
 * Obiekt przekazujący informację czy następny obiekt ma obsłużyć żądanie.
 */
public class Next {
    private boolean nextHandler;
    private boolean nextRoute;

    Next() {
        this.nextHandler = false;
        this.nextRoute = false;
    }

    /**
     * Funkcja, którą należy wywołać oby przekazać obiekty żądania i odpowiedzi do następnego obiektu obsługującego trasę.
     *
     * @throws NextError błąd rzucany, gdy metoda została już wcześniej wywołana
     */
    public void next() throws NextError {
        if (this.nextHandler || this.nextRoute) {
            throw new NextError("Next handler has been already invoked!");
        }
        this.nextHandler = true;
    }

    /**
     * Funkcja, którą należy wywołać oby przekazać obiekty żądania i odpowiedzi do następnej trasy.
     *
     * @throws NextError błąd rzucany, gdy metoda została już wcześniej wywołana
     */
    public void nextRoute() throws NextError {
        if (this.nextHandler || this.nextRoute) {
            throw new NextError("Next handler has been already invoked!");
        }
        this.nextRoute = true;
    }

    public boolean getNext() {
        return this.nextHandler;
    }

    public boolean getNextRoute() {
        return this.nextRoute;
    }

    public static class NextError extends JExpError {
        public NextError(String message) {
            super(message);
        }
    }
}
