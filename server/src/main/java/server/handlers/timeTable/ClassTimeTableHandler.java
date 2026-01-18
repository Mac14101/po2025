package server.handlers.timeTable;

import entities.SchoolClass;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class ClassTimeTableHandler extends DatabaseHandler {
    public ClassTimeTableHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<SchoolClass> schoolClasses = this.database.getClassSchedule(Integer.parseInt(request.getParam("classId")));
        response.json(schoolClasses);
    }
}
