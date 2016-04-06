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
    public int checkMove()
    {
        Piece p;
        //p = new Piece();
        int i;
        System.out.println(" " + (chance.get(0)).row + (chance.get(0)).col);
        p = CamelotGame.getPiece((chance.get(0)).row,(chance.get(0)).col);
        if(p == null)
        {
            System.out.println("h");
            return 0;
        }
        System.out.println("h1" + p.color + CamelotGame.turn);
        refreshPiece(p);
        if(CamelotGame.turn == p.color)
        {
            p.pos = new Position((chance.get(0)).row,(chance.get(0)).col);
            for(i=1;i<chanceCnt;i++)
            {
                if(p.checkNextMove(chance.get(i)) == 0)
                    return 0;
            }
            if(p.canter == 1 && p.jump == 0)
            {
                if(chance.get(0).row == chance.get(chanceCnt-1).row && chance.get(0).col == chance.get(chanceCnt-1).col)
                    return 0;
            }
            return 1;
        }
        return 0;
    }
    
    public void executeMove()
    {
        Piece p,deadPiece;
        int x,y,x2,y2,i;
        x = chance.get(0).row;
        y = chance.get(0).col;
        x2 = chance.get(chanceCnt - 1).row;
        y2 = chance.get(chanceCnt - 1).col;
        p = CamelotGame.getPiece(x,y);
        p.pos = new Position(x,y);
        if(p.jump == 0)
        {
            CamelotGame.grid[x][y].piece = null;
            CamelotGame.grid[x][y].empty = 1;
            refreshPiece(p);
            p.pos = new Position(x2,y2);
            CamelotGame.grid[x2][y2].piece = p; 
            CamelotGame.grid[x2][y2].empty = 0;
        }
        else
        {
           for(i=1;i<chanceCnt;i++)
           {
               deadPiece = p.executeNextMove(chance.get(i));
               
           }
           CamelotGame.grid[x][y].piece = null;
           CamelotGame.grid[x][y].empty = 1;
           refreshPiece(p);
            p.pos = new Position(x2,y2);
           CamelotGame.grid[x2][y2].piece = p;
           CamelotGame.grid[x2][y2].empty = 0;
        }
    }
    
}
