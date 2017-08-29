
package ristinollaai;

import static java.lang.System.currentTimeMillis;
import java.util.Scanner;
import static sun.swing.MenuItemLayoutHelper.max;

/**
 * This class controls the game logic. It's methods are static
 * and so acts like a singleton. You do not need to initialize an
 * instance of this.
 * @author max
 */
public class GameController {
    /* This controls whether or not AB pruning is enabled */
    public static boolean prune;
    
    /**
     * A method for running an AI vs AI game loop. 
     * It will also report the total time it took for 
     * the AIs to think when the game is finished.
     * @param game
     * @param sc 
     */
    public static void runEvE(Ristinolla game, Scanner sc){
        boolean gameOngoing = true;
        game.printBoard();
        long timeStart = System.currentTimeMillis();
        while(gameOngoing){
            Move mvToMake = new Move(-1,-1);
            if(game.getCurrentPlayer() == 'o'){
                mvToMake = MiniMaxAI.chooseMove(game, 'o');   
            } else {
                mvToMake = MiniMaxAI.chooseMove(game, 'x'); 
            }
            if(!game.makeMove(mvToMake)) System.out.println("Illegal move!");
            else{
                game.getMoves2().deleteMove(mvToMake);
                if(Ristinolla.checkForWin(game.getBoard())){
                    game.printBoard();
                    System.out.println(game.getCurrentPlayer() + " Wins!");
                    gameOngoing = false;
                } else if (!game.isBoardFull()){
                    game.changeTurn();
                } else {
                    gameOngoing = false;
                    System.out.println("Draw!");
                }
                game.printBoard();
            }
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println("Total thinking time: " + (timeEnd-timeStart) + "ms");
    }
    /**
     * This method runs the Player vs AI game loop. 
     * The player is always 'x' and the AI always 'o'.
     * @param game
     * @param sc 
     */
    public static void runPvE(Ristinolla game, Scanner sc){
        boolean gameOngoing = true;
        game.printBoard();
        while(gameOngoing){
            Move mvToMake = new Move(-1,-1);
            if(game.getCurrentPlayer() == 'o'){
                System.out.println("AI turn. Press enter to continue");
                mvToMake = MiniMaxAI.chooseMove(game, 'o');
                
            } else {
                int targetRow = 0;
                int targetCol = 0;
                System.out.println("Player turn: " + game.getCurrentPlayer());
                System.out.println("Enter row:");
                targetRow = sc.nextInt();
                System.out.println("Enter col:");
                targetCol = sc.nextInt();
                mvToMake = new Move(targetRow, targetCol);
            }
            if(!game.makeMove(mvToMake)) System.out.println("Illegal move! " + mvToMake.getRow() + mvToMake.getCol());
            else{
                System.out.println("Move eval:" + evalMove(mvToMake, game.getBoard())); 
                game.getMoves2().deleteMove(mvToMake);
                if(Ristinolla.checkForWin(game.getBoard())){
                    game.printBoard();
                    System.out.println(game.getCurrentPlayer() + " Wins!");
                    gameOngoing = false;
                } else if (!game.isBoardFull()){
                    game.changeTurn();
                } else {
                    gameOngoing = false;
                    System.out.println("Draw!");
                }
                game.printBoard();
            }
        }
    }
    /**
     * This is the game loop for a player versus player game.
     * The players simply take turns selecting their next move.
     * @param game
     * @param sc 
     */
    public static void runPvP(Ristinolla game, Scanner sc){
        boolean gameOngoing = true;
        game.printBoard();
        while(gameOngoing){
            int targetRow = 0;
            int targetCol = 0;
            System.out.println("Player turn: " + game.getCurrentPlayer());
            System.out.println("Enter row:");
            targetRow = sc.nextInt();
            System.out.println("Enter col:");
            targetCol = sc.nextInt();
            if(!game.placeMark(targetRow, targetCol)) System.out.println("Illegal move!");
            else{
                if(Ristinolla.checkForWin(game.getBoard())){
                    game.printBoard();
                    System.out.println(game.getCurrentPlayer() + " Wins!");
                    gameOngoing = false;
                } else if (!game.isBoardFull()){
                    game.changeTurn();
                } else {
                    gameOngoing = false;
                    System.out.println("Draw!");
                }
                System.out.println("Move eval:" + evalMove(new Move(targetRow, targetCol), game.getBoard()));
                game.printBoard();
            }
        }
    }
    
    /**
     * This method evaluates a given Move on a given
     * Ristinolla game board. It infers the player from
     * the mark placed. This move does not determine the minmax
     * algorithm heuristics, and is more of a general move scoring
     * for a particular move without considering the future. The minmax 
     * algorithm only uses it when maximum depth is reach to differentiate
     * between them by this function.
     * 
     * Currently it gives the most score if you place a mark in a column/row/diagonal
     * that has the most enemy marks and acts as a sort of "blocking enemy win" score.
     * Secondary points come from filling a row/col/diag with own marks but is less important.
     * A win or a loss are naturally the most significant but are potentially redundant.
     * @param move
     * @param board
     * @return 
     */
    public static int evalMove(Move move, char[][] board){
        if(Ristinolla.checkForWin(board)) return 100;
        
        char moveMaker = board[move.getRow()][move.getCol()];
        int cownMarks, coppMarks, cempties;
        cownMarks = coppMarks = cempties =0;
        //Count moves column marks
        for(int i = 0; i<board.length; i++){
            if(board[i][move.getCol()] == '.') cempties++;
            else if(board[i][move.getCol()] == moveMaker) cownMarks++;
            else coppMarks++;
        }
        
        int rownMarks, roppMarks, rempties;
        rownMarks = roppMarks = rempties = 0;
        //Count row marks
        for(int i=0; i<board.length; i++){
            if(board[move.getRow()][i] == '.') rempties++;
            else if(board[move.getRow()][i] == moveMaker) rownMarks++;
            else roppMarks++;
        }
        
        int downMarks, doppMarks, dempties, downMarks2, doppMarks2, dempties2;
        downMarks = doppMarks = dempties = downMarks2 = doppMarks2 = dempties2 =0;
        if(move.getRow() == move.getCol() || move.getRow() + move.getCol() == board.length-1){
            if(move.getRow() == move.getCol()){
                for(int i=0; i<board.length; i++){
                    if(board[i][i] == '.') dempties++;
                    else if(board[i][i] == moveMaker) downMarks++;
                    else doppMarks++;
                }
            }
            if(move.getRow() + move.getCol() == board.length-1){
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
