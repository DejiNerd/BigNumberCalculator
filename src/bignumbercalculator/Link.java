/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bignumbercalculator;

/**
 *
 * @author DeiNerd
 */
public class Link {
    public int val = 0;
    public Link prev = null;
    public Link next = null;
    
    public Link(int val) {
    this.val = val;
    next = null;
    prev = null;
    }
}
