import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class LinkedListTest {
    LinkedList<String> linkedList = new LinkedList<>();

    @BeforeEach
    void setUp() {
        linkedList.add("one");
        linkedList.add("two");
        linkedList.add("three");
    }

    @AfterEach
    void tearDown() {
        linkedList = null;
    }

    @Test
    void add() {
        linkedList.add("four");

        Assertions.assertEquals("four", linkedList.get(3), "'four' expected at fourth position (position number [3])");
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> linkedList.get(4));
    }

    @Test
    void testAdd() {
        linkedList.add(1, "dot");

        Assertions.assertEquals(4, linkedList.size(), "4 elements expected after adding 1 to array of 3");
        Assertions.assertEquals("one", linkedList.get(0));
        Assertions.assertEquals("dot", linkedList.get(1));
        Assertions.assertEquals("two", linkedList.get(2));
        Assertions.assertEquals("three", linkedList.get(3));
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> linkedList.get(4));
    }

    @Test
    void get() {
        Assertions.assertEquals("one", linkedList.get(0));
        Assertions.assertEquals("two", linkedList.get(1));
        Assertions.assertEquals("three", linkedList.get(2));
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> linkedList.get(3));
    }

    @Test
    void checkRange() {
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> linkedList.checkRange(-1));
        Assertions.assertDoesNotThrow(() -> linkedList.checkRange(0));
        Assertions.assertDoesNotThrow(() -> linkedList.checkRange(1));
        Assertions.assertDoesNotThrow(() -> linkedList.checkRange(2));
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> linkedList.checkRange(3));
    }

    @Test
    void remove() {
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> linkedList.remove(3));
        Assertions.assertEquals("one", linkedList.remove(0));
        Assertions.assertEquals("three", linkedList.remove(1));
        Assertions.assertEquals("two", linkedList.remove(0));
        Assertions.assertThrows(IndexOutOfRangeException.class, () -> linkedList.remove(0));
    }

    @Test
    void iterator() {
        String[] results = {"one", "two", "three"};

        Iterator<String> linkedListIterator = linkedList.iterator();
        Assertions.assertTrue(linkedListIterator.hasNext());

        int index = 0;

        while(linkedListIterator.hasNext()) {
            String stringItem = linkedListIterator.next();
            Assertions.assertEquals(results[index], stringItem, "position [" + index + "] does not match");
            index++;
        }

        Assertions.assertEquals(3, index);
//        Assertions.assertThrows(IndexOutOfRangeException.class, () -> linkedListIterator.next());
        Assertions.assertNull(linkedListIterator.next());
    }

    @Test
    void addAll() {
        LinkedList<String> otherLinkedList = new LinkedList<>();
        otherLinkedList.add("fourth");

        linkedList.addAll(otherLinkedList);

        Assertions.assertEquals("three", linkedList.get(2));
        Assertions.assertEquals("fourth", linkedList.get(3));

        Assertions.assertEquals(1, otherLinkedList.size(), "temporary linked list of size 1 should stay untouched");
        Assertions.assertEquals(4, linkedList.size(), "4 elements after adding other linked list of size 1 to 3");
    }

    @Test
    void size() {
        Assertions.assertEquals(3, linkedList.size(), "3 elements after init");
        linkedList.add("four");
        Assertions.assertEquals(4, linkedList.size(), "4 elements after adding one to 3");
    }
}