import java.util.Iterator;

public class HashMap<Key, Value> implements Iterable<HashMapItem<Key, Value>> {
    public static final int DEFAULT_INIT_CAPACITY = 16;
    public static final double DEFAULT_CAPACITY_MULTIPLICATOR = 2.0;
    public static final double DEFAULT_LOAD_FACTOR = 0.75;

    int capacity = 0; // number of buckets
    double capacityMultiplicator = DEFAULT_CAPACITY_MULTIPLICATOR;
    int size = 0; // number of elements
    double loadFactor = DEFAULT_LOAD_FACTOR;
    ArrayList<LinkedList<HashMapItem<Key, Value>>> buckets;

    public HashMap(int capacity) {
        this.capacity = capacity;
        buckets = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++) {
            LinkedList<HashMapItem<Key, Value>> bucket = new LinkedList<>();
            buckets.add(bucket);
        }
    }

    public HashMap() {
        this(DEFAULT_INIT_CAPACITY);
    }

    public void add(Key key, Value value) {
        if (key == null) {
            throw new NullPointerException("Cannot add null key");
        }

        checkCapacity();

        getBucket(key).add(new HashMapItem(key, value));
        size++;
    }

    public Value get(Key key) {
        return getValue(key, false);
    }

    public Value remove(Key key) {
        return getValue(key, true);
    }

    protected int getBucketId(Key key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    protected LinkedList<HashMapItem<Key, Value>> getBucket(Key key) {
        int position = getBucketId(key);
        LinkedList<HashMapItem<Key, Value>> foundBucket = buckets.get(position);

        return foundBucket;
    }

    protected Value getValue(Key key, boolean remove) {
        if (key == null) {
            throw new NullPointerException("Cannot get or remove null key");
        }

        LinkedList<HashMapItem<Key, Value>> bucket = getBucket(key);

        if (bucket == null) {
            return null;
        }

        Iterator<HashMapItem<Key, Value>> it = bucket.iterator();

        while (it.hasNext()) {
            HashMapItem<Key, Value> hashMapItem = it.next();

            if (hashMapItem.key == key) {
                if (remove) {
                    size--;
                    ((LinkedListIterator)it).removeIterated();
//                    it.remove();
                }

                return hashMapItem.value;
            }
        }

        return null;
    }

    @Override
    public Iterator<HashMapItem<Key, Value>> iterator() {
        return new HashMapIterator(this);
    }

    int checkCapacity() {
        if (size + 1 > capacity * loadFactor) {
            changeCapacity((int) (capacity * capacityMultiplicator));
        }

        return capacity;
    }

    void changeCapacity(int capacity) {
        HashMap other = new HashMap<Key, Value>(capacity);

        for (LinkedList<HashMapItem<Key, Value>> bucket : buckets) {
            for (HashMapItem<Key, Value> hashMapItem : bucket) {
                other.add(hashMapItem.key, hashMapItem.value);
            }
        }

        buckets = other.buckets;
        this.capacity = capacity;
    }

    public int size() {
        return size;
    }

    public double getLoadFactor () {
        return loadFactor;
    }

    public void setLoadFactor (double loadFactor) {
        this.loadFactor = loadFactor;
    }

    public double getCapacityMultiplicator () {
        return capacityMultiplicator;
    }

    public void setCapacityMultiplicator(double capacityMultiplicator) {
        this.capacityMultiplicator = capacityMultiplicator;
    }
}

class HashMapItem<Key, Value> {
    Key key;
    Value value;

    HashMapItem(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
}

class HashMapIterator<Key, Value> implements Iterator <HashMapItem> {
    HashMap<Key, Value> hashMap;
    Iterator<LinkedList<HashMapItem<Key, Value>>> bucketsIterator;
    Iterator<HashMapItem<Key, Value>> linkedListIterator = null;
    int iterationPosition = 0;

    HashMapIterator(HashMap<Key, Value> hashMap) {
        this.hashMap = hashMap;
        bucketsIterator = hashMap.buckets.iterator();

        if(bucketsIterator.hasNext()) {
            linkedListIterator = bucketsIterator.next().iterator();
        }
    }

    @Override
    public boolean hasNext() {
        if (iterationPosition < hashMap.size()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public HashMapItem next() {
        if (linkedListIterator == null) {
            return null;
        }

        while (true) {
            if (linkedListIterator.hasNext()) {
                iterationPosition++;
                return linkedListIterator.next();
            } else if(bucketsIterator.hasNext()) {
                linkedListIterator = bucketsIterator.next().iterator();
            } else {
                return null;
            }
        }
    }

    @Override
    public void remove() {
        if (linkedListIterator != null) {
            ((LinkedListIterator)linkedListIterator).removeIterated();
//            linkedListIterator.remove();
        }
    }
}
