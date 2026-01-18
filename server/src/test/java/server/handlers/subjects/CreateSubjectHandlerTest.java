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

public class CreateSubjectHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = CreateSubjectHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        Subject subject = Mockito.mock(Subject.class);
        Mockito.when(request.getBody((Class<Object>) Mockito.any())).thenReturn(subject);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        CreateSubjectHandler handler = new CreateSubjectHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).createSubject(subject);
        Mockito.verify(response, Mockito.times(1)).status(201);
        Mockito.verify(response, Mockito.times(1)).json(subject);
    }
}