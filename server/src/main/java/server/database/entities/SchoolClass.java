package server.database.entities;

public class SchoolClass {
    private Integer id;
    private String day;
    private String startTime;
    private String endTime;
    private SchoolGroup schoolGroup;
    private User teacher;
    private Subject subject;

    public SchoolClass(Integer id, String day, String startTime, String endTime) {
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
