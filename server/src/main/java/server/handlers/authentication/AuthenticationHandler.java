package server.handlers.authentication;

import database.ApplicationDatabase;
import jexp.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        UserCredentials credentials = request.getBody(UserCredentials.class);
        if (credentials == null || credentials.email == null || credentials.password == null) {
            response.status(400);
            return;
        }
        ResultSet userRow = ApplicationDatabase.getUserCredentials(credentials.email);
        try {
            if ((userRow != null) && (userRow.getString("password").equals(credentials.password))) {
                String token = request.logIn(userRow.getInt("uid"), userRow.getString("email"));
                response.send(token);
            } else {
                response.status(400);
            }
        } catch (SQLException e) {
            response.status(400);
        }
    }

    public static class UserCredentials {
        public String email;
        public String password;
    }
}
