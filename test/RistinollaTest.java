/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ristinollaai.Move;
import ristinollaai.Ristinolla;
/**
 *
 * @author max
 */
public class RistinollaTest {
    Ristinolla risti;
    public RistinollaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        risti = new Ristinolla(3, true);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void constTest(){
        Ristinolla r = new Ristinolla(3, true);
        Ristinolla r2 = new Ristinolla(4, false);
        assertEquals(3, r.getBoard().length);
        assertEquals(4, r2.getBoard().length);
        assertEquals('x', r.getCurrentPlayer());
        assertEquals('o', r2.getCurrentPlayer());
        assertEquals(9, r.getSpacesLeft());
        assertEquals(16, r2.getSpacesLeft());
        assertEquals(16, r2.getAvailableMoves().getSize());
        assertEquals(9, r.getAvailableMoves().getSize());
    }
    
    @Test
    public void initBoardTest(){
        risti.initBoard(3);
        boolean allEmpty = true;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(risti.getBoard()[i][i] != '.') allEmpty = false;    
            }
        }
        assertEquals(true, allEmpty);
        assertEquals(18, risti.getMovesLeft().getSize());
        assertEquals(18, risti.getMoves2().getSize());
    }
    
    @Test
    public void calcAvailableMovesTest(){
        risti = new Ristinolla(3, true);
        risti.placeMark(1, 1);
        risti.placeMark(0, 0);
        risti.placeMark(2, 2);
        
        assertEquals(6, risti.calcAvailableMoves().getSize());
        risti.placeMark(0, 1);
        assertEquals(5, risti.calcAvailableMoves().getSize());
    }
    @Test
    public void isBoardFullTest(){
        risti = new Ristinolla(3,true);
        assertEquals(false, risti.isBoardFull());
        risti.placeMark(0, 1);
        risti.placeMark(1, 1);
        risti.placeMark(0, 0);
        risti.placeMark(2, 2);
        risti.placeMark(0, 2);
        risti.placeMark(1, 0);
        risti.placeMark(1,2);
        risti.placeMark(2,0);
        risti.placeMark(2, 1);
        assertEquals(true, risti.isBoardFull());
    }
    
    @Test
    public void checkRowsForWinTest(){
        risti = new Ristinolla(3, true);
        assertEquals(false, Ristinolla.checkRowsForWin(risti.getBoard()));
        risti.placeMark(0, 0);
        risti.placeMark(1,1);
        risti.placeMark(2,2);
        assertEquals(false, Ristinolla.checkRowsForWin(risti.getBoard()));
        risti.placeMark(0, 1);
        risti.placeMark(0, 2);
        assertEquals(true, Ristinolla.checkRowsForWin(risti.getBoard()));
    }
    
    @Test
    public void checkColsForWinTest(){
        assertEquals(false, Ristinolla.checkColsForWin(risti.getBoard()));
        risti.placeMark(2, 1);
        risti.placeMark(1, 1);
        risti.placeMark(0, 1);
        assertEquals(true, Ristinolla.checkColsForWin(risti.getBoard()));
        
    }
    
    @Test
    public void checkDiagForWinTest(){
        risti = new Ristinolla(3, true);
        risti.placeMark(0, 0);
        risti.placeMark(1,1);
        risti.placeMark(2,2);
        assertEquals(true, Ristinolla.checkDiagForWin(risti.getBoard()));
        risti = new Ristinolla(3,true);
        assertEquals(false, Ristinolla.checkDiagForWin(risti.getBoard()));
        risti.placeMark(0, 2);
        risti.placeMark(1, 1);
        risti.placeMark(2, 0);
        assertEquals(true, Ristinolla.checkDiagForWin(risti.getBoard()));
    }
    
    @Test
    public void compareMarksTest(){
        assertEquals(true, Ristinolla.compareMarks(true, false, false));
        assertEquals(true, Ristinolla.compareMarks(false, false, true));
        assertEquals(false, Ristinolla.compareMarks(true, false, true));
        assertEquals(false, Ristinolla.compareMarks(true, true, false));
        assertEquals(false, Ristinolla.compareMarks(true, true, true));
    }
    
    @Test
    public void changeTurnTest(){
        assertEquals('x', risti.getCurrentPlayer());
        risti.changeTurn();
        assertEquals('o', risti.getCurrentPlayer());
        risti.changeTurn();
        assertEquals('x', risti.getCurrentPlayer());
    }
    
    @Test
    public void placeMarkTest(){
        risti = new Ristinolla(3, true);
        assertEquals(9, risti.calcAvailableMoves().getSize());
        risti.placeMark(1, 1);
        assertEquals(8, risti.calcAvailableMoves().getSize());
        assertEquals('x', risti.getBoard()[1][1]);        
    }
    
    @Test
    public void removeMarkTest(){
        risti = new Ristinolla(3, true);
        risti.placeMark(1, 1);
        assertEquals('x', risti.getBoard()[1][1]);
        risti.removeMark(1,1);
        assertEquals('.', risti.getBoard()[1][1]);
        assertEquals(9, risti.calcAvailableMoves().getSize());
    }
    @Test
    public void makeMoveTest(){
        risti = new Ristinolla(3, true);
        risti.makeMove(new Move(1,1));
        assertEquals('x', risti.getBoard()[1][1]);
    }
}
