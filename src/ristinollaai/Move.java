package ristinollaai;

/**
 * This is the class for the Move object, consisting
 * only of a row and a col index. This exists for convenience.
 * @author max
 */
public class Move {
    private int row;
    private int col;
    
    /* Default constructor */
    public Move(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    
    
}
