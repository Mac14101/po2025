package server.handlers;

import database.ApplicationDatabase;
import jexp.*;

public class DatabaseHandler implements Handler {
    protected ApplicationDatabase database;

    public DatabaseHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {

    }
}
