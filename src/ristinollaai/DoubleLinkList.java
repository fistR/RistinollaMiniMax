/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ristinollaai;

/**
 *
 * @author max
 */
public class DoubleLinkList {
    
    protected Node head;
    protected Node tail;
    public int size;
    
    public DoubleLinkList(){
        this.head = null;
        this.tail = null;
        size = 0;
    }
    
    public boolean isEmpty(){
        return head == null;
    }
    
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
    
    public void insertLast(Move obj){
        Node nw = new Node(obj, null, null);
        if(this.head == null){
            this.head = nw;
            this.tail = this.head;
        } else {
            nw.setPrev(this.tail);
            this.tail.setNext(nw);
            this.tail = nw;
            
        }
        size++;
    }
    
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
    
    public void deleteMove(Move move){
        int r = move.row;
        int c = move.col;

        Node temp = this.head;
        for(int i = 1; i<= size; i++){
            if(temp.obj.row == r && temp.obj.col == c){
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
