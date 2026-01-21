package jexp;

public interface Logger {
    public void logResponse(Request request, Response response);

    public void logError(Exception error, Request request, Response response);

    public void criticalError(Exception error);
}
