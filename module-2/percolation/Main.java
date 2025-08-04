import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Percolation test");

        PercolationStats stats = new PercolationStats(2, 10000);
        
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


            //     int size = 5;
    //     Percolation percolation = new Percolation(size);
    //     percolation.open(0,0);
    //     percolation.open(1,0);
    //     percolation.open(2,0);
    //     percolation.open(3,0);
    //     if (percolation.percolates()) {
    //         percolation.print();
    //         throw new RuntimeException("should not percolate!");
    //     }
    //     percolation.open(4,0);
    //     if (!percolation.percolates()) {
    //         percolation.print();
    //         throw new RuntimeException("should percolate!");
    //     }

    //     System.out.println("test 1 passed");


    //     size = 3;
    //     percolation = new Percolation(size);
    //     percolation.open(0, 0);
    //     percolation.open(1, 0);
    //     percolation.open(1, 1);
    //     if (percolation.percolates()) {
    //         percolation.print();
    //         throw new RuntimeException("should not percolate!");
    //     }
    //     percolation.open(2,1);
        
    //     if (!percolation.percolates()) {
    //         percolation.print();
    //         throw new RuntimeException("should percolate!");
    //     }
        
    //     size = 5;
    //     percolation = new Percolation(size);
    //     percolation.open(0,4);
    //     percolation.open(1,4);
    //     percolation.open(2,4);
    //     percolation.open(3,4);
    //     if (percolation.percolates()) {
    //         percolation.print();
    //         throw new RuntimeException("should not percolate!");
    //     }
    //     percolation.open(4,4);
    //     if (!percolation.percolates()) {
    //         percolation.print();
    //         throw new RuntimeException("should percolate!");
    //     }
    }
}