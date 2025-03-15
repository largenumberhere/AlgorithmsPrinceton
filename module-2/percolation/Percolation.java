import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.Throwable;

public class Percolation {
    private class RowCol {
        public RowCol() {
            row = 0;
            col = 0;
        }
        public int row;
        public int col;    
    }

    private int size;
    private WeightedQuickUnionUF union;
    private boolean[] siteOpen;


    int toLinear(int row, int col) {
        return (col * size ) + row;
    }

    RowCol fromLinear(int position) {
        RowCol rowCol = new RowCol();

        rowCol.col = position % size;
        rowCol.row = position / size;
        
        return rowCol;
    }

    private int topSite;
    private int bottomSite;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // initialize class variables
        size = n;
        union = new WeightedQuickUnionUF((n*n) + 2);
        siteOpen = new boolean[n * n];
        
        for (int i = 0; i < n*n; i++) {
            siteOpen[i] = false;
        }

        // initalize the top and bottom tings
        topSite = n*n + 0;
        bottomSite = n*n + 1;
        
        for(int i = 0; i < n; i++) {
            int pos = toLinear(0, i);
            union.union(pos, topSite);
        }

        for (int i = 0; i < n; i++) {
            int pos = toLinear(n-1, i);
            union.union(i, bottomSite);
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= size || col >= size) {
            throw new IllegalArgumentException("Open was given a row or column value that was out of bounds col:"+col+ " row:"+ row);
        }

        if (isOpen(row, col)) {
            return;
        }


        int pos = toLinear(row, col);

        // loop through each neighbour
        // left
        if (col -1 > 0) {

            int pos2 = toLinear(row, col-1);
            if (isOpenPos(pos2)) {
                union.union(pos2, pos);
                System.out.println("set " + pos2 + " => "+ pos);
            }
        }

        // right
        if (col + 1 < size) {
            int pos2 = toLinear(row, col+1);
            if (isOpenPos(pos2)) {
                union.union(pos2, pos);
                System.out.println("set " + pos2 + " => "+ pos);
            }
        }

        // down
        if (row+1 < size) {
            int pos2 = toLinear(row+1, col);
            if (isOpenPos(pos2)) {
                union.union(pos2, pos);
                System.out.println("set " + pos2 + " => "+ pos);
            }
        }

        // up
        if (row-1 > 0) {
            int pos2 = toLinear(row-1, col);
            if (isOpenPos(pos2)) {
                union.union(pos2, pos);
                System.out.println("set " + pos2 + " => "+ pos);
            }
        }
        
        // record that site has been opened
        siteOpen[pos] = true;

        return;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int pos = toLinear(row, col);
        
        return siteOpen[pos];
    }

    private boolean isOpenPos(int pos) {
        return siteOpen[pos];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int pos = toLinear(row, col);
        if (union.find(pos) == topSite) {
            return true;
        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int open = 0;
        for (int i = 0; i < siteOpen.length; i++) {
            if (siteOpen[i]) {
                open++;
            }
        }

        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return union.find(bottomSite) == topSite;
    }

    // test client (optional)
    public static void main(String[] args) throws Exception {
        int size = 5;
        Percolation percolation = new Percolation(size);
        
        for (int y = 0; y < size; y++) {
            percolation.open(y, 0);
            if (!percolation.isOpen(y, 0)) {
                throw new Exception("Site " + y + "," +"0" + " did not open");
            }
            System.out.println("Site opened" + y+ ","+ 0);
        }

        for (int x = 0; x < size; x ++) {
            for (int y = 0; y < size; y++) {
                if (percolation.isOpen(x, y)) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }

        if (!percolation.percolates()) {
            throw new Exception("The system did no percolate");
        }
        System.out.println("The system did percolate");
    }
}