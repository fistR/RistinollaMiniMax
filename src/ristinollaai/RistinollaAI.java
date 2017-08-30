package ristinollaai;
import java.util.Scanner;

/**
 * This is a short Data Structures and Algorithms project. The subject
 * is a minimax AI with AB pruning for Tic-Tac-Toe (Ristinolla).
 * 
 * You can play with people, versus the AI, or have AI play itself.
 * Minimax algorithm is compared with and without AB pruning easily, 
 * and the difference is quite noticeable.
 * @author max
 */
public class RistinollaAI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean quit = false;
        while(!quit){
            Scanner sc = new Scanner(System.in);
            System.out.println("Size of board(3-5 best):");
            int opt = sc.nextInt();
            System.out.println("Enable AB pruning? default: y (y/n):");
            GameController.prune = (sc.next().charAt(0) == 'n' ? false : true);
            Ristinolla game = new Ristinolla(opt, true);
            System.out.println("1.Person vs Person");
            System.out.println("2.Person vs AI");
            System.out.println("3. AI vs AI");
        
            int option = sc.nextInt();
        
            if(option == 1) GameController.runPvP(game, sc);
            else if(option == 2) GameController.runPvE(game, sc);
            else if(option == 3 ) GameController.runEvE(game, sc);
            System.out.println("Play again?(y/n)");
            quit = (sc.next().charAt(0) == 'n' ? true : false);
        }
    }
    

    
}
