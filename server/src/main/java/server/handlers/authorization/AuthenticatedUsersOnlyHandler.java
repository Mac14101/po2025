package server.handlers.authorization;

import jexp.*;

public class AuthenticatedUsersOnlyHandler implements Handler {

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (!request.sessionEstabilished()) {
            response.status(401);
            response.end();
            return;
        }
        next.next();
    }
}
