import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {

        String value = StdIn.readString();
        int countRead = 1;
        
        while (!StdIn.isEmpty()) {
            countRead++;
            String s = StdIn.readString();

            boolean shouldReplace = StdRandom.bernoulli(1.0 / (double) countRead);

            if (shouldReplace) {
                value = s;
            }
        }

        System.out.println(value);
    }
} 