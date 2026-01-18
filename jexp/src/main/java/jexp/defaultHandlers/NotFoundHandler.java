package jexp.defaultHandlers;

import jexp.*;

public class NotFoundHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        response.status(404);
        response.send("Error 404 - The server cannot find requested resources");
    }
}
