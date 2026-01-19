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

public class AddLessonHandlerTest {

    @Test
    public void handle() throws NoSuchFieldException, IllegalAccessException, JExpError {
        Field database = DatabaseHandler.class.getDeclaredField("database");
        database.setAccessible(true);
        ApplicationDatabase applicationDatabase = Mockito.mock(ApplicationDatabase.class);
        Request request = Mockito.mock(Request.class);
        Lesson lesson = Mockito.mock(Lesson.class);
        Mockito.when(request.getBody(Lesson.class)).thenReturn(lesson);
        Response response = Mockito.mock(Response.class);
        Next next = Mockito.mock(Next.class);
        AddLessonHandler handler = new AddLessonHandler();
        database.set(handler, applicationDatabase);
        handler.handle(request, response, next);
        Mockito.verify(response, Mockito.times(1)).json(lesson);
        Mockito.verify(response, Mockito.times(1)).status(201);
    }
}