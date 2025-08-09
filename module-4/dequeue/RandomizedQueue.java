import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count;
    private Object[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        final int startingCap = 0;
        items = new Object[startingCap];
        count = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        tryGrow();

        items[count++] =  objFromItem(item);
    }

    // remove and return a random item
    public Item dequeue() {
        if (count == 0) {
            throw new NoSuchElementException();
        }

        int offset = StdRandom.uniformInt(0, count);
        Item item = itemFromObj(items[offset]);

        items[offset] = items[count-1];
        if (count > 1) {
            items[count - 1] = null;
        }
        count--;

        tryShrink();

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (count == 0) {
            throw new NoSuchElementException();
        }

        int offset = StdRandom.uniformInt(0, count);
        Item item = itemFromObj(items[offset]);
        
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iter(this);
    }


    private RandomizedQueue (RandomizedQueue<Item> other) {
        this.count = other.count;
        this.items = new Object[other.count];
        for (int i = 0; i < other.count; i++) {
            this.items[i] = other.items[i];
        }
    }

    private class Iter implements Iterator<Item> {
        private Object[] itemsCopy;
        private int pos;
        
        private Iter(RandomizedQueue<Item> queueRef) {
            RandomizedQueue<Item> queue = new RandomizedQueue<Item>(queueRef);
            int queueSize = queue.size();
            itemsCopy = new Object[queueSize];
            int itemPos = 0;

            for (int i = 0; i < queueSize; i++) {
                itemsCopy[itemPos++] = objFromItem(queue.dequeue());
            }

            pos = 0;
        }
         
        @Override
        public boolean hasNext() {
            return pos < itemsCopy.length;
        }

        @Override
        public Item next() {
            if (pos >= itemsCopy.length) {
                throw new NoSuchElementException();
            }

            return itemFromObj(itemsCopy[pos++]);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }  

    private void tryGrow() {
        if (count >= items.length) {
            int newCap = items.length * 2;
            if (newCap == 0) {
                newCap = 1;
            }
            Object[] newItems = new Object[newCap];
            for (int i = 0; i < count; i++) {
                newItems[i] = items[i];
            }

            items = newItems;
        }
    }
    
    private void tryShrink() {
        if (count <= items.length / 4) {
            int newCap = items.length / 2; 
            Object[] newItems = new Object[newCap];
            for (int i = 0; i < count; i++) {
                newItems[i] = items[i];
            }

            items = newItems;
        }
    }

    private Item itemFromObj(Object obj) {
        return (Item) obj;
    }

    private Object objFromItem(Item item) {
        return (Object) item;
    }

    // unit testing (required)
    public static void main(String[] args) {
        
    }
}