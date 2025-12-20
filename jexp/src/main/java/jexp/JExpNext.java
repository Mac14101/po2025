package jexp;

public class JExpNext {
    private boolean nextHandler;
    private boolean nextRoute;

    JExpNext() {
        this.nextHandler = false;
        this.nextRoute = false;
    }

    public void next() throws Exception {
        if (this.nextHandler || this.nextRoute) {
            throw new Exception(); // TODO Dodać klasę błędu następnego obiektu przetwarzania HTTP
        }
        this.nextHandler = true;
    }

    public void nextRoute() throws Exception {
        if (this.nextHandler || this.nextRoute) {
            throw new Exception(); // TODO Dodać klasę błędu następnego obiektu przetwarzania HTTP
        }
        this.nextRoute = true;
    }

    public boolean getNext() {
        return this.nextHandler;
    }

    public boolean getNextRoute() {
        return this.nextRoute;
    }
}
