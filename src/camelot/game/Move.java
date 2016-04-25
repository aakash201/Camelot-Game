/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camelot.game;

import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author aakashtyagi
 */
public class Move {
    
    public ArrayList<Position> chance;
    public int chanceCnt;
    
    public Move()
    {
        chanceCnt = 0;
        chance = new ArrayList<Position>();
    }
    
    public Move(Move m)
    {
        int i;
        chanceCnt = m.chanceCnt;
        chance = new ArrayList<Position>();
        for(i=0;i<m.chanceCnt;i++)
        {
            chance.add(new Position(m.chance.get(i)));
        }
    }
    
    public void getMove()
    {
        int i,a,b;
        Scanner in  = new Scanner(System.in);
        System.out.println("\nEnter no. of moves : ");
        chanceCnt = in.nextInt();
        Position pos;
        for(i=0;i<chanceCnt;i++)
        {
            System.out.println("\nEnter next single move : ");
            a = in.nextInt();
            b = in.nextInt();
            pos = new Position(a,b);
            chance.add(pos);
        }
    }
    
    public void refreshPiece(Piece p)
    {
        p.canter = p.jump = p.plainMove = 0;
    }
    public int checkMove(CamelotGame cg)
    {
        Piece p;
       
        //p = new Piece();
        int i;
        //System.out.println(" " + (chance.get(0)).row + (chance.get(0)).col);
        p = cg.getPiece((chance.get(0)).row,(chance.get(0)).col);
        if(p == null)
        {
            //System.out.println("h");
            return 0;
        }
        //System.out.println("h1" + p.color + cg.turn);
        refreshPiece(p);
        if(cg.turn == p.color)
        {
            p.pos = new Position((chance.get(0)).row,(chance.get(0)).col);
            Position tempPos = new Position(p.pos);
            for(i=1;i<chanceCnt;i++)
            {
                if(p.checkNextMove(chance.get(i),cg) == 0)
                {
                    p.pos = new Position(tempPos);
                    return 0;
                }
            }
            if(p.canter == 1 && p.jump == 0)
            {
                if(chance.get(0).row == chance.get(chanceCnt-1).row && chance.get(0).col == chance.get(chanceCnt-1).col)
                {
                    p.pos = new Position(tempPos);
                    return 0;
                }
            }
            p.pos = new Position(tempPos);
            return 1;
        }
        return 0;
    }
    
    public ArrayList<Piece> executeMove(CamelotGame cg)
    {
        String[][] grid = new String[17][13];
        int i,j;
        /*
        for(i=1;i<=16;i++)
        {
            for(j=1;j<=12;j++)
            {
                grid[i][j] = new String();
                if(cg.grid[i][j].empty == 0)
                    grid[i][j] = cg.grid[i][j].piece.id;
                else if(cg.grid[i][j].valid == 1)
                    grid[i][j] = "1 ";
                else grid[i][j] = "0 ";
            }
        }
        for(i=1;i<=16;i++)
        {
            for(j=1;j<=12;j++)
                System.out.print(grid[i][j] + " ");
            System.out.print("\n");
        }
                
        display();*/
        Piece p,deadPiece;
        ArrayList<Piece> deadPieceList = new ArrayList<Piece>();
        int x,y,x2,y2;
        x = chance.get(0).row;
        y = chance.get(0).col;
        x2 = chance.get(chanceCnt - 1).row;
        y2 = chance.get(chanceCnt - 1).col;
        p = cg.getPiece(x,y);
        p.pos = new Position(x,y);
        if(p.jump == 0)
        {
            cg.grid[x][y].piece = null;
            cg.grid[x][y].empty = 1;
            refreshPiece(p);
            p.pos = new Position(x2,y2);
            cg.grid[x2][y2].piece = p; 
            cg.grid[x2][y2].empty = 0;
        }
        else
        {
           for(i=1;i<chanceCnt;i++)
           {
               p.bug = new String("okay");
               deadPiece = p.executeNextMove(chance.get(i),cg);
               if(p.bug == "error")
               {
                   System.out.println("\nerroneous move : ");
                   for(i=1;i<=16;i++)
                   {
                       for(j=1;j<=12;j++)
                           System.out.print(grid[i][j] + " ");
                       System.out.print("\n");
                   }
                   display();
               }
               if(deadPiece != null)
               {
                   deadPieceList.add(new Piece(deadPiece));
               }
           }
           cg.grid[x][y].piece = null;
           cg.grid[x][y].empty = 1;
           refreshPiece(p);
            p.pos = new Position(x2,y2);
           cg.grid[x2][y2].piece = p;
           cg.grid[x2][y2].empty = 0;
        }
        return deadPieceList;
    }
    
    public void display()
    {
        int i;
        System.out.println("Move");
        System.out.println(chanceCnt);
        for(i=0;i<chanceCnt;i++)
        {
            System.out.print(chance.get(i).row + " "  + chance.get(i).col + "  ");
        }
        System.out.println("\n");
    }
    
    public String toString()
    {
        String str = new String();
        int i;
        for(i=0;i<chanceCnt;i++)
        {
            str = str + (" " + (char) ('A' + chance.get(i).row - 1) + chance.get(i).col + " ");
        }
        return str;
    }
    
    public int popBack()
    {
        if(chanceCnt == 0)
            return 0;
        else
        {
            chanceCnt--;
            chance.remove(chanceCnt);
            return 1;
        }
    }
    
}
