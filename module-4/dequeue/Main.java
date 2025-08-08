public class Main {
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(0);
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addLast(4);

        for (int i : deque) {
            System.out.println("" + i + " ");
        }

        while(deque.size() > 0) {
            Integer a = deque.removeFirst();
            System.out.println("" + a + " ");
        }

    }
}