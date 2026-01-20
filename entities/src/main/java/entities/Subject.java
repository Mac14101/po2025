package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa reprezentująca użytkownika (rekord tabeli `subjects` z bazy danych). Wybrane atrybuty mogą być typu null,
 * jeżeli nie wszystkie dane zostały uzupełnione!!!
 */
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

    /**
     * Generuje obiekt przedmiotu z rekordu bazy danych.
     *
     * @param resultSet wynik kwerendy SQL
     * @return wygenerowany obiekt przedmiotu
     */
    public static Subject readSubject(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "sbid");
        String name = getStringColumn(resultSet, "sbname");
        return new Subject(id, name);
    }

    /**
     * Generuje listę przedmiotów z bazy danych.
     *
     * @param resultSet wynik kwerendy SQL
     * @return wygenerowana lista przedmiotów
     */
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

    /**
     * Zwraca identyfikator przedmiotu.
     *
     * @return identyfikator przedmiotu
     */
    public Integer getId() {
        return id;
    }

    /**
     * Ustawia identyfikator przedmiotu
     *
     * @param id identyfikator przedmiotu
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Zwraca nazwę przedmiotu
     *
     * @return nazwa przedmiotu
     */
    public String getName() {
        return name;
    }

    /**
     * Ustawia nazwę przedmiotu. Sprawdza poprawność nazwę na podstawie RegEx, jeśli nazwa jest niepoprawny rzuca wyjątek IllegalArgumentException.
     *
     * @param name nazwa przedmiotu
     * @throws IllegalArgumentException
     */
    public void setName(String name) {
        if (name == null) {
            return;
        }
        if (!name.matches("^[A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+$")) {
            throw new IllegalArgumentException("Invalid subject name!");
        }
        this.name = name;
    }

}
