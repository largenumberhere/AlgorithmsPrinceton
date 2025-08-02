
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Percolation test");

        PercolationStats stats = new PercolationStats(200, 100);
        
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev =" + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo()+", " + stats.confidenceHi() + "]");
        

        // Percolation.main(new String[] {"", ""});
        // WeightedQuickUnionUF union = new WeightedQuickUnionUF(4);
        // union.union(0,1);
        // union.union(1,2);
        // int parent = union.find(2);
// 
        // System.out.println(parent);
    }
}