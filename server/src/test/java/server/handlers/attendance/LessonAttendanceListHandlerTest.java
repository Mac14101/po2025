package server.handlers.attendance;

import database.ApplicationDatabase;
import entities.Attendance;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class LessonAttendanceListHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        ArrayList<Attendance> attendances = Mockito.mock(ArrayList.class);
        Mockito.when(applicationDatabase.getAttendanceList(Mockito.anyInt())).thenReturn(attendances);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getParam("lessonId")).thenReturn("1");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        LessonAttendanceListHandler handler = new LessonAttendanceListHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getAttendanceList(1);
        Mockito.verify(response, Mockito.times(1)).json(attendances);
    }
}