package server.handlers.subjects;

import database.ApplicationDatabase;
import entities.Subject;
import jexp.*;

public class CreateSubjectHandler implements Handler {
    private ApplicationDatabase database;

    public CreateSubjectHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        Subject subject = request.getBody(Subject.class);
        this.database.createSubject(subject);
        response.status(201);
        response.json(subject);
    }
}
