package entities;

public class Student {
    private User user;
    private SchoolGroup schoolGroup;

    public Student(User user, SchoolGroup schoolGroup) {
        this.user = user;
        this.schoolGroup = schoolGroup;
    }

    public Student() {
        this.user = null;
        this.schoolGroup = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SchoolGroup getSchoolGroup() {
        return schoolGroup;
    }

    public void setSchoolGroup(SchoolGroup schoolGroup) {
        this.schoolGroup = schoolGroup;
    }
}
