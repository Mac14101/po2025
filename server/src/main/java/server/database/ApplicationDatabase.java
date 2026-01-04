package server.database;

import database.Database;
import server.database.entities.SchoolGroup;
import server.database.entities.Student;
import server.database.entities.Subject;
import server.database.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationDatabase extends Database {
    private static ApplicationDatabase instance;
    private static String dbName;

    private ApplicationDatabase() {
        super();
    }

    private static ApplicationDatabase getInstance() {
        return instance;
    }

    public static void openConnection() throws SQLException {
        if (instance != null) {
            throw new RuntimeException("Database already connected!");
        }
        instance = new ApplicationDatabase();
        instance.connect(dbName);
    }

    public static void config(String name) {
        if (instance != null) {
            throw new RuntimeException("Connection is open, cannot configure database name!");
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

    public static ArrayList<User> getAllUsers() {
        //TODO
        return null;
    }

    public static void createUser(User user) {
        //TODO
    }

    public static ArrayList<Subject> getAllSubjects() {
        //TODO
        return null;
    }

    public static void createSubject(Subject subject) {
        //TODO
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

    public static void addStudent(Student student) {
        //TODO
    }

}
