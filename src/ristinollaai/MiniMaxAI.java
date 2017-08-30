package ristinollaai;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * This class handles the AI decision making process
 * using the minimax algorithm with alpha-beta pruning. 
 * Move-ordering is implemented but is not currently used
 * as it reduces performance rather than boosts it in its current state.
 * The depth has been manually selected as the lowest that yields seemingly
 * perfect AI moves. A "perfect" decision would result from a depth equal to the
 * number of empty spaces remaining, but on 4x4 and bigger board sizes it is not
 * feasible as the computation takes way too long. A depth of 5 is selected.
 * 
 * This minimax solution works by taking a list of all the available moves,
 * then placing the AI mark on them and then calling minimax to score that move.
 * Minimax then calls itself recursively alternating between the minimizing and 
 * maximizing player to simulate both players playing perfectly. Then the move
 * with the best score is chosen. AB pruning eliminates the need to search branches
 * that already will be worse than the best one so far.
 * @author max
 */
public class MiniMaxAI {
    /**
     * This is the method called when wanting an AI decision.
     * This method makes a list of the possible moves and scores them
     * with minimax, then returns the best one.
     * @param game
     * @param currentPlayer
     * @return 
     */
    public static Move chooseMove(Ristinolla game, char currentPlayer){
        Move bestNextMove = new Move(0,0);
        int bestNextScore = -10000;
        DoubleLinkList availableMoves = game.calcAvailableMoves();
        
        for(int i = 1; i<=availableMoves.size; i++){
            
            currentPlayer = game.getCurrentPlayer();
            Move currentMove = availableMoves.getNode(i).obj;
            game.placeMark(currentMove.getRow(), currentMove.getCol());
            game.changeTurn();
            int tempScore = minimaxMoveAB2(game, game.getAvailableMoves(), currentPlayer, 5, -10000, 10000, currentMove, 0, GameController.prune);
            if(tempScore > bestNextScore){
                bestNextScore = tempScore;
                bestNextMove = currentMove;
            }
            game.removeMark(currentMove.getRow(), currentMove.getCol());
            game.changeTurn();
        }
        
        System.out.println("Chosen move score : " + bestNextScore);
        return bestNextMove;
    }
    
    /**
     * This is the actual minimax algorithm. 
     * 
     * First it checks if the game game is at an end state,
     * meaning if depth is reached, if the game is won/lost, 
     * or if there are no more available moves. It then returns
     * a score depending on which one. A lot of score for a win
     * for the maximizing player and vice versa. Different paths to loss
     * are differentiated by how many moves it took to reach them. A win with the least
     * steps is the best, and a loss with the most steps is the best loss. Draws
     * and max depth reached are scored by a last move evaluation.
     * 
     * If the game is not at an end state, depending on whether the current
     * simulated player is the maximizing or minimizing player the algorithm calls
     * itself recursively and stopping the iteration for that node if AB condition
     * is met.
     * @param game
     * @param moves
     * @param maximizingPlayer
     * @param depth
     * @param a
     * @param b
     * @param lastMove
     * @param score
     * @param prune
     * @return 
     */
    public static int minimaxMoveAB2(Ristinolla game, DoubleLinkList moves, char maximizingPlayer, int depth, int a, int b, Move lastMove, int score, boolean prune){
        /* Check for game end and score accordingly */
        if(depth == 0 || Ristinolla.checkForWin(game.getBoard()) || game.isBoardFull()){
            if(Ristinolla.checkForWin(game.getBoard()) && game.getCurrentPlayer() == maximizingPlayer) return -100-depth;
            else if(Ristinolla.checkForWin(game.getBoard())) return 100+depth;
            if(game.getSpacesLeft() == 0) return 0-depth + ((game.getCurrentPlayer() == maximizingPlayer) ? -GameController.evalMove(lastMove, game.getBoard()) : GameController.evalMove(lastMove, game.getBoard()));
            if(depth == 0) return (game.getCurrentPlayer() == maximizingPlayer) ? -GameController.evalMove(lastMove, game.getBoard()) : GameController.evalMove(lastMove, game.getBoard());
        }
        /* Simulate best maximizing player move */
        if(maximizingPlayer == game.getCurrentPlayer()){
            int bestScore = -1000;
            DoubleLinkList mvs = game.calcAvailableMoves();
            for(int i=1; i<=mvs.size; i++){
                Move cur = mvs.getNode(i).obj;
                game.placeMark(cur.getRow(), cur.getCol());
                game.changeTurn();
                int v = minimaxMoveAB2(game, moves, maximizingPlayer, depth-1, a, b, cur, score, prune);
                game.changeTurn();
                bestScore = max(v, bestScore);
                a = max(a, v);
                game.removeMark(cur.getRow(), cur.getCol());
                if(b <= a && prune) break;
                
            }
            return bestScore;
        }else { // Simulate best minimizing player move
            int bestScore = 1000;
            DoubleLinkList mvs = game.calcAvailableMoves();
            for(int i=1; i<=mvs.size; i++){
                Move cur = mvs.getNode(i).obj;
                game.placeMark(cur.getRow(), cur.getCol());
                game.changeTurn();
                int v = minimaxMoveAB2(game, moves, maximizingPlayer, depth-1, a, b, cur, score, prune);
                game.changeTurn();
                bestScore = min(v, bestScore);
                game.removeMark(cur.getRow(), cur.getCol());
                b = min(b, v);
                if(b <= a && prune) break;
            }
            return bestScore;
        }
       
    }
    
}

