
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ristinollaai.DoubleLinkList;
import ristinollaai.Move;
import ristinollaai.Node;

/**
 *
 * @author max
 */
public class NodeTest {
    
    public NodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void coherenceTest(){
        DoubleLinkList dll = new DoubleLinkList();
        dll.insertLast(new Move(0,0));
        dll.insertLast(new Move(1,1));
        dll.insertLast(new Move(2,2));
        dll.insertLast(new Move(3,3));
        dll.insertLast(new Move(4,4));
        dll.deleteNode(3);
        assertEquals(3, dll.getNode(3).getObj().getCol());
        assertEquals(4, dll.getNode(3).getNext().getObj().getCol());
        assertEquals(1, dll.getNode(2).getObj().getCol());
        dll.insertLast(new Move(6,6));
        assertEquals(6, dll.getNode(4).getNext().getObj().getCol());
    }
    
    @Test
    public void defaultConstTest() {
        Node node = new Node();
        assertEquals(null, node.getNext());
        assertEquals(null, node.getPrev());
        assertEquals(null, node.getObj());
    }
    
    @Test
    public void paramConstTest() {
        Move move = new Move(1,1);
        Node next = new Node();
        Node prev = new Node();
        Node node = new Node(move, next, prev);
        assertEquals(next, node.getNext()); 
        assertEquals(prev, node.getPrev());
        assertEquals(move, node.getObj());
    }
    
    @Test
    public void deleteNodeTest(){
        DoubleLinkList dll = new DoubleLinkList();
        dll.insertFirst(new Move(1,1));
        dll.deleteNode(1);
        assertEquals(0, dll.getSize());
        assertEquals(null, dll.getHead());
    }
    
    @Test
    public void deleteMoveTest(){
        DoubleLinkList dll = new DoubleLinkList();
        dll.insertFirst(new Move(1,1));
        dll.insertLast(new Move(2,2));
        dll.deleteMove(new Move(2,2));
        assertEquals(dll.getTail().getObj().getCol(), 1);
    }
    
    @Test
    public void getNodeTest(){
        DoubleLinkList dll = new DoubleLinkList();
        dll.insertFirst(new Move(0,0));
        dll.insertFirst(new Move(1,1));
        dll.insertFirst(new Move(2,2));
        assertEquals(1, dll.getNode(2).getObj().getCol());
    }
}
