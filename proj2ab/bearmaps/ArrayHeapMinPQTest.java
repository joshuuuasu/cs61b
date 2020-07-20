package bearmaps;

import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void basicTest() {
        ArrayHeapMinPQ<Integer> intPQ = new ArrayHeapMinPQ<>();
        assertEquals(0, intPQ.size());

        intPQ.add(1, 1.0);
        intPQ.add(2, 2.0);
        intPQ.add(3, 0.5);
        intPQ.add(4, 0.1);
        intPQ.add(5, 0.3);

        assertTrue(intPQ.contains(1));
        assertTrue(intPQ.contains(5));
        assertFalse(intPQ.contains(7));
        assertEquals((Integer) 4, intPQ.getSmallest());
        assertEquals(5, intPQ.size());
        assertEquals((Integer) 4, intPQ.removeSmallest());
        assertEquals((Integer) 5, intPQ.removeSmallest());
    }

    @Test
    public void resizeTest() {
        ArrayHeapMinPQ<Integer> intPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100; i++) {
            intPQ.add(i, 100.0 - i);
        }

        assertEquals(100, intPQ.size());
        assertEquals((Integer) 99, intPQ.getSmallest());

        for (int i = 99; i > 10; i--) {
            assertEquals((Integer) i, intPQ.removeSmallest());
        }
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<Integer> intPQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 100; i++) {
            intPQ.add((Integer) i, i);
        }

        for (int i = 0; i < 50; i++) {
            intPQ.changePriority((Integer) i, i / 10.0 + 0.0001);
            assertEquals((Integer) i, intPQ.removeSmallest());
        }
    }

    @Test
    public void randomizedTest() {
        ArrayHeapMinPQ<Integer> intPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> nIntPQ = new NaiveMinPQ<>();

        for (int i = 0; i < 200; i++) {
            double x = StdRandom.uniform();
            intPQ.add(i, x);
            nIntPQ.add(i, x);
            assertEquals(nIntPQ.getSmallest(), intPQ.getSmallest());
        }

        for (int i = 0; i < 100; i++) {
            double x = StdRandom.uniform();
            intPQ.changePriority(i, x);
            nIntPQ.changePriority(i, x);
            assertEquals(nIntPQ.getSmallest(), intPQ.getSmallest());
        }

        for (int i = 0; i < 150; i++) {
            assertEquals(nIntPQ.removeSmallest(), intPQ.removeSmallest());
        }
    }

    @Test
    public void addTimingTest() {
        //Add operation for arrayHeapMinPQ actually is worse than naiveMinPQ.
        ArrayHeapMinPQ<Integer> intPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> nIntPQ = new NaiveMinPQ<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            double x = StdRandom.uniform();
            intPQ.add(i, x);
        }
        long end = System.currentTimeMillis();
        long total = end - start;

        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            double x = StdRandom.uniform();
            nIntPQ.add(i, x);
        }
        long end1 = System.currentTimeMillis();
        long total1 = end1 - start1;

        System.out.println("My total time elapsed for adding: " + total/1000.0 +  " seconds.");
        System.out.println("Naive total time elapsed for adding: " + total1/1000.0 +  " seconds.");
    }

    @Test
    public void changePriorityTimingTest() {
        ArrayHeapMinPQ<Integer> intPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> nIntPQ = new NaiveMinPQ<>();

        for (int i = 0; i < 1000000; i++) {
            double x = StdRandom.uniform();
            intPQ.add(i, x);
            nIntPQ.add(i, x);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            int ind = StdRandom.uniform(1000000);
            double x = StdRandom.uniform();
            intPQ.changePriority(ind, x);
        }
        long end = System.currentTimeMillis();
        long total = end - start;

        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            int ind = StdRandom.uniform(1000000);
            double x = StdRandom.uniform();
            nIntPQ.changePriority(ind, x);
        }
        long end1 = System.currentTimeMillis();
        long total1 = end1 - start1;

        System.out.println("My total time elapsed for changePriority: " + total/1000.0 +  " seconds.");
        System.out.println("Naive total time elapsed for changePriority: " + total1/1000.0 +  " seconds.");
        assertTrue(total * 300 < total1);
    }

    @Test
    public void removeSmallestTimingTest() {
        ArrayHeapMinPQ<Integer> intPQ = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> nIntPQ = new NaiveMinPQ<>();

        for (int i = 0; i < 1000000; i++) {
            double x = StdRandom.uniform();
            intPQ.add(i, x);
            nIntPQ.add(i, x);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            intPQ.removeSmallest();
        }
        long end = System.currentTimeMillis();
        long total = end - start;

        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            nIntPQ.removeSmallest();
        }
        long end1 = System.currentTimeMillis();
        long total1 = end1 - start1;

        System.out.println("My total time elapsed for removeSmallest: " + total/1000.0 +  " seconds.");System.out.println("Naive total time elapsed for removeSmallest: " + total1/1000.0 +  " seconds.");
        assertTrue(total * 300 < total1);
    }
}
