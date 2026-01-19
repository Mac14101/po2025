# Moduł entities
Moduł entities odpowiada za reprezentowanie danych poprzez obiekty. Moduł składa się z dwóch pakietów **entities** i **json**

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

## Klasy encji
1. Message
Klasa zawierająca informację zwrotną o błędach w danych żądania.
Atrybuty:
   * private **HashMap<String, String>** messages - lista wiadomości, kluczem jest obiekt, który jest błędy (np. dla uwierzytelniania "login")
Metody:
   * public **void** addMessage() - ustawia wiadomość dla wybranego klucza
   * public **HashMap<String, String>** getMessages() - zwraca wiadomość dla wybranego klucza
2. DatabaseEntity
Abstrakcyjna klasa zawierająca statyczne metody pobierająca wybrane kolumny z obiektu **ResultSet**.
Metody:
   * protected static **Integer** getIntColumn() - zwraca kolumnę o wybranej nazwie, która jest typu **int**, w przypadku braku kolumny zwraca **null**
   * protected static **String** getStringColumn() - zwraca kolumnę o wybranej nazwie, która jest typu **String**, w przypadku braku kolumny o wybranej nazwie zwraca **null**

3. User
Klasa reprezentująca użytkownika (rekord tabeli `users` z [bazy danych](./database.md)). ***Wybrane atrybuty mogą być typu null, jeżeli nie wszystkie dane zostały uzupełnione!!!***
Atrybuty:
   * private **Integer** id - identyfikator użytkownika (kolumna `uid` z tabeli `users`)
   * private **String** email - adres e-mail (kolumna `email` z tabeli `users`)
   * private **String** name - imie użytkownika (kolumna `uname` z tabeli `users`)
   * private **String** surname - nazwisko użytkownika (kolumna `surname` z tabeli `users`)
   * private **String** password - hasło do konta użytkownika (kolumna `password` z tabeli `users`)
   * private **Role** role - rola użytkownika (kolumna `role` z tabeli `users`)
Metody:
   * public static **User** readUser() - generuje obiekt użytkownika z rekordu bazy danych
   * public static **ArrayList<User>** readUserArray() - generuje listę obiektów użytkownika z bazy danych
   * public **Integer** getId() - zwraca id użytkownika, ***może być null!!!***
   * public **void** setId() - ustawia id użytkownika
   * public **String** getEmail() - zwraca e-mail użytkownika, ***może być null!!!***
   * public **void** setEmail() - ustawia adres e-mail, sprawdza poprawność adresu na podstawie RegEx, jeśli adres jest niepoprawny rzuca wyjątek **IllegalArgumentException**
   * public **String** getSurname() - zwraca nazwisko użytkownika, ***może być null!!!***
   * public **void** setSurname() - ustawia nazwisko użytkownika, sprawdza poprawność nazwiska na podstawie RegEx, jeśli nazwisko jest niepoprawne rzuca wyjątek **IllegalArgumentException**
   * public **String** getName() - zwraca imię użytkownika, ***może być null!!!***
   * public **void** setName() - ustawia imię użytkownika, sprawdza poprawność imienia na podstawie RegEx, jeśli imie jest niepoprawne rzuca wyjątek **IllegalArgumentException**
   * public **String** getPassword() - zwraca hasło do konta użytkownika, ***może być null!!***
   * public **void** setPassword() - ustawia hasło do konta użytkownika
   * public **Role** getRole() - zwraca rolę użytkownika, ***może być null!!***
   * public **void** setRole() - ustawia rolę użytkownika

4. User.Role
Obiekt `enum`, lista roli użytkowników.
   * ADMIN - "admin"
   * STUDENT - "student"
   * TEACHER - "teacher"
Metody:
   * public static **Role** getRoleFromName() - zwraca wartość z listy roli użytkowników na podstawie argumentu typu **String**
   * public String toString() - zwraca przypisaną do roli wartość typu **String**

