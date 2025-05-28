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

    private boolean areUnionNodesJoined(int id1, int id2) {
        int root1 = getUnionNodeRoot(id1);
        int root2 = getUnionNodeRoot(id2);
        return root1 == root2;
    }

    // traverse the nodes until we find the root node
    private int getUnionNodeRoot(int nodeid) {
        if (union.find(nodeid) == nodeid) {
            return nodeid;
        }
        
        int outnode = nodeid;
        while (true) {
            int parent = union.find(outnode);
            if (parent == outnode) {
                break;
            }

            outnode = parent;
        }

        return outnode;
    }

    
    private int toLinear(int row, int col) {
        return (row * size ) + col;
    }

    private RowCol fromLinear(int position) {
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

        // initalize the top and bottom virtual notes
        topSite = n*n + 0;
        bottomSite = n*n + 1;
        
        // point top row to topSite virtual node
        for(int i = 0; i < n; i++) {
            int pos = toLinear(0, i);
            union.union(topSite, pos);
        } 

        // point last row to bottomSite virtual node
        for (int i = 0; i < n; i++) {
            int pos = toLinear(n-1, i);
            union.union(bottomSite, pos);
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


        int new_node = toLinear(row, col);

        // point each open neighbout to it
        // todo: check if neighour is open first
        int neighbour_x = 0; 
        int neighbour_y = 0;

        // left 
        neighbour_x = col - 1;
        neighbour_y = row;
        if (neighbour_x > 0 && !isOpen(neighbour_x, neighbour_y)) {
            int pos = toLinear(neighbour_x, neighbour_y);
            union.union(pos, new_node);
        }

        // right
        neighbour_x = col + 1;
        neighbour_y = row;
        if (neighbour_x < size && !isOpen(neighbour_x, neighbour_y)) {
            int pos = toLinear(neighbour_x, neighbour_y);
            union.union(pos, new_node);
        }

        // up
        neighbour_x = col;
        neighbour_y = row -1;
        if (neighbour_y >= 0 && !isOpen(neighbour_x, neighbour_y)) {
            int pos = toLinear(neighbour_x, neighbour_y);
            union.union(pos, new_node);
        }

        // down
        neighbour_x = col;
        neighbour_y = row +1;
        if (neighbour_y < size && !isOpen(neighbour_x, neighbour_y)) {
            int pos = toLinear(neighbour_x, neighbour_y);
            union.union(pos, new_node);
        }

        // record that site has been opened
        siteOpen[new_node] = true;

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
        if (!isOpen(row, col)) {
            return false;
        }

        if (areUnionNodesJoined(pos, topSite)) {
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
        return getUnionNodeRoot(bottomSite) == topSite;
            
    }

    private void printSystem() {
        for (int x = 0; x < size; x ++) {
            for (int y = 0; y < size; y++) {
                if (isOpen(x, y)) {
                    System.out.print("O");
                } else {
                    System.out.print("X");
                }
            }
            System.out.println();
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int thisNode = union.find(toLinear(x , y));
                // left
                if (x - 1 > 0) {
                    if (thisNode == union.find(toLinear(x-1, y))) {
                        System.out.println("" + x + "," + y + " -> " + (x-1) + "," + y);
                    }
                }
                
                // right
                if (x + 1 < size) {
                    if (thisNode == union.find(toLinear(x+1, y))) {
                        System.out.println("" + x + "," + y + " -> "+ (x+1) + "," + y);
                    }
                }

                // up
                if (y - 1 > 0) {
                    if (thisNode == union.find(toLinear(x, y-1))) {
                        System.out.println("" + x + "," + y + " -> " + x+ "," + (y -1));
                    }
                }

                // down
                if (y + 1 < size) {
                    if (thisNode == union.find(toLinear(x, y+1))) {
                        System.out.println("" + x + "," + y + " -> " + x+ "," + (y + 1));
                    }
                }
            }
        }
    }

    // tests for percolate==true and isFull
    private static void test1() throws Exception {
        int size = 5;
        Percolation percolation = new Percolation(size);
        
        for (int y = 0; y < size; y++) {
            int x = 0;

            percolation.open(x, y);
            if (!percolation.isOpen(x, y)) {
                throw new Exception("Site " + x + "," + y + " did not open");
            }
            if (!percolation.isFull(x, y)) {
                throw new Exception("Site " + x + "," + y + " did not full");
            }

            if ( y+1 < size && percolation.isFull(x, y+1)) {
                throw new Exception("Site " + x + "," + y + " leaked bellow");
            }

            System.out.println("Site opened" + x+ ","+ y);
        }
        if (!percolation.percolates()) {
            percolation.printSystem(); //
            percolation.printSystem(); //printSystem(percolation, size);
            throw new Exception("The system did no percolate");
        }
    }

    // tests for percolate==false, isFull
    private static void test2() throws Exception {
        int size = 5;
        Percolation percolation = new Percolation(size);
        for (int y = 0; y < size-1; y++) {
            int x = 0;
            percolation.open(x, y);
        }
        if (percolation.percolates()) {
            percolation.printSystem(); // (percolation, size);
            throw new Exception("system should not percolate");
        }
        if (percolation.isFull(size-1, 0)) {
            throw new Exception("An un-opened node should not be full");
        }
        
        for (int x = 1; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (percolation.isOpen(x, y)) {
                    throw new Exception("node should not be open " + x + " " + y);
                }
                if (percolation.isFull(x, y)) {
                    throw new Exception("node should not be full" + x + " " + y);
                }
            }
        }
        
        percolation.printSystem(); // (percolation, size);
    }

    // test client (optional)
    public static void main(String[] args) throws Exception {
        Percolation percolation = new Percolation(2);
        if (percolation.percolates()) {
            throw new Exception("Percolated on start");
        }
        // percolation.open(0, 0);
        // if (percolation.percolates()) {
        //     throw new Exception("Percolated after start");
        // }

        // percolation.open(0, 1);
        // if (!percolation.percolates()) {
        //     throw new Exception("Should percolate");
        // }


        test1();
        test2();



     
        System.out.println("The system did percolate");
    }
}