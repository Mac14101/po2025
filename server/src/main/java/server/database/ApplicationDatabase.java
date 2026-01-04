package server.database;

import database.Database;

import java.sql.SQLException;

public class ApplicationDatabase extends Database {
    private static ApplicationDatabase instance;
    private static String dbName;

    private ApplicationDatabase() {
        super();
    }

    private static ApplicationDatabase getInstance() {
        return instance;
    }

    public static void openConnection() throws SQLException {
        if (instance != null) {
            throw new RuntimeException("Database already connected!");
        }
        instance = new ApplicationDatabase();
        instance.connect(dbName);
    }

    public static void config(String name) {
        if (instance != null) {
            throw new RuntimeException("Connection is open, cannot configure database name!");
        }
        dbName = name;
    }

    public static void closeConnection() throws SQLException {
        if (instance == null) {
            throw new RuntimeException("Database not connected!");
        }
        instance.close();
        instance = null;
    }
}
