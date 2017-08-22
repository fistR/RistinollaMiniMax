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
public class Node {
    protected Move obj;
    protected Node next, prev;
    
    public Node(){
        next = null;
        prev = null;
        obj = null;
    }
    
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

    public Object getObj() {
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
