package server.handlers.timeTable;

import entities.SchoolClass;
import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class StudentTimeTableHandler extends DatabaseHandler {
    public StudentTimeTableHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        if (User.Role.getRoleFromName(request.getSession("userRole")) != User.Role.STUDENT) {
            next.next();
            return;
        }
        ArrayList<SchoolClass> schoolClasses = this.database.getStudentSchedule(Integer.parseInt(request.getSession("userId")));
        response.json(schoolClasses);
    }
}
