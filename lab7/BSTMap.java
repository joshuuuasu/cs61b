//@source: https://algs4.cs.princeton.edu/32bst/BST.java.html

import javax.swing.text.html.HTMLDocument;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;           // Number of nodes in subtree, containing the node itself.

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BSTMap(K key, V val) {
        root = new Node(key, val, 1);
    }

    public BSTMap() {
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    private V get(K key, Node n) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            return n.val;
        } else if (cmp < 0) {
            return get(key, n.left);
        } else {
            return get(key, n.right);
        }
    }

    @Override
    public V get(K key) {
        return get(key, root);
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if (n == null) {
            return 0;
        }
        return n.size;
    }


    private Node put(K key, V val, Node n) {
        if (n == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(n.key);
        if (cmp == 0) {
            n.val = val;
        } else if (cmp < 0) {
            n.left = put(key, val, n.left);
        } else {
            n.right = put(key, val, n.right);
        }
        n.size = 1 + size(n.left) + size(n.right); // Cannot use n.left.size, since n.left could be null.
        return n;
    }

    @Override
    public void put(K key, V val) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (val == null) {
            remove(key);
            return;
        }
        root = put(key, val, root);
    }

    @Override
    public Set<K> keySet() {
        Set<K> BSTSet = new HashSet<>();
        for (int i = 0; i < size(root); i++) {
            BSTSet.add(rank(i).key);
        }
        return BSTSet;
    }

    private Node remove(K key, Node n) {
        if (key == null) {
            return n;
        }
        if (n == null) {
            return null;
        }

        int cmp = n.key.compareTo(key);
        if (cmp == 0) {
            if (n.left == null) {
                return n.right;
            } else if (n.right == null) {
                return n.left;
            }
            Node temp = n;
            n = min(temp.right);
            n.right = deleteMin(temp.right);
            n.left = temp.left;
        } else if (cmp < 0) {
            n.right = remove(key, n.right);
        } else {
            n.left = remove(key, n.left);
        }
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    private Node min(Node n) {
        if (n.left == null) {
            return n;
        }
        return min(n.left);
    }

    /* Return the tree which has the node with min key removed. */
    private Node deleteMin(Node n) {
        if (n.left == null) {
            return n.right;
        }
        n.left = deleteMin(n.left);
        n.size = size(n.left) + size(n.right) + 1;
        return n;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        V toRemove = get(key);
        root = remove(key, root);
        return toRemove;
    }

    @Override
    public V remove(K key, V val) {
        if (!containsKey(key)) {
            return null;
        }
        if (get(key).equals(val)) {
            return remove(key);
        }
        return null;
    }

    @Override
    public Iterator iterator() {
        return new BSTIterator(root);
    }

    private class BSTIterator implements Iterator<K> {
        private Stack<Node> stack = new Stack<>();

        public BSTIterator(Node n) {
            while (n != null) {
                // Push root node and all left nodes to the stack.
                stack.push(n);
                n = n.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Node curr = stack.pop();

            if (curr.right != null) {
                Node temp = curr.right;
                while (temp != null) {
                    stack.push(temp);
                    temp = temp.left;
                }
            }
            return curr.key;
        }
    }

    private Node rank(int r, Node n) {
        /** Return the Node with rank r on the subtree of Node n. Rank r starts from 0.*/
        if (n == null) {
            return null;
        }
        int t = size(n.left);
        if (r == t) {
            return n;
        } else if (r < t) {
            return rank(r, n.left);
        } else {
            return rank(r - t - 1, n.right);
        }
    }

    private Node rank(int r) {
        if (r < 0 || r >= size(root)) {
            throw new IllegalArgumentException();
        }
        return rank(r, root);
    }

    public void printInOrder() {
        for (int i = 0; i < size(root); i++) {
            System.out.println(rank(i).key + " " + rank(i).val);
        }
    }
}
