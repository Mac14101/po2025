package server.handlers.timeTable;

import database.ApplicationDatabase;
import entities.SchoolClass;
import entities.User;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TeacherTimeTableHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        ArrayList<SchoolClass> schoolClasses = Mockito.mock(ArrayList.class);
        Mockito.when(applicationDatabase.getTeacherSchedule(1)).thenReturn(schoolClasses);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getSession("userId")).thenReturn("1");
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.TEACHER.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        TeacherTimeTableHandler handler = new TeacherTimeTableHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getTeacherSchedule(1);
        Mockito.verify(response, Mockito.times(1)).json(schoolClasses);
    }

    @Test
    public void handleNotTeacher() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getSession("userRole")).thenReturn(User.Role.ADMIN.toString());
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        TeacherTimeTableHandler handler = new TeacherTimeTableHandler();
        handler.handle(request, response, next);
        Mockito.verify(next, Mockito.times(1)).next();
    }
}