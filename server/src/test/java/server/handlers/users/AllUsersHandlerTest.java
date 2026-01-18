package server.handlers.users;

import database.ApplicationDatabase;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AllUsersHandlerTest {

    private AllUsersHandler allUsersHandler;

    @Before
    public void setUp() {
        allUsersHandler = new AllUsersHandler();
    }

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = AllUsersHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        database.set(allUsersHandler, applicationDatabase);
        Mockito.when(applicationDatabase.getAllUsers()).thenReturn(Mockito.mock(ArrayList.class));
        Request request = Mockito.mock(Request.class);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        allUsersHandler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getAllUsers();
        Mockito.verify(response, Mockito.times(1)).json(Mockito.any(ArrayList.class));
    }
}