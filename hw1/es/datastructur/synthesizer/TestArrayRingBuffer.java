package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Joshua Su
 */

public class TestArrayRingBuffer {
    @Test
    public void basicTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        assertEquals(4, arb.capacity());
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertTrue(arb.isFull());

        Integer actual = arb.peek();
        assertEquals((Integer) 1, actual);

        actual = arb.dequeue();
        assertEquals((Integer) 1, actual);
        assertEquals(3, arb.fillCount());
    }
}
