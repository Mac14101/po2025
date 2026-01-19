package server.handlers.attendance;

import entities.Attendance;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class StudentAttendanceHandler extends DatabaseHandler {
    public StudentAttendanceHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Attendance> attendances = this.database.getUserAttendance(Integer.parseInt(request.getSession("userId")));
        response.json(attendances);
    }
}
