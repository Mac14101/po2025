package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SchoolClass extends Entity {
    private Integer id;
    private String day;
    private String startTime;
    private String endTime;
    private SchoolGroup schoolGroup;
    private User teacher;
    private Subject subject;

    public SchoolClass(Integer id, String day, String startTime, String endTime, SchoolGroup schoolGroup, User teacher, Subject subject) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.schoolGroup = null;
        this.teacher = null;
        this.subject = null;
    }

    public SchoolClass() {
        this.id = null;
        this.day = null;
        this.startTime = null;
        this.endTime = null;
        this.schoolGroup = null;
        this.teacher = null;
        this.subject = null;
    }

    public static SchoolClass readSchoolClass(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "id");
        String day = getStringColumn(resultSet, "day");
        String startTime = getStringColumn(resultSet, "startTime");
        String endTime = getStringColumn(resultSet, "endTime");
        SchoolGroup schoolGroup = SchoolGroup.readSchoolGroup(resultSet);
        User teacher = User.readUser(resultSet);
        Subject subject = Subject.readSubject(resultSet);
        return new SchoolClass(id, day, startTime, endTime, schoolGroup, teacher, subject);
    }

    public static ArrayList<SchoolClass> readSchoolClassArray(ResultSet resultSet) {
        try {
            ArrayList<SchoolClass> schoolClasses = new ArrayList<>();
            while (resultSet.next()) {
                schoolClasses.add(readSchoolClass(resultSet));
            }
            return schoolClasses;
        } catch (SQLException e) {
            return null;
        }
    }

    public SchoolGroup getSchoolGroup() {
        return schoolGroup;
    }

    public void setSchoolGroup(SchoolGroup schoolGroup) {
        this.schoolGroup = schoolGroup;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
