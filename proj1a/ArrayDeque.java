import java.util.Arrays;

public class ArrayDeque<T> {
    /**  This is to build implementations of a Deque using arrays. */
    public T[] items;
    public int nextFirst;
    public int nextLast;
    public int size;

    public ArrayDeque(T item) {
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 5;
        items[4] = item;
        size = 1;
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        items = (T[]) new Object[other.items.length];
        System.arraycopy(other.items, 0, items, 0, other.items.length);
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        size = other.size;
    }

    public void resize(int capacity) {
        /** This resize method is only for INCREASE items.length. */
        T[] temp = (T[]) new Object[capacity];
        if (nextFirst == items.length - 1) {
            System.arraycopy(items, 0, temp, 4, items.length - 1);
        } else if (nextFirst == 0) {
            System.arraycopy(items, 1, temp, 4, items.length - 1);
        } else {
            System.arraycopy(items, nextFirst + 1, temp, 4, items.length - nextFirst - 1);
            System.arraycopy(items, 0, temp, 3 + items.length - nextFirst, nextFirst);
        }
        items = temp;
        nextFirst = 3;
        nextLast = 4 + size;
    }

    public void addFirst(T item) {
        if (nextFirst == nextLast) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst -= 1;
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
        size += 1;
    }

    public void addLast(T item) {
        if (nextFirst == nextLast) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast += 1;
        if (nextLast >= items.length) {
            nextLast = 0;
        }
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void printDeque(int start, int end) {
        /** print items from (including) start and end, assuming end >= start. */
        for (int i = start; i < end; i++) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        System.out.print(items[end]);
        System.out.println(' ');
    }

    public void printDeque() {
        if (nextFirst == items.length - 1) {
            printDeque(0, items.length - 2);
        } else if (nextLast == 0) {
            printDeque(1, items.length - 1);
        } else if (nextFirst > nextLast) {
            printDeque(nextFirst + 1, items.length - 1);
            printDeque(0, nextLast - 1);
        } else {
            printDeque(nextFirst + 1, nextLast - 1);
        }
    }

    public double usageFactor() {
        return ((double) size) / items.length;
    }

    public void halve() {
        T[] temp = (T[]) new Object[items.length / 2];
        if (nextFirst > nextLast) {
            System.arraycopy(items, nextFirst + 1, temp, 4, items.length - 1 - nextFirst);
            System.arraycopy(items, 0, temp, 3 + items.length -nextFirst, nextLast);
        } else {
            System.arraycopy(items, nextFirst + 1, temp, 4, size);
        }
        items = temp;
        nextFirst = 3;
        nextLast = 4 + size;
    }

    public T removeFirst() {
        T temp;
        if (nextFirst == items.length - 1) {
            temp = items[0];
            nextFirst = 0;
        } else {
            temp = items[nextFirst + 1];
            nextFirst += 1;
        }
        size -= 1;
        while (items.length >= 16 && usageFactor() < 0.25) {
            halve();
        }
        return temp;
    }

    public T removeLast() {
        T temp;
        if (nextLast == 0) {
            temp = items[items.length - 1];
            nextLast = items.length - 1;
        } else {
            temp = items[nextLast - 1];
            nextLast -= 1;
        }
        size -= 1;
        while (items.length >= 16 && usageFactor() < 0.25) {
            halve();
        }
        return temp;
    }

    public T get(int index) {
        if (nextFirst < nextLast && index < size) {
            return items[nextFirst + 1 + index];
        } else if (nextFirst == items.length - 1 && index < size) {
            return items[index];
        } else if (nextFirst > nextLast && index < items.length - 1 - nextFirst) {
            return items[nextFirst + 1 + index];
        } else if (nextFirst > nextLast && index >= items.length - 1 - nextFirst && index < size) {
            return items[index - items.length + 1 + nextFirst];
        } else if (nextLast == 0 && index < size) {
            return items[nextFirst + 1 + index];
        }
        return null;
    }
}
