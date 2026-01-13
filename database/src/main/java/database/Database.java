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
        this.connection.setAutoCommit(false);
    }

    public void close() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }


    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        this.ensureConnected();
        return this.connection.prepareStatement(sql);
    }

    public boolean executeCreateStatement(PreparedStatement statement) throws SQLException {
        this.ensureConnected();
        boolean result = statement.execute();
        this.connection.commit();
        return result;
    }

    public int executeUpdateStatement(PreparedStatement statement) throws SQLException {
        this.ensureConnected();
        int result = statement.executeUpdate();
        this.connection.commit();
        return result;
    }

    public ResultSet executeQueryStatement(PreparedStatement statement) throws SQLException {
        this.ensureConnected();
        ResultSet result = statement.executeQuery();
        this.connection.commit();
        return result;
    }

    public void commit() throws SQLException {
        this.ensureConnected();
        this.connection.commit();
    }

    public void rollback() throws SQLException {
        this.ensureConnected();
        this.connection.rollback();
    }

    private void ensureConnected() {
        if (this.connection == null) {
            throw new IllegalStateException("Database is not connected");
        }
    }
}
