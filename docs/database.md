# Baza danych e-dziennika

Aplikacja korzysta z wbudowanej bazy danych SQLite.

## Tabele w bazie danych

1. Użytkownicy(users)
   Tabela przechowująca konta użytkowników aplikacji.

| Nazwa kolumny | Typ          | Dodatkowe opcje kolumny | Opis                                              |
|---------------|--------------|-------------------------|---------------------------------------------------|
| uid           | INTEGER      | PRIMARY KEY             | Unikatowy identyfikator przypisany do użytkownika |
| email         | VARCHAR(100) | UNIQUE NOT NULL         | Unikalny adres e-mail użytkownika                 |
| uname         | VARCHAR(50)  | NOT NULL                | Imie użytkownika                                  |
| surname       | VARCHAR(50)  | NOT NULL                | Nazwisko użytkownika                              |
| password      | TEXT         | NOT NULL                | Hasło do konta użytkownika                        |
| role          | VARCHAR(20)  | DEFAULT NULL            | Rola użytkownika (np. admin, student)             |

2. Klasy(classes)
   Tabela przechowująca listę klas.

| Nazwa kolumny | Typ     | Dodatkowe opcje kolumny | Opis                                        |
|---------------|---------|-------------------------|---------------------------------------------|
| cid           | INTEGER | PRIMARY KEY             | Unikatowy identyfikator przypisany do klasy |
| number        | INTEGER | NOT NULL                | Numer klasy (np. 1, 2, 7)                   |
| letter        | CHAR(1) | NOT NULL                | Litera klasy (np. A, B, D)                  |

3. Uczniowie(students)
   Tabela przechowująca informacje o przypisaniu uczniów do klas.

| Nazwa kolumny | Typ     | Dodatkowe opcje kolumny | Opis                      |
|---------------|---------|-------------------------|---------------------------|
| uid           | INTEGER | FOREIGN KEY, UNIQUE     | Identyfikator użytkownika |
| cid           | INTEGER | FOREIGN KEY             | Identyfikator klasy       |

4. Przedmioty(subjects)
   Tabela przechowująca listę przedmiotów.

| Nazwa kolumny | Typ     | Dodatkowe opcje kolumny | Opis                                                       |
|---------------|---------|-------------------------|------------------------------------------------------------|
| sbid           | INTEGER | PRIMARY KEY             | Unikatowy identyfikator przypisany do przedmiotu szkolnego |
| sbname        | TEXT    | UNIQUE NOT NULL         | Nazwa przedmiotu szkolnego                                 |

5. Plan zajęć(time_table)
   Tabela przechowująca tygodniowy plan zajęć.

| Nazwa kolumny | Typ     | Dodatkowe opcje kolumny | Opis                                                     |
|---------------|---------|-------------------------|----------------------------------------------------------|
| ttid          | INTEGER | PRIMARY KEY             | Unikatowy identyfikator zajęć lekcyjnych w plane lekcji  |
| day           | TEXT    | NOT NULL                | Dzień w którym odbywają się zajęcia (np. Monday, Friday) |
| startTime     | TEXT    | NOT NULL                | Godzina rozpoczęcia zajęć, zapisana w formacie HH:MM:SS  |
| endTime       | TEXT    | NOT NULL                | Godzina zakończenia zajęć, zapisana w formacie HH:MM:SS  |
| cid           | INTEGER | FOREIGN KEY             | Identyfikator klasy, której dotyczy lekcja               |
| tid           | INTEGER | FOREIGN KEY             | Identyfikator nauczyciela, który prowadzi lekcję         |
| sbid           | INTEGER | FOREIGN KEY             | Identyfikator przedmiotu                                 |

6. Lekcje(lessons)
   Tabela przechowująca lekcję, które się odbyły.

| Nazwa kolumny | Typ     | Dodatkowe opcje kolumny | Opis                                                   |
|---------------|---------|-------------------------|--------------------------------------------------------|
| lid           | INTEGER | , PRIMARY KEY           | Unikatowy identyfikator lekcji                         |
| topic         | TEXT    | NOT NULL                | Temat Zajęć lekcyjnych                                 |
| date          | TEXT    | NOT NULL                | Data, kiedy odbywały się zajęcia w formacie DD.MM.YYYY |
| ttid          | INTEGER | FOREIGN KEY             | Identyfikator zajęć z planu zajęć                      |

7. Frekwencja(attendance)
   Tabela przechowująca frekwencję uczniów na zajęciach.

| Nazwa kolumny | Typ     | Dodatkowe opcje kolumny | Opis                                  |
|---------------|---------|-------------------------|---------------------------------------|
| sid           | INTEGER | FOREIGN KEY             | Identyfikator ucznia                  |
| lid           | INTEGER | FOREIGN KEY             | Identyfikator lekcji                  |
| status        | TEXT    | NOT NULL                | Status frekwencji (np. present, late) |

8. Oceny(grades)
   Tabela przechowująca oceny uczniów.

| Nazwa kolumny | Typ         | Dodatkowe opcje kolumny | Opis                                            |
|---------------|-------------|-------------------------|-------------------------------------------------|
| gid           | INTEGER     | PRIMARY KEY             | Unikatowy identyfikator oceny                   |
| sid           | INTEGER     | FOREIGN KEY             | Identyfikator ucznia, który otrzymał ocenę      |
| sbid          | INTEGER     | FOREIGN KEY             | Identyfikator przedmiotu                        |
| tid           | INTEGER     | FOREIGN KEY             | Identyfikator nauczyciela, który wystawił ocenę |
| grade         | VARCHAR(10) | NOT NULL                | Ocena                                           |

