package server.handlers.students;

import database.ApplicationDatabase;
import entities.Student;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class StudentsClassHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        ArrayList<Student> students = Mockito.mock(ArrayList.class);
        Mockito.when(applicationDatabase.getClassStudents(1)).thenReturn(students);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getParam("classId")).thenReturn("1");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        StudentsClassHandler handler = new StudentsClassHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getClassStudents(1);
        Mockito.verify(response, Mockito.times(1)).json(students);
    }
}