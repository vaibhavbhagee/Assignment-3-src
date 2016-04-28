import Game_Engine.*;

import javax.swing.*;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import java.awt.Font;

import java.util.Timer;
import java.util.TimerTask;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

public class MouseClickOrMotion extends JPanel implements MouseMotionListener, MouseListener{

	private BlankArea blankArea = new BlankArea(new Color(20,11,231,188));
    private int board_side;
    private int ball_radius;
    private int[] paddle_length = new int[4];
    private int[] paddle_height = new int[4];
    private JTextArea textArea;
    private final static String newline = "\n";

    private boolean disableMouse = false;
    
    private Rectangle p1;
    private Rectangle p2,p3,p4;
    private Rectangle board;
    private Circle b1;
    
    private boolean onlyGodKnowsWhyThisFunctionIsBeingCalledTwice = false;

    private Rectangle l1,l2,l3,l4;

    private Timer timer;
    private Timer timer2;
    private int i=0;
    private Board board_b;

    private Board boardFromPrevPage;

    private int offsetx,offsety;
    private int prev_x;
    private int[] prev_array = new int [10];
    private int prev_array_index=0;
    private int smoothen_x=0;
    private int diff;
    private String playerName;
    private int individualDiff;

    private int gameMode=0;
    long cur=0;
long curp=0;
long c1=0,c2=0;

    private boolean soundplayed = false;

    private Game_Engine.DataForEngine dfe = new Game_Engine.DataForEngine();
    private Game_Engine.DataForUI dfui = new Game_Engine.DataForUI();

    private  MouseClickOrMotion newContentPane;

    private  JFrame frame = new JFrame("PingPong");

    private int min(int a, int b)
    {
        if (a<b)
            return a;
        else
            return b;
    }

