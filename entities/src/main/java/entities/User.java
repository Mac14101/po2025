package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa reprezentująca użytkownika (rekord tabeli `users` z bazy danych). Wybrane atrybuty mogą być typu null,
 * jeżeli nie wszystkie dane zostały uzupełnione!!!
 */
public class User extends DatabaseEntity {
    private Integer id;
    private String email;
    private String name;
    private String surname;
    private String password;
    private Role role;

    public User(Integer id, String email, String name, String surname, String password, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        if (role != null) {
            this.role = Role.getRoleFromName(role);
        }
    }

    public User() {
        this.id = null;
        this.email = null;
        this.name = null;
        this.surname = null;
        this.password = null;
        this.role = null;
    }

    /**
     * Generuje obiekt użytkownika z rekordu bazy danych.
     *
     * @param resultSet wynik kwerendy SQL
     * @return wygenerowany obiekt użytkownika
     */
    public static User readUser(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "uid");
        String email = getStringColumn(resultSet, "email");
        String name = getStringColumn(resultSet, "uname");
        String surname = getStringColumn(resultSet, "surname");
        String password = getStringColumn(resultSet, "password");
        String role = getStringColumn(resultSet, "role");
        User user = new User(id, email, name, surname, password, role);
        return user;
    }

    /**
     * Generuje listę obiektów użytkownika z bazy danych.
     *
     * @param resultSet wynik kwerendy SQL
     * @return wygenerowana lista użytkowników
     */
    public static ArrayList<User> readUserArray(ResultSet resultSet) {
        try {
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(User.readUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Zwraca id użytkownika.
     *
     * @return identyfikator uzytkownika
     */
    public Integer getId() {
        return id;
    }

    /**
     * Ustawia id użytownika.
     *
     * @param id identyfikator użytkownika
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Zwraca adres e-mail użytkownika.
     *
     * @return adres e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia adres e-mail użytkownika. Sprawdza poprawność adresu na podstawie RegEx, jeśli adres jest niepoprawny rzuca wyjątek IllegalArgumentException.
     *
     * @param email adres e-mail
     * @throws IllegalArgumentException niepoprawny adres e-mail
     */
    public void setEmail(String email) {
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email address!");
        } else if (email.length() > 100) {
            throw new IllegalArgumentException("Email address is too long!");
        }
        this.email = email;
    }

    /**
     * Zwraca nazwisko użytkownika.
     *
     * @return nazwisko użytkownika
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Ustawia nazwisko użytkownika. Sprawdza poprawność nazwiska na podstawie RegEx, jeśli nazwisko jest niepoprawne rzuca wyjątek IllegalArgumentException.
     *
     * @param surname nazwisko użytkownika
     * @throws IllegalArgumentException niepoprawne nazwisko
     */
    public void setSurname(String surname) {
        if (!surname.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$")) {
            throw new IllegalArgumentException("Invalid surname format!");
        } else if (surname.length() > 50) {
            throw new IllegalArgumentException("Surname is too long!");
        }
        this.surname = surname;
    }

    /**
     * Ustawia imie użytkownika.
     *
     * @return imie użytkownika
     */

    public String getName() {
        return name;
    }

    /**
     * Ustawia imię użytkownika. Sprawdza poprawność imienia na podstawie RegEx, jeśli imię jest niepoprawne rzuca wyjątek IllegalArgumentException.
     *
     * @param name imię użytkownika
     * @throws IllegalArgumentException niepoprawne imię
     */
    public void setName(String name) {
        if (!name.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$")) {
            throw new IllegalArgumentException("Invalid name format!");
        } else if (name.length() > 50) {
            throw new IllegalArgumentException("Name is too long!");
        }
        this.name = name;
    }

    /**
     * Zwraca hasło do konta użytkownika.
     *
     * @return hasło do konta użytkownika
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ustawia hasło do konta użytkownika.
     *
     * @param password hasło do konta użytkownika
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Zwraca rolę użytkownika.
     *
     * @return rola użytkownika
     */
    public Role getRole() {
        return role;
    }

    /**
     * Ustwaia rolę uzytkownika
     *
     * @param role rola użytkownika
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Lista możliwych roli użytkowników.
     */
    public enum Role {
        ADMIN("admin"), STUDENT("student"), TEACHER("teacher");
        private final String role;

        private Role(String role) {
            this.role = role;
        }

        /**
         * Wybiera role użytkownika na podstawie podanego łańcucha znaków
         *
         * @param name nazwa roli użytkownika
         * @return rola użytkownika
         */
        public static Role getRoleFromName(String name) {
            for (Role role : Role.values()) {
                if (role.name().equalsIgnoreCase(name)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Invalid role!");
        }

        /**
         * Rola reprezentowana poprzez ciąg znaków
         *
         * @return reprezentacja roli jako ciąg znaków
         */
        @Override
        public String toString() {
            return role;
        }
    }
}
