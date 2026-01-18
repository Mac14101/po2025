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
import java.util.ArrayList;

public class ClassTimeTableHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        ArrayList<SchoolClass> schoolClasses = Mockito.mock(ArrayList.class);
        Mockito.when(applicationDatabase.getClassSchedule(1)).thenReturn(schoolClasses);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getParam("classId")).thenReturn("1");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        ClassTimeTableHandler handler = new ClassTimeTableHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getClassSchedule(1);
        Mockito.verify(response, Mockito.times(1)).json(schoolClasses);
    }
}