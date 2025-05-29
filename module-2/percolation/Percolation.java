import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.Throwable;


public class Percolation {
    private WeightedQuickUnionUF union;
    private int size;
    private int head;
    private int tail;
    private boolean[] openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.union = new WeightedQuickUnionUF(n*n + 2);
        this.size = n;

        this.head = (n*n);
        this.tail = (n*n)+1;

        openSites = new boolean[n * n];
        for (int i = 0; i < n*n; i++) {
            openSites[i] = false;
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // join to each of its neighbours
        Position position = Position.createFromRowCol(row, col, size);

        int offset = position.toOffset();
        openSites[offset] = true;
        if (position.hasUp()) {
            int side = position.getUp().toOffset();
            if (openSites[side]) {
                union.union(offset, side);
            }
        } 
        if (position.hasDown()) {
            int side = position.getDown().toOffset();
            if (openSites[side]) {
                union.union(offset, side);
            }
        }
        if (position.hasLeft()) {
            int side = position.getLeft().toOffset();
            if (openSites[side]) {
                union.union(offset, side);
            }
        } 
        if (position.hasRight()) {
            int side = position.getRight().toOffset();
            if (openSites[side]) {
                union.union(offset, side);
            }
        }

        if (!position.hasDown()) {
            // System.out.println("joined to tail at r:" + row + ", c:" + col);
            union.union(offset, tail);
        }
        if (!position.hasUp()) {
            // System.out.println("joined to head at r:" + row + ", c:" + col);
            union.union(offset, head);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int offset = Position.createFromRowCol(row, col, size).toOffset();
        return openSites[offset];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }

        Position p1 = Position.createFromRowCol(row, col, size);
        if (union.find(p1.toOffset()) == union.find(head)) {
            return true;
        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int tally = 0;
        for (int i = 0; i < openSites.length; i++) {
            if (openSites[i]) {
                tally +=1;
            }
        }
        return tally;
    }

    // does the system percolate?
    public boolean percolates() {
        return union.find(head) == union.find(tail);
    }

    private void print() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (isOpen(y, x)) {
                    System.out.print("O");                    
                } else {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }

        
        System.out.println("sets count:" + (union.count()-2));
        System.out.println("grid size:" + size * size);
         
    }

    // test client (optional)
    public static void main(String[] args) {
        int size = 5;
        Percolation percolation = new Percolation(size);
        percolation.open(0,0);
        percolation.open(1,0);
        percolation.open(2,0);
        percolation.open(3,0);
        if (percolation.percolates()) {
            percolation.print();
            throw new RuntimeException("should not percolate!");
        }
        percolation.open(4,0);
        if (!percolation.percolates()) {
            percolation.print();
            throw new RuntimeException("should percolate!");
        }

        System.out.println("test 1 passed");


        size = 3;
        percolation = new Percolation(size);
        percolation.open(0, 0);
        percolation.open(1, 0);
        percolation.open(1, 1);
        if (percolation.percolates()) {
            percolation.print();
            throw new RuntimeException("should not percolate!");
        }
        percolation.open(2,1);
        
        if (!percolation.percolates()) {
            percolation.print();
            throw new RuntimeException("should percolate!");
        }
        
        size = 5;
        percolation = new Percolation(size);
        percolation.open(0,4);
        percolation.open(1,4);
        percolation.open(2,4);
        percolation.open(3,4);
        if (percolation.percolates()) {
            percolation.print();
            throw new RuntimeException("should not percolate!");
        }
        percolation.open(4,4);
        if (!percolation.percolates()) {
            percolation.print();
            throw new RuntimeException("should percolate!");
        }
    }

}