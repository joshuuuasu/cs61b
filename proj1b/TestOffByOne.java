import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void equalCharsTest() {
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertFalse(offByOne.equalChars('x', 'z'));
        assertFalse(offByOne.equalChars('a', 'B'));
    }
}
