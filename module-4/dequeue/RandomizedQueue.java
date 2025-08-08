import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Deque<Item> queue;

    // construct an empty randomized queue
    public RandomizedQueue() {
        Deque<Item> queue = new Deque<Item>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size() {
        return queue.size();
    }

    // add the item
    public void enqueue(Item item) {
        
    }

    // remove and return a random item
    public Item dequeue() {return null;}

    // return a random item (but do not remove it)
    public Item sample() {return null;}

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return null;
    }  

    private class Iter<Item> implements Iterator {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Item next() {
            return null;
        }

    }  

    // unit testing (required)
    public static void main(String[] args) {

    }

}