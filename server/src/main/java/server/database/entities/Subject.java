package server.database.entities;

public class Subject {
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
