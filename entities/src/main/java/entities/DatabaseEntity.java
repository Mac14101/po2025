package entities;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstrakcyjna klasa, zawierająca statyczne metody pobierające dane z obiektu ResultSet, który jest wynikiem kwerendy SQL.
 */
public abstract class DatabaseEntity {
    /**
     * Zwraca wartość wybranej kolumny typu int, w przypadku braku kolumny o wybranej nazwie zwraca null
     *
     * @param resultSet  wynik kwerendy SQL
     * @param columnName nazwa kolumny
     * @return wartość kolumny lub null
     */
    protected static Integer getIntColumn(ResultSet resultSet, String columnName) {
        try {
            return resultSet.getInt(columnName);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Zwraca wartość wybranej kolumny typu String, w przypadku braku kolumny o wybranej nazwie zwraca null
     *
     * @param resultSet  wynik kwerendy SQL
     * @param columnName nazwa kolumny
     * @return wartość kolumny
     */
    protected static String getStringColumn(ResultSet resultSet, String columnName) {
        try {
            return resultSet.getString(columnName);
        } catch (SQLException e) {
            return null;
        }
    }
}
