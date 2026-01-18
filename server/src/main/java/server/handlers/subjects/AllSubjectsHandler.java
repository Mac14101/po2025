package server.handlers.subjects;

import entities.Subject;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class AllSubjectsHandler extends DatabaseHandler {
    public AllSubjectsHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Subject> subjects = this.database.getAllSubjects();
        response.json(subjects);
    }
}
