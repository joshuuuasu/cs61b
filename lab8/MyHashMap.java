import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {

    private ArrayList<ArrayList<Entry>> buckets;
    private int size;       // number of entries
    private int numBuckets;
    private double loadFactor;
    private Set<K> keySet;

    private static class Entry<K, V> {
        K key;
        V val;

        public Entry (K k, V v) {
            this.key = k;
            this.val = v;
        }
    }

    public MyHashMap() {
        this(16);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor){
        if (initialSize <= 0 || loadFactor <= 0.0 || loadFactor > 1.0) {
            throw new IllegalArgumentException();
        }
        this.buckets = new ArrayList<>();
        this.size = 0;
        this.numBuckets = initialSize;
        this.loadFactor = loadFactor;
        this.keySet = new HashSet<>();

        for (int i = 0; i < numBuckets; i++) {
            buckets.add(new ArrayList<Entry>());
        }
    }

    @Override
    public void clear() {
        buckets = new ArrayList<>();
        size = 0;
        keySet = new HashSet<>();
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        if (this == null) {
            return false;
        }
        return keySet.contains(key);
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % numBuckets;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        if (this == null || this.size == 0) {
            return null;
        }
        int bucketInd = hash(key);
        for (Entry e: buckets.get(bucketInd)) {
            if (e.key.equals(key)) {
                return (V) e.val;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    private double calcLoadFactor() {
        return ((double) size() / buckets.size());
    }

    private void resize() {
        ArrayList<ArrayList<Entry>> newBuckets = new ArrayList<>();
        numBuckets *= 2;
        for (int i = 0; i < numBuckets; i++) {
            newBuckets.add(new ArrayList<Entry>());
        }
        for (int i = 0; i < buckets.size(); i++) {
            for (Entry e: buckets.get(i)) {
                int ind = hash((K) e.key);
                newBuckets.get(ind).add(e);
            }
        }
        buckets = newBuckets;
    }

    @Override
    public void put(K key, V val) {
        int bucketInd = hash(key);
        for (Entry e: buckets.get(bucketInd)) {
            if (e.key == key) {
                e.val = val;
                return;
            }
        }
        buckets.get(bucketInd).add(new Entry(key, val));
        size += 1;
        keySet.add(key);
        if (calcLoadFactor() > loadFactor) {
            resize();
        }
    }

    @Override
    public Set keySet() {
//        return keySet;
        Set<K> keys = new HashSet<>();
        for (K k: this) {
            keys.add(k);
        }
        return keys;
    }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        V res = get(key);
        int ind = hash(key);
        for (Entry e: buckets.get(ind)) {
            if (e.key.equals(key)) {
                buckets.get(ind).remove(e);
                size -= 1;
                keySet.remove(key);
                break;
            }
        }
        return res;
    }

    @Override
    public V remove(K key, V val) {
        if (!containsKey(key)) {
            return null;
        }
        V res = get(key);
        if (res == val) {
            return remove(key);
        } else {
            return null;
        }
    }

    @Override
    public Iterator iterator() {
        return new myHashMapIterator();
//        return keySet.iterator();
    }

    private class myHashMapIterator implements Iterator<K> {
        private LinkedList<Entry<K, V>> list;
        private Entry<K, V> curr;

        public myHashMapIterator() {
            for (int i = 0; i < numBuckets; i++) {
                for (Entry e: buckets.get(i)) {
                    list.add(e);
                }
            }
            curr = list.peek();
        }

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public K next() {
            K res = curr.key;
            list.pop();
            curr = list.peek();
            return res;
        }
    }
}
