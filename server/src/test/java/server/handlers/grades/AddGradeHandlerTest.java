package server.handlers.grades;

import database.ApplicationDatabase;
import entities.Grade;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;

public class AddGradeHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getParam("studentId")).thenReturn("1");
        Mockito.when(request.getSession("userId")).thenReturn("2");
        Grade grade = Mockito.mock(Grade.class);
        Mockito.when(request.getBody(Grade.class)).thenReturn(grade);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AddGradeHandler handler = new AddGradeHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(request, Mockito.times(1)).getBody(Grade.class);
        Mockito.verify(applicationDatabase, Mockito.times(1)).addStudentGrade(1, 2, grade);
        Mockito.verify(response, Mockito.times(1)).status(201);
        Mockito.verify(response, Mockito.times(1)).json(grade);
    }
}