package ristinollaai;

/**
 * This is a move-specific Node implementation
 * for a Doubly Lined List.
 * @author max
 */
public class Node {
    protected Move obj;
    protected Node next, prev;
    /* Default constructor initializes everything to null */
    public Node(){
        next = null;
        prev = null;
        obj = null;
    }
    
    /* Parameterized constructor initializes by given values */
    public Node(Move obj, Node ne, Node pr){
        this.obj = obj;
        this.next = ne;
        this.prev = pr;
    }
    
    public void setNext(Node n){
        this.next = n;
    }
    
    public void setPrev(Node p){
        this.prev = p;
    }

    public Move getObj() {
        return obj;
    }

    public void setObj(Move obj) {
        this.obj = obj;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }
    
    
}
