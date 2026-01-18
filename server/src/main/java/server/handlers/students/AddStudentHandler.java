package server.handlers.students;

import entities.Student;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class AddStudentHandler extends DatabaseHandler {

    public AddStudentHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        Student student = request.getBody(Student.class);
        this.database.addStudent(student.getId(), student.getSchoolGroup().getId());
        response.status(201);
        response.json(student);
    }
}