5. UserCredentials
Obiekt zawierający zestaw danych do uwierzytelniania.
Atrybuty:
   * private **String** email - adres email
   * private **String** password - hasło
Metody:
   * public String getEmail() - zwraca adres e-mail
   * public void setEmail() - ustawia adres e-mail
   * public String getPassword() - zwraca hasło
   * public void setPassword() - ustawia hasło

5. Subject
Klasa reprezentująca przedmiot (rekord tabeli `subjects` z [bazy danych](./database.md)). ***Wybrane atrybuty mogą być typu null, jeżeli nie wszystkie dane zostały uzupełnione!!!***
Atrybuty:
   * private **Integer** id - identyfikator przedmiotu (kolumna `sbid` z tabeli `subjects`)
   * private **String** name - nazwa przedmiotu (kolumna `sbname` z tabeli `subjects`)
Metody:
   * public static **Subject** readSubject() - generuje obiekt przedmiotu z rekordu bazy danych
   * public static **ArrayList<Subject>** readSubjectsArray() - generuje listę obiektów przedmiotu z bazy danych
   * public **Integer** getId() - zwraca identyfikator przedmiotu, ***może być null!!!***
   * public **void** setId() - ustawia identyfikator przedmiotu
   * public **String** getName() - zwraca nazwę przedmiotu, ***może być null!!!***
   * public **void** setName() - ustawia nazwę przedmiotu, sprawdza poprawność nazwy na podstawie RegEx, jeśli nazwa jest niepoprawne rzuca wyjątek **IllegalArgumentException**

5. SchoolGroup
Klasa reprezentująca klasę (rekord tabeli `classes` z [bazy danych](./database.md)). ***Wybrane atrybuty mogą być typu null, jeżeli nie wszystkie dane zostały uzupełnione!!!***
Atrybuty:
   * private **Integer** id - identyfikator przedmiotu (kolumna `cid` z tabeli `classes`)
   * private **Integer** number - numer klasy (kolumna `number` z tabeli `classes`)
   * private **Character** letter - litera klasy (kolumna `letter` z tabeli `classes`)
   * private **ArrayList<Student>** students - lista uczniów należących do klasy
Metody:
   * public static **SchoolGroup** readSchoolGroup() - generuje obiekt klasy z rekordu bazy danych
   * public static **ArrayList<SchoolGroup>** readSchoolGroupArray() - generuje listę obiektów klas z bazy danych
   * public **Integer** getId() - zwraca identyfikator klasy, ***może być null!!!***
   * public **void** setId() - ustawia identyfikator klasy
   * public **Integer** getNumber() - zwraca numer klasy, ***może być null!!!***
   * public **void** setNumber() - ustawia numer klasy, musi być większy niż 0, w przeciwnym wypadku rzuca wyjątek **IllegalArgumentException**
   * public **Character** getLetter() - zwraca literę klasy, ***może być null!!!***
   * public **void** setLetter() - ustawia literę klasy z zakresu "A-Z", w przeciwnym wypadku rzuca wyjątek **IllegalArgumentException**
   * public **ArrayList<Student>** getStudents() - zwraca listę uczniów należących do klasy, ***może być null!!!***
   * public **void** addStudent() - dodaje ucznia do klasy

6. Student
Klasa reprezentująca ucznia (rekord tabeli `students` z [bazy danych](./database.md)). Dziedziczy po klasie **User**, aby umożliwić zapisanie danych ucznia. ***Wybrane atrybuty mogą być typu null, jeżeli nie wszystkie dane zostały uzupełnione!!!***
Atrybuty:
   * private **SchoolGroup** schoolGroup - klasa do której należy uczeń
Metody:
   * public static **Student** readStudent() - generuje obiekt ucznia z rekordu bazy danych
   * public static **ArrayList<Student>** readStudentArray() - generuje listę obiektów uczniów z bazy danych
   * public **SchoolGroup** getSchoolGroup() - zwraca obiekt klasy, do której należy uczeń
   * public **void** setSchoolGroup() - ustawia obiekt klasy, do której należy uczeń