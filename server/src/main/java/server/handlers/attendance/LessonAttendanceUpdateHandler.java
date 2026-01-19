package server.handlers.attendance;

import entities.Attendance;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class LessonAttendanceUpdateHandler extends DatabaseHandler {
    public LessonAttendanceUpdateHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        Attendance attendance = request.getBody(Attendance.class);
        this.database.updateAttendance(Integer.parseInt(request.getParam("lessonId")), attendance);
        response.json(attendance);
    }
}
