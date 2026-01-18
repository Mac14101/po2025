package server.handlers.authentication;

import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class UserDataHandler extends DatabaseHandler {
    public UserDataHandler() {
        super();
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
