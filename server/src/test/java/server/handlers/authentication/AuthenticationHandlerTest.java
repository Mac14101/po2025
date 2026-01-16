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
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class AuthenticationHandlerTest {

    @Test
    public void userFound() throws JExpError {
        try (MockedStatic<ApplicationDatabase> databaseMockedStatic = Mockito.mockStatic(ApplicationDatabase.class)) {
            User user = Mockito.mock(User.class);
            Mockito.when(user.getEmail()).thenReturn("email");
            Mockito.when(user.getPassword()).thenReturn("password");
            Mockito.when(user.getId()).thenReturn(1);
            databaseMockedStatic.when(() -> {
                ApplicationDatabase.getUserCredentials(Mockito.anyString());
            }).thenReturn(user);
            Request request = Mockito.mock(Request.class);
            Mockito.when(request.sessionEstabilished()).thenReturn(true);
            UserCredentials userCredentials = new UserCredentials("email", "password");
            Mockito.when(request.getBody(UserCredentials.class)).thenReturn(userCredentials);
            Mockito.when(request.logIn(Mockito.any(), Mockito.any())).thenReturn("token");
            Response response = Mockito.mock(Response.class);
            Next next = Mockito.mock(Next.class);
            AuthenticationHandler authenticationHandler = new AuthenticationHandler();
            authenticationHandler.handle(request, response, next);
            Mockito.verify(request, Mockito.times(1)).logIn(1, "email");
            Mockito.verify(response, Mockito.times(1)).send("token");
        }
    }

    @Test
    public void userNotFound() throws JExpError {
        try (MockedStatic<ApplicationDatabase> databaseMockedStatic = Mockito.mockStatic(ApplicationDatabase.class)) {
            User user = Mockito.mock(User.class);
            Mockito.when(user.getEmail()).thenReturn(null);
            Mockito.when(user.getPassword()).thenReturn(null);
            Mockito.when(user.getId()).thenReturn(null);
            databaseMockedStatic.when(() -> {
                ApplicationDatabase.getUserCredentials(Mockito.anyString());
            }).thenReturn(user);
            Request request = Mockito.mock(Request.class);
            Mockito.when(request.sessionEstabilished()).thenReturn(true);
            UserCredentials userCredentials = new UserCredentials("email", "password");
            Mockito.when(request.getBody(UserCredentials.class)).thenReturn(userCredentials);
            Response response = Mockito.mock(Response.class);
            Next next = Mockito.mock(Next.class);
            AuthenticationHandler authenticationHandler = new AuthenticationHandler();
            authenticationHandler.handle(request, response, next);
            Mockito.verify(response, Mockito.times(1)).status(400);
            Mockito.verify(response, Mockito.times(1)).json(Mockito.any(Message.class));
        }
    }

    @Test
    public void noCredentials() throws JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.sessionEstabilished()).thenReturn(true);
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
        Mockito.when(request.sessionEstabilished()).thenReturn(false);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AuthenticationHandler authenticationHandler = new AuthenticationHandler();
        authenticationHandler.handle(request, response, next);
        Mockito.verify(next, Mockito.times(1)).next();
    }
}