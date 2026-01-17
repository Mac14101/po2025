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
import java.util.ArrayList;

public class AllClassesHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = AllClassesHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        ArrayList<SchoolGroup> schoolGroups = Mockito.mock(ArrayList.class);
        Mockito.when(applicationDatabase.getAllClass()).thenReturn(schoolGroups);
        Request request = Mockito.mock(Request.class);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AllClassesHandler handler = new AllClassesHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getAllClass();
        Mockito.verify(response, Mockito.times(1)).json(schoolGroups);
    }
}