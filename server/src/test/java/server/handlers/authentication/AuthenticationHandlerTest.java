package server.handlers.authentication;

import database.ApplicationDatabase;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.sql.RowSet;
import java.sql.SQLException;

public class AuthenticationHandlerTest {

    @Test
    public void userFound() throws JExpError, SQLException {
        try (MockedStatic<ApplicationDatabase> databaseMockedStatic = Mockito.mockStatic(ApplicationDatabase.class)) {
            RowSet rowSet = Mockito.mock(RowSet.class);
            Mockito.when(rowSet.getString("password")).thenReturn("password");
            databaseMockedStatic.when(() -> {
                ApplicationDatabase.getUserCredentials(Mockito.anyString());
            }).thenReturn(rowSet);
            Request request = Mockito.mock(Request.class);
            AuthenticationHandler.UserCredentials userCredentials = new AuthenticationHandler.UserCredentials();
            userCredentials.email = "email";
            userCredentials.password = "password";
            Mockito.when(request.getBody(AuthenticationHandler.UserCredentials.class)).thenReturn(userCredentials);
            Mockito.when(request.logIn(Mockito.any(), Mockito.any())).thenReturn("token");
            Response response = Mockito.mock(Response.class);
            Next next = Mockito.mock(Next.class);
            AuthenticationHandler authenticationHandler = new AuthenticationHandler();
            authenticationHandler.handle(request, response, next);
            Mockito.verify(response, Mockito.times(1)).send("token");
        }
    }

    @Test
    public void userNotFound() throws JExpError, SQLException {
        try (MockedStatic<ApplicationDatabase> databaseMockedStatic = Mockito.mockStatic(ApplicationDatabase.class)) {
            RowSet rowSet = Mockito.mock(RowSet.class);
            Mockito.when(rowSet.getString("password")).thenThrow(SQLException.class);
            databaseMockedStatic.when(() -> {
                ApplicationDatabase.getUserCredentials(Mockito.anyString());
            }).thenReturn(rowSet);
            Request request = Mockito.mock(Request.class);
            AuthenticationHandler.UserCredentials userCredentials = new AuthenticationHandler.UserCredentials();
            userCredentials.email = "email";
            userCredentials.password = "password";
            Mockito.when(request.getBody(AuthenticationHandler.UserCredentials.class)).thenReturn(userCredentials);
            Response response = Mockito.mock(Response.class);
            Next next = Mockito.mock(Next.class);
            AuthenticationHandler authenticationHandler = new AuthenticationHandler();
            authenticationHandler.handle(request, response, next);
            Mockito.verify(response, Mockito.times(1)).status(400);
        }
    }

    @Test
    public void noCredentials() throws JExpError, SQLException {
        Request request = Mockito.mock(Request.class);
        AuthenticationHandler.UserCredentials userCredentials = new AuthenticationHandler.UserCredentials();
        Mockito.when(request.getBody(AuthenticationHandler.UserCredentials.class)).thenReturn(userCredentials);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AuthenticationHandler authenticationHandler = new AuthenticationHandler();
        authenticationHandler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).status(400);

    }
}