package jexp.defaultHandlers;

import jexp.Handler;
import jexp.Next;
import jexp.Request;
import jexp.Response;

public class NotFoundHandler implements Handler {
    @Override
    public void handle(Request request, Response response, Next next) throws Response.ResponseError, Next.NextError {
        response.status(404);
        response.send("Error 404 - The server cannot find requested resources");
    }
}
