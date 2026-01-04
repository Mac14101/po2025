package jexp.defaultHandlers;

import jexp.ErrorHandler;
import jexp.JExpError;
import jexp.Request;
import jexp.Response;

public class ServerErrorHandler implements ErrorHandler {
    @Override
    public void handle(Exception exception, Request request, Response response) throws JExpError {
        System.err.println(exception.getMessage());
        response.status(500);
        response.send("Error 500 - Server Error");
    }
}
