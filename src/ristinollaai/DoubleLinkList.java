package ristinollaai;

/**
 * An implementation of a Doubly Linked List. A list made of Nodes pointing to the next and 
 * the previous Node, where the first Node is the head and the last one the tail.
 * In this project the Node implementation
 * is specific to containing Moves. This list and its features have been implemented
 * in a by-need basis. Other than getters and setters, only methods that have been needed
 * have been implemented.
* @author max
 */
public class DoubleLinkList {
    
    /* The first node */
    protected Node head;
    /* The last node */
    protected Node tail;
    protected int size;
    
    /**
     * The constructor initializes an empty list
     * with the head and tail initially null.
     */
    public DoubleLinkList(){
        this.head = null;
        this.tail = null;
        size = 0;
    }
    /***
     * Method for checking if the list is empty.
     * @return true or false
     */
    public boolean isEmpty(){
        return head == null;
    }
    
    /***
     * Method for inserting a Move at the front
     * of the list. This new Node becomes the head.
     * @param obj A Move.
     */
    public void insertFirst(Move obj){
        Node nw = new Node(obj, null, null);
        if(this.head == null){
            this.head = nw;
            this.tail = this.head;
        } else {
            this.head.setPrev(nw);
            nw.setNext(this.head);
            this.head = nw;
        }
        size++;
    }
    /***
     * A method for inserting an Move to 
     * the end of the list. This Node 
     * becomes the new tail.
     * @param obj A Move
     */
    public void insertLast(Move obj){
        Node nw = new Node(obj, null, null);
        if(this.head == null){
            this.head = nw;
            this.tail = this.head;
        } else {
            this.tail.setNext(nw);
            nw.setPrev(this.tail);
            this.tail = nw;
        }
        size++;
    }
    /***
     * A method for inserting an object at 
     * a given location. If the location is out of bounds
     * the object is inserted last.
     * @param obj
     * @param pos 
     */
    public void insertAt(Move obj, int pos){
        Node nw = new Node(obj, null, null);
        if(this.head == null){
            insertFirst(obj);
        } else if(pos >= size){
            insertLast(obj);
            this.tail = nw;
        } else if(pos == 1){
            this.head.getNext().setPrev(nw);
            nw.setNext(this.head.getNext());
            this.head = nw;
        } else {
            Node temp = this.head;
            for(int i = 2; i<=pos;i++){
                temp = temp.getNext();
                if(i == pos){
                    nw.setNext(temp);
                    nw.setPrev(temp.getPrev());
                    nw.getPrev().setNext(nw);
                    temp.setPrev(nw);
                }
            }
        }
        size++;
        
    }
    /**
     * A method for deleting a Node by its position.
     * @param num 
     */
    public void deleteNode(int num){
        if(num == 1){
            if(size == 1){
                this.head = null;
                this.tail = null;
                size = 0;
                return;
            }
            this.head = this.head.getNext();
            this.head.setPrev(null);
            size--;
            return;
        }
        if(num == size){
            this.tail = this.tail.getPrev();
            this.tail.setNext(null);
            size--;
            return;
        }
        Node temp = this.head.getNext();
        for(int i = 2; i<= size; i++){
            if(i == num){
                Node pr = temp.getPrev();
                Node ne = temp.getNext();
                
                pr.setNext(ne);
                ne.setPrev(pr);
                size--;
                return;
            }
            temp = temp.getNext();
        }
    }
    
    /**
     * A method by deleting a Node by its
     * contained Move.
     * @param move 
     */
    public void deleteMove(Move move){
        int r = move.getRow();
        int c = move.getCol();

        Node temp = this.head;
        for(int i = 1; i<= size; i++){
            if(temp.obj.getRow() == r && temp.obj.getCol() == c){
                if(i == 1){
                    if(i == size){
                        this.head = null;
                        this.tail = null;
                    } else {
                        this.head = temp.getNext();
                        this.head.prev = null;
                    }
                }else if (i == size){
                    this.tail = temp.getPrev();
                    this.tail.next = null;
                } else{
                    Node pr = temp.getPrev();
                    Node ne = temp.getNext();
                
                    pr.setNext(ne);
                    ne.setPrev(pr);
                }
                
                size--;
                return;
            }
            temp = temp.getNext();
        }
    }
    
    /**
     * A method to get the node specified by
     * index.
     * @param num
     * @return 
     */
    public Node getNode(int num){
        if(num == 1){
          return this.head;
        } else if(num == size){
            return this.tail;
        } else {
            Node temp = this.head;
            for(int i = 1; i<= size; i++){
                if(i==num){
                    return temp;
                }
                temp = temp.getNext();
            }
        }
        return null;
    }
 
    
    
}
