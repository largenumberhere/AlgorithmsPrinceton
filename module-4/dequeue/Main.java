

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.NullPointerException;
public class Main {
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(0);
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);

        System.out.println("Expecing 3, 2, 1, 0, 4,: ");
        System.out.print("       ");
        for (int i : deque) {
            System.out.print("" + i + ", ");
        }
        System.out.println("");
        System.out.println("Expecting 3, 2, 1, 0, 4,: ");
        System.out.print("       ");
        while(deque.size() > 0) {
            Integer a = deque.removeFirst();
            System.out.print(""+ a + ", ");
        }
        System.out.println("");

        deque = new Deque<Integer>();
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        System.out.println("Expecing 1, 2, 3,:");
        System.out.print("       ");
        for (int i : deque) {
            System.out.print("" + i + ", ");
        }
        System.out.println("");
        System.out.println("Expecting 1, 2, 3,:");
        System.out.print("       ");
        while(deque.size() > 0) {
            Integer a = deque.removeFirst();
            System.out.print(""+ a + ", ");
        }
        System.out.println("");

        deque = new Deque<Integer>();
        deque.addLast(3);
        deque.addLast(4);
        deque.removeLast();
        deque.addFirst(4);
        deque.removeFirst();
        System.out.println("Expecting 3,:");
        System.out.print("        ");
        while (deque.size() > 0) {
            Integer a = deque.removeFirst();
            System.out.println("" + a + ", ");
        }
        deque.addLast(1);
        deque.removeFirst();
        deque.addFirst(2);
        deque.removeLast();
        System.out.println("Expecting 0 size: \n        Size = " + deque.size());
        Exception exc = null;
        
        final Deque<Integer> deque2 = deque;
        if (!throwsException(NoSuchElementException.class, ()-> {deque2.removeFirst();})) {
            System.out.println("Incorrect or no exception thrown for removeFirst on empty queue");
        }

        final Deque<Integer> deque3 = deque;
        if (!throwsException(NoSuchElementException.class, ()-> {deque3.removeLast();})) {
            System.out.println("Incorrect or none exception thrown on removeLast on empty queue");
        }

        deque = new Deque<Integer>();
        deque.addFirst(1);

        Iterator iter = deque.iterator();
        iter.next();
        
        if (throwsException(NoSuchElementException.class, () -> { iter.next(); })) {
            System.out.println("Expected exception thrown for iter.next() at end");
        } else {
            System.out.println("Wrong or none exception thrown for iter.next() at end!");
        }

        if (throwsException(UnsupportedOperationException.class, () -> {iter.remove();})) {
            System.out.println("Expected exception thrown for a call to remove on iter");
        } else {
            System.out.println("Wrong or none exception thrown for a call to remove on iter");
        }

    }

    private interface ExceptionFunc {
        public void throwException();
    }
    private static <E extends Throwable> boolean throwsException (Class<E> exc, ExceptionFunc exceptionFunc) {
        try {
            exceptionFunc.throwException();
        }
        catch (Exception e) {
            if (exc.isInstance(e)) {
                return true;
            }
        }

        return false;
    }
}