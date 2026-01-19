package server.handlers.authorization;

import entities.User;
import jexp.*;

public class StudentOnlyHandler implements Handler {

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (!request.sessionEstabilished()) {
            response.status(401);
            response.end();
            return;
        }
        if (User.Role.getRoleFromName(request.getSession("userRole")) != User.Role.STUDENT) {
            response.status(401);
            response.end();
            return;
        }
        next.next();
    }
}
