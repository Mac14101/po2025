package jexp.session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Manager extends Thread {
    private static Manager instance;
    private HashMap<String, User> clients;

    Manager() {
        this.clients = new HashMap<>();
        this.start();
    }

    public static Manager getInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void refreshSession() {
        for (String token : this.clients.keySet()) {
            User user = this.clients.get(token);
            if (!user.getToken().equals(token)) {
                this.clients.remove(token);
            }
            if (user.getTokenTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
                this.clients.remove(token);
            }

        }
    }

    public String addSession() {
        String token = generateToken();
        this.clients.put(token, new User(token));
        return token;
    }

    public User getSession(String token) {
        return this.clients.get(token);
    }

    public String refreshToken(String token) {
        User user = this.clients.get(token);
        this.clients.remove(token);
        String newToken = generateToken();
        user.setToken(newToken);
        this.clients.put(newToken, user);
        return newToken;
    }

    @Override
    public void run() {
        while (true) {
            this.refreshSession();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
