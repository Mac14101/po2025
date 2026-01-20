package entities;

import java.sql.ResultSet;

public class Teacher extends User {
    public Teacher(Integer id, String email, String name, String surname, String password) {
        super(id, email, name, surname, password, Role.TEACHER.toString());
    }

    public Teacher() {
        super();
        this.setRole(Role.TEACHER);
    }

    public static Teacher readTeacher(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "tid");
        String email = getStringColumn(resultSet, "temail");
        String name = getStringColumn(resultSet, "tname");
        String surname = getStringColumn(resultSet, "tsurname");
        String password = getStringColumn(resultSet, "tpassword");
        String role = getStringColumn(resultSet, "role");
        if (role != null && !role.equals("teacher")) {
            throw new IllegalArgumentException("Invalid role");
        }
        return new Teacher(id, email, name, surname, password);
    }
}
