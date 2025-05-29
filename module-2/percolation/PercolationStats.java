
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Scanner;

public class PercolationStats {
    private Percolation[] models;
    private int[] counts;

    // sample mean of percolation threshold
    public double mean() {
        if (counts == null) {
            counts = new int[models.length];
            for (int i = 0; i < models.length; i++) {
                counts[i] = models[i].numberOfOpenSites();
            }
        }

        double mean = StdStats.mean(counts);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if ( counts == null) {
            counts = new int[models.length];
            for (int i = 0; i < models.length; i++) {
                counts[i] = models[i].numberOfOpenSites();
            }
        }

        double deviation = StdStats.stddev(counts);

        return deviation;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        // https://stackoverflow.com/questions/30340551/java-calculate-confidence-interval
        if ( counts == null) {
            counts = new int[models.length];
            for (int i = 0; i < models.length; i++) {
                counts[i] = models[i].numberOfOpenSites();
            }
        }

        double m =  mean();
        double deviation = stddev();
        double confidenceLevel = 0.96;
        double tmp = confidenceLevel * deviation / Math.sqrt(counts.length);

        return m - tmp;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        // https://stackoverflow.com/questions/30340551/java-calculate-confidence-interval
        if ( counts == null) {
            counts = new int[models.length];
            for (int i = 0; i < models.length; i++) {
                counts[i] = models[i].numberOfOpenSites();
            }
        }

        double m =  mean();
        double deviation = stddev();
        double confidenceLevel = 0.96;
        double tmp = confidenceLevel * deviation / Math.sqrt(counts.length);

        return m + tmp;
    }


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        models = new Percolation[trials];
        counts = null;
        Percolation percolation;
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (! percolation.percolates()) {
                int random_col = StdRandom.uniformInt(0, n);
                int random_row = StdRandom.uniformInt(0, n);

                if (!percolation.isOpen(random_row, random_col)) {
                    percolation.open(random_row, random_col);
                }
            }

            models[i] = percolation;
        }
    }

    // test client (see below)
    // main() method that takes two command-line arguments n and T, performs T independent computational experiments (discussed above) on an n-by-n grid, 
    // and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold. 
    public static void main(String[] args) {


            // n and T where n = size and T is the count of tests
            // print mean, deviation, 95% confidence interval
            Scanner scanner = new Scanner(System.in);
            int size = scanner.nextInt();
            int tests = scanner.nextInt();

            PercolationStats percolationStats = new PercolationStats(size, tests);
            System.out.println("mean = " + percolationStats.mean());
            System.out.println("stddev =" + percolationStats.stddev());
            System.out.println("95% confidence interval = [" + percolationStats.confidenceLo()+", " + percolationStats.confidenceHi() + "]");

    }

}