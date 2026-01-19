# Moduł server
Moduł server jest głównym modułem w działaniu serwera HTTP. Zawiera plik *Main.java*, czyli główną instancję serwera, oraz klasy obsługujące poszczególne endpointy API. Korzysta z modułu **jexp** i **database**.

## Endpointy API

1. Uwierzytelnianie
    * `/` - odczytanie nagłówka `Authorization` i znalezienie odpowiedniego obiektu sesji, jeżeli taki nie zostanie znaleziony przechodzi do następnej dopasowanej trasy
    * `/session/` **GET** - endpoint odświeżający token uwierzytelniania, ***obsługuje tylko uwierzytelnionych użytkowników!!!***
    * `/login/` **POST** - logowanie i ustawianie sesji, jeżeli użytkownik jest uwierzytelniony przechodzi do następnej trasy, jeżeli użytkownik podał nieprawidłowe dane zwraca odpowiedź o statusie **400**, w przeciwnym wypadku ustanawia sesję i przesyła użytkownikowi token uwierzytelniania 
    * `/logout/` **GET** - wylogowanie i zniszczenie sesji, ***obsługuje tylko uwierzytelnionych użytkowników!!!***
    * `/self/` **GET** - dane użytkownika, ***obsługuje tylko uwierzytelnionych użytkowników!!!***
2. Konta użytkowników
    * `/user/` **GET** - lista wszystkich użytkowników, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
    * `/user/` **POST** - tworzy nowego użytkownika, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
3. Przedmioty szkolne
    * `/subject/` **GET** - lista wszystkich przedmiotów, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
    * `/subject/` **POST** - tworzy nowy przedmiot, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
4. Klasy
    * `/class/` **GET** - lista wszystkich klas, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
    * `/class/` **POST** - tworzy nową klasę, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
5. Uczniowie
    * `/student/` **GET** - lista wszystkich uczniów , ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
    * `/student/:classId/` **GET** - lista uczniów w klasie o id **classId**, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
    * `/student/` **POST** - dodaje wybranego ucznia do wybranej klasy, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
6. Plan zajęć
    * `/timetable/:classId/` **GET** - lista zajęć z planu zajęć klasy, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
    * `/timetable/` **POST** - dodawania zajęć do planu zajęć klasy, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami administratora!!!***
    * `/student/timetable/` **GET** - wyświetlanie planu zajęć, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami ucznia!!!*** (***nie zaimplementowano***)
7. Lekcje
    * `/lesson/` **GET** - wyświetlanie listy ostatnich lekcji, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami nauczyciela!!!*** (***nie zaimplementowano***)
    * `/lesson/` **POST** - dodawanie lekcji, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami nauczyciela!!!*** (***nie zaimplementowano***)
8. Obecność na zajęciach
    * `/attendance/:lessonId/` **GET** - wyświetlanie listy obecności na lekcji, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami nauczyciela!!!*** (***nie zaimplementowano***)
    * `/attendance/:lessonId/` **PUT** - aktualizacja obecności ucznia na lekcji, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami nauczyciela!!!*** (***nie zaimplementowano***)
    * `/student/attendance/` **GET** - wyświetlanie obecności, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami ucznia!!!*** (***nie zaimplementowano***)
9. Oceny
    * `/grade/:studentId/` **GET** - wyświetlanie ocen ucznia, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami nauczyciela!!!*** (***nie zaimplementowano***)
    * `/grade/:studentId/` **POST** - dodawanie oceny ucznia, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami nauczyciela!!!*** (***nie zaimplementowano***)
    * `/student/grade/` **GET** - wyświetlanie ocen, ***obsługuje tylko uwierzytelnionych użytkowników z uprawnieniami ucznia!!!*** (***nie zaimplementowano***)
