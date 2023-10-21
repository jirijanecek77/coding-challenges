package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Sorted Linked list implementation permits all elements (except {@code null}).
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 *
 * @param <T> the type of elements held in this collection
 * @author Jiri Janecek
 */

public class SortedLinkedList<T extends Comparable<T>> {

    private int size = 0;
    /**
     * Pointer to first node.
     */
    private Node first;

    private class Node {

        T item;
        Node next;

        Node(Node next, T item) {
            this.item = item;
            this.next = next;
        }

    }

    /**
     * Constructs an empty list.
     */
    public SortedLinkedList() {
    }


    /**
     * Constructs a list containing the elements of the specified collection.
     * Sort collection with effective sorting algorithm and build list from end to the beginning.
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public SortedLinkedList(Collection<? extends T> c) {
        this();
        List<T> sortedList = new ArrayList<>(c);
        sortedList.sort(Collections.reverseOrder());

        // Iterate over the sorted elements
        for (T item : sortedList) {
            first = new Node(first, item);
        }
        size = sortedList.size();
    }

    /**
     * Appends the specified element to the correct position of this list to keep it sorted.
     * *
     *
     * @param e element to be appended to this list
     */
    public void add(T e) {
        checkNonNull(e);

        if (first == null) {
            // handle empty list
            first = new Node(null, e);
            size = 1;
            return;
        }

        Node current = first;
        Node last = null;
        while (current != null) {
            if (current.item.compareTo(e) >= 0) {
                if (last == null) {
                    first = new Node(first, e);
                } else {
                    last.next = new Node(current, e);
                }
                size++;
                return;
            }
            last = current;
            current = current.next;
        }

        // add to the end of the list
        last.next = new Node(null, e);
        size++;
    }

    /**
     * Returns item on position of index, if index exceeds size of list, returns IndexOutOfBoundsException
     *
     * @param index element to be appended to this list
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range");
        }

        var current = first;
        while (index-- > 0) {
            current = current.next;
        }
        return current.item;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * {@code i} such that
     * {@code Objects.equals(o, get(i))}
     * (if such an element exists).  Returns {@code true} if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param item element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    public boolean remove(T item) {
        checkNonNull(item);
        Node current = first;
        Node last = null;
        while (current != null) {
            if (current.item.equals(item)) {
                if (last == null) {
                    first = first.next;
                } else {
                    last.next = current.next;
                }
                size--;
                return true;
            }
            last = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Appends all the items of the collection {@code c} to correct position to keep list sorted
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public void addAll(Collection<? extends T> c) {
        c.forEach(this::add);
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this list contains the specified element.
     * More formally, returns {@code true} if and only if this list contains
     * at least one element {@code item} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param item element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     */
    public boolean contains(T item) {
        checkNonNull(item);

        Node current = first;
        while (current != null) {
            if (current.item.equals(item)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private void checkNonNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }
}
