package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection connection;
    private String url;

    public Database(String url) {
        this.url = url;
        this.connection = null;
    }

    public Database() {
        this.url = null;
        this.connection = null;
    }

    public void connect(String url) throws SQLException {
        this.url = url;
        this.connect();
    }

    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(url);
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}
