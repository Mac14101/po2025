package jexp;

public class MethodHandler implements Handler {
    String method;
    Handler handler;

    public MethodHandler(String method, Handler handler) {
        this.method = method;
        this.handler = handler;
    }

    public void handle(Request request, Response response, Next next) throws Next.NextError, Response.ResponseError {
        if (request.getMethod().equals(this.method)) {
            handler.handle(request, response, next);
        } else {
            next.next();
        }
    }
}
