package server.handlers.users;

import database.ApplicationDatabase;
import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class CreateSessionHandlerTest {

    private CreateUserHandler createUserHandler;

    @Before
    public void setUp() {
        createUserHandler = new CreateUserHandler();
    }

    @Test
    public void handle() {
        try (MockedStatic<ApplicationDatabase> applicationDatabaseMockedStatic = Mockito.mockStatic(ApplicationDatabase.class)) {
            Request request = Mockito.mock(Request.class);
            User user = Mockito.mock(User.class);
            Mockito.when(request.getBody(User.class)).thenReturn(user);
            Response response = Mockito.mock(Response.class);
            Next next = Mockito.mock(Next.class);
            createUserHandler.handle(request, response, next);
            applicationDatabaseMockedStatic.verify(() -> ApplicationDatabase.createUser(user), Mockito.times(1));
            Mockito.verify(response, Mockito.times(1)).json(user);
            Mockito.verify(response, Mockito.times(1)).status(201);
        } catch (JExpError e) {
            throw new RuntimeException(e);
        }
    }
}