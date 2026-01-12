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
        this.connection = DriverManager.getConnection(url);
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    public Statement getStatement() throws SQLException {
        return this.connection.createStatement();
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.connection.prepareStatement(sql);
    }
}
