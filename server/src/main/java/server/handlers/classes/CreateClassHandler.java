package server.handlers.classes;

import entities.SchoolGroup;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class CreateClassHandler extends DatabaseHandler {

    public CreateClassHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        SchoolGroup schoolGroup = request.getBody(SchoolGroup.class);
        this.database.createClass(schoolGroup);
        response.status(201);
        response.json(schoolGroup);
    }
}
