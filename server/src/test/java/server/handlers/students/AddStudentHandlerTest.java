package server.handlers.students;

import database.ApplicationDatabase;
import entities.SchoolGroup;
import entities.Student;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;

public class AddStudentHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        Student student = Mockito.mock(Student.class);
        Mockito.when(student.getId()).thenReturn(1);
        SchoolGroup schoolGroup = Mockito.mock(SchoolGroup.class);
        Mockito.when(schoolGroup.getId()).thenReturn(2);
        Mockito.when(student.getSchoolGroup()).thenReturn(schoolGroup);
        Mockito.when(request.getBody(Student.class)).thenReturn(student);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AddStudentHandler handler = new AddStudentHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).addStudent(1, 2);
        Mockito.verify(response, Mockito.times(1)).json(student);
    }
}