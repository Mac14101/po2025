package server.handlers.authentication;

import database.ApplicationDatabase;
import entities.Message;
import entities.User;
import entities.UserCredentials;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class AuthenticationHandlerTest {
    @Test
    public void userFound() throws JExpError, NoSuchFieldException, IllegalAccessException {
        Field database = AuthenticationHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        User user = Mockito.mock(User.class);
        Mockito.when(user.getEmail()).thenReturn("email");
        Mockito.when(user.getPassword()).thenReturn("password");
        Mockito.when(user.getId()).thenReturn(1);
        Mockito.when(applicationDatabase.getUserCredentials(Mockito.anyString())).thenReturn(user);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        UserCredentials userCredentials = new UserCredentials("email", "password");
        Mockito.when(request.getBody(UserCredentials.class)).thenReturn(userCredentials);
        Mockito.when(request.logIn(Mockito.any(), Mockito.any())).thenReturn("token");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AuthenticationHandler authenticationHandler = new AuthenticationHandler();
        database.set(authenticationHandler, applicationDatabase);
        authenticationHandler.handle(request, response, next);
        Mockito.verify(request, Mockito.times(1)).logIn(1, "email");
        Mockito.verify(response, Mockito.times(1)).send("token");
    }

    @Test
    public void userNotFound() throws JExpError, NoSuchFieldException, IllegalAccessException {
        Field database = AuthenticationHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        User user = Mockito.mock(User.class);
        Mockito.when(user.getEmail()).thenReturn(null);
        Mockito.when(user.getPassword()).thenReturn(null);
        Mockito.when(user.getId()).thenReturn(null);
        Mockito.when(
                applicationDatabase.getUserCredentials(Mockito.anyString())
        ).thenReturn(user);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        UserCredentials userCredentials = new UserCredentials("email", "password");
        Mockito.when(request.getBody(UserCredentials.class)).thenReturn(userCredentials);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AuthenticationHandler authenticationHandler = new AuthenticationHandler();
        database.set(authenticationHandler, applicationDatabase);
        authenticationHandler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(400);
        Mockito.verify(response, Mockito.times(1)).json(Mockito.any(Message.class));

    }

    @Test
    public void noCredentials() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        UserCredentials userCredentials = new UserCredentials();
        Mockito.when(request.getBody(UserCredentials.class)).thenReturn(userCredentials);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AuthenticationHandler authenticationHandler = new AuthenticationHandler();
        authenticationHandler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(400);
        Mockito.verify(response, Mockito.times(1)).json(Mockito.any(Message.class));
    }

    @Test
    public void sessionEstablished() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AuthenticationHandler authenticationHandler = new AuthenticationHandler();
        authenticationHandler.handle(request, response, next);
        Mockito.verify(next, Mockito.times(1)).next();
    }
}