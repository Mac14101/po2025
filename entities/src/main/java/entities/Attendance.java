package entities;

public class Attendance {
    private User student;
    private Lesson lesson;
    private String status;

    public Attendance(User student, Lesson lesson, String status) {
        this.student = student;
        this.lesson = lesson;
        this.status = status;
    }

    public Attendance() {
        this.student = null;
        this.lesson = null;
        this.status = null;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
