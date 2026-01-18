package server.handlers.subjects;

import database.ApplicationDatabase;
import entities.Subject;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AllSubjectsHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = AllSubjectsHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        ArrayList<Subject> subjects = Mockito.mock(ArrayList.class);
        Mockito.when(applicationDatabase.getAllSubjects()).thenReturn(subjects);
        Request request = Mockito.mock(Request.class);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AllSubjectsHandler handler = new AllSubjectsHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getAllSubjects();
        Mockito.verify(response, Mockito.times(1)).json(subjects);
    }
}