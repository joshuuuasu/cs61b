package bearmaps;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private Entry[] entries;
    private HashMap<T, Integer> items;          //map to item's location in the array.
    private int size;

    private static final int INITIALSIZE = 16;
    private static final double MAXLOADFACTOR = 0.75;
    private static final double MINLOADFACTOR = 0.25;

    private class Entry {
        private T item;
        private double priority;

        Entry(T t, Double d) {
            item = t;
            priority = d;
        }

        void setPriority(double d) {
            priority = d;
        }
    }

    public ArrayHeapMinPQ() {
        entries = new ArrayHeapMinPQ.Entry[INITIALSIZE];
        items = new HashMap<>();
        size = 0;
    }

    private void swim(int k) {
        while (k > 1) {
            if (entries[k / 2].priority > entries[k].priority) {
                Entry temp = entries[k / 2];
                entries[k / 2] = entries[k];
                entries[k] = temp;
                items.replace(entries[k / 2].item, k / 2);
                items.replace(entries[k].item, k);
                swim(k / 2);
            }
            break;
        }
    }

    private void resize(int i, int s) {
        Entry[] temp = new ArrayHeapMinPQ.Entry[i];
        System.arraycopy(entries, 0, temp, 0, s);
        entries = temp;
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        Entry e = new Entry(item, priority);
        size += 1;
        if (((double) size) / entries.length > MAXLOADFACTOR) {
            resize(entries.length * 2, size);
        }
        entries[size] = e;
        items.put(item, size);
        swim(size);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return entries[1].item;
    }

    private void sink(int k) {
        while (size >= 2 * k + 1) {
            if (entries[2 * k + 1].priority > entries[2 * k].priority) {
                if (entries[k].priority > entries[2 * k].priority) {
                    Entry temp = entries[k];
                    entries[k] = entries[k * 2];
                    entries[k * 2] = temp;
                    items.replace(entries[k * 2].item, k * 2);
                    items.replace(entries[k].item, k);
                    sink(k * 2);
                }
            } else {
                if (entries[k].priority > entries[2 * k + 1].priority) {
                    Entry temp = entries[k];
                    entries[k] = entries[k * 2 + 1];
                    entries[k * 2 + 1] = temp;
                    items.replace(entries[k * 2 + 1].item, k * 2 + 1);
                    items.replace(entries[k].item, k);
                    sink(k * 2 + 1);
                }
            }
            break;
        }
    }

    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T res = entries[1].item;
        entries[1] = entries[size];
        entries[size] = null;
        size -= 1;
        sink(1);
        items.remove(res);
        if (((double) size) / entries.length < MINLOADFACTOR && entries.length > INITIALSIZE) {
            resize(entries.length / 2, size + 1);
        }
        return res;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (size() == 0 || !contains(item)) {
            throw new NoSuchElementException();
        }
        int ind = items.get(item);
        double oldPriority = entries[ind].priority;
        entries[ind].setPriority(priority);
        if (priority < oldPriority) {
            swim(ind);
        } else {
            sink(ind);
        }
    }
}
