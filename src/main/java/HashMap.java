import java.util.Iterator;

public class HashMap<Key, Value> implements Iterable<HashMapItem<Key, Value>> {
    public static int DEFAULT_INIT_SIZE = 10;
    public static double DEFAULT_SIZE_MULTIPLICATOR = 1.5;
    int size = 0;
    double sizeMultiplicator = DEFAULT_SIZE_MULTIPLICATOR;
    int totalItemsCount = 0;
    ArrayList<LinkedList<HashMapItem<Key, Value>>> buckets;

    public HashMap(int size) {
        this.size = size;
        buckets = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            LinkedList<HashMapItem<Key, Value>> bucket = new LinkedList<>();
            buckets.add(bucket);
        }
    }

    public HashMap() {
        this(DEFAULT_INIT_SIZE);
    }

    public void add(Key key, Value value) {
        if (key == null) {
            throw new NullPointerException("Cannot add null key");
        }

        getBucket(key).add(new HashMapItem(key, value));
        totalItemsCount++;
    }

    public Value get(Key key) {
        return getValue(key, false);
    }

    public Value remove(Key key) {
        return getValue(key, true);
    }

    protected int getBucketId(Key key) {
        return key.hashCode() % size;
    }

    protected LinkedList<HashMapItem<Key, Value>> getBucket(Key key) {
        int position = getBucketId(key);
        LinkedList<HashMapItem<Key, Value>> foundBucket = buckets.get(position);

//        if (foundBucket == null) {
//            foundBucket = new LinkedList<>();
//            buckets.set(position, foundBucket);
//        }

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
                    totalItemsCount--;
                    it.remove();
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

    void resize() {
        resize((int) (size * sizeMultiplicator));
    }

    void resize(int newSize) {
        HashMap other = new HashMap<Key, Value>(newSize);

        for (LinkedList<HashMapItem<Key, Value>> bucket : buckets) {
            for (HashMapItem<Key, Value> hashMapItem : bucket) {
                other.add(hashMapItem.key, hashMapItem.value);
            }
        }

        buckets = other.buckets;
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
//    ArrayList<LinkedList<HashMapItem<Key, Value>>> buckets;
    Iterator<LinkedList<HashMapItem<Key, Value>>> bucketsIterator;
    Iterator<HashMapItem<Key, Value>> linkedListIterator = null;
    int iterationPosition = 0;

    HashMapIterator(HashMap<Key, Value> hashMap) {
        this.hashMap = hashMap;
//        this.buckets = hashMap.buckets;
        bucketsIterator = hashMap.buckets.iterator();

        if(bucketsIterator.hasNext()) {
            linkedListIterator = bucketsIterator.next().iterator();
        }
    }

    @Override
    public boolean hasNext() {
        if (iterationPosition < hashMap.size) {
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
            linkedListIterator.remove();
        }
    }
}
