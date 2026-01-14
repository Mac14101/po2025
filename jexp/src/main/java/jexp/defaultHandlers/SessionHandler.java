package jexp.defaultHandlers;

import jexp.*;
import jexp.session.Manager;
import jexp.session.Session;

import java.util.List;

public class SessionHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        List<String> header = request.getHeaders().get("Authorization");
        if (header != null && !header.isEmpty()) {
            String token = header.getFirst().split(" ")[1];
            Manager sessionManager = Manager.getInstance();
            Session session = sessionManager.getSession(token);
            if (session != null) {
                request.setSessionObject(session);
            }
        }
        next.next();
    }
}
