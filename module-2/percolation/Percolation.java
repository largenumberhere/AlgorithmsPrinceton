import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF union;
    private int size;   // the count of rows, or count of coluns. Both should be the same
    private int head;   // offset of the top virtual node
    private int tail;   // offsset of the bottom virtual node
    private boolean[] openSites;
    private int openSitesCount;

    // test client (optional)
    public static void main(String[] args) {

    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be at least 1");
        }

        this.union = new WeightedQuickUnionUF(n*n + 2);
        this.size = n;

        this.head = (n*n);
        this.tail = (n*n) + 1;

        openSites = new boolean[n * n];
        for (int i = 0; i < n*n; i++) {
            openSites[i] = false;
        }

        openSitesCount = 0;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row = oneToZeroOffset(row);
        col = oneToZeroOffset(col);

        int offset = rowColToOffset(row, col);
        if (offset == -1) {
            throw new IllegalArgumentException("Illegal bounds r=" + row + " c=" + col + " size=" + size); 
        }

        if (union.find(head) == tail) {
            if (!openSites[offset]) {
                openSitesCount++;
                openSites[offset] = true;
            }
            
            // prevent backwash.
            return;
        }

        // join to each of its neighbours
        if (!openSites[offset]) {
            openSitesCount++;
            openSites[offset] = true;
        }
        
        int upRow = row - 1;
        int upCol = col;
        if (boundsCheck(upRow, upCol)) {
            int sideOffset = rowColToOffset(upRow, upCol);
            if (openSites[sideOffset]) {
                union.union(offset, sideOffset);
            }
        } 
        else {
            union.union(offset, head);
        }

        int downRow = row + 1;
        int donwCol = col;
        if (boundsCheck(downRow, donwCol)) {
            int sideOffset = rowColToOffset(downRow, donwCol);
            if (openSites[sideOffset]) {
                union.union(offset, sideOffset);
            }
        } 
        else {
            union.union(offset, tail);
        }

        int leftRow = row;
        int leftCol = col - 1;
        if (boundsCheck(leftRow, leftCol)) {
            int sideOffset = rowColToOffset(leftRow, leftCol);
            if (openSites[sideOffset]) {
                union.union(offset, sideOffset);
            }
        }
        
        int rightRow = row;
        int rightCol = col + 1;
        if (boundsCheck(rightRow, rightCol)) {
            int sideOffset = rowColToOffset(rightRow, rightCol);
            if (openSites[sideOffset]) {
                union.union(offset, sideOffset);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // account for 1-based indexing
        row = oneToZeroOffset(row);
        col = oneToZeroOffset(col);

        int offset = rowColToOffset(row, col);
        if (offset == -1) {
            throw new IllegalArgumentException("Illegal bounds r=" + row + " c=" + col + " size=" + size);
        }

        return openSites[offset];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row = oneToZeroOffset(row);
        col = oneToZeroOffset(col);

        if (!isOpen(zeroToOneOffset(row), zeroToOneOffset(col))) {
            return false;
        }
        
        int offset = rowColToOffset(row, col);
        if (offset == -1) {
            throw new IllegalArgumentException("Illegal bounds r=" + row + " c=" + col + " size=" + size);
        }
        if (union.find(offset) == union.find(head)) {
            return true;
        } 

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return union.find(head) == union.find(tail);
    }

    private int oneToZeroOffset(int offset) {
        return offset - 1;
    }
    private int zeroToOneOffset(int offset){
        return offset + 1;
    }

    // works on 0-indexed values
    // returns -1 on error
    private int rowColToOffset(int row, int col) {
        if (!boundsCheck(row, col)) {
            return -1;
        }
        
        int offset = (row * size) + col;
        return offset;  
    }

    // works on 0-indexed values
    private boolean boundsCheck(int row, int col) {
        if (row >= size || col >= size || row < 0 || col < 0) {
            return false;
        }

        return true;
    }

}


