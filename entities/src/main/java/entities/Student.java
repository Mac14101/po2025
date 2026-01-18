package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Student extends User {
    private SchoolGroup schoolGroup;

    public Student(Integer id, String email, String name, String surname, String password, String role, SchoolGroup schoolGroup) {
        super(id, email, name, surname, password, role);
        this.schoolGroup = schoolGroup;
    }

    public Student(User user, SchoolGroup schoolGroup) {
        super(user.getId(), user.getEmail(), user.getName(), user.getSurname(), user.getPassword(), user.getRole().toString());
        this.schoolGroup = schoolGroup;
    }

    public Student() {
        super();
        this.schoolGroup = null;
    }

    public static Student readStudent(ResultSet resultSet) {
        User user = User.readUser(resultSet);
        SchoolGroup schoolGroup = SchoolGroup.readSchoolGroup(resultSet);
        return new Student(user, schoolGroup);
    }

    public static ArrayList<Student> readStudentArray(ResultSet resultSet) {
        try {
            ArrayList<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                students.add(readStudent(resultSet));
            }
            return students;
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
}
