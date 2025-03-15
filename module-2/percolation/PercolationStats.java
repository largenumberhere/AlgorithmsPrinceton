
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        Percolation percolation;
        
        for (int i = 0; i < trials; i++) {
            percolation = new Percolation(n);
            while (true) {
                if (percolation.percolates()) {
                    break;
                }

                int random_col = StdRandom.uniformInt(0, n);
                int random_row = StdRandom.uniformInt(0, n);

                if (!percolation.isOpen(random_row, random_col)) {
                    percolation.open(random_row, random_col);
                }
                
            }
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return 0.0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0.0;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0.0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0.0;
    }


    // test client (see below)
    // main() method that takes two command-line arguments n and T, performs T independent computational experiments (discussed above) on an n-by-n grid, 
    // and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold. 
    public static void main(String[] args) {

            

    }

}