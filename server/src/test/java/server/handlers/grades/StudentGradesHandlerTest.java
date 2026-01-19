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
import java.util.ArrayList;

public class StudentGradesHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        ArrayList<Grade> grades = Mockito.mock(ArrayList.class);
        Mockito.when(applicationDatabase.getUserGrades(Mockito.anyInt())).thenReturn(grades);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getSession("userId")).thenReturn("1");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        StudentGradesHandler handler = new StudentGradesHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getUserGrades(1);
        Mockito.verify(response, Mockito.times(1)).json(grades);
    }
}