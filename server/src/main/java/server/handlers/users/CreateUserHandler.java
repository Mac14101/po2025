package server.handlers.users;

import entities.User;
import jexp.*;
import server.database.ApplicationDatabase;

public class CreateUserHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        User user = request.getBody(User.class);
        ApplicationDatabase.createUser(user);
        response.json(user);
        response.status(201);
    }
}
