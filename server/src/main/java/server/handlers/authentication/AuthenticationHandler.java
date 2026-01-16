package server.handlers.authentication;

import database.ApplicationDatabase;
import entities.Message;
import entities.User;
import jexp.*;

public class AuthenticationHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        UserCredentials credentials = request.getBody(UserCredentials.class);
        if (credentials == null || credentials.email == null || credentials.password == null) {
            response.status(400);
            Message message = new Message();
            message.addMessage("login", "Niepoprawny login lub hasło.");
            response.json(message);
            return;
        }
        User user = ApplicationDatabase.getUserCredentials(credentials.email);
        if (user.getEmail() == null || user.getPassword() == null) {
            response.status(400);
            Message message = new Message();
            message.addMessage("login", "Niepoprawny login lub hasło.");
            response.json(message);
            return;
        }
        if (user.getPassword().equals(credentials.password)) {
            String token = request.logIn(user.getId(), user.getEmail());
            response.status(200);
            response.type("text/plain");
            response.send(token);
        }
    }

    public static class UserCredentials {
        public String email;
        public String password;
    }
}
