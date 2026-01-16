package server.handlers.users;

import database.ApplicationDatabase;
import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class CreateUserHandlerTest {

    private CreateUserHandler createUserHandler;

    @Before
    public void setUp() {
        createUserHandler = new CreateUserHandler();
    }

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = CreateUserHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        database.set(createUserHandler, applicationDatabase);
        Request request = Mockito.mock(Request.class);
        User user = Mockito.mock(User.class);
        Mockito.when(request.getBody(User.class)).thenReturn(user);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        createUserHandler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).createUser(user);
        Mockito.verify(response, Mockito.times(1)).json(user);
        Mockito.verify(response, Mockito.times(1)).status(201);

    }
}