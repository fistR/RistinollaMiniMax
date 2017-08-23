/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ristinollaai;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 *
 * @author max
 */
public class MiniMaxAI {
    
    
    
    public static Move chooseMove(Ristinolla game, char currentPlayer){
        Move bestNextMove = new Move(0,0);
        int bestNextScore = -10000;
        int sz = game.getMoves2().size;
        
        for(int i = 1; i<=sz; i++){
            
            Move currentMove = game.getMoves2().getNode(i).obj;
            game.placeMark(currentMove.row, currentMove.col);
            game.changeTurn();
            currentPlayer = game.getCurrentPlayer();
            //int tempScore = minimaxMove(game, currentPlayer, game.getSpacesLeft()-1, currentMove, 0);
            int tempScore = minimaxMoveAB(game, currentPlayer, game.getSpacesLeft()/2+2, -10000, 10000, currentMove, 0);
            //System.out.println("Movescore " + tempScore + " r:" + currentMove.row + " c:" + currentMove.col);
            if(tempScore > bestNextScore){
                bestNextScore = tempScore;
                bestNextMove = currentMove;
            }
            game.removeMark(currentMove.row, currentMove.col);
            game.changeTurn();
        }

        System.out.println("Chosen score : " + bestNextScore);
        return bestNextMove;
    }
    
        public static int minimaxMoveAB(Ristinolla game, char currentPlayer, int depth, int a, int b, Move lastMove, int score){
        if(depth == 0 || game.checkForWin() || game.getSpacesLeft() == 0){
            if(depth == 0){
                //System.out.println("maxdepth");
                return score;
            };
            if(game.checkForWin() && currentPlayer == 'x') return score+1000+depth;
            else if(game.checkForWin()) return score-1000-depth;
            if(game.getSpacesLeft() == 0) return score;
        }
        if(currentPlayer == 'x') score += game.evalMove(lastMove);
        else score -= game.evalMove(lastMove);
        int sz = game.getMovesLeft().size;
        if(currentPlayer == 'o'){
            int bestScore = -1000;
            for(int i=1; i<=sz; i++){
                Move cur = game.getMovesLeft().getNode(i).obj;
                game.placeMark(cur.row, cur.col);
                game.changeTurn();
                int v = minimaxMoveAB(game, 'x', depth-1, a, b, cur, score);
                game.changeTurn();
                bestScore = max(v, bestScore);
                a = max(a, v);
                game.removeMark(cur.row, cur.col);
                if(b <= a) break;
                
            }
            return bestScore;
        }else {
            int bestScore = 1000;
            
            for(int i=1; i<=sz; i++){
                Move cur = game.getMovesLeft().getNode(i).obj;
                game.placeMark(cur.row, cur.col);
                game.changeTurn();
                //System.out.println("asd" + cur.row + cur.col);
                int v = minimaxMoveAB(game, 'o', depth-1, a, b, cur, score);
                game.changeTurn();
                bestScore = min(v, bestScore);
                game.removeMark(cur.row, cur.col);
                b = min(b, v);
                if(b <= a) break;
            }
            return bestScore;
        }
       
    }
    
    public static int minimaxMove(Ristinolla game, char currentPlayer, int depth, Move lastMove, int score){
        if(depth == 0 || game.checkForWin() || game.getSpacesLeft() == 0){
            if(depth == 0) return 0;
            if(game.checkForWin() && currentPlayer == 'x') return score+1000+depth;
            if(game.checkForWin() && currentPlayer == 'o') return score-1000-depth;
            if(game.getSpacesLeft() == 0) return 0;
        }
        if(currentPlayer == 'x') score += game.evalMove(lastMove);
        else score -= game.evalMove(lastMove);
        
        int sz = game.getMovesLeft().size;
        if(currentPlayer == 'o'){
            int bestScore = -1000;
            for(int i=1; i<=sz; i++){
                Move cur = game.getMovesLeft().getNode(i).obj;
                game.placeMark(cur.row, cur.col);
                game.changeTurn();
                int v = minimaxMove(game, 'x', depth-1, cur, score);
                game.changeTurn();
                bestScore = max(v, bestScore);
                game.removeMark(cur.row, cur.col);
                
            }
            return bestScore;
        }else {
            int bestScore = 1000;
            
            for(int i=1; i<=sz; i++){
                Move cur = game.getMovesLeft().getNode(i).obj;
                game.placeMark(cur.row, cur.col);
                game.changeTurn();
                //if(cur.row == 1 && cur.col == 1) System.out.println("YES");
                //System.out.println("asd" + cur.row + cur.col);
                int v = minimaxMove(game, 'o', depth-1, cur, score);
                //System.out.print(" " + v);
                game.changeTurn();
                bestScore = min(v, bestScore);
                game.removeMark(cur.row, cur.col);
                
            }
            //System.out.print("\n");
            return bestScore;
        }
       
    }
    
    
    public static int minimaxMove2(Ristinolla game, char currentPlayer, int depth){
        int score = 0;
        
        DoubleLinkList avMoves = game.getMovesLeft();
        
        if(game.checkForWin()){
            score = (game.getCurrentPlayer() == 'o' ? 1 : -1);
        } else if(game.getMovesLeft().size != 0){
            char nextPlayer = (currentPlayer == 'o') ? 'x' : 'o';
            int bestVal = (currentPlayer == 'o') ? 1 : -1;
            
            for(int i = 0; i < game.getSpacesLeft() && score != bestVal; i++){
                Move mv = (Move)avMoves.getNode(i+1).obj;
                game.placeMark(mv.row, mv.col);
                int nextScore = minimaxMove2(game, nextPlayer, depth-1);
                game.removeMark(mv.row, mv.col);
                
                if(currentPlayer == 'o' && nextScore > score
                        || currentPlayer == 'x' && nextScore < score
                        || i == 0){
                    score = nextScore;
                }
            }
        }
        
        return score;
    }
}
