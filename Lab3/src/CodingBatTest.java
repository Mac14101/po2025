import static org.junit.Assert.*;

public class CodingBatTest {

    @org.junit.Test
    public void sleepIn() {
        assertEquals(true,new CodingBat().sleepIn(false,false));
        assertEquals(false,new CodingBat().sleepIn(true,false));
        assertEquals(true,new CodingBat().sleepIn(false,true));
        assertEquals(true,new CodingBat().sleepIn(true,true));
    }
}