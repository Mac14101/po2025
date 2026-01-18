package server.handlers.users;

import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class CreateUserHandler extends DatabaseHandler {
    public CreateUserHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        User user = request.getBody(User.class);
        this.database.createUser(user);
        response.json(user);
        response.status(201);
    }
}
