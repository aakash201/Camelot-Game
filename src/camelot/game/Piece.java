/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camelot.game;

import java.util.ArrayList;

/**
 *
 * @author aakashtyagi
 */
public class Piece {
    public int color,plainMove,canter,jump;
    public String id;
    public int isKnight;
    public Position pos;
    public int[] hash;
    public ArrayList<Move> validMoves;
    public Move tempMove;
    public String bug;
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
    
    public Piece(Piece p)
    {
        color = p.color;isKnight = p.isKnight;plainMove = canter = jump = 0;
        id = p.id;pos = new Position(p.pos);
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
        
        if(var == 1 || var == 2 )
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
        //System.out.println(str);
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
        //System.out.println(str);
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
            //System.out.println(" in jump");
            Piece pc;
            x = (x+x2)/2;
            y = (y + y2)/2;
            //System.out.println(x+" " + y);
            pc = cg.getPiece(x,y);
            pc.pos = new Position(x,y);
            cg.grid[x][y].piece = null;
            cg.grid[x][y].empty = 1;
            //System.out.println(pc.id);
            return pc;
        }
        
        else
        {
            System.out.println("error");
            bug = "error";
            //cg.display();
            System.out.println(x + " " + y + "  " + x2 + " " + y2);
        }
        
        return null;
    }
    
    public void markInHash(Position p)
    {
        int idx;
        idx = (p.row-1)*12 + p.col;
        hash[idx] = 2;
    }
    
    public void unmarkInHash(Position p)
    {
        int idx;
        idx = (p.row-1)*12 + p.col;
        hash[idx] = 1;
    }
    
    int valid(int x,int y)
    {
        if(x>=1 && y>=1 && x<=16 && y<=12)
            return 1;
        return 0;
    }
    int get(int x,int y)
    {
        if(valid(x,y) == 0)
            return -1;
        int idx;
        idx = 12*(x-1) + y;
        idx = hash[idx];
        return idx;
    }
    public ArrayList<Position> getNextPlainMoves()
    {
        ArrayList<Position> plainMove = new ArrayList<Position>();
        int x,y;
        x = pos.row;
        y = pos.col;
        if(get(x+1,y) == 1) plainMove.add(new Position(x+1,y));
        if(get(x+1,y-1) == 1) plainMove.add(new Position(x+1,y-1));
        if(get(x,y-1) == 1) plainMove.add(new Position(x,y-1));
        if(get(x-1,y-1) == 1) plainMove.add(new Position(x-1,y-1));
        if(get(x-1,y) == 1) plainMove.add(new Position(x-1,y));
        if(get(x-1,y+1) == 1) plainMove.add(new Position(x-1,y+1));
        if(get(x,y+1) == 1) plainMove.add(new Position(x,y+1));
        if(get(x+1,y+1) == 1) plainMove.add(new Position(x+1,y+1));
        return plainMove;
    }
    
    public ArrayList<Position> getNextCanterMoves()
    {
        ArrayList<Position> canterMove = new ArrayList<Position>();
        int x,y,c;
        x = pos.row;
        y = pos.col;
        c = (color == 0) ? 3 : 4;
        if(get(x+2,y) == 1 && get(x+1,y) == c) canterMove.add(new Position(x+2,y));
        if(get(x+2,y-2) == 1 && get(x+1,y-1) == c) canterMove.add(new Position(x+2,y-2));
        if(get(x,y-2) == 1 && get(x,y-1) == c) canterMove.add(new Position(x,y-2));
        if(get(x-2,y-2) == 1 && get(x-1,y-1) == c) canterMove.add(new Position(x-2,y-2));
        if(get(x-2,y) == 1 && get(x-1,y) == c) canterMove.add(new Position(x-2,y));
        if(get(x-2,y+2) == 1 && get(x-1,y+1) == c) canterMove.add(new Position(x-2,y+2));
        if(get(x,y+2) == 1 && get(x,y+1) == c) canterMove.add(new Position(x,y+2));
        if(get(x+2,y+2) == 1 && get(x+1,y+1) == c) canterMove.add(new Position(x+2,y+2));
        return canterMove;
    }
    
    public ArrayList<Position> getNextJumpMoves()
    {
        ArrayList<Position> jumpMove = new ArrayList<Position>();
        int x,y,c;
        x = pos.row;
        y = pos.col;
        c = (color == 0) ? 4 : 3;
        if(get(x+2,y) == 1 && get(x+1,y) == c) jumpMove.add(new Position(x+2,y));
        if(get(x+2,y-2) == 1 && get(x+1,y-1) == c) jumpMove.add(new Position(x+2,y-2));
        if(get(x,y-2) == 1 && get(x,y-1) == c) jumpMove.add(new Position(x,y-2));
        if(get(x-2,y-2) == 1 && get(x-1,y-1) == c) jumpMove.add(new Position(x-2,y-2));
        if(get(x-2,y) == 1 && get(x-1,y) == c) jumpMove.add(new Position(x-2,y));
        if(get(x-2,y+2) == 1 && get(x-1,y+1) == c) jumpMove.add(new Position(x-2,y+2));
        if(get(x,y+2) == 1 && get(x,y+1) == c) jumpMove.add(new Position(x,y+2));
        if(get(x+2,y+2) == 1 && get(x+1,y+1) == c) jumpMove.add(new Position(x+2,y+2));
        return jumpMove;
    }
   
    public void getAllJumpMovesUtil(CamelotGame cg)
    {
        ArrayList<Position> temp;
        Position tempPos = new Position(pos);
        int i,x,y,z,c;
        markInHash(pos);
        
        temp = getNextJumpMoves();/*
        System.out.println("start jump\n");
        for(i=0;i<temp.size();i++)
        System.out.println(temp.get(i).toString());
        System.out.println("jump\n");*/
        
        for(i=0;i<temp.size();i++)
        {
            // code for marking middle position as empty
            x = (tempPos.row + temp.get(i).row)/2;
            y = (tempPos.col + temp.get(i).col)/2;
            z = (x-1)*12 + y;
            c = hash[z];
            hash[z] = 1;
            tempMove.chance.add(new Position(temp.get(i)));tempMove.chanceCnt++;
            if(tempMove.checkMove(cg) == 1)
            validMoves.add(new Move(tempMove));
            else System.out.println("wrong move");
            pos = new Position(temp.get(i));
            getAllJumpMovesUtil(cg);
            tempMove.popBack();
            hash[z] = c;
        }
        pos = new Position(tempPos);
        unmarkInHash(pos);
    }
    public void getAllCanterMovesUtil(CamelotGame cg)
    {
        ArrayList<Position> temp;
        Position tempPos = new Position(pos);
        int i,x,y,z,c;
        markInHash(pos);
        temp = getNextCanterMoves();/*
        System.out.println("start canter\n");
        for(i=0;i<temp.size();i++)
        System.out.println(temp.get(i).toString());
        System.out.println("canter\n");*/
        
        for(i=0;i<temp.size();i++)
        {
            tempMove.chance.add(new Position(temp.get(i)));tempMove.chanceCnt++;
            if(tempMove.checkMove(cg) == 1)
            validMoves.add(new Move(tempMove));
            else System.out.println("wrong move");
            pos = new Position(temp.get(i));
            getAllCanterMovesUtil(cg);
            tempMove.popBack();
        }
        if(isKnight == 1)
        {
        temp = getNextJumpMoves();/*
        System.out.println("start jump\n");
        for(i=0;i<temp.size();i++)
        System.out.println(temp.get(i).toString());
        System.out.println("jump\n");*/
        
        for(i=0;i<temp.size();i++)
        {
            x = (tempPos.row + temp.get(i).row)/2;
            y = (tempPos.col + temp.get(i).col)/2;
            z = (x-1)*12 + y;
            c = hash[z];
            hash[z] = 1;
            tempMove.chance.add(new Position(temp.get(i)));tempMove.chanceCnt++;
            if(tempMove.checkMove(cg) == 1)
            validMoves.add(new Move(tempMove));
            else System.out.println("wrong move");
            pos = new Position(temp.get(i));
            getAllJumpMovesUtil(cg);
            tempMove.popBack();
            hash[z] = c;
        }
        }
        pos = new Position(tempPos);
        unmarkInHash(pos);
        
    }
    public void getAllMovesUtil(CamelotGame cg)
    {
        Position tempPos = new Position(pos);
        tempMove.chance.add(new Position(pos));
        tempMove.chanceCnt++;
        ArrayList<Position> temp;
        int i,x,y,z,c;
        temp = new ArrayList<Position>();
        markInHash(pos);
        temp = getNextPlainMoves(); /*
        System.out.println("start plain\n");
        for(i=0;i<temp.size();i++)
        System.out.println(temp.get(i).toString());
        System.out.println("plain\n");*/
           
        for(i=0;i<temp.size();i++)
        {
            tempMove.chance.add(new Position(temp.get(i)));tempMove.chanceCnt++;
            validMoves.add(new Move(tempMove));
            tempMove.popBack();
        }
        
        temp = getNextCanterMoves();/*
        System.out.println("start canter\n");
        for(i=0;i<temp.size();i++)
        System.out.println(temp.get(i).toString());
        System.out.println("canter\n");*/
        
        for(i=0;i<temp.size();i++)
        {
            tempMove.chance.add(new Position(temp.get(i)));tempMove.chanceCnt++;
            if(tempMove.checkMove(cg) == 1)
            validMoves.add(new Move(tempMove));
            else System.out.println("wrong move");
            pos = new Position(temp.get(i));
            getAllCanterMovesUtil(cg);
            tempMove.popBack();
        }
        pos = new Position(tempPos);
        temp = getNextJumpMoves();/*
        System.out.println("start jump\n");
        for(i=0;i<temp.size();i++)
        System.out.println(temp.get(i).toString());
        System.out.println("jump\n");*/
        
        for(i=0;i<temp.size();i++)
        {
            // code for marking middle position as empty
            x = (tempPos.row + temp.get(i).row)/2;
            y = (tempPos.col + temp.get(i).col)/2;
            z = (x-1)*12 + y;
            c = hash[z];
            hash[z] = 1;
            tempMove.chance.add(new Position(temp.get(i)));tempMove.chanceCnt++;
            if(tempMove.checkMove(cg) == 1)
            validMoves.add(new Move(tempMove));
            else System.out.println("wrong move");
            pos = new Position(temp.get(i));
            getAllJumpMovesUtil(cg);
            tempMove.popBack();
            hash[z] = c;
        }
        pos = new Position(tempPos);
        unmarkInHash(pos);
    }
    
    
    public void display()
    {
        System.out.println("\nValid Moves:\n");
        int i,j;
        Move move;
        for(i=0;i<validMoves.size();i++)
        {
            move = validMoves.get(i);
            move.display();
        }
    }
    public ArrayList<Move> getAllMoves( CamelotGame cg)
    {
        hash = cg.getHashCode();
        Position tempPos = new Position(pos);
        validMoves = new ArrayList<Move>();
        tempMove = new Move();
        plainMove = canter=jump = 0;
        getAllMovesUtil(cg);
        pos = new Position(tempPos);
        plainMove = canter=jump = 0;
        //display();
        return validMoves;
    }

    
}
