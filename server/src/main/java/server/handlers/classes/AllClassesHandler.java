package server.handlers.classes;

import entities.SchoolGroup;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class AllClassesHandler extends DatabaseHandler {

    public AllClassesHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<SchoolGroup> classes = this.database.getAllClass();
        response.json(classes);
    }
}
