package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * {@inheritDoc User}
 * Klasa reprezentująca klasę (rekord tabeli `classes` z bazy danych). Dziedziczy po klasie*User,
 * aby umożliwić zapisanie danych ucznia.  Wybrane atrybuty mogą być typu null,
 * jeżeli nie wszystkie dane zostały uzupełnione!!!
 */
public class Student extends User {
    private SchoolGroup schoolGroup;

    public Student(Integer id, String email, String name, String surname, String password, SchoolGroup schoolGroup) {
        super(id, email, name, surname, password, Role.STUDENT.toString());
        this.schoolGroup = schoolGroup;
    }

    public Student(User user, SchoolGroup schoolGroup) {
        super(user.getId(), user.getEmail(), user.getName(), user.getSurname(), user.getPassword(), user.getRole().toString());
        if (this.getRole() != Role.STUDENT) {
            throw new IllegalArgumentException("Wrong role");
        }
        this.schoolGroup = schoolGroup;
    }

    public Student() {
        super();
        this.schoolGroup = null;
    }

    /**
     * Generuje obiekt ucznia z rekordu bazy danych.
     *
     * @param resultSet wynik kwerendy SQL
     * @return wygenerowany obiekt ucznia
     */
    public static Student readStudent(ResultSet resultSet) {
        User user = User.readUser(resultSet);
        SchoolGroup schoolGroup = SchoolGroup.readSchoolGroup(resultSet);
        return new Student(user, schoolGroup);
    }

    /**
     * Generuje listę uczniów z bazy danych.
     *
     * @param resultSet wynik kwerendy SQL
     * @return wygenerowana lista uczniów
     */
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

    /**
     * Zwraca obiekt klasy, do której należy uczeń.
     *
     * @return obiekt klasy, do której należy uczeń
     */
    public SchoolGroup getSchoolGroup() {
        return schoolGroup;
    }

    /**
     * Ustawia obiekt klasy, do której należy uczeń.
     *
     * @param schoolGroup obiekt klasy, do której należy ucze
     */
    public void setSchoolGroup(SchoolGroup schoolGroup) {
        this.schoolGroup = schoolGroup;
    }

    @Override
    public void setRole(Role role) {
    }
}
