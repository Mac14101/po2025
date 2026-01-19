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

public class LessonAttendanceUpdateHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getParam("lessonId")).thenReturn("1");
        Attendance attendance = Mockito.mock(Attendance.class);
        Mockito.when(request.getBody(Attendance.class)).thenReturn(attendance);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        LessonAttendanceUpdateHandler handler = new LessonAttendanceUpdateHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).updateAttendance(1, attendance);
        Mockito.verify(response, Mockito.times(1)).json(attendance);
    }
}