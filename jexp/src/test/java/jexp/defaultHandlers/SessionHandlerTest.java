package jexp.defaultHandlers;

import com.sun.net.httpserver.Headers;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import jexp.session.Manager;
import jexp.session.Session;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class SessionHandlerTest {

    @Test
    public void handle() throws JExpError, NoSuchFieldException, IllegalAccessException {
        try (MockedStatic<Manager> sessionManagerMock = Mockito.mockStatic(Manager.class);) {
            Manager manager = Mockito.mock(Manager.class);
            sessionManagerMock.when(Manager::getInstance).thenReturn(manager);
            Session session = Mockito.mock(Session.class);
            Mockito.when(manager.getSession(Mockito.anyString())).thenReturn(session);
            Request request = Mockito.mock(Request.class);
            Headers headers = new Headers();
            headers.add("Authorization", "Basic token");
            Mockito.when(request.getHeaders()).thenReturn(headers);
            Response response = Mockito.mock(Response.class);
            Next next = Mockito.mock(Next.class);
            SessionHandler handler = new SessionHandler();
            handler.handle(request, response, next);
            Mockito.verify(manager, Mockito.times(1)).getSession("token");
            Mockito.verify(next, Mockito.times(1)).next();
        }
    }
}