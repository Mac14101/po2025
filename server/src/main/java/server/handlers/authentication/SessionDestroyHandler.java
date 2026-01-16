package server.handlers.authentication;

import jexp.*;

public class SessionDestroyHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (!request.sessionEstabilished()) {
            response.status(200);
            response.end();
            return;
        }
        request.logOut();
        response.status(200);
        response.end();
    }
}
