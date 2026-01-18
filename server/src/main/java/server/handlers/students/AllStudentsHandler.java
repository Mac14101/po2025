package server.handlers.students;

import database.ApplicationDatabase;
import entities.Student;
import jexp.*;

import java.util.ArrayList;

public class AllStudentsHandler implements Handler {
    private ApplicationDatabase database;

    public AllStudentsHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Student> students = database.getAllStudents();
        response.json(students);
    }
}
