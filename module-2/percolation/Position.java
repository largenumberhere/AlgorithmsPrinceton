import java.lang.IndexOutOfBoundsException;

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
        size = columns;
    }

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

        return new Position(row, col-1, size);
    }
    public boolean hasRight() {
        return (col + 1) < size;
    }
    public Position getRight() {
        if (!hasRight()) {
            throw new IndexOutOfBoundsException("there is none to the right");
        } 

        return new Position(row, col +1, size);
    }
    public boolean hasUp() {
        return (row -1) >= 0;
    }
    public Position getUp() {
        if (!hasUp()) {
            throw new IndexOutOfBoundsException("there is none upwards");
        }

        return new Position(row-1, col, size);
    }
    public boolean hasDown() {
        return (row +1) < size;
    }
    public Position getDown() {
        if (!hasDown()) {
            throw new IndexOutOfBoundsException("there is no downwards");
        }

        return new Position(row+1, col, size);
    }
}