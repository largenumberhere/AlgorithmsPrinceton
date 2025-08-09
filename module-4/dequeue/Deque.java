import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int count;

    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        Node newNode = new Node(item);
        newNode.next = head;
        if (head != null) {
            head.prev = newNode;
        } else {
            tail = newNode;
        }
        head = newNode;

        count += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        Node newNode = new Node(item);

        if (head == null) {
            head = newNode; 
        }
        if (tail != null) {
            tail.next = newNode;
        } else {
            tail = newNode;
        }
        newNode.prev = tail;
        
        count += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (head == null || tail == null) {
            throw new NoSuchElementException();
        }

        Item firstData = head.value;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }

        count -= 1;
        return firstData;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (head == null || tail == null) {
            throw new NoSuchElementException();
        }
        
        Item lastData = tail.value;
        Node newLast = tail.prev;
        if (newLast != null) {
            newLast.next = null;
        }
        tail = newLast;

        count -= 1;
        return lastData;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new Iter(head);
    }
    
    private class Iter implements Iterator<Item> {
        private Node current;
        private Iter(Node node) {
            this.current = node;
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Item value = current.value;
            current = current.next;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        Node next;
        Node prev;
        Item value;

        private Node(Item value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        
    }

}