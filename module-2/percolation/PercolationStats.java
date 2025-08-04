import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double mean;
    private double stddev;
    private double confLo;
    private double confHi;

    // test client (see below)
    // main() method that takes two command-line arguments n and T, performs T independent computational experiments (discussed above) on an n-by-n grid, 
    // and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold. 
    public static void main(String[] args) {
        // n and T where n = size and T is the count of tests
        // print mean, deviation, 95% confidence interval
        int size = Integer.parseInt(args[0]);
        int tests = Integer.parseInt(args[1]);
        
        PercolationStats percolationStats = new PercolationStats(size, tests);
        System.out.println("mean = " + percolationStats.mean());
        System.out.println("stddev =" + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials < 1) {
            throw new IllegalArgumentException("trials must be at least 1");
        }
        if (n < 1) {
            throw new IllegalArgumentException("n mus be at least 1");
        }

        // perform the experiments
        Percolation[] models = new Percolation[trials];
        for (int i = 0; i < trials; i++) {
           Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randomCol = StdRandom.uniformInt(1, n + 1);
                int randomRow = StdRandom.uniformInt(1, n + 1);
                
                if (!percolation.isOpen(randomRow, randomCol)) {
                    percolation.open(randomRow, randomCol);
                }
            }

            models[i] = percolation;
        }
        // calculate mean, stddev
        int[] counts = new int[models.length];
        for (int i = 0; i < models.length; i++) {
            counts[i] = models[i].numberOfOpenSites();
        }
        mean = StdStats.mean(counts);
        stddev = StdStats.stddev(counts);

        // calculate confidence
        // https://stackoverflow.com/questions/30340551/java-calculate-confidence-interval
        double confidenceLevel = 0.96;
        double tmp = confidenceLevel * stddev / Math.sqrt(counts.length);
        confLo = mean - tmp;
        confHi = mean + tmp;

    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confHi;
    }
}