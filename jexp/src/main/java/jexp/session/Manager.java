package jexp.session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class Manager extends Thread {
    private static Manager instance;
    private HashMap<String, Session> clients;

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

    private String generateToken() {
        String token = UUID.randomUUID().toString();
        while (this.clients.containsKey(token)) {
            token = UUID.randomUUID().toString();
        }
        return token;
    }

    public void refreshSession() {
        for (String token : this.clients.keySet()) {
            Session session = this.clients.get(token);
            if (!session.getToken().equals(token)) {
                this.clients.remove(token);
            }
            if (session.getTokenTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
                this.clients.remove(token);
            }

        }
    }

    public String addSession() {
        String token = this.generateToken();
        this.clients.put(token, new Session(token));
        return token;
    }

    public void removeSession(String token) {
        this.clients.remove(token);
    }

    public Session getSession(String token) {
        return this.clients.get(token);
    }

    public String refreshToken(String token) {
        Session session = this.clients.get(token);
        this.clients.remove(token);
        String newToken = generateToken();
        session.setToken(newToken);
        this.clients.put(newToken, session);
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
