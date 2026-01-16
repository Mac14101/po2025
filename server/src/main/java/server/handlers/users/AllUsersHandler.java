package server.handlers.users;

import database.ApplicationDatabase;
import entities.User;
import jexp.*;

import java.util.ArrayList;

public class AllUsersHandler implements Handler {
    private ApplicationDatabase database;

    public AllUsersHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<User> users = this.database.getAllUsers();
        response.json(users);
    }
}
