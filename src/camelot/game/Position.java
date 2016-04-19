/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camelot.game;

/**
 *
 * @author aakashtyagi
 */
public class Position {
    
    public int row;
    public int col;
    
    public Position()
    {
        
    }
    
    public Position(int a,int b)
    {
        row = a;col = b;
    }
    
    public Position(Position p)
    {
        this.row = p.row;
        this.col = p.col;
    }
    
    public String toString()
    {
        String str = new String();
        str = row + " " + col;
        return str;
    }
    
}
