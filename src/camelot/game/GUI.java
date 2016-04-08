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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author aakashtyagi
 */
public class GUI extends JFrame implements ActionListener{
    
    public JButton[][] JButtonArr;
    ImageIcon blackPawn,blackKnight,whitePawn,whiteKnight,castle1,castle2;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public GUI(int rows, int cols) {
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

                button.addActionListener(this);
            }
        }
    }
    
    public void init()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    
    public void refreshGrid(CamelotGame cg)
    {
        int i,j,v;
        JButton btn;
        Piece pc;
        
        
        for(i=1;i<=16;i++)
        {
            for(j=1;j<=12;j++)
            {
                
                btn = JButtonArr[i][j];
                v = (i%2) ^ (j%2);
                
                if(v == 1)
                    btn.setBackground(Color.YELLOW);
                else btn.setBackground(Color.CYAN);
                
                if(cg.grid[i][j].valid == 0)
                {
                    btn.setBackground(Color.WHITE);
                }
                
                if(cg.grid[i][j].castle == 1)
                {
                    if(j % 2 == 0)
                        btn.setIcon(castle1);
                    else btn.setIcon(castle2);
                }
                pc = cg.getPiece(i,j);
                if(pc != null)
                {
                    assignPiece(btn,pc);
                }
                else btn.setIcon(null);
                
                
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
    
  
    
    
    
}
