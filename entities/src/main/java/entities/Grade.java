package entities;

public class Grade {
    private Integer id;
    private User student;
    private Subject subject;
    private User teacher;
    private GradeName grade;

    public Grade(Integer id, User student, Subject subject, User teacher, String grade) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
        this.grade = GradeName.fromGrade(grade);
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

    public GradeName getGrade() {
        return grade;
    }

    public void setGrade(GradeName grade) {
        this.grade = grade;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public static enum GradeName {
        NDOP("1"), DOP("2"), DOST("3"), DB("4"), BDB("5"), CEL("6"), NP("np"), NB("nb");
        private final String grade;

        private GradeName(String grade) {
            this.grade = grade;
        }

        public static GradeName fromGrade(String grade) {
            for (GradeName gr : GradeName.values()) {
                if (gr.grade.equals(grade)) {
                    return gr;
                }
            }
            throw new IllegalArgumentException("Unknown grade!");
        }

        @Override
        public String toString() {
            return grade;
        }
    }
}
