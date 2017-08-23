/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ristinollaai;

import static sun.swing.MenuItemLayoutHelper.max;

/**
 *
 * @author max
 */
public class Ristinolla {
    
    private char[][] board;
    private int spacesLeft;
    private char currentPlayer;
    private DoubleLinkList availableMoves;
    private DoubleLinkList moves2;
    
    public Ristinolla(int size, boolean xGoesFirst){
        board = new char[size][size];
        if(xGoesFirst) currentPlayer = 'x';
        else currentPlayer = 'o';
        spacesLeft = size * size;
        availableMoves = new DoubleLinkList();
        moves2 = new DoubleLinkList();
        initBoard(size);
    }
    
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
    
    public void initBoard(int size){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                board[i][j] = '.';
                availableMoves.insertLast(new Move(i,j));
                moves2.insertLast(new Move(i,j));
            }
        }
    }
    
    public boolean isBoardFull(){
        if(spacesLeft > 0) return false;
        else return true;
    }
    
    public boolean checkForWin(){
        boolean re = (checkRowsForWin() || checkColsForWin() || checkDiagForWin());
        //if(re) System.out.println("Winning combo found for " + ((currentPlayer == 'o') ? 'x' : 'o'));
        return re;
    }
    
    public boolean checkRowsForWin(){
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
 
    public boolean checkColsForWin(){
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
    
    public boolean checkDiagForWin(){
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
    
    public boolean compareMarks(boolean hasX, boolean hasE, boolean hasO){
            if(hasX && !hasO && !hasE) return true;
            else if (!hasX && hasO && !hasE) return true;
            else return false;
    }
    
    public void changeTurn(){
        if(currentPlayer == 'x') currentPlayer = 'o';
        else currentPlayer = 'x';
    }
    
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
    
    public boolean removeMark(int row, int col){
        if((row >= 0) && (row < board.length)){
            if((col >= 0) && (col < board.length)){
                board[row][col] = '.';
                availableMoves.insertLast(new Move(row,col));
                spacesLeft++;
                return true;
            }
        }
        return false;
    }
    public boolean makeMove(Move move){
        availableMoves.deleteMove(move);
        return placeMark(move.row, move.col);
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
    
    public int evalMove(Move move){
        if(checkForWin()) return 100;
        
        char moveMaker = board[move.row][move.col];
        int cownMarks, coppMarks, cempties;
        cownMarks = coppMarks = cempties =0;
        //Count moves column marks
        for(int i = 0; i<board.length; i++){
            if(board[i][move.col] == '.') cempties++;
            else if(board[i][move.col] == moveMaker) cownMarks++;
            else coppMarks++;
        }
        
        int rownMarks, roppMarks, rempties;
        rownMarks = roppMarks = rempties = 0;
        //Count row marks
        for(int i=0; i<board.length; i++){
            if(board[move.row][i] == '.') rempties++;
            else if(board[move.row][i] == moveMaker) rownMarks++;
            else roppMarks++;
        }
        
        int downMarks, doppMarks, dempties, downMarks2, doppMarks2, dempties2;
        downMarks = doppMarks = dempties = downMarks2 = doppMarks2 = dempties2 =0;
        if(move.row == move.col || move.row + move.col == board.length-1){
            if(move.row == move.col){
                for(int i=0; i<board.length; i++){
                    if(board[i][i] == '.') dempties++;
                    else if(board[i][i] == moveMaker) downMarks++;
                    else doppMarks++;
                }
            }
            if(move.row + move.col == board.length-1){
                for(int i=0, j=board.length-1; i<board.length; i++, j--){
                    if(board[j][i] == '.')dempties2++;
                    else if(board[j][i] == moveMaker) downMarks2++;
                    else doppMarks2++;
                }
            }
        }
        int significantOwnMarks = max(rownMarks, downMarks, downMarks2, cownMarks);
        int significantOppMarks = max(roppMarks, doppMarks, doppMarks2, coppMarks);
        int significantEmpties = max(rempties, dempties, dempties2, cempties);
        if(significantOppMarks >= significantOwnMarks) return significantOppMarks * 4;
        else if (significantOwnMarks > significantOppMarks) return significantOwnMarks * 2;
        else return 1;
        
    }
}



