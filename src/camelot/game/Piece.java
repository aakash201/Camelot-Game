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
public class Piece {
    public int color,plainMove,canter,jump;
    public String id;
    public int isKnight;
    public Position pos;
    
    public Piece()
    {
        id="";
        isKnight = color = plainMove = canter = jump = 0;
    }
    
    public Piece(int type,int clr)
    {
        id="";
        isKnight = color = plainMove = canter = jump = 0;
        id = (type == 1 ? "K" : "P");
        id = id + (clr == 1 ? "B" : "W");
        isKnight = type;
        color = clr;
    }
    
    public String getMoveType(Position start,Position end)
    {
        int var;
        Piece p;
        p = CamelotGame.getPiece(end.row,end.col);
        if(p != null)
            return "none";
        String str = new String();
        str = "plain";
        var = (end.row - start.row)*(end.row-start.row) + (end.col - start.col)*(end.col - start.col);
        if(var == 1 || var == 2)
            return str;
        var = (end.row - start.row)*(end.row-start.row) + (end.col - start.col)*(end.col - start.col);
        if(var == 8 || var == 4)
        {
            p = CamelotGame.getPiece((end.row + start.row)/2,(end.col + start.col)/2);
            if(p != null && p.color == color)
            {
                str="canter";
                return str;
            }
            else if(p!=null)
            {
                str="jump";
                return str;
            }
        }
        str="none";
        return str;
    }
    public int checkNextMove(Position p)
    {
        String str = new String();
        str = getMoveType(pos,p);
        System.out.println(str);
        pos = p;
        if(str=="plain")
        {
            if(plainMove == 0 && canter == 0 && jump == 0)
            {
                plainMove = 1;
                return 1;
            }
            else return 0;
        }
        if(str == "canter")
        {
            if(plainMove == 1 || jump == 1)
            {
                return 0;
            }
            else
            {
                canter = 1;
                return 1;
            }
        }
        if(str == "jump")
        {
            if(isKnight == 0)
            {
                if(canter == 1 || plainMove == 1)
                {
                    return 0;
                }
                else 
                {
                    jump = 1;
                    return 1;
                }
            }
            else
            {
                if(plainMove == 1)
                {
                    return 0;
                }
                else{
                    jump = 1;return 1;
                }
            }
        }
        return 0;
    }
    
    public Piece executeNextMove(Position p)
    {
        String str = new String();
        int x,y,x2,y2;
        str = getMoveType(pos,p);
        System.out.println(str);
        x = pos.row;y = pos.col;x2 = p.row;y2 = p.col;
        CamelotGame.grid[x][y].piece = null;
        CamelotGame.grid[x][y].empty = 1;
        CamelotGame.grid[x2][y2].piece = this;
        CamelotGame.grid[x2][y2].empty = 0;
        pos = new Position(x2,y2);
        if(str == "plain" || str == "canter")
            return null;
        
        else if(str == "jump")
        {
            Piece pc;
            x = (p.row + p.row)/2;
            y = (p.col + p.col)/2;
            
            pc = CamelotGame.getPiece(x2,y2);
            CamelotGame.grid[x][y].piece = null;
            CamelotGame.grid[x][y].empty = 1;
            return pc;
        }
        
        else
        {
            System.out.println("error");
        }
        
        return null;
    }
}
