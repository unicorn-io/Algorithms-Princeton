/**
 *  @author unicorn-io
 *  Deque Implementation
 *  A deque is a sub-type of queue that allows 
 *  Insertion and Removal at both the ends
 *  The name deque is short for "Double Ended Queue"
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int numberOfitems = 0;

    /**
     * The Data type Node that has next prev and current values
     */
    private class Node {
        private Node next;
        private Node prev;
        private Item item;
    }

    /**
     * Constructor Initializes an Null DEQUE 
     */
    public Deque() {
        first = null;
        last = null;
    }

    /**
     *            Checks Whether The DEQUE is empty
     * 
     * @return    bool "True" if EMPTY else "False"
     */
    public boolean isEmpty() {
        return numberOfitems == 0;
    }

    /**
     *            Gives the Size of the current Deque
     * 
     * @return    The size of the Deque
     */
    public int size() {
        return numberOfitems;
    }

    /**
     *                This function adds a new Item
     *                In the Deque at the beginning
     * 
     * @param item    The item to be added at beginning
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Bad Input!");
        }
        Node oldFirst = first; // Acts Temporary Place Holder
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        numberOfitems++;
    }

    /**
     *                This function adds a new Item
     *                In the Deque at the end.
     * 
     * @param item    The item to be added at the end.
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Bad Input!");
        }
        Node oldLast = last; // Acts Temporary Place Holder.
        last = new Node();
        last.prev = oldLast;
        last.next = null;
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        numberOfitems++;
    }

    /**
     *            Removes The element at the Beginning
     * 
     * @return    The item that was removed from beginning  
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("cha!!");
        }
        Item item = first.item;
        if (numberOfitems == 1) {
            first = null;
            last = first;
        } else {
            first = first.next;
            first.prev = null;
        }

        numberOfitems--;
        return item;
    }

    /**
     *            Removes The element at the Last
     * 
     * @return    The item that was removed from the Last.  
     */
    public Item removeLast() {
        if (isEmpty()) { throw new NoSuchElementException("cha!!");
        }
        Item item = last.item;
        if (numberOfitems == 1) {
            first = null;
            last = first;
        } else {
            last = last.prev;
            last.next = null;
        }
        numberOfitems--;
        return item;
    }

    /**
     * This lines of Code returns an Iterator
     * That will allow us to use the for-each loop
     * And adds functionality for easy access.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException("Cha!!"); }
        public Item next() {
            if (current == null) { throw new NoSuchElementException("The deque is empty"); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
