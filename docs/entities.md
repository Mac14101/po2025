# Moduł entities
Moduł entities odpowiada za reprezentowanie danych poprzez obiekty. Moduł składa się z dwóch pakietów **entities** i **json**.

## Obiekt JSON
Klasa odpowiedzialna za przetwarzanie JSON, oparta o wzorzec singleton.
Atrybuty:
   * private static **JSON** instance - jedyna istniejąca instancja obiektu JSON
   * private **ObjectMapper** mapper - obiekt mappera Jackson
Metody:
   * private static **JSON** getInstance() - zwraca instancję obiektu JSON, jeżeli instancja nie istnieje tworzy ją
   * private **ObjectMapper** getMapper() - zwraca obiekt mappera
   * public static **T** parse() throws **JsonProcessingException** - zamienia JSON na obiekt Java wybranej klasy
   * public static **String** stringify() throws **JsonProcessingException** - zamienia obiekt Java na JSON

## Diagram UML Klas

```plantuml
@startuml
package "json"{
class JSON{
    - ObjectMapper mapper;
    - ObjectMapper getMapper();
}
}
package "entities"{
class DatabaseEntity{}
class User{
    - Integer id;
    - String email;
    - String name;
    - String surname;
    - String password;
    - Role role;
    + Integer getId();
    + void setId();
    + String getEmail();
    + void setEmail();
    + String getSurname();
    + void setSurname();
    + String getName();
    + void setName();
    + String getPassword();
    + void setPassword();
    + Role getRole();
    + void setRole();
}
enum Role as "User.Role"{
    * admin;
    * student;
    * teacher;
    + String toString();
}
class SchoolGroup{
    - Integer id;
    - Integer number;
    - Character letter;
    - ArrayList<Student> students;
    + Integer getId();
    + void setId();
    + Integer getNumber();
    + void setNumber();
    + Character getLetter();
    + void setLetter();
    + ArrayList<Student> getStudents();
    + void addStudent();
}
class Student{
    - SchoolGroup schoolGroup;
    + SchoolGroup getSchoolGroup();
    + void setSchoolGroup();
}
class Subject{
    - Integer id;
    - String name;
    + Integer getId();
    + void setId();
    + String getName();
    + void setName();
}
class Teacher{}
class Lesson{
    - Integer id;
    - String topic;
    - String date;
    - SchoolClass schoolClass;
    + Integer getId();
    + void setId();
    + String getTopic();
    + void setTopic();
    + String getDate();
    + void setDate();
    + SchoolClass getSchoolClass();
    + void setSchoolClass();
}
class SchoolClass{
    - Integer id;
    - String day;
    - String startTime;
    - String endTime;
    - SchoolGroup schoolGroup;
    - Teacher teacher;
    - Subject subject;
    - Integer room;
    + Integer getRoom();
    + void setRoom();
    + SchoolGroup getSchoolGroup();
    + void setSchoolGroup();
    + Teacher getTeacher();
    + void setTeacher();
    + Subject getSubject();
    + void setSubject();
    + Integer getId();
    + void setId();
    + String getDay();
    + void setDay();
    + String getStartTime();
    + void setStartTime();
    + String getEndTime();
    + void setEndTime();
}
class Attendance{
    - Student student;
    - Lesson lesson;
    - Status status;
    + Student getStudent();
    + void setStudent();
    + Lesson getLesson();
    + void setLesson();
    + Status getStatus();
    + void setStatus();
}
enum Status as "Attendance.Status"{
    * present;
    * absent;
    * late;
    * undefined;
    + String toString();
}
class Grade{
    - Integer id;
    - Student student;
    - Subject subject;
    - Teacher teacher;
    - GradeName grade;
    - String title;
    - String comment;
    + String getTitle();
    + void setTitle();
    + String getComment();
    + void setComment();
    + Integer getId();
    + void setId();
    + Student getStudent();
    + void setStudent();
    + Subject getSubject();
    + void setSubject();
    + GradeName getGrade();
    + void setGrade();
    + Teacher getTeacher();
    + void setTeacher();
}
enum GradeName as "Grade.GradeName"{
    * 1;
    * 2;
    * 3;
    * 4;
    * 5;
    * 6;
    * np;
    * nb;
    + String toString();
}
class Message{
    - HashMap<String, String> messages;
    + void addMessage();
    + HashMap<String, String> getMessages();
}
class UserChangePassword{
    + String newPassword;
    + String newPasswordConfirm;
}
class UserCredentials{
    - String email;
    - String password;
    + String getEmail();
    + void setEmail();
    + String getPassword();
    + void setPassword();
}
}
entities.User--|>entities.DatabaseEntity
entities.Grade--|>entities.DatabaseEntity
entities.Attendance--|>entities.DatabaseEntity
entities.Lesson--|>entities.DatabaseEntity
entities.SchoolClass--|>entities.DatabaseEntity
entities.SchoolGroup--|>entities.DatabaseEntity
entities.Subject--|>entities.DatabaseEntity
entities.Student--|>entities.User
entities.Teacher--|>entities.User
entities.Attendance--*entities.Student
entities.Attendance--*entities.Lesson
entities.Attendance--*entities.Status
entities.Grade--*entities.Student
entities.Grade--*entities.Subject
entities.Grade--*entities.Teacher
entities.Grade--*entities.GradeName
entities.Lesson--*entities.SchoolClass
entities.SchoolClass--*entities.SchoolGroup
entities.SchoolClass--*entities.Teacher
entities.SchoolGroup--*entities.Student
entities.Student--*entities.SchoolGroup
entities.User--*entities.Role
@enduml
```