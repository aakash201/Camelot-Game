/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camelot.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author aakashtyagi
 */
public class GUI extends JFrame implements MouseListener{
    
    public JButton[][] JButtonArr;
    ImageIcon blackPawn,blackKnight,whitePawn,whiteKnight,castle1,castle2;
    JTextField textTurn,textMove,textResult,textPlayerName;
    JButton updateBtn;
    public int moveFlag;
    public CamelotGame game;
    public ArrayList<Piece> deadPieceList;
    Move move;
    
    @Override
    public void mouseClicked(MouseEvent e) {
        JButton btn;
        int var;
        btn = JButtonArr[1][1];
        if(e.getSource() == updateBtn)
        {
            System.out.println("hello");
            game.player2.name = textPlayerName.getText();
            System.out.println(game.player2.name);
        }
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
            updateInfoPanel();
        }
        else if(e.getSource() == JButtonArr[3][1])
        {
            move = new Move();
            deadPieceList = new ArrayList<Piece>();
            Piece piece;
            int i,j;
            
            
            move = MiniMax.Maxi(2,game).move;                     // AI move
            game.display();
            move.display();
            deadPieceList=game.singleMove(move,1);
            refreshGridUtil(game);
            updateInfoPanel();
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
                            updateInfoPanel();
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
        JPanel guiObj = new JPanel(new BorderLayout(30,30));
        JPanel guiObj1 = new JPanel(new GridLayout(rows+1,cols+1));
        //pane.setLayout(new GridLayout(rows, cols));
        //ImageIcon pawn = new ImageIcon("src/images/blackKnight.png");
        guiObj.add(guiObj1);
        for (i = 0; i <= 16; i++) {
            for(j = 0 ; j <= 12 ; j++)
            {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50,50));
                //button.setIcon(pawn);
                JButtonArr[i][j] = button;
                guiObj1.add(button);
                
                button.addMouseListener(this);
            }
        }
        
        
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.CYAN);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10,10,10,10);
        constraints.gridx = 0;
        constraints.gridy = 0; 
        constraints.gridwidth = 2;
        JLabel picLabel = new JLabel(new ImageIcon("src/images/pawnsKnights.png"));
        Border border = BorderFactory.createBevelBorder(1, Color.DARK_GRAY, Color.DARK_GRAY);
        picLabel.setBorder(border);
        panel.add(picLabel, constraints);
        constraints.gridwidth = 1;
        JLabel playerName = new JLabel("  Player Name:");
       
        constraints.gridy = 1;
        panel.add(playerName, constraints);
        constraints.gridx = 1;
        textPlayerName= new JTextField(17);
     
        panel.add(textPlayerName, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        updateBtn = new JButton("Update");
        updateBtn.addMouseListener(this);
        panel.add(updateBtn, constraints);
        JLabel labelTurn = new JLabel("Turn");
        textTurn = new JTextField(17);
        textTurn.setHorizontalAlignment(SwingConstants.CENTER);
        textTurn.setFont(new Font("Times New Roman",1, 13));
        textTurn.setText("A I");
        constraints.gridy = 3;
        panel.add(labelTurn,constraints);
        constraints.gridx = 1;
        panel.add(textTurn,constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        JLabel labelLastMove = new JLabel("Last Move");
        panel.add(labelLastMove,constraints);
        constraints.gridx = 1;
        textMove = new JTextField(17);
        textMove.setHorizontalAlignment(SwingConstants.CENTER);
        textMove.setFont(new Font("Times New Roman",1, 13));
        panel.add(textMove,constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        JLabel labelResult = new JLabel("Result");
        panel.add(labelResult,constraints);
        constraints.gridx = 1;
        textResult=new JTextField(17);
        textResult.setHorizontalAlignment(SwingConstants.CENTER);
        textResult.setFont(new Font("Times New Roman",1, 13));
        panel.add(textResult,constraints);
        /*
        JLabel AIDeadPieces = new JLabel("AI Dead Pieces");
        JLabel playerDeadPieces = new JLabel("Your Dead Pieces");
        JButton[] AIPieces = new JButton[12];
        JButton[] PlayerPieces = new JButton[12];
        constraints.gridx = 0;constraints.gridy = 5;
        panel.add(AIDeadPieces,constraints);
        constraints.gridy = 6;
        for(i=0;i<6;i++)
        {
            AIPieces[i] = new JButton();
            AIPieces[i].setPreferredSize(new Dimension(30,30));
            panel.add(AIPieces[i],constraints);
            constraints.gridx++;
        }
        
        constraints.gridy = 7;constraints.gridx = 0;
        for(i=6;i<12;i++)
        {
            AIPieces[i] = new JButton();
            AIPieces[i].setPreferredSize(new Dimension(30,30));
            panel.add(AIPieces[i],constraints);
            constraints.gridx++;
        }
        */
        guiObj.add(panel,BorderLayout.WEST);
        pane.add(guiObj);
        
        
        JButton btn;
        btn = JButtonArr[1][1];
        btn.setText("Cancel");
        btn = JButtonArr[2][1];
        btn.setText("U");
        btn = JButtonArr[3][1];
        btn.setText("AI");
        
        for(i=1; i<=12; i++)
        {
            btn = JButtonArr[0][i];
            btn.setText(""+i);
            btn.setBackground(Color.WHITE);
        }
        for(i=1;i<=16;i++)
        {
            btn= JButtonArr[i][0];
            btn.setText("" + (char)('A' + i-1));
            btn.setBackground(Color.WHITE);
            
        }
        btn = JButtonArr[0][0];
        btn.setBackground(Color.GREEN);
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
    
    void updateInfoPanel()
    {
        if(game.turn == 0)
        {
            textTurn.setText("AI");
        }
        else textTurn.setText(game.player2.name);
        
        textMove.setText(move.toString());
        
        if(game.winner!=-1)
        {
            String str = new String();
            str = "Game Over. ";
            if(game.winner == 0)
                str = str + "Draw";
            else if(game.winner == 1)
            {
                str = str + "You Lose";
            }
            else if(game.winner == 2)
            {
                str = str + "You won";
            }
            textResult.setText(str);
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
