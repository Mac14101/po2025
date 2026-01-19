package server.handlers.lessons;

import entities.Lesson;
import jexp.JExpError;
import jexp.Next;
import jexp.Request;
import jexp.Response;
import server.handlers.DatabaseHandler;

public class AddLessonHandler extends DatabaseHandler {
    public AddLessonHandler() {
        super();
    }

    @Override
    public void handle(Request request, Response response, Next next) throws JExpError {
        Lesson lesson = request.getBody(Lesson.class);
        this.database.addLesson(lesson);
        response.status(201);
        response.json(lesson);
    }
}
