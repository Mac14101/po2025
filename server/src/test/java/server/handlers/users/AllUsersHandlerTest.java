package server.handlers.users;

import database.ApplicationDatabase;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AllUsersHandlerTest {
    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Mockito.when(applicationDatabase.getAllUsers()).thenReturn(Mockito.mock(ArrayList.class));
        Request request = Mockito.mock(Request.class);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AllUsersHandler allUsersHandler = new AllUsersHandler();
        database.set(allUsersHandler, applicationDatabase);
        allUsersHandler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getAllUsers();
        Mockito.verify(response, Mockito.times(1)).json(Mockito.any(ArrayList.class));
    }
}