import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListTest {
    ArrayList<String> arrayList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        arrayList.add("one");
        arrayList.add("two");
        arrayList.add("three");
    }

    @AfterEach
    void tearDown() {
        arrayList = null;
    }

    @Test
    void get() {
        String[] results = {"one", "two", "three"};

        for (int i = 0; i < 3; i++) {
            Assertions.assertEquals(results[i], arrayList.get(i));
        }

        Assertions.assertThrows(IndexOutOfRangeException.class, () -> arrayList.get(3));
    }

    @Test
    void set() {
        arrayList.set(1, "second");

        Assertions.assertEquals( "second", arrayList.get(1));
    }

    @Test
    void add() {
        arrayList.add("four");

        Assertions.assertEquals("four", arrayList.get(3));
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> arrayList.get(4));
    }

    @Test
    void testAdd() {
        arrayList.add(1, "added two");
        Assertions.assertEquals("added two", arrayList.get(1));
        Assertions.assertEquals("two", arrayList.get(2));
        Assertions.assertEquals("three", arrayList.get(3));
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> arrayList.get(4));
    }

    @Test
    void remove() {
        arrayList.remove(1);
        Assertions.assertEquals("three", arrayList.get(1));
    }

    @Test
    void checkRange() {
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> arrayList.checkRange(-1));
        Assertions.assertDoesNotThrow(() -> arrayList.checkRange(0));
        Assertions.assertDoesNotThrow(() -> arrayList.checkRange(1));
        Assertions.assertDoesNotThrow(() -> arrayList.checkRange(2));
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> arrayList.checkRange(3));
    }

    @Test
    void size() {
        Assertions.assertEquals(3, arrayList.size());
    }

    @Test
    void iterator() {
        String[] results = {"one", "two", "three"};

        Iterator<String> arrayListIterator = arrayList.iterator();
        Assertions.assertTrue(arrayListIterator.hasNext());

        int index = 0;

        while(arrayListIterator.hasNext()) {
            String stringItem = arrayListIterator.next();
            Assertions.assertEquals(results[index], stringItem, "position [" + index + "] does not match");
            index++;
        }

        Assertions.assertEquals(3, index);
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> arrayListIterator.next());
//        Assertions.assertEquals(null, arrayListIterator.next());
    }
}