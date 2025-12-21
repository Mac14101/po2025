package jexp;

public class Next {
    private boolean nextHandler;
    private boolean nextRoute;

    Next() {
        this.nextHandler = false;
        this.nextRoute = false;
    }

    public void next() throws NextError {
        if (this.nextHandler || this.nextRoute) {
            throw new NextError("Next handler has been already invoked!");
        }
        this.nextHandler = true;
    }

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
