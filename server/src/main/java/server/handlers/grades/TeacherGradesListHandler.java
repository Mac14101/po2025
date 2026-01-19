package server.handlers.grades;

import entities.Grade;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class TeacherGradesListHandler extends DatabaseHandler {

    public TeacherGradesListHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Grade> grades = this.database.getStudentGrades(Integer.parseInt(request.getParam("studentId")), Integer.parseInt(request.getSession("userId")));
        response.json(grades);
    }
}