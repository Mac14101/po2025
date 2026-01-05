package entities;

import java.util.ArrayList;

public class SchoolGroup {
    Integer id;
    Integer number;
    Character letter;
    ArrayList<Student> students;

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
