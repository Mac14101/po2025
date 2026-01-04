package server.database.entities;

public class User {
    private Integer id;
    private String email;
    private String name;
    private String surname;
    private String password;
    private String role;

    public User(Integer id, String email, String name, String surname, String password, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
    }

    public User() {
        this.id = null;
        this.email = null;
        this.name = null;
        this.surname = null;
        this.password = null;
        this.role = null;
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
            throw new IllegalArgumentException(email + " is not a valid email address!");
        }
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (!surname.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$")) {
            throw new IllegalArgumentException("Invalid surname format!");
        }
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.matches("^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$")) {
            throw new IllegalArgumentException("Invalid name format!");
        }
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
