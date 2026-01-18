package server.handlers.classes;

import database.ApplicationDatabase;
import entities.SchoolGroup;
import jexp.*;

public class CreateClassHandler implements Handler {
    private ApplicationDatabase database;

    public CreateClassHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        SchoolGroup schoolGroup = request.getBody(SchoolGroup.class);
        this.database.createClass(schoolGroup);
        response.status(201);
        response.json(schoolGroup);
    }
}
