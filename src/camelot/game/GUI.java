/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camelot.game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;

/**
 *
 * @author aakashtyagi
 */
public class GUI extends JFrame implements MouseListener{
    
    public JButton[][] JButtonArr;
    ImageIcon blackPawn,blackKnight,whitePawn,whiteKnight,castle1,castle2;
    public int moveFlag;
    public CamelotGame game;
    public ArrayList<Piece> deadPieceList;
    Move move;
    
    @Override
    public void mouseClicked(MouseEvent e) {
        JButton btn;
        int var;
        btn = JButtonArr[1][1];
        if(e.getSource() == JButtonArr[1][1])
        {
            /*
            moveFlag = ((moveFlag == 1) ? 0 : 1);
            btn.setText((moveFlag == 1) ? "S" : "M");
            if(moveFlag == 0)
            {
                // add code here
                deadPieceList=game.singleMove(move);
                //refreshGrid(game);
                if(game.checkState() == 0)
                    game.declareWinner();
            }
            else
            {
                move = new Move();
                deadPieceList = new ArrayList<Piece>();
            }
            return ;
                    */
            move = new Move();
            refreshGridUtil(game);
        }
        else if(e.getSource() == JButtonArr[2][1])
        {
            game.revertMove(move, deadPieceList);
            refreshGrid(game);
        }
        else if(e.getSource() == JButtonArr[3][1])
        {
            move = new Move();
            deadPieceList = new ArrayList<Piece>();
            Piece piece;
            int i,j;
            
            
            move = MiniMax.Maxi(3,game).move;
            game.display();
            move.display();
            deadPieceList=game.singleMove(move,1);
            refreshGridUtil(game);
            
        }
        else
        {
            
            int i,j;
            for(i=1;i<=16;i++)
            {
                for(j=1;j<=12;j++)
                {
                    btn = JButtonArr[i][j];
                    if(e.getSource() == btn)
                    {
                        if(moveFlag == 0)
                        {
                            moveFlag = 1;
                            move = new Move();
                            deadPieceList = new ArrayList<Piece>();
                        }
                    
                        Border border = BorderFactory.createBevelBorder(1,Color.BLUE,Color.BLUE);
                        btn.setBorder(border);
                        move.chance.add(new Position(i,j));
                        move.chanceCnt++;
                        
                        if(e.getButton() == MouseEvent.BUTTON3)
                        {
                            moveFlag = 0;
                            deadPieceList=game.singleMove(move,1);
                            refreshGridUtil(game);
                            
                        }
                    
                    }
                }
            }
        }
    }
    
    public GUI(int rows, int cols) {
        moveFlag = 0;
        JButtonArr = new JButton[rows+1][cols+1];
        blackPawn = new ImageIcon("src/images/blackPawn.png");
        whitePawn = new ImageIcon("src/images/whitePawn.png");
        blackKnight = new ImageIcon("src/images/blackKnight.png");
        whiteKnight = new ImageIcon("src/images/whiteKnight.png");
        castle1 = new ImageIcon("src/images/castle1.png");
        castle2 = new ImageIcon("src/images/castle2.png");
        int i,j;
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(rows, cols));
        //ImageIcon pawn = new ImageIcon("src/images/blackKnight.png");
        for (i = 1; i <= 16; i++) {
            for(j = 1 ; j <= 12 ; j++)
            {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50,50));
                //button.setIcon(pawn);
                JButtonArr[i][j] = button;
                pane.add(button);
             
                button.addMouseListener(this);
            }
        }
        JButton btn;
        btn = JButtonArr[1][1];
        btn.setText("Cancel");
        btn = JButtonArr[2][1];
        btn.setText("U");
        btn = JButtonArr[3][1];
        btn.setText("AI");
    }
    
    public void init(CamelotGame cg)
    {
        game = cg;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    
    public void refreshGrid(CamelotGame cg)
    {
        game = cg;
        int i,j,v;
        JButton btn;
        Piece pc;
        for(i=1;i<=16;i++)
        {
            for(j=1;j<=12;j++)
            {
                
                btn = JButtonArr[i][j];
                Border defaultBorder = BorderFactory.createBevelBorder(1);
                btn.setBorder(defaultBorder);
                v = (i%2) ^ (j%2);
                
                if(v == 1)
                    btn.setBackground(Color.YELLOW);
                else btn.setBackground(Color.CYAN);
                pc = cg.getPiece(i,j);
                if(cg.grid[i][j].valid == 0)
                {
                    btn.setBackground(Color.WHITE);
                }
                
                else if(cg.grid[i][j].castle == 1)
                {
                    //System.out.println("castle");
                    if(j % 2 == 0)
                        btn.setIcon(castle1);
                    else btn.setIcon(castle2);
                }
                
                if(pc != null)
                {
                    pc.pos = new Position(i,j);
                    cg.grid[i][j].empty=0;
                    assignPiece(btn,pc);
                }
                else
                {
                    if(cg.grid[i][j].castle != 1)
                    btn.setIcon(null);
                    else btn.setIcon(castle1);
                    cg.grid[i][j].empty = 1;
                }
            }
        }
    }
    
    public void refreshGridUtil(CamelotGame cg)
    {
        game = cg;
        int i,j;
        JButton btn;
        Piece pc;
        for(i=1;i<=16;i++)
        {
            for(j=1;j<=12;j++)
            {
                btn = JButtonArr[i][j];
                Border defaultBorder = BorderFactory.createBevelBorder(1);
                btn.setBorder(defaultBorder);
                //btn.getBorder();
                pc = cg.getPiece(i,j);
                if(pc != null)
                {
                    pc.pos = new Position(i,j);
                    cg.grid[i][j].empty = 0;
                    assignPiece(btn,pc);
                }
                
                else {
                    if ( cg.grid[i][j].castle == 0)
                    btn.setIcon(null);
                    else btn.setIcon(castle1);
                    cg.grid[i][j].empty = 1;
                }
            }
        }
    }
    
    
    
    public void assignPiece(JButton btn, Piece pc)
    {
        if(pc.color == 1)
        {
            btn.setIcon((pc.isKnight == 1 ? blackKnight : blackPawn));
        }
        else
        {
            btn.setIcon((pc.isKnight == 1 ? whiteKnight : whitePawn));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
