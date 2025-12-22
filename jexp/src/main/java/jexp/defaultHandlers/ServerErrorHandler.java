package jexp.defaultHandlers;

import jexp.Handler;
import jexp.Next;
import jexp.Request;
import jexp.Response;

public class ServerErrorHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws Response.ResponseError, Next.NextError {
        response.status(500);
        response.send("Error 500 - Server Error");
    }
}
