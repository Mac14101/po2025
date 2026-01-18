package server.handlers.classes;

import database.ApplicationDatabase;
import entities.SchoolGroup;
import jexp.*;

import java.util.ArrayList;

public class AllClassesHandler implements Handler {
    private ApplicationDatabase database;

    public AllClassesHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<SchoolGroup> classes = this.database.getAllClass();
        response.json(classes);
    }
}
