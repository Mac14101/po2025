package jexp;

public interface Handler {
    public void handle(Request request, Response response, Next next) throws Exception;
}
