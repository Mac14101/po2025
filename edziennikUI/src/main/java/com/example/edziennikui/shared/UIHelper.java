package com.example.edziennikui.shared;

import entities.SchoolGroup;
import entities.Subject;
import entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.function.Consumer;

public class UIHelper {

    /**
     * Ustawia konwerter dla usera
     */
    public static void setupUserComboBox(ComboBox<User> combo) {
        combo.setConverter(new StringConverter<>() {
            @Override public String toString(User u) {
                return u == null ? "" : u.getName() + " " + u.getSurname();
            }
            @Override public User fromString(String s) { return null; }
        });
    }

    /**
     * Ustawia konwerter dla klasy
     */
    public static void setupClassComboBox(ComboBox<SchoolGroup> combo) {
        combo.setConverter(new StringConverter<>() {
            @Override
            public String toString(SchoolGroup sg) {
                return sg == null ? "" : sg.getNumber() + " " + sg.getLetter();
            }
            @Override public SchoolGroup fromString(String s) { return null; }
        });
    }

    /**
     * Ustawia konwerter dla przedmiotu
     */
    public static void setupSubjectComboBox(ComboBox<Subject> combo) {
        combo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Subject s) {
                return s == null ? "" : s.getName();
            }
            @Override public Subject fromString(String s) { return null; }
        });
    }

    /**
     * Generyczna metoda do otwierania okien modalnych.
     * @param fxmlPath Ścieżka do pliku FXML
     * @param title Tytuł okna
     * @param controllerConsumer Funkcja do przekazania danych do kontrolera
     */
    public static <T> void openModal(String fxmlPath, String title, Consumer<T> controllerConsumer) {
        try {
            FXMLLoader loader = new FXMLLoader(UIHelper.class.getResource(fxmlPath));
            Parent root = loader.load();

            if (controllerConsumer != null) {
                T controller = loader.getController();
                controllerConsumer.accept(controller);
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            AlertHelper.showError("Błąd", "Nie można otworzyć okna: " + title);
        }
    }
}