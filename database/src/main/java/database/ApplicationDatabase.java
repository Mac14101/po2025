package database;

import entities.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationDatabase extends Database {
    private static Database instance;
    private static String dbName;

    private ApplicationDatabase() {
        super();
    }


    private static Database getInstance() {
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
        Database instance = getInstance();
        String usersTable = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT, email VARCHAR(100) UNIQUE NOT NULL, name VARCHAR(50) NOT NULL, surname VARCHAR(50) NOT NULL, password TEXT NOT NULL, role VARCHAR(20) DEFAULT NULL, PRIMARY KEY(id));";
        instance.getPreparedStatement(usersTable).execute();
        String classesTable = "CREATE TABLE IF NOT EXISTS classes (id INT PRIMARY KEY AUTO_INCREMENT, number INT NOT NULL, letter CHAR(1) NOT NULL, PRIMARY KEY(id));";
        instance.getPreparedStatement(classesTable).execute();
        String studentsTable = "CREATE TABLE IF NOT EXISTS students (uid INT UNIQUE, cid INT, FOREIGN KEY(uid) REFERENCES users(id), FOREIGN KEY(cid) REFERENCES classes(id));";
        instance.getPreparedStatement(studentsTable).execute();
        String subjectsTable = "CREATE TABLE IF NOT EXISTS subjects (id INT AUTO_INCREMENT, name TEXT UNIQUE NOT NULL, PRIMARY KEY(id));";
        instance.getPreparedStatement(subjectsTable).execute();
        String timeTabelTable = "CREATE TABLE IF NOT EXISTS time_table (id INT AUTO_INCREMENT, day TEXT NOT NULL, startTime TEXT NOT NULL, endTime TEXT NOT NULL, cid INT, tid INT, sid INT, PRIMARY KEY(id), FOREIGN KEY(cid) REFERENCE classes(id), FOREIGN KEY(tid) REFERENCE users(id), FOREIGN KEY(sid) REFERENCES subjects(id));";
        instance.getPreparedStatement(timeTabelTable).execute();
        String lessonsTable = "CREATE TABLE IF NOT EXISTS lessons (id INT AUTO_INCREMENT, topic TEXT NOT NULL, date TEXT NOT NULL, ttid INT, PRIMARY KEY(id), FOREIGN KEY(ttid) REFERENCE time_table(id));";
        instance.getPreparedStatement(lessonsTable).execute();
        String attendanceTable = "CREATE TABLE IF NOT EXISTS attendance (sid INT, lid INT, status TEXT NOT NULL, FOREIGN KEY(sid) REFERENCE students(id), FOREIGN KEY(lid) REFERENCE lessons(id));";
        instance.getPreparedStatement(attendanceTable).execute();
        String gradesTable = "CREATE TABLE IF NOT EXISTS grades (id INT AUTO_INCREMENT, sid INT, sbid INT, tid INT, grade VARCHAR(10) NOT NULL, PRIMARY KEY(id), FOREIGN KEY(sid) REFERENCE users(id), FOREIGN KEY(sbid) REFERENCE subjects(id), FOREIGN KEY(tid) REFERENCE users(id));";
        instance.getPreparedStatement(gradesTable).execute();
        instance.commit();
    }

    public static void insertAdmin() throws SQLException {
        Database instance = getInstance();
        User admin = new User(null, "admin@gmail.com", "Admin", "Admin", "admin123", User.Role.ADMIN.toString());
        String adminInsertSQL = "INSERT INTO users (email, name, surname, password, role) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement adminInsertStatement = instance.getPreparedStatement(adminInsertSQL);
        adminInsertStatement.setString(1, admin.getEmail());
        adminInsertStatement.setString(2, admin.getName());
        adminInsertStatement.setString(3, admin.getSurname());
        adminInsertStatement.setString(4, admin.getPassword());
        adminInsertStatement.setString(5, admin.getRole().toString());
        instance.getPreparedStatement(adminInsertSQL).execute();
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
