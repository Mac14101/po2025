package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa reprezentująca klasę (rekord tabeli `classes` z bazy danych). Wybrane atrybuty mogą być typu null,
 * jeżeli nie wszystkie dane zostały uzupełnione!!!
 */
public class SchoolGroup extends DatabaseEntity {
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


    /**
     * Generuje obiekt klasy z rekordu bazy danych.
     *
     * @param resultSet wynik kwerendy SQL
     * @return wygenerowany obiekt klasy
     */
    public static SchoolGroup readSchoolGroup(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "cid");
        Integer number = getIntColumn(resultSet, "number");
        String letterString = getStringColumn(resultSet, "letter");
        Character letter = letterString == null ? null : letterString.charAt(0);
        return new SchoolGroup(id, number, letter);
    }


    /**
     * Generuje listę klas z bazy danych.
     *
     * @param resultSet wynik kwerendy SQL
     * @return wygenerowana lista klas
     */
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


    /**
     * Zwraca identyfikator klasy.
     *
     * @return identyfikator klasy
     */
    public Integer getId() {
        return id;
    }

    /**
     * Ustawia identyfikator klasy
     *
     * @param id identyfikator klasy
     */

    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * Zwraca numer klasy.
     *
     * @return numer klasy
     */
    public Integer getNumber() {
        return number;
    }


    /**
     * Ustawia numer klasy, musi być większy od 0.
     *
     * @param number numer klasy
     * @throws IllegalArgumentException
     */
    public void setNumber(Integer number) {
        if (number < 0) {
            throw new IllegalArgumentException("Class number is lower than 0!");
        }
        this.number = number;
    }

    /**
     * Zwraca literę klasy.
     *
     * @return litera klasy
     */
    public Character getLetter() {
        return letter;
    }

    /**
     * Ustawia literę klasy, musi być z zakresu A-Z.
     *
     * @param letter litera klasy
     * @throws IllegalArgumentException
     */
    public void setLetter(Character letter) {
        if (!letter.toString().matches("^[A-Z]$")) {
            throw new IllegalArgumentException("Class letter is not valid!");
        }
        this.letter = letter;
    }

    /**
     * Zwraca listę uczniów należących do klasy.
     *
     * @return lista uczniów klasy
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Dodaje ucznia do klasy.
     *
     * @param student nowy uczeń
     */
    public void addStudent(Student student) {
        this.students.add(student);
    }

}
