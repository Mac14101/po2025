package entities;

import java.util.HashMap;

/**
 * Klasa zawierająca informację zwrotną o błędach w danych żądania.
 */
public class Message {
    private HashMap<String, String> messages;

    public Message() {
        this.messages = new HashMap<>();
    }


    /**
     * Ustawia wiadomość dla wybranego klucza.
     *
     * @param key     klucz, do którego przypisana jest wiadomość
     * @param message wiadomość
     */
    public void addMessage(String key, String message) {
        this.messages.put(key, message);
    }

    /**
     * Zwraca wiadomość przypisaną do wybranego klucza.
     *
     * @return wiadomość
     */
    public HashMap<String, String> getMessages() {
        return this.messages;
    }
}
