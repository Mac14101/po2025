package server.handlers.attendance;

import entities.Attendance;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class LessonAttendanceListHandler extends DatabaseHandler {
    public LessonAttendanceListHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Attendance> attendances = this.database.getAttendanceList(Integer.parseInt(request.getParam("lessonId")));
        response.json(attendances);
    }
}
