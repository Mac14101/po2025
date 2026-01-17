package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Lesson extends DatabaseEntity {
    private Integer id;
    private String topic;
    private String date;
    private SchoolClass schoolClass;

    public Lesson(Integer id, String topic, String date) {
        this.id = id;
        this.topic = topic;
        this.date = date;
        this.schoolClass = null;
    }

    public Lesson() {
        this.id = null;
        this.topic = null;
        this.date = null;
        this.schoolClass = null;
    }

    public static Lesson readLesson(ResultSet resultSet) {
        Integer id = getIntColumn(resultSet, "lid");
        String topic = getStringColumn(resultSet, "topic");
        String date = getStringColumn(resultSet, "date");
        SchoolClass schoolClass = SchoolClass.readSchoolClass(resultSet);
        return new Lesson(id, topic, date);
    }

    public static ArrayList<Lesson> readLessonArray(ResultSet resultSet) {
        try {
            ArrayList<Lesson> lessons = new ArrayList<>();
            while (resultSet.next()) {
                lessons.add(readLesson(resultSet));
            }
            return lessons;
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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
