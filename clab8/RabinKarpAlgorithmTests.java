import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "ell";
        assertEquals(1, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }


    @Test
    public void basic2() {
        String input = "helloworldhellocomputerHELLOWORLDHELLOCOMPUTER";
        String pattern = "UBER";
        String pattern1 = "comp";
        assertEquals(-1, RabinKarpAlgorithm.rabinKarp(input, pattern));
        assertEquals(15, RabinKarpAlgorithm.rabinKarp(input, pattern1));
    }
}
