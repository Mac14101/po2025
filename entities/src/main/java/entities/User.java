package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User extends Entity {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.matches("^[A-Za-z0-9_%+-]+@[A-Za-z]+.[A-Za-z]$")) {
            throw new IllegalArgumentException("Invalid email address!");
        } else if (email.length() > 100) {
            throw new IllegalArgumentException("Email address is too long!");
        }
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (!surname.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$")) {
            throw new IllegalArgumentException("Invalid surname format!");
        } else if (surname.length() > 50) {
            throw new IllegalArgumentException("Surname is too long!");
        }
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$")) {
            throw new IllegalArgumentException("Invalid name format!");
        } else if (name.length() > 50) {
            throw new IllegalArgumentException("Name is too long!");
        }
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role {
        ADMIN("admin"), STUDENT("student"), TEACHER("teacher");
        private final String role;

        private Role(String role) {
            this.role = role;
        }

        public static Role getRoleFromName(String name) {
            for (Role role : Role.values()) {
                if (role.name().equalsIgnoreCase(name)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Invalid role!");
        }

        @Override
        public String toString() {
            return role;
        }
    }
}
