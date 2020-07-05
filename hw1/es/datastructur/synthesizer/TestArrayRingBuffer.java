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

    @Test
    public void iteratorTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        int sum = 0;

        for (int i: arb) {
            sum += i;
        }

        assertEquals(10, sum);
    }

    @Test
    public void equalsTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(4);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);

        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<>(4);
        arb2.enqueue(1);
        arb2.enqueue(2);
        arb2.enqueue(3);
        arb2.enqueue(4);

        ArrayRingBuffer<Integer> arb3 = new ArrayRingBuffer<>(4);
        arb3.enqueue(1);
        arb3.enqueue(2);
        arb3.enqueue(3);

        assertTrue(arb.equals(arb2));
        assertFalse(arb.equals(arb3));
    }
}
