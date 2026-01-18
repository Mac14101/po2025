package server.handlers.timeTable;

import entities.SchoolClass;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class AddClassTimeTableHandler extends DatabaseHandler {
    public AddClassTimeTableHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        SchoolClass schoolClass = request.getBody(SchoolClass.class);
        this.database.addClassSchedule(schoolClass);
        response.status(201);
        response.json(schoolClass);
    }
}
