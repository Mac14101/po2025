package server.handlers.subjects;

import entities.Subject;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class CreateSubjectHandler extends DatabaseHandler {
    public CreateSubjectHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        Subject subject = request.getBody(Subject.class);
        this.database.createSubject(subject);
        response.status(201);
        response.json(subject);
    }
}
