package server.handlers;

import jexp.Logger;
import jexp.Request;
import jexp.Response;

import java.time.LocalDateTime;

public class MainLogger implements Logger {
    private String name;

    public MainLogger(String name) {
        this.name = name;
    }

    private String messageInfo() {
        return this.name + "[" + LocalDateTime.now().toString() + "] : ";
    }

    @Override
    public void logResponse(Request request, Response response) {
        System.out.println(this.messageInfo() + response.getStatus() + "\t" + request.getPath() + "\t" + (response.getBody() == null ? "0" : response.getBody().getBytes().length));
    }

    @Override
    public void logError(Exception error, Request request, Response response) {
        System.err.println(this.messageInfo() + response.getStatus() + "\t" + request.getPath() + "\t" + (response.getBody() == null ? "0" : response.getBody().getBytes().length) + "\n" + error.getMessage());
    }

    @Override
    public void criticalError(Exception error) {
        System.err.println(this.messageInfo() + "Critical error occurred\n" + error.getMessage());
    }
}
