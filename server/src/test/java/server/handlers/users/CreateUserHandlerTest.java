package server.handlers.users;

import database.ApplicationDatabase;
import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;

public class CreateUserHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        User user = Mockito.mock(User.class);
        Mockito.when(request.getBody(User.class)).thenReturn(user);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        CreateUserHandler createUserHandler = new CreateUserHandler();
        database.set(createUserHandler, applicationDatabase);
        createUserHandler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).createUser(user);
        Mockito.verify(response, Mockito.times(1)).json(user);
        Mockito.verify(response, Mockito.times(1)).status(201);

    }
}