package jexp.defaultHandlers;

import jexp.*;
import jexp.session.Manager;
import jexp.session.User;

import java.util.List;

public class SessionHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        List<String> header = request.getHeaders().get("Authorization");
        if (header != null && !header.isEmpty()) {
            String token = header.getFirst().split(" ")[1];
            Manager sessionManager = Manager.getInstance();
            User user = sessionManager.getSession(token);
            if (user != null) {
                request.setUser(user);
            }
        }
        next.next();
    }
}
