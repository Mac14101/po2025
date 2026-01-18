package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Subject extends DatabaseEntity {
    private Integer id;
    private String name;

    public Subject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject() {
        this.id = null;
        this.name = null;
    }

    public static Subject readSubject(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "sid");
        String name = getStringColumn(resultSet, "sname");
        return new Subject(id, name);
    }

    public static ArrayList<Subject> readSubjectsArray(ResultSet resultSet) {
        try {
            ArrayList<Subject> subjects = new ArrayList<>();
            while (resultSet.next()) {
                subjects.add(readSubject(resultSet));
            }
            return subjects;
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.matches("^[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+$")) {
            throw new IllegalArgumentException("Invalid subject name!");
        }
        this.name = name;
    }

}
