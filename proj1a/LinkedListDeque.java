public class LinkedListDeque<T> {
    /**  This is to build implementations of a Deque using lists. */

    private class TNode {
        /** creating TNode with prev, item and next. */

        private TNode prev;
        private T item;
        private TNode next;

        public TNode(T someT, TNode prevTNode, TNode nextTNode) {
            prev = prevTNode;
            item = someT;
            next = nextTNode;
        }
    }

    private TNode sentinel;
    private int size;

    public LinkedListDeque(T x) {
        sentinel = new TNode(null, null, null);
        /* By convention, sentinel.item is set to null. */
        sentinel.next =  new TNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    private LinkedListDeque() {
        /** creating empty LinkedListDeque. */
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    private LinkedListDeque(LinkedListDeque other) {
        /** create a copy of other. */
        sentinel = new TNode(null, null, null);
        for (int i = 0; i < other.size(); i++) {
            this.addLast((T) other.get(i));
            /* syntax is from IntelliJ Debug correction! Woof! */
        }
    }

    public void addFirst(T item) {
        sentinel.next = new TNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        sentinel.prev = new TNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public boolean isEmpty() {
        return sentinel.prev == sentinel && sentinel.next == sentinel;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        TNode copy = sentinel;
        while (copy.next != sentinel) {
            System.out.print(copy.next.item);
            System.out.print(' ');
            copy = copy.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        T temp = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        if (size < 0) {
            size = 0;
        }
        return temp;
    }

    public T removeLast() {
        T temp = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        if (size < 0) {
            size = 0;
        }
        return temp;
    }

    public T get(int index) {
        TNode node1 = sentinel;
        if (index < this.size()) {
            for (int i = 0; i <= index; i++) {
                node1 = node1.next;
            }
        }
        return node1.item;
    }

    private T getRecursive(LinkedListDeque<T> localDeque, int index) {
        /** helper function for recursive get method. */
        if (index >= localDeque.size()) {
            return null;
        } else if (index == 0) {
            return localDeque.sentinel.next.item;
        }
        localDeque.removeFirst();
        return localDeque.getRecursive(index - 1);
    }

    public T getRecursive(int index) {
        LinkedListDeque copy = new LinkedListDeque(this);
        return (T) getRecursive(copy, index);
    }
}
