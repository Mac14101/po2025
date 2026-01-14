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

    public static String getUserPassword(String email) {
        try {
            Database currentInstance = getInstance();
            String selectUsersSQL = "";
            PreparedStatement selectUserStatement = currentInstance.getPreparedStatement(selectUsersSQL);
            selectUserStatement.setString(1, email);
            ResultSet result = selectUserStatement.executeQuery();
            return result.getString("password");
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
        try {
            Database currentInstance = getInstance();
            String selectSchoolGroupsSQL = "";
            ResultSet result = currentInstance.executeQueryStatement(currentInstance.getPreparedStatement(selectSchoolGroupsSQL));
            return SchoolGroup.readSchoolGroupArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createClass(SchoolGroup schoolGroup) {
        try {
            Database currentInstance = getInstance();
            String createClassSQL = "";
            PreparedStatement createClassStatement = currentInstance.getPreparedStatement(createClassSQL);
            createClassStatement.setInt(1, schoolGroup.getNumber());
            createClassStatement.setString(1, String.valueOf(schoolGroup.getLetter()));
            currentInstance.executeUpdateStatement(createClassStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Student> getAllStudents() {
        try {
            Database currentInstance = getInstance();
            String selectStudentsSQL = "";
            ResultSet result = currentInstance.executeQueryStatement(currentInstance.getPreparedStatement(selectStudentsSQL));
            return Student.readStudentArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Student> getClassStudents(int classId) {
        try {
            Database currentInstance = getInstance();
            String selectStudentsSQL = "";
            PreparedStatement selectStudentsStatement = currentInstance.getPreparedStatement(selectStudentsSQL);
            selectStudentsStatement.setInt(1, classId);
            ResultSet result = currentInstance.executeQueryStatement(selectStudentsStatement);
            return Student.readStudentArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addStudent(int studentId, int classId) {
        try {
            Database currentInstance = getInstance();
            String addStudentSQL = "";
            PreparedStatement addStudentStatement = instance.getPreparedStatement(addStudentSQL);
            addStudentStatement.setInt(1, studentId);
            addStudentStatement.setInt(2, classId);
            currentInstance.executeUpdateStatement(addStudentStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<SchoolClass> getClassSchedule() {
        try {
            Database currentInstance = getInstance();
            String selectSchoolClassesSQL = "";
            ResultSet result = instance.executeQueryStatement(instance.getPreparedStatement(selectSchoolClassesSQL));
            return SchoolClass.readSchoolClassArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addClassSchedule(SchoolClass schoolClass) {
        try {
            Database currentInstance = getInstance();
            String addClassScheduleSQL = "";
            PreparedStatement addClassScheduleStatement = instance.getPreparedStatement(addClassScheduleSQL);
            addClassScheduleStatement.setString(1, schoolClass.getDay());
            addClassScheduleStatement.setString(2, schoolClass.getStartTime());
            addClassScheduleStatement.setString(3, schoolClass.getEndTime());
            addClassScheduleStatement.setInt(4, schoolClass.getSchoolGroup().getId());
            addClassScheduleStatement.setInt(5, schoolClass.getTeacher().getId());
            addClassScheduleStatement.setInt(6, schoolClass.getSubject().getId());
            instance.executeUpdateStatement(addClassScheduleStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
