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
public class CamelotGame {

    /**
     * @param args the command line arguments
     */
    public Cell[][] grid;
    public int turn;
    public int state;
    public Move curMove;
    public Player player1,player2;
    public GUI gui;
    public CamelotGame()
    {
        turn = 0;
        int i,j;
        grid = new Cell[17][13];
        for(i=0;i<17;i++)
        {
            for(j=0;j<13;j++)
            {
                grid[i][j] = new Cell();
            }
        }
        for(i=4;i<=13;i++)
        {
            for(j=1;j<=12;j++)
            {
                
                grid[i][j].valid = 1;
            }
        }
        for(j=2;j<=11;j++)
        {
            grid[3][j].valid =grid[14][j].valid = 1;
        }
        for(j=3;j<=10;j++)
        {
            grid[2][j].valid = grid[15][j].valid = 1;
        }
        grid[1][6].valid = grid[1][7].valid =grid[16][6].valid =grid[16][7].valid =1;
        grid[1][6].castle = grid[1][7].castle =grid[16][6].castle =grid[16][7].castle =1;
        // pawn : 0 , white: 0 , knight : 1 , black : 1
        for(j=4;j<=9;j++)
        {
            grid[6][j].setPiece(6,j,0,0);
            grid[11][j].setPiece(11,j,0,1);
        }
        for(j=5;j<=8;j++)
        {
            grid[7][j].setPiece(7,j,0,0);
            grid[10][j].setPiece(10,j,0,1);
        }
        Cell temp;
        temp = new Cell();
        grid[7][4].setPiece(7,4,1,0);grid[7][9].setPiece(7,9,1,0);grid[6][3].setPiece(6,3,1,0);grid[6][10].setPiece(6,10,1,0);
        grid[10][4].setPiece(10,4,1,1);grid[10][9].setPiece(10,9,1,1);grid[11][3].setPiece(11,3,1,1);grid[11][10].setPiece(11,10,1,1);
        // now add gui
        gui = new GUI(16,14);
        
    }
    
    public void display()
    {
        gui.refreshGrid(this);
        int i,j;
        String str;
        str="";
        for(i=16;i>=1;i--)
        {
            for(j=1;j<=12;j++)
            {
                if(grid[i][j].valid == 0)
                {
                    str="0  ";
                }
                else 
                {
                    if(grid[i][j].empty == 1)
                    {
                        if(grid[i][j].castle == 1)
                        {
                            str="C  ";
                        }
                        else str="1  ";
                    }
                    else
                    {
                        if(grid[i][j].castle == 1)
                        {
                            str = "C";
                        }
                        else str="1";
                        str = str + grid[i][j].piece.id;
                    }
                }
                System.out.print(str + " ");
            }
            System.out.println("\n");
        }
    }
    /*
    public int checkState()
    {
        return 0;
    }
    public Move getNextMove()
    {
        Move m;
        m = new Move();
        return m;
    }
    public int checkMove()
    {
        return 0;
    }
    public int executeMove()
    {
        return 0;
    }
    */
    public int checkState()
    {
        return 1;
    }
    
    public void declareWinner()
    {
        
    }
    
    public void singleMove(Move move)
    {
        move.display();
        if(move.checkMove(this) == 1)
        {
            System.out.println("\ncorrect move\n");
            move.executeMove(this);
            if(turn == 0) turn = 1;
            else turn = 0;
            gui.refreshGrid(this);
            this.display();
        }
        else System.out.println("\nincorrect move\n");
    }
    
    Piece getPiece(int row,int col)
    {
        if(grid[row][col].empty == 1)
            return null;
        return (grid[row][col].piece);
    }
    
    public void play()
    {
        gui.refreshGrid(this);
        while(checkState() == 1)
        {
            Move m;
            m = new Move();
            m.getMove();
            if(m.checkMove(this) == 1)
            {
                System.out.println("\ncorrect move\n");
                m.executeMove(this);
                if(turn == 0) turn = 1;
                else turn = 0;
                gui.refreshGrid(this);
                this.display();
            }
            else System.out.println("\nincorrect move\n");
            
        }
        declareWinner();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        CamelotGame game ;
        
        game = new CamelotGame();
        game.gui.init(game);
        game.display();
        //game.play();
        game.display();
    }
    
}
