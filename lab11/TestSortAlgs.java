import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestSortAlgs {

    private Queue<Integer> createQueue() {
        Queue<Integer> q = new Queue<>();
        for (int i = 0; i < 50; i++) {
            int x = StdRandom.uniform(100);
            q.enqueue(x);
        }
        return q;
    }

    @Test
    public void testQuickSort() {
        Queue<Integer> q = createQueue();
        q = QuickSort.quickSort(q);
        assertTrue(isSorted(q));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> q = createQueue();
        q = MergeSort.mergeSort(q);
        assertTrue(isSorted(q));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
