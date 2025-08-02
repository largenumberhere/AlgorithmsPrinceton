import edu.princeton.cs.algs4.WeightedQuickUnionUF;
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

        this.head = (n * n);
        this.tail = (n * n) + 1;

        openSites = new boolean[n * n];
        for (int i = 0; i < n * n; i++) {
            openSites[i] = false;
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // account for 1-based indexing
        row -= 1;
        col -= 1;

        // join to each of its neighbours
        Position position;
        try {
            position = Position.createFromRowCol(row, col, size);
        } catch(IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }

        int offset = position.toOffset();
        try {
            openSites[offset] = true;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            throw new IndexOutOfBoundsException("Attempt to access illegal array offset " + offset + " in array of size " + openSites.length + " for row=" + position.getRow() + " col=" + position.getCol());
        }
        
        
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
        row -= 1;
        col -= 1;

        int offset = 0;
        try {
            int offset = Position.createFromRowCol(row, col, size).toOffset();
        } catch(IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }

        try {
            boolean siteOpen = openSites[offset];
        catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            throw new IndexOutOfBoundsException("Attempt to access illegal array offset " + offset + " in array of size " + openSites.length + " for row=" + position.getRow() + " col=" + position.getCol());
        }
        return siteOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }

        // account for 1-based indexing
        row -= 1;
        col -= 1;

        Position p1;
        try {
            p1 = Position.createFromRowCol(row, col, size);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
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
                tally += 1;
            }
        }
        return tally;
    }

    // does the system percolate?
    public boolean percolates() {
        return union.find(head) == union.find(tail);
    }

    // test client (optional)
    public static void main(String[] args) { 
    }

}

class Position {
    public int row;
    private int col;
    private int size;

    public static Position createFromRowCol(int row, int col, int columns) {
        if (row >= columns || col >= columns || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Attempt to create an invalid position");
        }

        Position position = new Position(row, col, columns);
        return position;
    }   

    public static Position createFromOffset(int offset, int size) {
        int col = offset % size;
        int row = offset / size;
        Position position = new Position(row, col, size);

        return position;
    }

    private Position(int row, int col, int columns) {
        this.row = row;
        this.col = col;
        size = columns;
    }

    public int getCol() {
        return this.col;
    }

    public int getSize() {
        return this.size;
    }
    public int getRow() {
        return this.row;
    }

    public int toOffset() {
        int offset = (row * size) + col;
        return offset;
    }

    public boolean hasLeft() {
        return (col -1) >= 0;
    }
    public Position getLeft() {
        if (!hasLeft()) {
            throw new IndexOutOfBoundsException("there is none to the left");
        }

        return new Position(row, col - 1, size);
    }
    public boolean hasRight() {
        return (col + 1) < size;
    }
    public Position getRight() {
        if (!hasRight()) {
            throw new IndexOutOfBoundsException("there is none to the right");
        } 

        return new Position(row, col + 1, size);
    }
    public boolean hasUp() {
        return (row - 1) >= 0;
    }
    public Position getUp() {
        if (!hasUp()) {
            throw new IndexOutOfBoundsException("there is none upwards");
        }

        return new Position(row - 1, col, size);
    }
    public boolean hasDown() {
        return (row + 1) < size;
    }
    public Position getDown() {
        if (!hasDown()) {
            throw new IndexOutOfBoundsException("there is no downwards");
        }

        return new Position(row + 1, col, size);
    }
}