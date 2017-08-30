/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;
import ristinollaai.DoubleLinkList;
import ristinollaai.Move;

/**
 *
 * @author max
 */
public class DoubleLinkListTest {
    
    @Test
    public void defaultConstTest(){
        DoubleLinkList dll = new DoubleLinkList();
        assertEquals(null, dll.getHead());
        assertEquals(0, dll.getSize());
        assertEquals(null, dll.getTail());
    }
    
    @Test
    public void isEmptyTest(){
        DoubleLinkList dll = new DoubleLinkList();
        assertEquals(true, dll.isEmpty());
        dll.insertFirst(new Move(1,1));
        assertEquals(false, dll.isEmpty());
    }
    
    @Test
    public void insertFirstTest(){
        DoubleLinkList dll = new DoubleLinkList();
        dll.insertFirst(new Move(1,1));
        dll.insertFirst(new Move(2,2));
        assertEquals(2, dll.getHead().getObj().getCol());
    }
    
    @Test
    public void insertLastTest(){
        DoubleLinkList dll = new DoubleLinkList();
        dll.insertFirst(new Move(1,1));
        dll.insertLast(new Move(2,2));
        assertEquals(dll.getTail().getObj().getCol(), 2);
    }
}
