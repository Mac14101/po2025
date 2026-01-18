package server.handlers.students;

import database.ApplicationDatabase;
import entities.Student;
import jexp.*;

public class AddStudentHandler implements Handler {
    private ApplicationDatabase database;

    public AddStudentHandler() {
        this.database = ApplicationDatabase.getInstance();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        Student student = request.getBody(Student.class);
        this.database.addStudent(student.getId(), student.getSchoolGroup().getId());
        response.status(201);
        response.json(student);
    }
}
