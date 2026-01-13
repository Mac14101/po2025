package server.handlers.users;

import database.ApplicationDatabase;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

public class AllUsersHandlerTest {

    private AllUsersHandler allUsersHandler;

    @Before
    public void setUp() {
        allUsersHandler = new AllUsersHandler();
    }

    @Test
    public void handle() {
        try (MockedStatic<ApplicationDatabase> applicationDatabaseMockedStatic = Mockito.mockStatic(ApplicationDatabase.class)) {
            applicationDatabaseMockedStatic.when(ApplicationDatabase::getAllUsers).thenReturn(Mockito.mock(ArrayList.class));
            Request request = Mockito.mock(Request.class);
            Response response = Mockito.mock(Response.class);
            Next next = Mockito.mock(Next.class);
            allUsersHandler.handle(request, response, next);
            applicationDatabaseMockedStatic.verify(ApplicationDatabase::getAllUsers, Mockito.times(1));
            Mockito.verify(response, Mockito.times(1)).json(Mockito.any(ArrayList.class));
        } catch (JExpError e) {
            throw new RuntimeException(e);
        }
    }
}