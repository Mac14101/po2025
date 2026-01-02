package jexp.defaultHandlers;

import jexp.*;

public class ServerErrorHandler implements ErrorHandler {
    @Override
    public void handle(Exception exception, Request request, Response response, Next next) throws JExpError {
        System.err.println(exception.getMessage());
        response.status(500);
        response.send("Error 500 - Server Error");
    }
}
