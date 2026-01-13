# Baza danych e-dziennika

Aplikacja korzysta z wbudowanej bazy danych SQLite.

## Tabele w bazie danych

1. Użytkownicy(users)
   Tabela przechowująca konta użytkowników aplikacji.

| Nazwa kolumny | Typ          | Dodatkowe opcje kolumny | Opis                                              |
|---------------|--------------|-------------------------|---------------------------------------------------|
| id            | INT          | PRIMARY KEY             | Unikatowy identyfikator przypisany do użytkownika |
| email         | VARCHAR(100) | UNIQUE NOT NULL         | Unikalny adres e-mail użytkownika                 |
| name          | VARCHAR(50)  | NOT NULL                | Imie użytkownika                                  |
| surname       | VARCHAR(50)  | NOT NULL                | Nazwisko użytkownika                              |
| password      | TEXT         | NOT NULL                | Hasło do konta użytkownika                        |
| role          | VARCHAR(20)  | DEFAULT NULL            | Rola użytkownika (np. admin, student)             |

Kwerendy:

* CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT, email VARCHAR(100) UNIQUE NOT NULL, name VARCHAR(50) NOT
  NULL, surname VARCHAR(50) NOT NULL, password TEXT NOT NULL, role VARCHAR(20) DEFAULT NULL, PRIMARY KEY(id));
* INSERT INTO users (email, name, surname, password, role) VALUES (:email, :name, :surname, :password, :role);
* SELECT U.id, U.email, U.name, U.surname, U.role FROM users AS U; - wybranie wszystkich użytkowników

2. Klasy(classes)
   Tabela przechowująca listę klas.

| Nazwa kolumny | Typ     | Dodatkowe opcje kolumny | Opis                                        |
|---------------|---------|-------------------------|---------------------------------------------|
| id            | INT     | PRIMARY KEY             | Unikatowy identyfikator przypisany do klasy |
| number        | INT     | NOT NULL                | Numer klasy (np. 1, 2, 7)                   |
| letter        | CHAR(1) | NOT NULL                | Litera klasy (np. A, B, D)                  |

Kwerendy:

* CREATE TABLE IF NOT EXISTS classes (id INT PRIMARY KEY AUTO_INCREMENT, number INT NOT NULL, letter CHAR(1) NOT NULL,
  PRIMARY KEY(id));
* INSERT INTO classes (number, letter) VALUES (:number, :letter);

3. Uczniowie(students)
   Tabela przechowująca informacje o przypisaniu uczniów do klas.

| Nazwa kolumny | Typ | Dodatkowe opcje kolumny | Opis                      |
|---------------|-----|-------------------------|---------------------------|
| uid           | INT | FOREIGN KEY, UNIQUE     | Identyfikator użytkownika |
| cid           | INT | FOREIGN KEY             | Identyfikator klasy       |

Kwerendy:

* CREATE TABLE IF NOT EXISTS students (uid INT UNIQUE, cid INT, FOREIGN KEY(uid) REFERENCES users(id), FOREIGN KEY(cid)
  REFERENCES classes(id));
* INSERT INTO students (uid, cid) VALUES(:uid, :cid);

4. Przedmioty(subjects)
   Tabela przechowująca listę przedmiotów.

| Nazwa kolumny | Typ  | Dodatkowe opcje kolumny | Opis                                                       |
|---------------|------|-------------------------|------------------------------------------------------------|
| id            | INT  | PRIMARY KEY             | Unikatowy identyfikator przypisany do przedmiotu szkolnego |
| name          | TEXT | UNIQUE NOT NULL         | Nazwa przedmiotu szkolnego                                 |

Kwerendy:

* CREATE TABLE IF NOT EXISTS subjects (id INT AUTO_INCREMENT, name TEXT UNIQUE NOT NULL, PRIMARY KEY(id));
* INSERT INTO subjects (name) VALUES (:name);

5. Plan zajęć(time_table)
   Tabela przechowująca tygodniowy plan zajęć.

