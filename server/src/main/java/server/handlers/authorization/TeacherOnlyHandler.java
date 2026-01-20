package server.handlers.authorization;

import entities.User;
import jexp.*;

public class TeacherOnlyHandler implements Handler {

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (User.Role.getRoleFromName(request.getSession("userRole")) != User.Role.TEACHER) {
            response.status(401);
            response.end();
            return;
        }
        next.next();
    }
}
