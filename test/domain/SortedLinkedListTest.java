package domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortedLinkedListTest {

    @Test
    void givenEmptyList_whenGetSize_thenZero() {
        assertEquals(0, new SortedLinkedList<String>().size());
    }

    @Test
    void giveEmpty_whenAddNull_thenThrowsException() {
        var list = new SortedLinkedList<String>();
        assertThrows(IllegalArgumentException.class, () -> list.add(null));
    }

    @Test
    void giveEmpty_whenGet_thenThrowsException() {
        var list = new SortedLinkedList<String>();
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    void givenEmptyList_whenAdd_thenOneItem() {
        var list = new SortedLinkedList<String>();
        list.add("foo");
        assertEquals(1, list.size());
    }

    @Test
    void givenList_whenAddAtTheBeginningOfTheList_thenCorrectOrder() {
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
    void givenEmptyList_whenAddAll_thenCorrectItem() {
        var list = new SortedLinkedList<String>();
        list.addAll(List.of("z", "a", "b", "c", "x", "a"));
        assertEquals("a", list.get(0));
        assertEquals("a", list.get(1));
        assertEquals("b", list.get(2));
        assertEquals("c", list.get(3));
        assertEquals("x", list.get(4));
        assertEquals("z", list.get(5));
    }

    @Test
    void giveList_whenContains_thenCorrect() {
        var list = new SortedLinkedList<>(List.of("z", "a", "b", "c", "x", "a"));
        assertTrue(list.contains("a"));
        assertFalse(list.contains("d"));
    }

    @Test
    void giveList_whenRemove_thenCorrect() {
        var list = new SortedLinkedList<>(List.of("a", "b", "c"));
        assertTrue(list.remove("b"));
        assertTrue(list.remove("c"));
        assertTrue(list.remove("a"));
        assertFalse(list.contains("a"));
        assertEquals(0, list.size());

    }

    @Test
    void giveListOfIntegers_whenContains_thenCorrect() {
        var list = new SortedLinkedList<Integer>();
        list.add(10);
        assertTrue(list.contains(10));
    }
}