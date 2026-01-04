package database;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.Assert.assertSame;


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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void close() throws SQLException {
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
    public void execute() {
        try (MockedStatic<DriverManager> driverMock = Mockito.mockStatic(DriverManager.class)) {
            Connection connectionMock = Mockito.mock(Connection.class);
            Statement statementMock = Mockito.mock(Statement.class);
            ResultSet resultSetMock = Mockito.mock(ResultSet.class);
            driverMock.when(() -> DriverManager.getConnection("jdbc:sqlite:test")).thenReturn(connectionMock);
            Mockito.when(connectionMock.createStatement()).thenReturn(statementMock);
            Mockito.when(statementMock.executeQuery(Mockito.anyString())).thenReturn(resultSetMock);
            database.connect("test");
            assertSame(resultSetMock, database.execute("query"));
            Mockito.verify(connectionMock, Mockito.times(1)).createStatement();
            Mockito.verify(statementMock, Mockito.times(1)).executeQuery("query");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}