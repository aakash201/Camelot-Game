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
public class Cell {
    public int empty;
    public int castle;
    public Piece piece;
    public int valid;
   
    
    public Cell()
    {
        valid = 0;
        empty = 1;
        castle = 0;
        
    }
    
    public void setPiece(int i,int j,int type,int clr)
    {
        if((i==1 || i==16) && (j==6 || j==7))
            castle = 1;
        else castle = 0;
        empty = 0;
        valid = 1;
        piece = new Piece(type,clr);
    }
    
    public void setPiece(Piece pc)
    {
        if(pc == null)
        {
            empty = 1;
            piece = null;
        }
        else
        {
            empty = 0;
            piece = new Piece(pc);
        }
    }
    
}
