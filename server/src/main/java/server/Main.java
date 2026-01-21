package server;

import database.ApplicationDatabase;
import entities.User;
import jexp.Router;
import jexp.Server;
import jexp.defaultHandlers.SessionHandler;
import jexp.defaultHandlers.SessionRefreshHandler;
import server.handlers.MainLogger;
import server.handlers.attendance.LessonAttendanceListHandler;
import server.handlers.attendance.LessonAttendanceUpdateHandler;
import server.handlers.attendance.StudentAttendanceHandler;
import server.handlers.authentication.AuthenticationHandler;
import server.handlers.authentication.SessionDestroyHandler;
import server.handlers.authentication.UserDataHandler;
import server.handlers.authorization.*;
import server.handlers.classes.AllClassesHandler;
import server.handlers.classes.CreateClassHandler;
import server.handlers.grades.AddGradeHandler;
import server.handlers.grades.StudentGradesHandler;
import server.handlers.grades.TeacherGradesListHandler;
import server.handlers.lessons.AddLessonHandler;
import server.handlers.lessons.TeacherLessonsHandler;
import server.handlers.students.AddStudentHandler;
import server.handlers.students.AllStudentsHandler;
import server.handlers.students.StudentsClassHandler;
import server.handlers.subjects.AllSubjectsHandler;
import server.handlers.subjects.CreateSubjectHandler;
import server.handlers.timeTable.AddClassTimeTableHandler;
import server.handlers.timeTable.ClassTimeTableHandler;
import server.handlers.timeTable.StudentTimeTableHandler;
import server.handlers.timeTable.TeacherTimeTableHandler;
import server.handlers.users.AllUsersHandler;
import server.handlers.users.CreateUserHandler;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, Router.RouterError, IOException {
        ApplicationDatabase database = ApplicationDatabase.getInstance();
        database.connect("database.db");
        database.initialize(new User(null, "admin@gmail.com", "Admin", "Admin", "admin123", User.Role.ADMIN.toString()));
        Server server = new Server();
        server.use(new MainLogger("Server"));

        AuthenticatedUsersOnlyHandler authenticatedUsersOnlyHandler = new AuthenticatedUsersOnlyHandler();
        AdminOnlyHandler adminOnlyHandler = new AdminOnlyHandler();
        AdminTeacherOnlyHandler adminTeacherOnlyHandler = new AdminTeacherOnlyHandler();
        TeacherOnlyHandler teacherOnlyHandler = new TeacherOnlyHandler();
        StudentOnlyHandler studentOnlyHandler = new StudentOnlyHandler();

        //Trasy uwierzytelniania
        server.use("/", new SessionHandler());
        server.use("/session/", new SessionRefreshHandler());
        server.post("/login/", new AuthenticationHandler());
        server.get("/logout/", new SessionDestroyHandler());
        //Trasy do pobierania danych
        server.use("/self/", authenticatedUsersOnlyHandler);
        server.get("/self/timetable/", new StudentTimeTableHandler());
        server.get("/self/timetable/", new TeacherTimeTableHandler());
        server.get("/self/attendance/", new StudentAttendanceHandler());
        server.get("/self/attendance/", studentOnlyHandler);
        server.get("/self/grade/", new StudentGradesHandler());
        server.get("/self/grade/", studentOnlyHandler);
        server.get("/self/", new UserDataHandler());
        //Trasy do manipulacji użytkownikami
        server.use("/user/", authenticatedUsersOnlyHandler);
        server.use("/user/", adminOnlyHandler);
        server.get("/user/", new AllUsersHandler());
        server.post("/user/", new CreateUserHandler());
        //Trasy do manipulacji przedmiotami szkolnymi
        server.use("/subject/", authenticatedUsersOnlyHandler);
        server.get("/subject/", adminTeacherOnlyHandler);
        server.get("/subject/", new AllSubjectsHandler());
        server.post("/subject/", adminOnlyHandler);
        server.post("/subject/", new CreateSubjectHandler());
        //Trasy do manipulacji klasami
        server.use("/class/", authenticatedUsersOnlyHandler);
        server.get("/class/", adminTeacherOnlyHandler);
        server.get("/class/", new AllClassesHandler());
        server.post("/class/", adminOnlyHandler);
        server.post("/class/", new CreateClassHandler());
        //Trasy do manipulacji uczniami
        server.use("/student/", authenticatedUsersOnlyHandler);
        server.get("/student/:classId/", adminTeacherOnlyHandler);
        server.get("/student/:classId/", new StudentsClassHandler());
        server.use("/student/", adminOnlyHandler);
        server.get("/student/", new AllStudentsHandler());
        server.post("/student/", new AddStudentHandler());
        //Trasy do manipulacji planem zajęć
        server.use("/timetable/", authenticatedUsersOnlyHandler);
        server.use("/timetable/", adminOnlyHandler);
        server.get("/timetable/:classId/", new ClassTimeTableHandler());
        server.post("/timetable/", new AddClassTimeTableHandler());
        server.post("/timetable//", new AddClassTimeTableHandler());
        //Trasy do manipulacji lekcjami
        server.use("/lesson/", authenticatedUsersOnlyHandler);
        server.use("/lesson/", teacherOnlyHandler);
        server.get("/lesson/", new TeacherLessonsHandler());
        server.post("/lesson/", new AddLessonHandler());
        //Trasy do manipulacji obecnościami
        server.use("/attendance/", authenticatedUsersOnlyHandler);
        server.use("/attendance/", teacherOnlyHandler);
        server.get("/attendance/:lessonId/", new LessonAttendanceListHandler());
        server.put("/attendance/:lessonId/", new LessonAttendanceUpdateHandler());
        //Trasy do manipulacji ocenami
        server.use("/grade/", authenticatedUsersOnlyHandler);
        server.use("/grade/", teacherOnlyHandler);
        server.get("/grade/:studentId/", new TeacherGradesListHandler());
        server.post("/grade/:studentId/", new AddGradeHandler());
        server.listen(8080);
    }
}