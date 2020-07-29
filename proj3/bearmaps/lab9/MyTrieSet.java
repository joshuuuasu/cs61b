package bearmaps.lab9;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;

public class MyTrieSet implements TrieSet61B {

    private static final int R = 128;
    private Node root;

    private class Node {
        private boolean isKey;
        private DataIndexedCharMap<Node> next;
        Node(boolean b, int R) {
            isKey = b;
            next = new DataIndexedCharMap<Node>(R);
        }
    }

    private class DataIndexedCharMap<V> {
        private V[] items;
        private List<Character> keys;
        DataIndexedCharMap(int R) {
            items = (V[]) new Object[R];
            keys = new ArrayList<Character>();
        }
        V get(char c) {
            return items[c];
        }
        boolean containsKey(char key) {
            return keys.contains(key);
        }
        void put(char c, V v) {
                items[c] = v;
                keys.add(c);
        }
        List<Character> keys() {
            return keys;
        }
    }

    public MyTrieSet() {
        root = new Node(false, R);
    }

    @Override
    public void clear() {
        root = new Node(false, R);
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        char[] charArray = key.toCharArray();
        Node temp = root;
        for (char c: charArray) {
            if (!temp.next.containsKey(c)) {
                return false;
            }
            temp = temp.next.get(c);
        }
        if (temp.isKey == false) {
            return false;
        }
        return true;
    }

    @Override
    public void add(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (contains(key)) {
            return;
        }
        Node temp = root;
        for (int i = 0; i < key.length(); i++) {
            if (!temp.next.containsKey(key.charAt(i))) {
                temp.next.put(key.charAt(i), new Node(false, R));
            }
            temp = temp.next.get(key.charAt(i));
        }
        temp.isKey = true;
    }

    private void addString(String s, List<String> l, Node n) {
        if (n.isKey == true) {
            l.add(s);
        }
        for (Character c: n.next.keys()) {
            addString(s + c, l, n.next.get(c));
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> list = new ArrayList<>();
        Node temp = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (!temp.next.containsKey(c)) {
                return list;
            }
            temp = temp.next.get(c);
        }
        addString(prefix, list, temp);
        return list;
    }

    @Override
    public String longestPrefixOf(String key) {
        String res = "";
        Node temp = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (temp.next.containsKey(c)) {
                res += c;
                temp = temp.next.get(c);
            } else {
                return res;
            }
        }
        return res;
    }
}
