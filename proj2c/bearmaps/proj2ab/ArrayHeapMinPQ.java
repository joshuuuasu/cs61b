package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<Entry> entries;
    private HashMap<T, Integer> items;          //map to item's location in the array.

    private class Entry {
        private T item;
        private double priority;

        Entry(T t, Double d) {
            item = t;
            priority = d;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double d) {
            priority = d;
        }
    }

    public ArrayHeapMinPQ() {
        entries = new ArrayList<>();
        items = new HashMap<>();
    }

    private int parent(int k) {
        return k == 0 ? 0 : (k - 1) / 2;
    }

    private int leftChild(int k) {
        return 2 * k + 1;
    }

    private int rightChild(int k) {
        return 2 * k + 2;
    }

    private boolean smaller(int i, int j) {
        return entries.get(i).getPriority() < entries.get(j).getPriority();
    }

    private void swap(int i, int j) {
        Entry temp = entries.get(i);
        entries.set(i, entries.get(j));
        entries.set(j, temp);
        items.replace(entries.get(i).getItem(), i);
        items.replace(entries.get(j).getItem(), j);
    }

    private void swim(int k) {
        if (k > 0 && smaller(k, parent(k))) {
            swap(k, parent(k));
            swim(parent(k));
        }
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        entries.add(new Entry(item, priority));
        items.put(item, size() - 1);
        swim(size() - 1);
    }

    @Override
    public boolean contains(T item) {
        return items.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return entries.get(0).item;
    }

    private void sink(int k) {
        int smallest = k;
        if (size() > leftChild(k) && smaller(leftChild(k), smallest)) {
            smallest = leftChild(k);
        }
        if (size() > rightChild(k) && smaller(rightChild(k), smallest)) {
            smallest = rightChild(k);
        }
        if (smallest != k) {
            swap(k, smallest);
            sink(smallest);
        }
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T res = entries.get(0).item;
        swap(0, size() - 1);
        entries.remove(size() - 1);
        items.remove(res);
        sink(0);
        return res;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (size() == 0 || !contains(item)) {
            throw new NoSuchElementException();
        }
        int ind = items.get(item);
        double oldPriority = entries.get(ind).getPriority();
        entries.get(ind).setPriority(priority);
        if (priority < oldPriority) {
            swim(ind);
        } else {
            sink(ind);
        }
    }
}
