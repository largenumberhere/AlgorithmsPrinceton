import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.Throwable;
import java.lang.IndexOutOfBoundsException;



public class Percolation {
    private WeightedQuickUnionUF union;
    private int size;
    private int head;
    private int tail;
    private boolean[] openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be at least 1");
        }

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
        // account for 1-based indexing
        row -=1;
        col -=1;

        // join to each of its neighbours
        Position position = Position.tryCreateFromRowCol(row, col, size);
        if (position == null) {
            throw new IllegalArgumentException("Illegal bounds r=" + row + " c=" + col + " size=" + size);
        }

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
        // account for 1-based indexing
        row -=1;
        col -=1;

        Position p = Position.tryCreateFromRowCol(row, col, size);
        if (p == null) {
            throw new IllegalArgumentException("Illegal bounds r=" + row + " c=" + col + " size=" + size);
        }
        int offset = p.toOffset();

        return openSites[offset];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // account for 1-based indexing
        row -=1;
        col -=1;

        if (!isOpen(row, col)) {
            return false;
        }

        Position p1 = Position.tryCreateFromRowCol(row, col, size);
        if (p1 == null) {
            throw new IllegalArgumentException("Illegal bounds r=" + row + " c=" + col + " size=" + size);
        }
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

    // private void print() {
    //     for (int y = 0; y < size; y++) {
    //         for (int x = 0; x < size; x++) {
    //             if (isOpen(y, x)) {
    //                 System.out.print("O");                    
    //             } else {
    //                 System.out.print(".");
    //             }
    //         }
    //         System.out.println("");
    //     }

        
    //     System.out.println("sets count:" + (union.count()-2));
    //     System.out.println("grid size:" + size * size);
         
    // }

    // test client (optional)
    public static void main(String[] args) { 
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



class Position {
    public int row;
    private int col;
    private int size;

    public int getCol() {
        return this.col;
    }

    public int getSize() {
        return this.size;
    }
    public int getRow() {
        return this.row;
    }

    private Position(int row, int col, int columns) {
        this.row = row;
        this.col = col;
        this.size = columns;
    }

    private static Position createFromRowCol(int row, int col, int columns) {
        // System.out.println("creating position for row "+ row + " col" + col + "columns" + columns);
        if (row >= columns || col >= columns || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Attempt to create an invalid position");
        }

        Position position = new Position(row, col, columns);
        return position;
    }   

    public static Position tryCreateFromRowCol(int row, int col, int columns) {
        Position p = null;
        try {
            p = createFromRowCol(row, col, columns);
        } catch(IndexOutOfBoundsException e) {}
        
        return p;
    }

    public static Position createFromOffset(int offset, int size) {
        int col = offset % size;
        int row = offset / size;
        Position position = new Position(row, col, size);


        return position;
    }

    public static Position tryCreateFromOffset(int offset, int size) {
        Position p = null;
        try {
            p = createFromOffset(offset, size);
        } catch (IndexOutOfBoundsException e) {}

        return p;
    }

    public int toOffset() {
        int offset = (row * size) + col;
        return offset;
    }

    public boolean hasLeft() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("bad size");
        }
        return (col -1) >= 0;
    }
    public Position getLeft() {
        
        if (!hasLeft()) {
            throw new IndexOutOfBoundsException("there is none to the left");
        }

        return new Position(row, col-1, size);
    }
    public boolean hasRight() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("bad size");
        }
        return (col + 1) < size;
    }
    public Position getRight() {
        if (!hasRight()) {
            throw new IndexOutOfBoundsException("there is none to the right");
        } 

        return new Position(row, col +1, size);
    }
    public boolean hasUp() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("bad size");
        }
        return (row -1) >= 0;
    }
    public Position getUp() {
        if (!hasUp()) {
            throw new IndexOutOfBoundsException("there is none upwards");
        }

        return new Position(row-1, col, size);
    }
    public boolean hasDown() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("bad size");
        }
        return (row +1) < size;
    }
    public Position getDown() {
        if (!hasDown()) {
            throw new IndexOutOfBoundsException("there is no downwards");
        }

        return new Position(row+1, col, size);
    }
}