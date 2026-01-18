package database;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

public class DatabaseTest {
    private Database database;

    @Before
    public void setUp() throws SQLException {
        database = new Database();
    }

    @Test
    public void connect() {
        try (MockedStatic<DriverManager> driverMock = Mockito.mockStatic(DriverManager.class)) {
            Connection connectionMock = Mockito.mock(Connection.class);
            driverMock.when(() -> DriverManager.getConnection("jdbc:sqlite:test")).thenReturn(connectionMock);
            database.connect("test");
            driverMock.verify(() -> DriverManager.getConnection("jdbc:sqlite:test"));
            Mockito.verify(connectionMock, Mockito.times(1)).setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void close() {
        try (MockedStatic<DriverManager> driverMock = Mockito.mockStatic(DriverManager.class)) {
            Connection connectionMock = Mockito.mock(Connection.class);
            driverMock.when(() -> DriverManager.getConnection("jdbc:sqlite:test")).thenReturn(connectionMock);
            database.connect("test");
            database.close();
            Mockito.verify(connectionMock, Mockito.times(1)).close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getPreparedStatement() throws NoSuchFieldException, IllegalAccessException, SQLException {
        Field connection = Database.class.getDeclaredField("connection");
        connection.setAccessible(true);
        Connection connectionMock = Mockito.mock(Connection.class);
        connection.set(database, connectionMock);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionMock.prepareStatement(Mockito.anyString())).thenReturn(preparedStatementMock);
        assertSame(preparedStatementMock, database.getPreparedStatement("sql"));
        Mockito.verify(connectionMock, Mockito.times(1)).prepareStatement("sql");
    }

    @Test
    public void executeCreateStatement() throws NoSuchFieldException, IllegalAccessException, SQLException {
        assertThrows(IllegalStateException.class, () -> database.executeCreateStatement(null));
        Field connection = Database.class.getDeclaredField("connection");
        connection.setAccessible(true);
        Connection connectionMock = Mockito.mock(Connection.class);
        connection.set(database, connectionMock);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        database.executeCreateStatement(preparedStatementMock);
        Mockito.verify(preparedStatementMock, Mockito.times(1)).execute();
        Mockito.verify(connectionMock, Mockito.times(1)).commit();
    }

    @Test
    public void executeUpdateStatement() throws NoSuchFieldException, IllegalAccessException, SQLException {
        assertThrows(IllegalStateException.class, () -> database.executeUpdateStatement(null));
        Field connection = Database.class.getDeclaredField("connection");
        connection.setAccessible(true);
        Connection connectionMock = Mockito.mock(Connection.class);
        connection.set(database, connectionMock);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        database.executeUpdateStatement(preparedStatementMock);
        Mockito.verify(preparedStatementMock, Mockito.times(1)).executeUpdate();
        Mockito.verify(connectionMock, Mockito.times(1)).commit();
    }

    @Test
    public void executeQueryStatement() throws NoSuchFieldException, IllegalAccessException, SQLException {
        assertThrows(IllegalStateException.class, () -> database.executeQueryStatement(null));
        Field connection = Database.class.getDeclaredField("connection");
        connection.setAccessible(true);
        Connection connectionMock = Mockito.mock(Connection.class);
        connection.set(database, connectionMock);
        PreparedStatement preparedStatementMock = Mockito.mock(PreparedStatement.class);
        database.executeQueryStatement(preparedStatementMock);
        Mockito.verify(preparedStatementMock, Mockito.times(1)).executeQuery();
        Mockito.verify(connectionMock, Mockito.times(1)).commit();
    }

    @Test
    public void commit() throws NoSuchFieldException, IllegalAccessException, SQLException {
        assertThrows(IllegalStateException.class, () -> database.commit());
        Field connection = Database.class.getDeclaredField("connection");
        connection.setAccessible(true);
        Connection connectionMock = Mockito.mock(Connection.class);
        connection.set(database, connectionMock);
        database.commit();
        Mockito.verify(connectionMock, Mockito.times(1)).commit();
    }

    @Test
    public void rollback() throws NoSuchFieldException, IllegalAccessException, SQLException {
        assertThrows(IllegalStateException.class, () -> database.rollback());
        Field connection = Database.class.getDeclaredField("connection");
        connection.setAccessible(true);
        Connection connectionMock = Mockito.mock(Connection.class);
        connection.set(database, connectionMock);
        database.rollback();
        Mockito.verify(connectionMock, Mockito.times(1)).rollback();
    }
}