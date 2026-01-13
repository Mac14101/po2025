package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SchoolGroup extends Entity {
    private Integer id;
    private Integer number;
    private Character letter;
    private ArrayList<Student> students;

    public SchoolGroup(Integer id, Integer number, Character letter) {
        this.id = id;
        this.number = number;
        this.letter = letter;
        this.students = new ArrayList<>();
    }

    public SchoolGroup() {
        this.id = null;
        this.number = null;
        this.letter = null;
        this.students = new ArrayList<>();
    }

    public static SchoolGroup readSchoolGroup(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "id");
        Integer number = getIntColumn(resultSet, "number");
        Character letter = getStringColumn(resultSet, "letter").charAt(0);
        return new SchoolGroup(id, number, letter);
    }

    public static ArrayList<SchoolGroup> readSchoolGroupArray(ResultSet resultSet) {
        try {
            ArrayList<SchoolGroup> schoolGroups = new ArrayList<>();
            while (resultSet.next()) {
                schoolGroups.add(readSchoolGroup(resultSet));
            }
            return schoolGroups;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        if (number < 0) {
            throw new IllegalArgumentException("Class number is lower than 0!");
        }
        this.number = number;
    }

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        if (!letter.toString().matches("^[A-Z]$")) {
            throw new IllegalArgumentException("Class letter is not valid!");
        }
        this.letter = letter;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

}
