package server.database.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SubjectTest {

    private Subject subject;

    @Before
    public void setUp() {
        subject = new Subject();
    }

    @Test
    public void setName() {
        subject.setName("name");
        assertEquals("name", subject.getName());
        assertThrows(IllegalArgumentException.class, () -> subject.setName("."));
    }
}