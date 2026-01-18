package server.handlers.students;

import entities.Student;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class AllStudentsHandler extends DatabaseHandler {
    public AllStudentsHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Student> students = database.getAllStudents();
        response.json(students);
    }
}
