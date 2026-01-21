package com.example.edziennikui.shared;

import entities.SchoolGroup;
import entities.Subject;
import entities.User;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

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
}