package entities;

import java.util.HashMap;

public class Message {
    private HashMap<String, String> messages;

    public Message() {
        this.messages = new HashMap<>();
    }

    public void addMessage(String key, String message) {
        this.messages.put(key, message);
    }

    public HashMap<String, String> getMessages() {
        return this.messages;
    }
}
