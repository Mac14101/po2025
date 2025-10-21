import static org.junit.Assert.*;

public class CodingBatTest {

    @org.junit.Test
    public void sleepIn() {
        assertEquals(true,new CodingBat().sleepIn(false,false));
        assertEquals(false,new CodingBat().sleepIn(true,false));
        assertEquals(true,new CodingBat().sleepIn(false,true));
        assertEquals(true,new CodingBat().sleepIn(true,true));
    }
    @org.junit.Test
    public void notString() {
        assertEquals("not candy",new CodingBat().notString("candy"));
        assertEquals("not x",new CodingBat().notString("x"));
        assertEquals("not bad",new CodingBat().notString("not bad"));
        assertEquals("not bad",new CodingBat().notString("bad"));
        assertEquals("not",new CodingBat().notString("not"));
        assertEquals("not is not",new CodingBat().notString("is not"));
        assertEquals("not no",new CodingBat().notString("no"));
    }
    @org.junit.Test
    public void shiftLeft() {
        assertArrayEquals(new int[]{2, 5, 3, 6},new CodingBat().shiftLeft(new int[]{6, 2, 5, 3}));
        assertArrayEquals(new int[]{2, 1},new CodingBat().shiftLeft(new int[]{1, 2}));
        assertArrayEquals(new int[]{1},new CodingBat().shiftLeft(new int[]{1}));
        assertArrayEquals(new int[]{},new CodingBat().shiftLeft(new int[]{}));
        assertArrayEquals(new int[]{1, 2, 2, 4, 1},new CodingBat().shiftLeft(new int[]{1, 1, 2, 2, 4}));
        assertArrayEquals(new int[]{1, 1, 1},new CodingBat().shiftLeft(new int[]{1, 1, 1}));
        assertArrayEquals(new int[]{2, 3, 1},new CodingBat().shiftLeft(new int[]{1, 2, 3}));
    }
}