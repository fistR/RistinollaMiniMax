package ristinollaai;

import static sun.swing.MenuItemLayoutHelper.max;

/**
 * This is the Ristinolla/Tic-Tac-Toe game class. 
 * It contains the board(2d char array), the state of the game
 * and available moves. The board consists of empty spaces '.',
 * 'x' marks, and 'o' marks.
 * spacesLeft is always up to date by being incremented at the 
 * appropriate times. The current player represents the player
 * who will place the next mark.
 * @author max
 */
public class Ristinolla {
    
    private char[][] board;
    private int spacesLeft;
    private char currentPlayer;
    private DoubleLinkList availableMoves;
    private DoubleLinkList moves2;
    
    /**
     * Constructor. Give the size of the game board
     * and whether or not x goes first or 'o' goes first.
     * x is recommended as is the default for tic-tac-toe.
     * @param size
     * @param xGoesFirst 
     */
    public Ristinolla(int size, boolean xGoesFirst){
        board = new char[size][size];
        if(xGoesFirst) currentPlayer = 'x';
        else currentPlayer = 'o';
        spacesLeft = size * size;
        availableMoves = new DoubleLinkList();
        moves2 = new DoubleLinkList();
        initBoard(size);
    }
    
    /**
     * Method for printing the board onto the console.
     * It will print the board array separated by --- and |
     * to look sort of like a tic-tac-toe board.
     */
    public void printBoard(){
        
        System.out.print("-");
        for(int i = 0;i<board.length-1; i++) System.out.print("----");
        System.out.println("----");
        for(int i = 0; i<board.length; i++){
            System.out.print("| ");
            for(int j=0; j<board.length; j++){
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("");
            System.out.print("-");
            for(int k = 0;k<board.length-1; k++) System.out.print("----");
            System.out.println("----");
        }
    }
    /**
     * Method for initializing the board with all empty marks.
     * @param size 
     */
    public void initBoard(int size){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                board[i][j] = '.';
                availableMoves.insertLast(new Move(i,j));
                moves2.insertLast(new Move(i,j));
            }
        }
    }
    /**
     * This method returns a list of the available moves left.
     * This is needed to always have the right order list
     * due to minimax iterating. Getting rid of this and replacing
     * the moves list with perhaps a self organizing heap might 
     * improve performance.
     * @return 
     */
    public DoubleLinkList calcAvailableMoves(){
        DoubleLinkList moves = new DoubleLinkList();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                if(board[i][j] == '.')moves.insertLast(new Move(i,j));
            }
        }
        return moves;
    }
    /**
     * Method for getting the remaining available moves
     * so that they are roughly sorted for move-ordering
     * in the minmax ab pruning algorithm to improve performance.
     * Is not currently used as it does not improve performance.
     * @return 
     */
    public DoubleLinkList calcAvailableMovesSorted(){
        DoubleLinkList moves = new DoubleLinkList();
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board.length; j++){
                if(board[i][j] == '.'){
                    Move mv = new Move(i,j);
                    placeMark(mv.getRow(), mv.getCol());
                    if(moves.size == 0) moves.insertFirst(mv);
                    else if(GameController.evalMove(mv, board) >= GameController.evalMove(moves.getNode(1).obj, board)){
                        moves.insertFirst(mv);
                    }
                    else if(GameController.evalMove(mv, board) <= GameController.evalMove(moves.tail.obj, board)){
                        moves.insertLast(mv);
                    } else {
                        //moves.insertAt(mv, (int)moves.size/2);
                        moves.insertLast(mv);
                    }
                    removeMark(mv.getRow(),mv.getCol());
                    
                    
                }
            }
        }
        return moves;
    }
    
    /**
     * Method for checking if the game is over by no
     * more available spaces.
     * @return 
     */
    public boolean isBoardFull(){
        if(spacesLeft > 0) return false;
        else return true;
    }
    /**
     * This method checks if there is a winner by separate functions
     * checking rows, columns and diagonals. If any are true there is a winner
     * @param board
     * @return 
     */
    public static boolean checkForWin(char[][] board){
        boolean re = (checkRowsForWin(board) || checkColsForWin(board) || checkDiagForWin(board));
        //if(re) System.out.println("Winning combo found for " + ((currentPlayer == 'o') ? 'x' : 'o'));
        return re;
    }
    
    /**
     * Method to check if any row contains a win.
     * @param board
     * @return 
     */
    public static boolean checkRowsForWin(char[][] board){
        boolean hasWin, hasX, hasE, hasO;
        hasWin = false;
        for(int i = 0; i<board.length; i++){
            hasWin = hasX = hasE = hasO = false;
            for(int j=0; j<board.length; j++){
                if(board[i][j] == 'x') hasX = true;
                else if(board[i][j] == 'o') hasO = true;
                else hasE = true;
            }
            if(hasX && !hasO && !hasE) hasWin = true;
            else if (!hasX && hasO && !hasE) hasWin = true;
            if(hasWin) return hasWin;      
        }
        return hasWin;
    }
 
    /**
     * Method to check if any column contains a win
     * @param board
     * @return 
     */
    public static boolean checkColsForWin(char[][] board){
        boolean hasWin, hasX, hasE, hasO;
        hasWin = false;
        for(int i = 0; i<board.length; i++){
            hasWin = hasX = hasE = hasO = false;
            for(int j=0; j<board.length; j++){
                if(board[j][i] == 'x') hasX = true;
                else if(board[j][i] == 'o') hasO = true;
                else hasE = true;
            }
            hasWin = compareMarks(hasX, hasE, hasO);
            if(hasWin) return hasWin;
        }
        return hasWin;
    }
    /**
     * Method to check if any Diagonal contains a win
     * @param board
     * @return 
     */
    public static boolean checkDiagForWin(char[][] board){
        boolean hasWin, hasX, hasE, hasO;
        hasWin = hasX = hasE = hasO = false;
        for(int i=0; i<board.length;i++){
            if(board[i][i] == 'x') hasX = true;
            else if(board[i][i] == 'o') hasO = true;
            else hasE = true;
            hasWin = compareMarks(hasX, hasE, hasO);
        }
        if(hasWin) return hasWin;
        hasWin = hasX = hasE = hasO = false;
        for(int j=0, i=board.length-1; j<board.length; i--, j++){
            if(board[i][j] == 'x') hasX = true;
            else if(board[i][j] == 'o') hasO = true;
            else hasE = true;
            hasWin = compareMarks(hasX, hasE, hasO);
        }
        return hasWin;
    }
    /**
     * This method is used by the winning condition checking functions
     * to see if a col/row/diag has only of one type of character.
     * @param hasX
     * @param hasE
     * @param hasO
     * @return 
     */
    public static boolean compareMarks(boolean hasX, boolean hasE, boolean hasO){
            if(hasX && !hasO && !hasE) return true;
            else if (!hasX && hasO && !hasE) return true;
            else return false;
    }
    /**
     * Change the active player.
     */
    public void changeTurn(){
        if(currentPlayer == 'x') currentPlayer = 'o';
        else currentPlayer = 'x';
    }
    /**
     * Place a mark. The mark will be that of
     * the current active player.
     * @param row
     * @param col
     * @return 
     */
    public boolean placeMark(int row, int col){
        if((row >= 0) && (row < board.length)){
            if((col >= 0) && (col < board.length)){
                if(board[row][col] == '.'){
                    board[row][col] = currentPlayer;
                    availableMoves.deleteMove(new Move(row, col));
                    spacesLeft--;
                    //System.out.println("" + currentPlayer + row + col);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Remove a mark at a given location.
     * @param row
     * @param col
     * @return 
     */
    public boolean removeMark(int row, int col){
        if((row >= 0) && (row < board.length)){
            if((col >= 0) && (col < board.length)){
                board[row][col] = '.';
                availableMoves.insertFirst(new Move(row,col));
                spacesLeft++;
                return true;
            }
        }
        return false;
    }
    /**
     * Make a move. placeMark is called for this.
     * @param move
     * @return 
     */
    public boolean makeMove(Move move){
        availableMoves.deleteMove(move);
        return placeMark(move.getRow(), move.getCol());
    }

    public char[][] getBoard() {
        return board;
    }
    
    public int getSpacesLeft() {
        return spacesLeft;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public DoubleLinkList getMovesLeft() {
        return availableMoves;
    }

    public DoubleLinkList getAvailableMoves() {
        return availableMoves;
    }

    public DoubleLinkList getMoves2() {
        return moves2;
    }
    
    
    

}