    public void playSound(String clipname)
    {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
              MouseClickOrMotion.class.getResourceAsStream(clipname));
            clip.open(inputStream);
            clip.start(); 
          } catch (Exception e) {
            System.err.println(e.getMessage());
          }
    }

    public MouseClickOrMotion(String name, int mode, int isHost, Board b_old, int individualDifficulty) {
        super(new GridBagLayout());
        System.out.println("MCOM");
        boardFromPrevPage = b_old;
        mode = gameMode;
        individualDiff = individualDifficulty;
        playerName = name;
        GridBagLayout gridbag = (GridBagLayout)getLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setBackground(new Color(0,0,0,255));

        l1 = new Rectangle(0,0,0,0,0);
        l2 = new Rectangle(0,0,0,0,0);
        l3= new Rectangle(0,0,0,0,0);
        l4 = new Rectangle(0,0,0,0,0);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridheight = GridBagConstraints.REMAINDER;

        c.insets = new Insets(1, 1, 1, 1);
        gridbag.setConstraints(blankArea, c);
        blankArea.setBackground(new Color(160,32,240,188));
        add(blankArea);
      
        c.insets = new Insets(0, 0, 0, 0);
        c.gridheight = 200;
        textArea = new JTextArea(300,300);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gridbag.setConstraints(scrollPane, c);

        blankArea.addMouseMotionListener(this);
        blankArea.addMouseListener(this);
 
        setPreferredSize(new Dimension(960, 700));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        frame.pack();
        board_side = (int)(0.8*min( (int)(frame.getSize().getHeight()), (int)(frame.getSize().getWidth()) ));
        paddle_length[0] = (int)(board_side*0.25);
        paddle_height[0] = (int)(board_side*0.05);

        // board_b = new Board(board_side,board_side);
        b1 = new Circle(300,302,40);
        
        timer = new Timer();
        timer2 = new Timer();

        Timer timer3 = new Timer();
        timer3.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run()
            {

                //if(dfui!=null)dfui.redLife(0);
                //if(dfui!=null)dfui.redLife(1);
                //if(dfui!=null)dfui.redLife(2);
                //if(dfui!=null)dfui.redLife(3);
            }
        },0,1000);

        timer2.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                if(blankArea.renderDone == true)
                {
                    if(board_b!=null && board_b.getSpeed()>12)
                        blankArea.trail_on();
                    else
                        blankArea.trail_off();
                    blankArea.renderDone = false;
                    blankArea.reDraw();
                }    
                c1++;
                //System.out.println("c1:"+c1);
            }
        },0,20);

        blankArea.trail_on();

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                i++;
                cur=System.currentTimeMillis();
                blankArea.setTrails();
                    if(p1!=null)
                    {
                        diff = p1.getMidX()-prev_x;
                        prev_x = p1.getMidX();
                        
                        smoothen_x = (smoothen_x - prev_array[prev_array_index] +diff);
                        prev_array[prev_array_index] = diff;
                        prev_array_index = (prev_array_index + 1)%10;
                        dfe.setAll(p1.getMidX()-paddle_length[0]/2-offsetx,p1.getMidX()+paddle_length[0]/2-offsetx,p1.getFired(),smoothen_x);
                    }
                    try{
                        dfui = 
                        board_b.update(
                            dfe);
                    }catch(Exception e){e.printStackTrace();System.out.println("bancho");}

                    try{
                        if(i>3)
                        {
                            blankArea.replaceLives1(new ShowString("PLAYER1:: lives="+dfui.getLife(0),(int)(frame.getSize().getWidth()/3-board_side/2),(int)(frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20), board_b.playerIName(0)));
                            blankArea.replaceLives2(new ShowString("PLAYER2:: lives="+dfui.getLife(1),(int)(frame.getSize().getWidth()/3-board_side/2),(int)(frame.getSize().getHeight()/3)+60,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20), board_b.playerIName(1)));
                            blankArea.replaceLives3(new ShowString("PLAYER3:: lives="+dfui.getLife(2),(int)(frame.getSize().getWidth()/3-board_side/2),(int)(frame.getSize().getHeight()/3)+120,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20), board_b.playerIName(2)));
                            blankArea.replaceLives4(new ShowString("PLAYER4:: lives="+dfui.getLife(3),(int)(frame.getSize().getWidth()/3-board_side/2),(int)(frame.getSize().getHeight()/3)+180,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20), board_b.playerIName(3)));
                            //blankArea.reDraw2();
                        }
                    }catch(Exception e){e.printStackTrace();}
                    

                    renderNewCoordinates(dfui.getBallX(),dfui.getBallY());
                    try{
                        //System.out.println(dfui.paddle_pos[1]+":"+dfui.paddle_pos[2]+":"+dfui.paddle_pos[3]+"?"+offsety);
                        p2.setMidY(dfui.paddle_pos[1]+offsety);
                        p3.setMidX(dfui.paddle_pos[2]+offsetx);
                        p4.setMidY(dfui.paddle_pos[3]+offsety);
                        p2.setFired(dfui.getPowers()[1]);
                        p3.setFired(dfui.getPowers()[2]);
                        p4.setFired(dfui.getPowers()[3]);
                        
                        if(dfui.getBallPaddleCollide())
                            playSound("ballpaddlecollision.wav");
                        if(dfui.getBallWallCollide())
                            playSound("ballwallcollision.wav");

                        blankArea.addRedRectangles(5-dfui.getLife(0),5-dfui.getLife(1),5-dfui.getLife(2),5-dfui.getLife(3));

                        if(blankArea.redZone1.marJaApproval())
                        {
                            disableMouse = true;
                            p1.killPaddle();
                            blankArea.showGameOverLol();
                            if(soundplayed == false)
                            {
                                playSound("gameover.wav");
                                soundplayed = true;
                            }
                        }
                        if(blankArea.redZone2.marJaApproval())
                            p2.killPaddle();
                        if(blankArea.redZone3.marJaApproval())
                            p3.killPaddle();
                        if(blankArea.redZone4.marJaApproval())
                            p4.killPaddle();

                    }catch(Exception e){e.printStackTrace();System.out.println("maakichy");}
     

            }
        },0,20);


    }

    public void reInitBoard() {
        System.out.println("reInitBoard");
        if(onlyGodKnowsWhyThisFunctionIsBeingCalledTwice == false)
        {
            for(int i=0;i<10;i++)
                prev_array[i] = 0;
            smoothen_x=0;

            frame.pack();


            board_side = (int)(0.8*min( (int)(frame.getSize().getHeight()), (int)(frame.getSize().getWidth()) ));
            

            if(boardFromPrevPage == null)
            {
                System.out.println("wtf");
                board_b = new Board(board_side,board_side,playerName,0,0);
                System.out.println("ftw");
                board_b.setParams(board_side,board_side);
            }    
            else
            {
                 board_b = boardFromPrevPage;
                 board_b.setParams(board_side,board_side);
            }   
            board_b.setGameMode(gameMode);

            if(individualDiff>0)
                board_b.setIndividualAILevel(individualDiff);

            //System.out.println("banka"+board_side);
            
            paddle_length[0] = (int)(board_side*0.25);
            paddle_height[0] = (int)(board_side*0.05);
            paddle_length[2] = (int)(board_side*0.25);
            paddle_height[2] = (int)(board_side*0.05);

            paddle_length[1] = (int)(board_side*0.05);
            paddle_height[1] = (int)(board_side*0.25);
            paddle_length[3] = (int)(board_side*0.05);
            paddle_height[3] = (int)(board_side*0.25);

            blankArea.addString(new ShowString("PLAYER1:: lives="+dfui.getLife(0),(int)(frame.getSize().getWidth()/3-board_side/2),(int)(frame.getSize().getHeight()/3)+400,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20), board_b.playerIName(0)));
            blankArea.addString(new ShowString("PLAYER2:: lives="+dfui.getLife(1),(int)(frame.getSize().getWidth()/3-board_side/2),(int)(frame.getSize().getHeight()/3)-80,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20), board_b.playerIName(1)));
            blankArea.addString(new ShowString("PLAYER3:: lives="+dfui.getLife(2),(int)(frame.getSize().getWidth()/3-board_side/2),(int)(frame.getSize().getHeight()/3)-180,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20), board_b.playerIName(2)));
            blankArea.addString(new ShowString("PLAYER4:: lives="+dfui.getLife(3),(int)(frame.getSize().getWidth()/3-board_side/2),(int)(frame.getSize().getHeight()/3)+580,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20), board_b.playerIName(3)));

            p1 = new Rectangle((int)(frame.getSize().getWidth()/2),(int)((frame.getSize().getHeight()+board_side)/2)-paddle_height[0]/2,paddle_length[0],paddle_height[0],1);
            p4 = new Rectangle((int)(frame.getSize().getWidth()/2)-paddle_length[1]/2+board_side/2,(int)(frame.getSize().getHeight()/2),paddle_length[1],paddle_height[1],2);
            p3 = new Rectangle((int)(frame.getSize().getWidth()/2),(int)((frame.getSize().getHeight()-board_side)/2)+paddle_height[2]/2,paddle_length[2],paddle_height[2],3);
            p2 = new Rectangle((int)(frame.getSize().getWidth()/2)+paddle_length[3]/2-board_side/2,(int)(frame.getSize().getHeight()/2),paddle_length[3],paddle_height[3],4);

            board = new Rectangle((int)(frame.getSize().getWidth()/2), (int)(frame.getSize().getHeight()/2), board_side, board_side,0);
            board.overridecolor = 1;
            board.overridecolorwith = new Color(255,255,0,255);

            blankArea.setGameOverDim(frame.getSize(),board_side);

            blankArea.gWidth = (int)(frame.getSize().getWidth());
            blankArea.gHeight = (int)(frame.getSize().getHeight());
            blankArea.bSize = board_side;
            blankArea.setRed();

            offsetx = (int)(frame.getSize().getWidth()-board_side)/2;
            offsety = (int)(frame.getSize().getHeight()-board_side)/2;

            blankArea.newRect(board,p1,p2,p3,p4);
            onlyGodKnowsWhyThisFunctionIsBeingCalledTwice = true;
        }

    }

    public void renderNewCoordinates(int x, int y)
    {
        b1.setMidX(x+offsetx);
        b1.setMidY(y+offsety);

        blankArea.newCirc(b1);
        blankArea.newRect(board,p1,p2,p3,p4);
    }


	public void mousePressed(MouseEvent e) {
        if(soundplayed == false)
        {
            for(int i=0;i<e.getClickCount();i++)
                p1.fired_up();
        }
        else
        {
            if(gameMode!=0)
                board_b.end_game();
            frame.dispose();
            timer.cancel();
            timer.purge();
            (new Start()).launch(); frame.dispose();
        }
    }

    public void mouseReleased(MouseEvent e) {
            p1.fired_down();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        if(disableMouse == false)
        {
            if (e.getX() <= (int)(frame.getSize().getWidth()/2) + board_side/2 - paddle_length[0]/2)
                if(e.getX() >= (int)(frame.getSize().getWidth()/2) - board_side/2 + paddle_length[0]/2 )
                    p1.setMidX(e.getX());
                else
                    p1.setMidX((int)(frame.getSize().getWidth()/2) - board_side/2 + paddle_length[0]/2 );
            else
                p1.setMidX((int)(frame.getSize().getWidth()/2) + board_side/2 - paddle_length[0]/2);
                   blankArea.newRect(board,p1,p2,p3,p4);
        }

    }

    public void mouseDragged(MouseEvent e) {
        if(disableMouse == false)
        {
            if (e.getX() <= (int)(frame.getSize().getWidth()/2) + board_side/2 - paddle_length[0]/2)
                if(e.getX() >= (int)(frame.getSize().getWidth()/2) - board_side/2 + paddle_length[0]/2 )
                    p1.setMidX(e.getX());
                else
                    p1.setMidX((int)(frame.getSize().getWidth()/2) - board_side/2 + paddle_length[0]/2 );
            else
                p1.setMidX((int)(frame.getSize().getWidth()/2) + board_side/2 - paddle_length[0]/2);
                   blankArea.newRect(board,p1,p2,p3,p4);
        }
    }

    void saySomething(String eventDescription, MouseEvent e) {
        textArea.append(eventDescription + " detected on "
                        + e.getComponent().getClass().getName()
                        + "." + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    void saySomething1(String eventDescription, MouseEvent e) {
        textArea.append(eventDescription 
                        + " (" + e.getX() + "," + e.getY() + ")"
                        + " detected on "
                        + e.getComponent().getClass().getName()
                        + newline);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    private void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame.setResizable(false);

        //Create and set up the content pane.
        //newContentPane = new MouseClickOrMotion();
        newContentPane = this;
        newContentPane.setOpaque(true); //content panes must be opaque


        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                newContentPane.reInitBoard();    
            }
            public void componentHidden(ComponentEvent e) {
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }
        });

        frame.add(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


    public void launch(){
        createAndShowGUI();
    }
}