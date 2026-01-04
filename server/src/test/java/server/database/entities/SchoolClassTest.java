package server.database.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SchoolClassTest {

    private SchoolClass schoolClass;

    @Before
    public void setUp() {
        schoolClass = new SchoolClass();
    }

    @Test
    public void setNumber() {
        schoolClass.setNumber(1);
        assertEquals(1, schoolClass.getNumber().intValue());
        assertThrows(IllegalArgumentException.class, () -> schoolClass.setNumber(-1));
    }

    @Test
    public void setLetter() {
        schoolClass.setLetter('A');
        assertEquals('A', schoolClass.getLetter().charValue());
        assertThrows(IllegalArgumentException.class, () -> schoolClass.setLetter('a'));
    }
}