/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ristinollaai;

import java.util.Scanner;

/**
 *
 * @author max
 */
public class GameController {
    
    
    public static void runEvE(Ristinolla game, Scanner sc){
        boolean gameOngoing = true;
        game.printBoard();
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
                if(game.checkForWin()){
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
            if(!game.makeMove(mvToMake)) System.out.println("Illegal move! " + mvToMake.row + mvToMake.col);
            else{
                System.out.println("Move eval:" + game.evalMove(mvToMake)); 
                game.getMoves2().deleteMove(mvToMake);
                if(game.checkForWin()){
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
                if(game.checkForWin()){
                    game.printBoard();
                    System.out.println(game.getCurrentPlayer() + " Wins!");
                    gameOngoing = false;
                } else if (!game.isBoardFull()){
                    game.changeTurn();
                } else {
                    gameOngoing = false;
                    System.out.println("Draw!");
                }
                System.out.println("Move eval:" + game.evalMove(new Move(targetRow, targetCol)));
                game.printBoard();
            }
        }
    }
}
