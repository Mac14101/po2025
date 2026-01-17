package server.handlers.subjects;

import database.ApplicationDatabase;
import entities.Subject;
import jexp.*;

import java.util.ArrayList;

public class AllSubjectsHandler implements Handler {
    private ApplicationDatabase database;

    public AllSubjectsHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Subject> subjects = this.database.getAllSubjects();
        response.json(subjects);
    }
}
