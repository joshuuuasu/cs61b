import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {

    @Test
    public void isSameNumberTest() {
        assertTrue(Flik.isSameNumber(128,128));
        assertFalse(Flik.isSameNumber(1,0));
    }
}
