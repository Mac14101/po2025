package server.database.entities;

public class Grade {
    private Integer id;
    private User student;
    private Subject subject;
    private User teacher;
    private String grade;

    public Grade(Integer id, User student, Subject subject, User teacher, String grade) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
        this.grade = grade;
    }

    public Grade() {
        this.id = null;
        this.student = null;
        this.subject = null;
        this.teacher = null;
        this.grade = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
