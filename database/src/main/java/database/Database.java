package database;

import java.sql.*;

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

    public ResultSet execute(String query) throws SQLException {
        if (this.connection == null) {
            throw new SQLException("Database connection is null");
        }
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }
}
