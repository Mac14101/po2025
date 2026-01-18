package server.handlers.classes;

import database.ApplicationDatabase;
import entities.SchoolGroup;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class CreateClassHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = CreateClassHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        SchoolGroup schoolGroup = Mockito.mock(SchoolGroup.class);
        Mockito.when(request.getBody((Class<Object>) Mockito.any())).thenReturn(schoolGroup);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        CreateClassHandler handler = new CreateClassHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(request, Mockito.times(1)).getBody((Class<Object>) Mockito.any());
        Mockito.verify(applicationDatabase, Mockito.times(1)).createClass(schoolGroup);
        Mockito.verify(response, Mockito.times(1)).status(201);
        Mockito.verify(response, Mockito.times(1)).json(schoolGroup);
    }
}