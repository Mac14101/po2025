package database;

import java.sql.*;

public class Database {
    private Connection connection;
    private String url;

    public Database(String name) {
        this.url = "jdbc:sqlite:" + name;
        this.connection = null;
    }

    public Database() {
        this.url = null;
        this.connection = null;
    }

    public void connect(String name) throws SQLException {
        this.url = "jdbc:sqlite:" + name;
        this.connect();
    }

    public void connect() throws SQLException {
        if (this.url == null) {
            throw new IllegalArgumentException("Database URL is null");
        }
        if (this.connection != null && !this.connection.isClosed()) {
            return;
        }
        this.connection = DriverManager.getConnection(url);
    }

    public void close() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public Statement getStatement() throws SQLException {
        this.ensureConnected();
        return this.connection.createStatement();
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        this.ensureConnected();
        return this.connection.prepareStatement(sql);
    }

    private void ensureConnected() {
        if (this.connection == null) {
            throw new IllegalArgumentException("Database is not connected");
        }
    }
}
