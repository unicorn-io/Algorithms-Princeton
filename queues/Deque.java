import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int numberOfitems = 0;

    private class Node {
        private Node next;
        private Node prev;
        private Item item;
    }

    public Deque() {
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return numberOfitems == 0;
    }

    public int size() {
        return numberOfitems;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Bad Input!");
        }
        Node oldFirst = first;
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

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Bad Input!");
        }
        Node oldLast = last;
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
