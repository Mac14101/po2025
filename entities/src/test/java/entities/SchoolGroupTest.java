package entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SchoolGroupTest {

    private SchoolGroup schoolGroup;

    @Before
    public void setUp() {
        schoolGroup = new SchoolGroup();
    }

    @Test
    public void setNumber() {
        schoolGroup.setNumber(1);
        assertEquals(1, schoolGroup.getNumber().intValue());
        assertThrows(IllegalArgumentException.class, () -> schoolGroup.setNumber(-1));
    }

    @Test
    public void setLetter() {
        schoolGroup.setLetter('A');
        assertEquals('A', schoolGroup.getLetter().charValue());
        assertThrows(IllegalArgumentException.class, () -> schoolGroup.setLetter('a'));
    }
}