| Nazwa kolumny | Typ  | Dodatkowe opcje kolumny     | Opis                                                     |
|---------------|------|-----------------------------|----------------------------------------------------------|
| id            | INT  | AUTO INCREMENT, PRIMARY KEY | Unikatowy identyfikator zajęć lekcyjnych w plane lekcji  |
| day           | TEXT | NOT NULL                    | Dzień w którym odbywają się zajęcia (np. Monday, Friday) |
| startTime     | TEXT | NOT NULL                    | Godzina rozpoczęcia zajęć, zapisana w formacie HH:MM:SS  |
| endTime       | TEXT | NOT NULL                    | Godzina zakończenia zajęć, zapisana w formacie HH:MM:SS  |
| cid           | INT  | FOREIGN KEY                 | Identyfikator klasy, której dotyczy lekcja               |
| tid           | INT  | FOREIGN KEY                 | Identyfikator nauczyciela, który prowadzi lekcję         |
| sid           | INT  | FOREIGN KEY                 | Identyfikator przedmiotu                                 |

Kwerendy:

* CREATE TABLE IF NOT EXISTS time_table (id INT AUTO_INCREMENT, day TEXT NOT NULL, startTime TEXT NOT NULL, endTime TEXT
  NOT NULL, cid INT, tid INT, sid INT, PRIMARY KEY(id), FOREIGN KEY(cid) REFERENCE classes(id), FOREIGN KEY(tid)
  REFERENCE users(id), FOREIGN KEY(sid) REFERENCES subjects(id));
* INSERT INTO time_table (day, startTime, endTime, cid, tid, sid) VALUES (:day, :startTime, :endTime, :cid, :tid, :sid);

6. Lekcje(lessons)
   Tabela przechowująca lekcję, które się odbyły.

| Nazwa kolumny | Typ  | Dodatkowe opcje kolumny     | Opis                                                   |
|---------------|------|-----------------------------|--------------------------------------------------------|
| id            | INT  | AUTO INCREMENT, PRIMARY KEY | Unikatowy identyfikator lekcji                         |
| topic         | TEXT | NOT NULL                    | Temat Zajęć lekcyjnych                                 |
| date          | TEXT | NOT NULL                    | Data, kiedy odbywały się zajęcia w formacie DD.MM.YYYY |
| ttid          | INT  | FOREIGN KEY                 | Identyfikator zajęć z planu zajęć                      |

Kwerendy:

* CREATE TABLE IF NOT EXISTS lessons (id INT AUTO_INCREMENT, topic TEXT NOT NULL, date TEXT NOT NULL, ttid INT, PRIMARY
  KEY(id), FOREIGN KEY(ttid) REFERENCE time_table(id));
* INSERT INTO lessons (topic, date, ttid) VALUES (:topic, :date, :ttid);

7. Frekwencja(attendance)
   Tabela przechowująca frekwencję uczniów na zajęciach.

| Nazwa kolumny | Typ  | Dodatkowe opcje kolumny | Opis                                  |
|---------------|------|-------------------------|---------------------------------------|
| sid           | INT  | FOREIGN KEY             | Identyfikator ucznia                  |
| lid           | INT  | FOREIGN KEY             | Identyfikator lekcji                  |
| status        | TEXT | NOT NULL                | Status frekwencji (np. present, late) |

Kwerendy:

* CREATE TABLE IF NOT EXISTS attendance (sid INT, lid INT, status TEXT NOT NULL, FOREIGN KEY(sid) REFERENCE students(
  id), FOREIGN KEY(lid) REFERENCE lessons(id));
* INSERT INTO attendance (sid, lid, status) VALUES (:sid, :lid, :status);

8. Oceny(grades)
   Tabela przechowująca oceny uczniów.

| Nazwa kolumny | Typ         | Dodatkowe opcje kolumny     | Opis                                            |
|---------------|-------------|-----------------------------|-------------------------------------------------|
| id            | INT         | AUTO INCREMENT, PRIMARY KEY | Unikatowy identyfikator oceny                   |
| sid           | INT         | FOREIGN KEY                 | Identyfikator ucznia, który otrzymał ocenę      |
| sbid          | INT         | FOREIGN KEY                 | Identyfikator przedmiotu                        |
| tid           | INT         | FOREIGN KEY                 | Identyfikator nauczyciela, który wystawił ocenę |
| grade         | VARCHAR(10) | NOT NULL                    | Ocena                                           |

Kwerendy:

* CREATE TABLE IF NOT EXISTS grades (id INT AUTO_INCREMENT, sid INT, sbid INT, tid INT, grade VARCHAR(10) NOT NULL,
  PRIMARY KEY(id), FOREIGN KEY(sid) REFERENCE users(id), FOREIGN KEY(sbid) REFERENCE subjects(id), FOREIGN KEY(tid)
  REFERENCE users(id));
* INSERT INTO grades (sid, sbid, tid, grade) VALUES (:sid, :sbid, :tid, :grade);