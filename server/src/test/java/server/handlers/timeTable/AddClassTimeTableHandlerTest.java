package server.handlers.timeTable;

import database.ApplicationDatabase;
import entities.SchoolClass;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;

public class AddClassTimeTableHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        SchoolClass schoolClass = Mockito.mock(SchoolClass.class);
        Mockito.when(request.getBody(SchoolClass.class)).thenReturn(schoolClass);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AddClassTimeTableHandler handler = new AddClassTimeTableHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).addClassSchedule(schoolClass);
        Mockito.verify(response, Mockito.times(1)).status(201);
        Mockito.verify(response, Mockito.times(1)).json(schoolClass);
    }
}