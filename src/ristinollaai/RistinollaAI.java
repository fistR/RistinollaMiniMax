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
public class RistinollaAI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ristinolla game = new Ristinolla(4, true);
        
        System.out.println("1.Person vs Person");
        System.out.println("2.Person vs AI");
        System.out.println("3. AI vs AI");
        
        int option = sc.nextInt();
        
        if(option == 1) GameController.runPvP(game, sc);
        else if(option == 2) GameController.runPvE(game, sc);
        else if(option == 3 ) GameController.runEvE(game, sc);
    }
    

    
}
