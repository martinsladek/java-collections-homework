import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class HashMapTest {
    HashMap<String, String> hashMap;

    @BeforeEach
    void setUp() {
        hashMap = new HashMap<>();

        hashMap.add("two", "2");
        hashMap.add("one", "1");
        hashMap.add("three", "3");
    }

    @AfterEach
    void tearDown() {
        hashMap = null;
    }

    @Test
    void add() {
        hashMap.add("four", "4");
        Assertions.assertEquals(hashMap.get("four"), "4");
    }

    @Test
    void get() {
        Assertions.assertEquals("2", hashMap.get("two"));
        Assertions.assertEquals("1", hashMap.get("one"));
        Assertions.assertEquals("3", hashMap.get("three"));
    }

    @Test
    void remove() {
        String removed = hashMap.remove("two");

        Assertions.assertEquals("2", removed);

        Assertions.assertNull(hashMap.get("two"));
        Assertions.assertEquals("1", hashMap.get("one"));
        Assertions.assertEquals("3", hashMap.get("three"));

        hashMap.remove("one");
        hashMap.remove("three");

        Assertions.assertNull(hashMap.get("two"));
        Assertions.assertNull(hashMap.get("one"));
        Assertions.assertNull(hashMap.get("three"));
    }

    @Test
    void iterator() {
        String[] results = {"1", "2", "3"};

        Iterator<HashMapItem<String, String>> hashMapIterator = hashMap.iterator();
        Assertions.assertTrue(hashMapIterator.hasNext());

        int index = 0;

        while(hashMapIterator.hasNext()) {
            HashMapItem<String, String> hashMapItem = hashMapIterator.next();
            Assertions.assertEquals(results[index], hashMapItem.value);
            index++;
        }

        Assertions.assertEquals(3, index);
    }

    @Test
    void size() {
        Assertions.assertEquals(3, hashMap.size());

        hashMap.remove("one");
        Assertions.assertEquals(2, hashMap.size());

        hashMap.remove("three");
        Assertions.assertEquals(1, hashMap.size());

        hashMap.remove("two");
        Assertions.assertEquals(0, hashMap.size());

        hashMap.add("new-generation-1", "ng1");
        Assertions.assertEquals(1, hashMap.size());
    }
}