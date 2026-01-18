package server.handlers.authentication;

import database.ApplicationDatabase;
import entities.Message;
import entities.User;
import entities.UserCredentials;
import jexp.*;

public class AuthenticationHandler implements Handler {
    private ApplicationDatabase database;

    public AuthenticationHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (request.sessionEstabilished()) {
            next.next();
            return;
        }
        UserCredentials credentials = request.getBody(UserCredentials.class);
        if (credentials == null || credentials.getEmail() == null || credentials.getPassword() == null) {
            response.status(400);
            Message message = new Message();
            message.addMessage("login", "Niepoprawny login lub hasło.");
            response.json(message);
            return;
        }
        User user = this.database.getUserCredentials(credentials.getEmail());
        if (user.getEmail() == null || user.getPassword() == null) {
            response.status(400);
            Message message = new Message();
            message.addMessage("login", "Niepoprawny login lub hasło.");
            response.json(message);
            return;
        }
        if (user.getPassword().equals(credentials.getPassword())) {
            String token = request.logIn(user.getId(), user.getEmail());
            request.setSession("userRole", user.getRole().toString());
            response.status(200);
            response.type("text/plain");
            response.send(token);
        }
    }

}
