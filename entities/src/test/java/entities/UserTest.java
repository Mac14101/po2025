package entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class UserTest {

    private User user;

    @Before
    public void setUp() {
        this.user = new User();
    }

    @Test
    public void setEmail() throws Exception {
        user.setEmail("a@a.a");
        assertEquals("a@a.a", user.getEmail());
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("email"));
    }

    @Test
    public void setSurname() {
        user.setName("Name");
        assertEquals("Name", user.getName());
        assertThrows(IllegalArgumentException.class, () -> user.setName("name"));
    }

    @Test
    public void setName() {
        user.setSurname("Surname");
        assertEquals("Surname", user.getSurname());
        assertThrows(IllegalArgumentException.class, () -> user.setSurname("surname"));
    }
}