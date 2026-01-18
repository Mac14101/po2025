package server.handlers.users;

import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class AllUsersHandler extends DatabaseHandler {

    public AllUsersHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<User> users = this.database.getAllUsers();
        response.json(users);
    }
}
