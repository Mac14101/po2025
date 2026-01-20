package database;

import entities.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ApplicationDatabase extends Database {
    private static ApplicationDatabase instance = new ApplicationDatabase();

    public static ApplicationDatabase getInstance() {
        return instance;
    }

    public static void main(String[] args) throws SQLException {
        ApplicationDatabase database = ApplicationDatabase.getInstance();
        database.connect("database.db");
        System.out.println(database.getUserGrades(2));
    }

    public void initialize(User admin) throws SQLException {
        String usersTable = "CREATE TABLE IF NOT EXISTS users (uid INTEGER PRIMARY KEY, email VARCHAR(100) UNIQUE NOT NULL, uname VARCHAR(50) NOT NULL, surname VARCHAR(50) NOT NULL, password TEXT NOT NULL, role VARCHAR(20) DEFAULT NULL);";
        this.getPreparedStatement(usersTable).execute();
        String classesTable = "CREATE TABLE IF NOT EXISTS classes (cid INTEGER PRIMARY KEY, number INTEGER NOT NULL, letter CHAR(1) NOT NULL);";
        this.getPreparedStatement(classesTable).execute();
        String studentsTable = "CREATE TABLE IF NOT EXISTS students (uid INTEGER UNIQUE, cid INTEGER, FOREIGN KEY(uid) REFERENCES users(uid), FOREIGN KEY(cid) REFERENCES classes(cid));";
        this.getPreparedStatement(studentsTable).execute();
        String subjectsTable = "CREATE TABLE IF NOT EXISTS subjects (sbid INTEGER PRIMARY KEY, sbname TEXT UNIQUE NOT NULL);";
        this.getPreparedStatement(subjectsTable).execute();
        String timeTabelTable = "CREATE TABLE IF NOT EXISTS time_table (ttid INTEGER PRIMARY KEY, day TEXT NOT NULL, startTime TEXT NOT NULL, endTime TEXT NOT NULL, cid INTEGER, tid INTEGER, sbid INTEGER, FOREIGN KEY(cid) REFERENCES users(uid), FOREIGN KEY(sbid) REFERENCES subjects(sbid));";
        this.getPreparedStatement(timeTabelTable).execute();
        String lessonsTable = "CREATE TABLE IF NOT EXISTS lessons (lid INTEGER PRIMARY KEY, topic TEXT NOT NULL, date TEXT NOT NULL, ttid INTEGER, FOREIGN KEY(ttid) REFERENCES time_table(ttid));";
        this.getPreparedStatement(lessonsTable).execute();
        String attendanceTable = "CREATE TABLE IF NOT EXISTS attendance (sid INTEGER, lid INTEGER, status TEXT NOT NULL, FOREIGN KEY(sid) REFERENCES students(sid), FOREIGN KEY(lid) REFERENCES lessons(lid));";
        this.getPreparedStatement(attendanceTable).execute();
        String gradesTable = "CREATE TABLE IF NOT EXISTS grades (gid INTEGER PRIMARY KEY, sid INTEGER, sbid INTEGER, tid INTEGER, grade VARCHAR(10) NOT NULL, FOREIGN KEY(sid) REFERENCES users(uid), FOREIGN KEY(sbid) REFERENCES subjects(sbid), FOREIGN KEY(tid) REFERENCES users(uid));";
        this.getPreparedStatement(gradesTable).execute();
        String attendanceTrigger = "CREATE TRIGGER IF NOT EXISTS attendance_lessons\n" +
                "    AFTER INSERT ON lessons\n" +
                "BEGIN\n" +
                "    INSERT INTO attendance (sid, lid, status)\n" +
                "    SELECT S.uid AS sid, NEW.lid, 'undefined' AS status FROM time_table AS TT INNER JOIN students AS S ON S.cid=TT.cid WHERE TT.ttid=NEW.ttid;\n" +
                "END;";
        this.getPreparedStatement(attendanceTrigger).execute();
        if (!this.getPreparedStatement("SELECT * FROM users;").executeQuery().next()) {
            String insertAdmin = "INSERT INTO users (email, uname, surname, password, role) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement insertAdminStatement = this.getPreparedStatement(insertAdmin);
            insertAdminStatement.setString(1, admin.getEmail());
            insertAdminStatement.setString(2, admin.getName());
            insertAdminStatement.setString(3, admin.getSurname());
            insertAdminStatement.setString(4, admin.getPassword());
            insertAdminStatement.setString(5, admin.getRole().toString());
            insertAdminStatement.execute();
        }
        this.commit();
    }

    public ArrayList<User> getAllUsers() {
        try {
            String selectUsersSQL = "SELECT uid, email, uname, surname, role FROM users;";
            ResultSet result = this.executeQueryStatement(this.getPreparedStatement(selectUsersSQL));
            return User.readUserArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(User user) {
        try {
            String createUserSQL = "INSERT INTO users (email, uname, surname, password, role) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement createUserStatement = this.getPreparedStatement(createUserSQL);
            createUserStatement.setString(1, user.getEmail());
            createUserStatement.setString(2, user.getName());
            createUserStatement.setString(3, user.getSurname());
            createUserStatement.setString(4, user.getPassword());
            createUserStatement.setString(5, user.getRole().toString());
            this.executeUpdateStatement(createUserStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserCredentials(String email) {
        try {
            String selectUsersSQL = "SELECT uid, email, password, role FROM users WHERE email=?";
            PreparedStatement selectUserStatement = this.getPreparedStatement(selectUsersSQL);
            selectUserStatement.setString(1, email);
            ResultSet result = selectUserStatement.executeQuery();
            return User.readUser(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserData(int userId) {
        try {
            String selectUsersSQL = "SELECT uid, email, uname, surname, role FROM users WHERE uid=?";
            PreparedStatement selectUserStatement = this.getPreparedStatement(selectUsersSQL);
            selectUserStatement.setInt(1, userId);
            ResultSet result = selectUserStatement.executeQuery();
            return User.readUser(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Subject> getAllSubjects() {
        try {
            String selectSubjectsSQL = "SELECT sbname FROM subjects;";
            ResultSet result = this.executeQueryStatement(this.getPreparedStatement(selectSubjectsSQL));
            return Subject.readSubjectsArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createSubject(Subject subject) {
        try {
            String createSubjectSQL = "INSERT INTO subjects (sbname) VALUES (?);";
            PreparedStatement createSubjectStatement = this.getPreparedStatement(createSubjectSQL);
            createSubjectStatement.setString(1, subject.getName());
            this.executeUpdateStatement(createSubjectStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<SchoolGroup> getAllClass() {
        try {
            String selectSchoolGroupsSQL = "SELECT cid, number, letter FROM classes;";
            ResultSet result = this.executeQueryStatement(this.getPreparedStatement(selectSchoolGroupsSQL));
            return SchoolGroup.readSchoolGroupArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createClass(SchoolGroup schoolGroup) {
        try {
            String createClassSQL = "INSERT INTO classes (number, letter) VALUES (?, ?);";
            PreparedStatement createClassStatement = this.getPreparedStatement(createClassSQL);
            createClassStatement.setInt(1, schoolGroup.getNumber());
            createClassStatement.setString(2, String.valueOf(schoolGroup.getLetter()));
            this.executeUpdateStatement(createClassStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Student> getAllStudents() {
        try {
            String selectStudentsSQL = "SELECT U.uname, U.surname, C.number, C.letter FROM users AS U INNER JOIN students AS S ON S.uid=U.uid INNER JOIN classes AS C ON C.cid=S.cid;";
            ResultSet result = this.executeQueryStatement(this.getPreparedStatement(selectStudentsSQL));
            return Student.readStudentArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Student> getClassStudents(int classId) {
        try {
            String selectStudentsSQL = "SELECT U.uname, U.surname, C.number, C.letter FROM users AS U INNER JOIN students AS S ON S.uid=U.uid INNER JOIN classes AS C ON C.cid=S.cid WHERE S.cid=?;";
            PreparedStatement selectStudentsStatement = this.getPreparedStatement(selectStudentsSQL);
            selectStudentsStatement.setInt(1, classId);
            ResultSet result = this.executeQueryStatement(selectStudentsStatement);
            return Student.readStudentArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addStudent(int studentId, int classId) {
        try {
            String addStudentSQL = "INSERT INTO students (uid, cid) VALUES(?, ?);";
            PreparedStatement addStudentStatement = this.getPreparedStatement(addStudentSQL);
            addStudentStatement.setInt(1, studentId);
            addStudentStatement.setInt(2, classId);
            this.executeUpdateStatement(addStudentStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<SchoolClass> getClassSchedule(int classId) {
        try {
            String selectSchoolClassesSQL = "SELECT TT.day, TT.startTime, TT.endTime, T.uname AS tname, T.surname AS tsurname, SB.sbname FROM time_table AS TT INNER JOIN users AS T ON T.uid=TT.tid INNER JOIN subjects AS SB ON SB.sbid=TT.sbid WHERE TT.cid=?;";
            PreparedStatement selectSchoolClassesStatement = this.getPreparedStatement(selectSchoolClassesSQL);
            selectSchoolClassesStatement.setInt(1, classId);
            ResultSet result = this.executeQueryStatement(selectSchoolClassesStatement);
            return SchoolClass.readSchoolClassArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addClassSchedule(SchoolClass schoolClass) {
        try {
            String addClassScheduleSQL = "INSERT INTO time_table (day, startTime, endTime, cid, tid, sbid) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement addClassScheduleStatement = this.getPreparedStatement(addClassScheduleSQL);
            addClassScheduleStatement.setString(1, schoolClass.getDay());
            addClassScheduleStatement.setString(2, schoolClass.getStartTime());
            addClassScheduleStatement.setString(3, schoolClass.getEndTime());
            addClassScheduleStatement.setInt(4, schoolClass.getSchoolGroup().getId());
            addClassScheduleStatement.setInt(5, schoolClass.getTeacher().getId());
            addClassScheduleStatement.setInt(6, schoolClass.getSubject().getId());
            this.executeUpdateStatement(addClassScheduleStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Lesson> getLessons(int teacherId) {
        try {
            String getLessosnsSQL = "SELECT L.topic, L.date, SB.sbname FROM lessons AS L INNER JOIN time_table AS TT ON L.ttid=TT.ttid INNER JOIN subjects AS SB ON SB.sbid=TT.sbid WHERE TT.tid=?;";
            PreparedStatement getLessonsStatement = this.getPreparedStatement(getLessosnsSQL);
            getLessonsStatement.setInt(1, teacherId);
            ResultSet result = this.executeQueryStatement(getLessonsStatement);
            return Lesson.readLessonArray(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLesson(Lesson lesson) {
        try {
            String addLesson = "INSERT INTO lessons (topic, date, ttid) VALUES (?, ?, ?);";
            PreparedStatement addLessonStatement = this.getPreparedStatement(addLesson);
            addLessonStatement.setString(1, lesson.getTopic());
            addLessonStatement.setString(2, lesson.getDate());
            addLessonStatement.setInt(3, lesson.getSchoolClass().getId());
            this.executeUpdateStatement(addLessonStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Attendance> getAttendanceList(int lessonId) {
        try {
            String getAttendanceListSQL = "SELECT A.status, U.uname, U.surname, L.topic, L.date FROM attendance AS A INNER JOIN users AS U ON A.sid=U.uid INNER JOIN lessons AS L ON L.lid=A.lid WHERE L.lid=?;";
            PreparedStatement getAttendanceListStatement = this.getPreparedStatement(getAttendanceListSQL);
            getAttendanceListStatement.setInt(1, lessonId);
            ResultSet result = this.executeQueryStatement(getAttendanceListStatement);
            return Attendance.readAttendanceArray(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAttendance(int lessonId, Attendance attendance) {
        try {
            String updateAttendanceSQL = "UPDATE attendance SET status=? WHERE lid=? AND sid=?;";
            PreparedStatement updateAttendanceStatement = this.getPreparedStatement(updateAttendanceSQL);
            updateAttendanceStatement.setString(1, attendance.getStatus().toString());
            updateAttendanceStatement.setInt(2, lessonId);
            updateAttendanceStatement.setInt(3, attendance.getStudent().getId());
            this.executeUpdateStatement(updateAttendanceStatement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Grade> getStudentGrades(int studentId, int teacherId) {
        try {
            String getStudentGrades = "SELECT G.grade, SB.sbname, U.uname, U.surname FROM grades AS G INNER JOIN subjects AS SB ON G.sbid=SB.sbid INNER JOIN users AS U ON G.sid=U.uid WHERE G.tid=? AND G.sid=?;";
            PreparedStatement getStudentsGrades = this.getPreparedStatement(getStudentGrades);
            getStudentsGrades.setInt(1, teacherId);
            getStudentsGrades.setInt(2, studentId);
            ResultSet result = this.executeQueryStatement(getStudentsGrades);
            return Grade.readGradesArray(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addStudentGrade(int studentId, int teacherId, Grade grade) {
        try {
            String addStudentGradeSQL = "INSERT INTO grades (sid, sbid, tid, grade) VALUES (?, ?, ?, ?);";
            PreparedStatement addStudentGradeStatement = this.getPreparedStatement(addStudentGradeSQL);
            addStudentGradeStatement.setInt(1, studentId);
            addStudentGradeStatement.setString(2, grade.getSubject().getName());
            addStudentGradeStatement.setInt(3, teacherId);
            addStudentGradeStatement.setString(4, grade.getGrade().toString());
            this.executeUpdateStatement(addStudentGradeStatement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<SchoolClass> getStudentSchedule(int studentId) {
        try {
            String getUserScheduleSQL = "SELECT TT.day, TT.startTime, TT.startTime, SB.sbname, T.uname AS tname, T.surname AS tsurname FROM time_table AS TT INNER JOIN classes AS C ON C.cid=TT.cid INNER JOIN students AS S ON S.cid=C.cid INNER JOIN subjects AS SB ON SB.sbid=TT.sbid INNER JOIN users AS T ON T.uid=TT.tid WHERE S.uid=?;";
            PreparedStatement getUserScheduleStatement = this.getPreparedStatement(getUserScheduleSQL);
            getUserScheduleStatement.setInt(1, studentId);
            ResultSet result = this.executeQueryStatement(getUserScheduleStatement);
            return SchoolClass.readSchoolClassArray(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Attendance> getUserAttendance(int studentId) {
        try {
            String getUserAttendanceSQL = "SELECT A.status, L.topic, L.date FROM attendance AS A INNER JOIN users AS U ON U.uid=A.sid INNER JOIN lessons AS L ON L.lid=A.lid WHERE U.uid=?;";
            PreparedStatement getUserAttendanceStatement = this.getPreparedStatement(getUserAttendanceSQL);
            getUserAttendanceStatement.setInt(1, studentId);
            ResultSet result = this.executeQueryStatement(getUserAttendanceStatement);
            return Attendance.readAttendanceArray(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Grade> getUserGrades(int studentId) {
        try {
            String getUserGradeSQL = "SELECT G.grade, SB.sbname, T.uname AS tname, T.surname AS tsurname FROM grades AS G INNER JOIN users AS U ON U.uid=G.sid INNER JOIN subjects AS SB ON SB.sbid=G.sbid INNER JOIN users AS T ON T.uid=G.tid WHERE U.uid=?;";
            PreparedStatement getUserGradeStatement = this.getPreparedStatement(getUserGradeSQL);
            getUserGradeStatement.setInt(1, studentId);
            ResultSet result = this.executeQueryStatement(getUserGradeStatement);
            return Grade.readGradesArray(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<SchoolClass> getTeacherSchedule(int teacherId) {
        try {
            String getTeacherScheduleSQL = "SELECT A.status, L.topic, L.date FROM attendance AS A INNER JOIN users AS U ON U.uid=A.sid INNER JOIN lessons AS L ON L.lid=A.lid WHERE U.uid=?;";
            PreparedStatement getTeacherScheduleStatement = this.getPreparedStatement(getTeacherScheduleSQL);
            getTeacherScheduleStatement.setInt(1, teacherId);
            ResultSet result = this.executeQueryStatement(getTeacherScheduleStatement);
            return SchoolClass.readSchoolClassArray(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
