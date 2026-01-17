package server.handlers.authentication;

import database.ApplicationDatabase;
import entities.User;
import jexp.*;

public class UserDataHandler implements Handler {

    private ApplicationDatabase database;

    public UserDataHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (!request.sessionEstabilished()) {
            response.status(401);
            response.end();
            return;
        }
        User userData = this.database.getUserData(Integer.parseInt(request.getSession("userId")));
        response.json(userData);
    }
}
