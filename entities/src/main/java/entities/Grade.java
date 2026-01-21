package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Grade extends DatabaseEntity {
    private Integer id;
    private Student student;
    private Subject subject;
    private Teacher teacher;
    private GradeName grade;
    private String title;
    private String comment;

    public Grade(Integer id, Student student, Subject subject, Teacher Teacher, String grade, String title, String comment) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.teacher = teacher;
        if (grade != null) {
            this.grade = GradeName.fromGrade(grade);
        }
        this.title = title;
        this.comment = comment;
    }
    public Grade() {
        this.id = null;
        this.student = null;
        this.subject = null;
        this.teacher = null;
        this.grade = null;
        this.title = null;
        this.comment = null;
    }

    public static Grade readGrade(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "gid");
        Student student = Student.readStudent(resultSet);
        Subject subject = Subject.readSubject(resultSet);
        Teacher teacher = Teacher.readTeacher(resultSet);
        String grade = getStringColumn(resultSet, "grade");
        String title = getStringColumn(resultSet, "title");
        String comment = getStringColumn(resultSet, "comment");
        return new Grade(id, student, subject, teacher, grade, title, comment);
    }

    public static ArrayList<Grade> readGradesArray(ResultSet resultSet) {
        try {
            ArrayList<Grade> grades = new ArrayList<>();
            while (resultSet.next()) {
                grades.add(readGrade(resultSet));
            }
            return grades;
        } catch (SQLException e) {
            return null;
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
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
