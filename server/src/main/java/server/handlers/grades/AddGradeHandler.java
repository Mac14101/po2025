package server.handlers.grades;

import entities.Grade;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class AddGradeHandler extends DatabaseHandler {

    public AddGradeHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        Grade grade = request.getBody(Grade.class);
        this.database.addStudentGrade(Integer.parseInt(request.getParam("studentId")), Integer.parseInt(request.getSession("userId")), grade);
        response.status(201);
        response.json(grade);
    }
}
