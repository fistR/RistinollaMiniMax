/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ristinollaai.MiniMaxAI;
import ristinollaai.Move;
import ristinollaai.Ristinolla;

/**
 *
 * @author max
 */
public class MiniMaxAITest {
    
    @Test
    public void chooseMoveTest(){
        Ristinolla risti = new Ristinolla(3, true);
        risti.makeMove(new Move(0,0));
        risti.changeTurn();
        Move aimove = MiniMaxAI.chooseMove(risti, 'o');
        assertEquals(1, aimove.getRow());
        assertEquals(1, aimove.getCol());
        risti.makeMove(aimove);
        risti.changeTurn();
        risti.makeMove(new Move(1,0));
        risti.changeTurn();
        aimove = MiniMaxAI.chooseMove(risti, 'o');
        assertEquals(2, aimove.getRow());
        assertEquals(0, aimove.getCol());
    }
}
