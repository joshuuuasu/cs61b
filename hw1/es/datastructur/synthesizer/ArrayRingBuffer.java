package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {

    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;


    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */

    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        fillCount += 1;
        last += 1;
        if (last == capacity()) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T result = rb[first];
        fillCount -= 1;
        first += 1;
        if (first == capacity()) {
            first = 0;
        }
        return result;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {

        private int pos;

        MyIterator() {
            pos = first;
        }

        @Override
        public boolean hasNext() {
            if (isEmpty()) {
                return false;
            } else if (pos == -1) {
                return false;
            } else if (first < last) {
                return pos >= first && pos < last;
            } else {
                return pos >= first || pos < last;
            }
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new RuntimeException("No next!");
            }
            T result = rb[pos];
            pos += 1;
            if (pos == capacity()) {
                pos = 0;
            }
            if (pos == last) {
                pos = -1;
            }
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() == getClass()) {
            if (((ArrayRingBuffer) o).fillCount() == fillCount()) {
                if (((ArrayRingBuffer) o).capacity() == capacity()) {
                    if (((ArrayRingBuffer) o).first == first
                        && ((ArrayRingBuffer) o).last == last) {
                        for (int i = 0; i < capacity(); i++) {
                            if (((ArrayRingBuffer) o).rb[i] != rb[i]) {
                                return false;
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
