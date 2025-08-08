import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    int _size;

    // construct an empty deque
    public Deque() {
        head = new Node<Item>();
        tail = new Node<Item>();
        _size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return _size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return _size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        Node newNode = new Node<Item>();
        newNode.value = item;
        newNode.next = head.next;     
        if (newNode.next != null) {
            newNode.next.prev = newNode;
        }
        if (tail.next == null) {
            tail.next = newNode;
        }
           
        head.next = newNode;

        _size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("object cannot be null");
        }

        Node newNode = new Node<Item>();
        newNode.value = item;
        
        newNode.next = null;
        newNode.prev = tail.next;
        tail.next = newNode;
        
        // newNode.next = tail.next;
        // tail.next = newNode;
        // newNode.prev = tail;
        // tail.next = newNode;

        _size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (head.next == null) {
            throw new NoSuchElementException();
        }

        Node first = head.next;
        head.next = head.next.next;
        if (head.next != null) {
            head.next.prev = null;
        }
        
        if (head.next == null) {
            tail.next = null;
        }

        _size -= 1;
        return (Item) first.value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (head.next == null || tail == null) {
            throw new NoSuchElementException();
        }
        
        Node last = tail.next;
        tail.next = tail.prev;

        _size -= 1;
        return (Item) last.value;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new Iter<Item>(head);
    }
    
    private class Iter<Item> implements Iterator {
        private Node current;
        private Iter(Node dummyHead) {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Item next() {
            Node node = current.next;
            current = current.next;
            return (Item) node.value;
        }
    }

    private class Node<Item> {
        Node next;
        Node prev;
        Item value;

        private Node() {}
    }

    // unit testing (required)
    public static void main(String[] args) {}

}