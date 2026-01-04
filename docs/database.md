# Baza danych e-dziennika
Aplikacja korzysta z wbudowanej bazy danych SQLite.

## Tabele w bazie danych

1. Użytkownicy(users)
Tabela przechowująca konta użytkowników aplikacji.

|Nazwa kolumny|Typ|Dodatkowe opcje kolumny|Opis|
|-|-|-|-|
|id|INT|PRIMARY KEY|Unikatowy identyfikator przypisany do użytkownika|
|email|VARCHAR(100)|UNIQUE NOT NULL|Unikalny adres e-mail użytkownika|
|name|VARCHAR(50)|NOT NULL|Imie użytkownika|
|surname|VARCHAR(50)|NOT NULL|Nazwisko użytkownika|
|password|TEXT|NOT NULL|Hasło do konta użytkownika|
|role|VARCHAR(20)|DEFAULT NULL|Rola użytkownika (np. admin, student)|

2. Klasy(classes)
Tabela przechowująca listę klas.

|Nazwa kolumny|Typ|Dodatkowe opcje kolumny|Opis|
|-|-|-|-|
|id|INT|PRIMARY KEY|Unikatowy identyfikator przypisany do klasy|
|number|INT|NOT NULL|Numer klasy (np. 1, 2, 7)|
|letter|CHAR(1)|NOT NULL|Litera klasy (np. A, B, D)|

3. Uczniowie(students)
Tabela przechowująca informacje o przypisaniu uczniów do klas.

|Nazwa kolumny|Typ|Dodatkowe opcje kolumny|Opis|
|-|-|-|-|
|uid|INT|FOREIGN KEY, UNIQUE|Identyfikator użytkownika|
|cid|INT|FOREIGN KEY|Identyfikator klasy|

4. Przedmioty(subjects)
Tabela przechowująca listę przedmiotów.

|Nazwa kolumny|Typ|Dodatkowe opcje kolumny|Opis|
|-|-|-|-|
|id|INT|PRIMARY KEY|Unikatowy identyfikator przypisany do przedmiotu szkolnego|
|name|TEXT|UNIQUE NOT NULL|Nazwa przedmiotu szkolnego|

5. Plan zajęć(time_table)
Tabela przechowująca tygodniowy plan zajęć.

|Nazwa kolumny|Typ|Dodatkowe opcje kolumny|Opis|
|-|-|-|-|
|id|INT|AUTO INCREMENT, PRIMARY KEY|Unikatowy identyfikator zajęć lekcyjnych w plane lekcji|
|day|TEXT|NOT NULL|Dzień w którym odbywają się zajęcia (np. Monday, Friday)|
|startTime|TEXT|NOT NULL|Godzina rozpoczęcia zajęć, zapisana w formacie HH:MM:SS|
|endTime|TEXT|NOT NULL|Godzina zakończenia zajęć, zapisana w formacie HH:MM:SS|
|cid|INT|FOREIGN KEY|Identyfikator klasy, której dotyczy lekcja|
|tid|INT|FOREIGN KEY|Identyfikator nauczyciela, który prowadzi lekcję|
|sid|INT|FOREIGN KEY|Identyfikator przedmiotu|

6. Lekcje(lessons)
Tabela przechowująca lekcję, które się odbyły.

|Nazwa kolumny|Typ|Dodatkowe opcje kolumny|Opis|
|-|-|-|-|
|id|INT|AUTO INCREMENT, PRIMARY KEY|Unikatowy identyfikator lekcji|
|topic|TEXT|NOT NULL|Temat Zajęć lekcyjnych|
|date|TEXT|NOT NULL|Data, kiedy odbywały się zajęcia w formacie DD.MM.YYYY|
|ttid|INT|FOREIGN KEY|Identyfikator zajęć z planu zajęć|

7. Frekwencja(attendance)
Tabela przechowująca frekwencję uczniów na zajęciach.

|Nazwa kolumny|Typ|Dodatkowe opcje kolumny|Opis|
|-|-|-|-|
|sid|INT|FOREIGN KEY|Identyfikator ucznia|
|lid|INT|FOREIGN KEY|Identyfikator lekcji|
|status|TEXT|NOT NULL|Status frekwencji (np. present, late)|

8. Oceny(grades)
Tabela przechowująca oceny uczniów.

|Nazwa kolumny|Typ|Dodatkowe opcje kolumny|Opis|
|-|-|-|-|
|id|INT|AUTO INCREMENT, PRIMARY KEY|Unikatowy identyfikator oceny|
|sid|INT|FOREIGN KEY|Identyfikator ucznia, który otrzymał ocenę|
|sbid|INT|FOREIGN KEY|Identyfikator przedmiotu|
|tid|INT|NOT NULL|Identyfikator nauczyciela, który wystawił ocenę|