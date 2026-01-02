package jexp;

public interface ErrorHandler {
    public void handle(Exception error, Request request, Response response, Next next) throws JExpError;
}
