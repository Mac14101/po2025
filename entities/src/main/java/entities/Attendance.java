package entities;

public class Attendance extends Entity {
    private User student;
    private Lesson lesson;
    private Status status;

    public Attendance(User student, Lesson lesson, String status) {
        this.student = student;
        this.lesson = lesson;
        this.status = Status.fromString(status);
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        PRESENT("present"), ABSENT("absent"), LATE("late");
        private final String status;

        private Status(String status) {
            this.status = status;
        }

        public static Status fromString(String status) {
            for (Status s : Status.values()) {
                if (s.status.equals(status)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Invalid status");
        }

        @Override
        public String toString() {
            return status;
        }
    }
}