## Używane kwerendy

### Tworzenie tabel

* CREATE TABLE IF NOT EXISTS users (uid INTEGER PRIMARY KEY, email VARCHAR(100) UNIQUE NOT NULL, uname VARCHAR(50) NOT NULL, surname VARCHAR(50) NOT NULL, password TEXT NOT NULL, role VARCHAR(20) DEFAULT NULL); - tworzy tabelę `users`
* CREATE TABLE IF NOT EXISTS classes (cid INTEGER PRIMARY KEY, number INTEGER NOT NULL, letter CHAR(1) NOT NULL); - tworzy tabelę `classes`
* CREATE TABLE IF NOT EXISTS students (uid INTEGER UNIQUE, cid INTEGER, FOREIGN KEY(uid) REFERENCES users(uid), FOREIGN KEY(cid) REFERENCES classes(cid)); - tworzy tabelę `students`
* CREATE TABLE IF NOT EXISTS subjects (sbid INTEGER PRIMARY KEY, sbname TEXT UNIQUE NOT NULL); - tworzy tabelę `subjects`
* CREATE TABLE IF NOT EXISTS time_table (ttid INTEGER PRIMARY KEY, day TEXT NOT NULL, startTime TEXT NOT NULL, endTime TEXT NOT NULL, cid INTEGER, tid INTEGER, sid INTEGER, FOREIGN KEY(cid) REFERENCES users(uid), FOREIGN KEY(sid) REFERENCES subjects(sbid)); - tworzy tabelę `time_table`
* CREATE TABLE IF NOT EXISTS lessons (lid INTEGER PRIMARY KEY, topic TEXT NOT NULL, date TEXT NOT NULL, ttid INTEGER, FOREIGN KEY(ttid) REFERENCES time_table(ttid)); - tworzy tabelę `lessons`
* CREATE TABLE IF NOT EXISTS attendance (sid INTEGER, lid INTEGER, status TEXT NOT NULL, FOREIGN KEY(sid) REFERENCES students(sid), FOREIGN KEY(lid) REFERENCES lessons(lid)); - tworzy tabelę `attendance`
* CREATE TABLE IF NOT EXISTS grades (gid INTEGER PRIMARY KEY, sid INTEGER, sbid INTEGER, tid INTEGER, grade VARCHAR(10) NOT NULL, FOREIGN KEY(sid) REFERENCES users(uid), FOREIGN KEY(sbid) REFERENCES subjects(sbid), FOREIGN KEY(tid) REFERENCES users(uid)); - tworzy tabelę `grades`

### Wstawianie danych do tabeli

* INSERT INTO users (email, uname, surname, password, role) VALUES (:email, :uname, :surname, :password, :role); - wstawia nowego użytkownika do tabeli `users`
* INSERT INTO classes (number, letter) VALUES (:number, :letter); - dodaje nową klasę w tabeli `classes`
* INSERT INTO students (uid, cid) VALUES(:uid, :cid); - przypisuje ucznia do klasy
* INSERT INTO subjects (sbname) VALUES (:sbname); - dodaje nowy przedmiot
* INSERT INTO time_table (day, startTime, endTime, cid, tid, sid) VALUES (:day, :startTime, :endTime, :cid, :tid, :sid); - wstawia nowe zajęcia do tabeli `time_table`
* INSERT INTO lessons (topic, date, ttid) VALUES (:topic, :date, :ttid); - wstawia nową lekcję do tabeli `lessons`
* INSERT INTO attendance (sid, lid, status) VALUES (:sid, :lid, :status); - dodaje nowy status frekwencji ucznia na zajęciach
* INSERT INTO grades (sid, sbid, tid, grade) VALUES (:sid, :sbid, :tid, :grade); - dodaje nową ocenę ucznia do tabeli `grades`

### Pobieranie danych z tabel

* SELECT uid, email, uname, surname, role FROM users; - zwraca listę wszystkich użytkowników
* SELECT uid, email, password FROM users WHERE email=:email; - zwraca dane potrzebne do uwierzytelnienia i utworzenia sesji
* SELECT cid, number, letter FROM classes; - pobiera wszystkie klasy
* SELECT sbname FROM subjects; - pobiera wszystkie przedmioty
* SELECT U.name, U.surname, C.number, C.letter FROM users AS U INNER JOIN students AS S ON S.uid=U.uid INNER JOIN classes AS C ON C.cid=S.cid; - pobiera wszystkich uczniów razem z informację do której klasy należą
* SELECT U.name, U.surname FROM users AS U INNER JOIN students AS S ON S.uid=U.uid WHERE S.cid=:cid; - pobiera wszystkich uczniów przypisanych do wybranej klasy
* SELECT TT.day, TT.startTime, TT.endTime, T.uname, T.surname, SB.sbname FROM time_table AS TT INNER JOIN users AS T ON T.uid=TT.tid INNER JOIN subjects AS SB ON SB.sbid=TT.sbid WHERE TT.cid=:cid; - pobiera tygodniowy plan zajęć dla wybranej klasy
* SELECT uid, email, uname, surname, role FROM users WHERE uid=?; - pobiera dane wybranego użytkownika