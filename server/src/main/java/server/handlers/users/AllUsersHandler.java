package server.handlers.users;

import entities.User;
import jexp.*;
import server.database.ApplicationDatabase;

import java.util.ArrayList;

public class AllUsersHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<User> users = ApplicationDatabase.getAllUsers();
        response.json(users);
    }
}
