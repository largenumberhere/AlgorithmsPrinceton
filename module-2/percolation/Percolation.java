import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.Throwable;


public class Percolation {
    private WeightedQuickUnionUF union;
    private int size;
    private int head;
    private int tail;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.union = new WeightedQuickUnionUF(n*n + 2);
        this.size = n;

        this.head = n;
        this.tail = n+1;
        // join the top and bottom to 2 virtual nodes
        for (int i = 0; i < size; i++) {
            Position p1 = Position.createFromRowCol(0, i, size);
            union.union(head, p1.toOffset());

            Position p2 = Position.createFromRowCol(size-1, i, size);
            union.union(tail, p2.toOffset());
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // join to each of its neighbours
        Position position = Position.createFromRowCol(row, col, size);
        // System.out.println("position r:" + position.getRow() + " c:" + position.getCol() + " hasDown: " + position.hasDown());
        // System.out.println("position r:" + position.getRow() + " c:" + position.getCol() + " hasUp: " + position.hasUp());
        // System.out.println("position r:" + position.getRow() + " c:" + position.getCol() + " hasLeft: " + position.hasLeft());
        // System.out.println("position r:" + position.getRow() + " c:" + position.getCol() + " hasRight: " + position.hasRight());
        
        
        
        int offset = position.toOffset();
        if (position.hasUp()) {
            union.union(offset, position.getUp().toOffset());
        } 
        if (position.hasDown()) {
            union.union(offset, position.getDown().toOffset());
        }
        if (position.hasLeft()) {
            union.union(offset, position.getLeft().toOffset());
        } 
        if (position.hasRight()) {
            union.union(offset, position.getRight().toOffset());
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        Position position = Position.createFromRowCol(row, col, size);
        int root = union.find(position.toOffset());
        if (position.hasUp()) {
            if (union.find(position.getUp().toOffset()) != root) {
                // System.out.println("no up");
                return false;
            }
        }

        if (position.hasDown()) {
            if (union.find(position.getDown().toOffset())!= root) {
                // System.out.println("no dn");
                return false;
            }
        }

        if (position.hasLeft()) {
            if (union.find(position.getLeft().toOffset())!= root) {
                // System.out.println("no lf");
                return false;
            }
        }

        if (position.hasRight()) {
            if (union.find(position.getRight().toOffset()) != root) {
                // System.out.println("no rt");
                return false;
            }
        }

        return true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        Position p1 = Position.createFromRowCol(row, col, size);
        if (union.find(p1.toOffset()) == union.find(head)) {
            return true;
        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return -1;
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
    }

    // test client (optional)
    public static void main(String[] args) {
        int size = 5;
        Percolation percolation = new Percolation(size);
        percolation.open(0,0);
        percolation.print();
        System.out.println("");
        
        percolation.open(1,1);
        percolation.print();

        System.out.println("");
        percolation.open(2,2);

        percolation.print();
        
    }

}