package server.handlers.lessons;

import entities.Lesson;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

import java.util.ArrayList;

public class TeacherLessonsHandler extends DatabaseHandler {
    public TeacherLessonsHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        ArrayList<Lesson> lessons = this.database.getLessons(Integer.parseInt(request.getSession("userId")));
        response.json((lessons));
    }
}
