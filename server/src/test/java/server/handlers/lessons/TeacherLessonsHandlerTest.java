package server.handlers.lessons;

import database.ApplicationDatabase;
import entities.Lesson;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import org.junit.Test;
import org.mockito.Mockito;
import server.handlers.DatabaseHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TeacherLessonsHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        ArrayList<Lesson> lessons = new ArrayList<>();
        Mockito.when(applicationDatabase.getLessons(Mockito.anyInt())).thenReturn(lessons);
        Request request = Mockito.mock(Request.class);
        Mockito.when(request.getSession("userId")).thenReturn("1");
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        TeacherLessonsHandler handler = new TeacherLessonsHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(applicationDatabase, Mockito.times(1)).getLessons(1);
        Mockito.verify(response, Mockito.times(1)).json(lessons);
    }
}