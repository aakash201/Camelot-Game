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
    
    public String getMoveType(Position start,Position end,CamelotGame cg)
    {
        int var;
        Piece p;
        p = cg.getPiece(end.row,end.col);
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
            p = cg.getPiece((end.row + start.row)/2,(end.col + start.col)/2);
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
    public int checkNextMove(Position p,CamelotGame cg)
    {
        String str = new String();
        str = getMoveType(pos,p,cg);
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
    
    public Piece executeNextMove(Position p, CamelotGame cg)
    {
        String str = new String();
        int x,y,x2,y2;
        str = getMoveType(pos,p,cg);
        System.out.println(str);
        x = pos.row;y = pos.col;x2 = p.row;y2 = p.col;
        cg.grid[x][y].piece = null;
        cg.grid[x][y].empty = 1;
        cg.grid[x2][y2].piece = this;
        cg.grid[x2][y2].empty = 0;
        pos = new Position(x2,y2);
        if(str == "plain" || str == "canter")
            return null;
        
        else if(str == "jump")
        {
            System.out.println(" in jump");
            Piece pc;
            x = (x+x2)/2;
            y = (y + y2)/2;
            System.out.println(x+" " + y);
            pc = cg.getPiece(x,y);
            cg.grid[x][y].piece = null;
            cg.grid[x][y].empty = 1;
            System.out.println(pc.id);
            return pc;
        }
        
        else
        {
            System.out.println("error");
        }
        
        return null;
    }

    
}
