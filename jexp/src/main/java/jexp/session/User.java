package jexp.session;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

public class User {
    private HashMap<String, String> session;
    private LocalDateTime tokenTime;
    private String token;

    User(String token) {
        this.session = new HashMap<>();
        this.token = token;
        this.tokenTime = LocalDateTime.now();
    }

    public String getSession(String key) {
        return session.get(key);
    }

    public void setSession(String key, String value) {
        session.put(key, value);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if (Objects.equals(this.token, token)) {
            throw new IllegalArgumentException("Token must be modified!");
        }
        this.token = token;
        this.tokenTime = LocalDateTime.now();
    }

    public LocalDateTime getTokenTime() {
        return tokenTime;
    }

}
