package database;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ApplicationDatabaseTest {

    @Test
    public void initialize() throws NoSuchFieldException, IllegalAccessException, SQLException {
        Field database = ApplicationDatabase.class.getDeclaredField("instance");
        database.setAccessible(true);
        Database instance = Mockito.mock(Database.class);
        database.set(null, instance);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(instance.getPreparedStatement(Mockito.anyString())).thenReturn(preparedStatement);
        ApplicationDatabase.initialize();
        Mockito.verify(instance, Mockito.times(8)).getPreparedStatement(Mockito.anyString());
        Mockito.verify(preparedStatement, Mockito.times(8)).execute();
        Mockito.verify(instance, Mockito.times(1)).commit();
    }


}