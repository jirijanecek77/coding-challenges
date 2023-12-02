package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortedLinkedListTest {

    @Test
    void givenEmptyList_whenGetSize_thenZero() {
        assertEquals(0, new SortedLinkedList<String>().size());
    }

    @Test
    void givenEmpty_whenAddNull_thenThrowsException() {
        var list = new SortedLinkedList<String>();
        assertThrows(IllegalArgumentException.class, () -> list.add(null));
    }

    @Test
    void givenEmpty_whenGet_thenThrowsException() {
        var list = new SortedLinkedList<String>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    void givenEmptyList_whenAdd_thenOneItem() {
        var list = new SortedLinkedList<String>();
        list.add("foo");
        assertEquals("foo", list.iterator().next());
        assertEquals(1, list.size());
    }

    @Test
    void givenList_whenAddAtTheBeginning_thenCorrectOrder() {
        var list = new SortedLinkedList<>(List.of("z"));
        list.add("foo");

        assertEquals("foo", list.get(0));
        assertEquals(2, list.size());
    }

    @Test
    void givenList_whenAddToMiddleOfTheList_thenCorrectOrder() {
        var list = new SortedLinkedList<>(List.of("a", "z"));
        list.add("foo");

        assertEquals("foo", list.get(1));
        assertEquals(3, list.size());
    }

    @Test
    void givenList_whenAddToEndOfTheList_thenCorrectOrder() {
        var list = new SortedLinkedList<>(List.of("a"));
        list.add("foo");

        assertEquals("foo", list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void givenEmptyList_whenAddAllAndIterate_thenCorrectResult() {
        var list = new SortedLinkedList<String>();
        list.addAll(List.of("z", "a", "b", "c", "x", "a"));

        StringBuilder result = new StringBuilder();
        for (String item : list) {
            result.append(item);
        }
        assertEquals("aabcxz", result.toString());
        assertEquals(6, list.size());
    }

    @Test
    void givenList_whenAddAllAndIterate_thenCorrectResult() {
        var list = new SortedLinkedList<>(List.of("c", "e"));
        list.addAll(List.of("d", "x", "y", "b", "a", "z"));

        StringBuilder result = new StringBuilder();
        for (String item : list) {
            result.append(item);
        }
        assertEquals("abcdexyz", result.toString());
        assertEquals(8, list.size());
    }

    @Test
    void givenList_whenAddAllSame_thenCorrectResult() {
        var list = new SortedLinkedList<>(List.of("a", "b", "c"));
        list.addAll(List.of("c", "b", "a"));

        StringBuilder result = new StringBuilder();
        for (String item : list) {
            result.append(item);
        }
        assertEquals("aabbcc", result.toString());
        assertEquals(6, list.size());
    }


    @Test
    void givenListInitializedInConstructor_whenContains_thenCorrect() {
        List<String> data = new ArrayList<>(List.of("z", "a", "b", "c", "x", "a"));
        data.add(null);
        var list = new SortedLinkedList<>(data);
        assertTrue(list.contains("a"));
        assertFalse(list.contains("d"));
        assertEquals(6, list.size());
    }

    @Test
    void givenList_whenRemove_thenCorrect() {
        var list = new SortedLinkedList<>(List.of("a", "b", "c"));
        assertTrue(list.remove("b"));
        assertTrue(list.remove("c"));
        assertTrue(list.remove("a"));
        assertFalse(list.contains("a"));
        assertEquals(0, list.size());

    }

    @Test
    void givenListOfIntegers_whenContains_thenCorrect() {
        var list = new SortedLinkedList<Integer>(List.of(12, 11, 9));
        list.add(10);
        assertTrue(list.contains(10));
        assertEquals(4, list.size());
    }
}