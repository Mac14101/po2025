package database;

import entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationDatabase extends Database {
    private static Database instance;
    private static String dbName;

    public static Database getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Database not connected");
        }
        return instance;
    }

    public static void openConnection() throws SQLException {
        if (instance != null) {
            throw new IllegalStateException("Database already connected!");
        }
        instance = new Database();
        instance.connect(dbName);
    }

    public static void config(String name) {
        if (instance != null) {
            throw new IllegalStateException("Connection is open, cannot configure database name!");
        }
        dbName = name;
    }

    public static void closeConnection() throws SQLException {
        if (instance == null) {
            throw new RuntimeException("Database not connected!");
        }
        instance.close();
        instance = null;
    }

    public static void initialize() throws SQLException {
        Database currentInstance = getInstance();
        String usersTable = "";
        currentInstance.getPreparedStatement(usersTable).execute();
        String classesTable = "";
        currentInstance.getPreparedStatement(classesTable).execute();
        String studentsTable = "";
        currentInstance.getPreparedStatement(studentsTable).execute();
        String subjectsTable = "";
        currentInstance.getPreparedStatement(subjectsTable).execute();
        String timeTabelTable = "";
        currentInstance.getPreparedStatement(timeTabelTable).execute();
        String lessonsTable = "";
        currentInstance.getPreparedStatement(lessonsTable).execute();
        String attendanceTable = "";
        currentInstance.getPreparedStatement(attendanceTable).execute();
        String gradesTable = "";
        currentInstance.getPreparedStatement(gradesTable).execute();
        currentInstance.commit();
    }

    public static void insertAdmin() {
        User admin = new User(null, "admin@gmail.com", "Admin", "Admin", "admin123", User.Role.ADMIN.toString());

        createUser(admin);
    }

    public static ArrayList<User> getAllUsers() {
        try {
            Database currentInstance = getInstance();
            String selectUsersSQL = "";
            ResultSet result = currentInstance.executeQueryStatement(currentInstance.getPreparedStatement(selectUsersSQL));
            return User.readUserArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createUser(User user) {
        try {
            Database currentInstance = getInstance();
            String createUserSQL = "";
            PreparedStatement createUserStatement = currentInstance.getPreparedStatement(createUserSQL);
            createUserStatement.setString(1, user.getEmail());
            createUserStatement.setString(2, user.getName());
            createUserStatement.setString(3, user.getSurname());
            createUserStatement.setString(4, user.getPassword());
            createUserStatement.setString(5, user.getRole().toString());
            currentInstance.executeUpdateStatement(createUserStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Subject> getAllSubjects() {
        try {
            Database currentInstance = getInstance();
            String selectSubjectsSQL = "";
            ResultSet result = currentInstance.executeQueryStatement(currentInstance.getPreparedStatement(selectSubjectsSQL));
            return Subject.readSubjectsArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createSubject(Subject subject) {
        try {
            Database currentInstance = getInstance();
            String createSubjectSQL = "";
            PreparedStatement createSubjectStatement = currentInstance.getPreparedStatement(createSubjectSQL);
            createSubjectStatement.setString(1, subject.getName());
            currentInstance.executeUpdateStatement(createSubjectStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<SchoolGroup> getAllClass() {
        //TODO
        return null;
    }

    public static void createClass(SchoolGroup schoolClass) {
        //TODO
    }

    public static ArrayList<Student> getAllStudents() {
        //TODO
        return null;
    }

    public static ArrayList<Student> getClassStudents(int classId) {
        //TODO
        return null;
    }

    public static void addStudent(int studentId, int classId) {
        //TODO
    }

    public static ArrayList<SchoolClass> getClassSchedule() {
        //TODO
        return null;
    }

    public static void addClassSchedule(SchoolClass schoolClass) {
        //TODO
    }
}
