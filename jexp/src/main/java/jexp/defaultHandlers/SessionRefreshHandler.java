package jexp.defaultHandlers;

import jexp.*;

public class SessionRefreshHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (!request.sessionEstabilished()) {
            response.status(401);
        }
        String newToken = request.refreshSession();
        response.type("application/json");
        response.status(200);
        response.send(newToken);
    }
}
