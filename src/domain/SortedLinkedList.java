package domain;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Sorted singly Linked list implementation permits all elements (except {@code null}).
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 *
 * @param <T> the type of elements held in this collection
 * @author Jiri Janecek
 */

public class SortedLinkedList<T extends Comparable<T>> implements Iterable<T> {

    private int size = 0;
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
        initializeByCollection(c);
    }

    /**
     * Returns a iterator of the elements in this list (in proper
     * sequence), starting at the begging of the list.
     *
     * @return a Iterator of the elements in this list
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new SorterdLinkedListIterator();
    }

    /**
     * Appends the specified element to the correct position of this list to keep it sorted.
     * *
     *
     * @param item element to be appended to this list
     */
    public void add(T item) {
        checkNonNull(item);

        Node newNode = new Node(null, item);
        if (first == null || first.item.compareTo(item) >= 0) {
            newNode.next = first;
            first = newNode;
        } else {
            Node current = first;
            while (current.next != null && current.next.item.compareTo(item) < 0) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
        size++;
    }

    /**
     * Returns item on position of index, if index exceeds size of list, returns IndexOutOfBoundsException
     *
     * @param index element to be appended to this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
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
     * Sort items in the collection {@code c} and merge it with the current list
     *
     * @param c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
    public void addAll(Collection<? extends T> c) {
        if (first == null) {
            initializeByCollection(c);
            return;
        }

        List<T> itemsToAdd = new ArrayList<>(c);
        Collections.sort(itemsToAdd);

        Node current = first;
        Node last = null;
        for (T itemToAdd : itemsToAdd) {

            // skip all nodes before itemToAdd
            while (current != null && current.item.compareTo(itemToAdd) <= 0) {
                last = current;
                current = current.next;
            }

            Node newNode = new Node(null, itemToAdd);
            if (current != null) {
                if (last == null) {
                    // add before the first node
                    newNode.next = current;
                    first = newNode;
                } else {
                    // add before current
                    newNode.next = current;
                    last.next = newNode;
                }
            } else {
                // add to the end of the list
                last.next = newNode;
            }
            last = newNode;
            size++;
        }
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

        for (T current : this) {
            if (current.equals(item)) {
                return true;
            }
        }
        return false;
    }

    private class SorterdLinkedListIterator implements Iterator<T> {

        private Node currentNode = first;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T item = currentNode.item;
                currentNode = currentNode.next;
                return item;
            }
            throw new NoSuchElementException();
        }

    }

    private void initializeByCollection(Collection<? extends T> c) {
        c.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .forEach(item -> {
                    first = new Node(first, item);
                    size++;
                });
    }

    private void checkNonNull(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
    }
}
