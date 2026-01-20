package server.handlers.authorization;

import entities.User;
import jexp.*;

public class AdminOnlyHandler implements Handler {

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (User.Role.getRoleFromName(request.getSession("userRole")) != User.Role.ADMIN) {
            response.status(401);
            response.end();
            return;
        }
        next.next();
    }
}
