import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head;
    private Node<Item> tail;
    int _size;

    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
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

        Node newNode = new Node<Item>(item);
        newNode.next = head;
        if (head != null) {
            head.prev = newNode;
        } else {
            tail = newNode;
        }
        head = newNode;

        _size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("object cannot be null");
        }



        Node newNode = new Node<Item>(item);

        if (head == null) {
            head = newNode; 
        }
        if (tail != null) {
            tail.next = newNode;
        } else {
            tail = newNode;
        }
        newNode.prev = tail;
        
        _size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (head == null || tail == null) {
            throw new NoSuchElementException();
        }

        Item firstData = (Item) head.value;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }

        _size -= 1;
        return firstData;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (head == null || tail == null) {
            throw new NoSuchElementException();
        }
        
        Item lastData = (Item) tail.value;
        Node newLast = tail.prev;
        if (newLast != null) {
            newLast.next = null;
        }
        tail = newLast;

        _size -= 1;
        return lastData;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new Iter<Item>(head);
    }
    
    private class Iter<Item> implements Iterator {
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

            Item value = (Item) current.value;
            current = current.next;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node<Item> {
        Node next;
        Node prev;
        Item value;

        private Node(Item value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
        private Node(Node<Item> node) {
            this.next = node.next;
            this.prev = node.prev;
            this.value = node.value;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {}

}