package server.handlers.users;

import database.ApplicationDatabase;
import entities.User;
import jexp.*;

public class CreateUserHandler implements Handler {
    private ApplicationDatabase database;

    public CreateUserHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        User user = request.getBody(User.class);
        this.database.createUser(user);
        response.json(user);
        response.status(201);
    }
}
