package server.handlers.students;

import database.ApplicationDatabase;
import entities.Student;
import jexp.*;

import java.util.ArrayList;

public class StudentsClassHandler implements Handler {
    private ApplicationDatabase database;

    public StudentsClassHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Student> students = database.getClassStudents(Integer.parseInt(request.getParam("classId")));
        response.json(students);
    }
}
