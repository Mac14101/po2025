package entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Entity {
    protected static Integer getIntColumn(ResultSet resultSet, String columnName) {
        try {
            return resultSet.getInt(columnName);
        } catch (SQLException e) {
            return null;
        }
    }

    protected static String getStringColumn(ResultSet resultSet, String columnName) {
        try {
            return resultSet.getString(columnName);
        } catch (SQLException e) {
            return null;
        }
    }
}
