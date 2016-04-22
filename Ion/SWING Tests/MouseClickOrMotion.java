import javax.swing.*;

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

import java.util.Timer;
import java.util.TimerTask;

public class MouseClickOrMotion extends JPanel implements MouseMotionListener, MouseListener{

	private BlankArea blankArea = new BlankArea(new Color(20,11,231,188));
    private int board_side;
    private int ball_radius;
    private int[] paddle_length = new int[4];
    private int[] paddle_height = new int[4];
    private JTextArea textArea;
    private final static String newline = "\n";
    Rectangle p1;
    Rectangle p2,p3,p4;
    Rectangle board;
    Circle b1;
    Timer timer;
    int i=0;
    Board board_b;
    int offsetx,offsety;
    int prev_x;
    int[] prev_array = new int [10];
    int prev_array_index=0;
    int smoothen_x=0;
    int diff;

    private static MouseClickOrMotion newContentPane;

    private static JFrame frame = new JFrame("MouseClickOrMotion");

    private int min(int a, int b)
    {
        if (a<b)
            return a;
        else
            return b;
    }

    public MouseClickOrMotion() {
        super(new GridBagLayout());
        GridBagLayout gridbag = (GridBagLayout)getLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setBackground(new Color(0,0,123,255));

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridheight = GridBagConstraints.REMAINDER;

        c.insets = new Insets(1, 1, 1, 1);
        gridbag.setConstraints(blankArea, c);
        blankArea.setBackground(new Color(20,11,231,188));
        add(blankArea);
      
        c.insets = new Insets(0, 0, 0, 0);
        c.gridheight = 200;
        textArea = new JTextArea(300,300);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //scrollPane.setPreferredSize(new Dimension(100, 100));
        gridbag.setConstraints(scrollPane, c);
        //add(scrollPane);

        //Register for mouse events on blankArea and the panel.
        blankArea.addMouseMotionListener(this);
        blankArea.addMouseListener(this);
   
        

        setPreferredSize(new Dimension(1920, 1000));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));


        frame.pack();
        board_side = (int)(0.8*min( (int)(frame.getSize().getHeight()), (int)(frame.getSize().getWidth()) ));
        paddle_length[0] = (int)(board_side*0.25);
        paddle_height[0] = (int)(board_side*0.05);
        //System.out.println(board_side + " " + frame.getSize());

        board_b = new Board();
        b1 = new Circle(300,302,40);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                
                    board_b.update();
                    renderNewCoordinates(board_b.getX(),board_b.getY());
                    if(p1!=null)
                    {
                        diff = p1.getMidX()-prev_x;
                        prev_x = p1.getMidX();
                        
                        smoothen_x = (smoothen_x - prev_array[prev_array_index] +diff);
                        prev_array[prev_array_index] = diff;
                        prev_array_index = (prev_array_index + 1)%10;
 /*For Shreyan*/        System.out.println(smoothen_x);
                        
                    }
            }
        },0,20);


    }

    public void reInitBoard() {
        
        for(int i=0;i<10;i++)
            prev_array[i] = 0;
        smoothen_x=0;

        frame.pack();
        board_side = (int)(0.8*min( (int)(frame.getSize().getHeight()), (int)(frame.getSize().getWidth()) ));
        paddle_length[0] = (int)(board_side*0.25);
        paddle_height[0] = (int)(board_side*0.05);
        paddle_length[2] = (int)(board_side*0.25);
        paddle_height[2] = (int)(board_side*0.05);
System.out.println(board_side);
        paddle_length[1] = (int)(board_side*0.05);
        paddle_height[1] = (int)(board_side*0.25);
        paddle_length[3] = (int)(board_side*0.05);
        paddle_height[3] = (int)(board_side*0.25);
        //System.out.println(board_side + " " + frame.getSize());
       //p1 = new Rectangle((int)(frame.getSize().getWidth()-board_side) + board_side/2, (int)(frame.getSize().getHeight()-board_side) + board_side-paddle_height[0]/2, paddle_length[0], paddle_height[0]);
        p1 = new Rectangle((int)(frame.getSize().getWidth()/2),(int)((frame.getSize().getHeight()+board_side)/2)-paddle_height[0]/2,paddle_length[0],paddle_height[0]);

        p2 = new Rectangle((int)(frame.getSize().getWidth()/2)-paddle_length[1]/2+board_side/2,(int)(frame.getSize().getHeight()/2),paddle_length[1],paddle_height[1]);
        p3 = new Rectangle((int)(frame.getSize().getWidth()/2),(int)((frame.getSize().getHeight()-board_side)/2)+paddle_height[2]/2,paddle_length[2],paddle_height[2]);
        p4 = new Rectangle((int)(frame.getSize().getWidth()/2)+paddle_length[3]/2-board_side/2,(int)(frame.getSize().getHeight()/2),paddle_length[3],paddle_height[3]);

        board = new Rectangle((int)(frame.getSize().getWidth()/2), (int)(frame.getSize().getHeight()/2), board_side, board_side);

        offsetx = (int)(frame.getSize().getWidth()-board_side)/2;
        offsety = (int)(frame.getSize().getHeight()-board_side)/2;

        blankArea.newRect(board,p1,p2,p3,p4);

    }

    public void renderNewCoordinates(int x, int y)
    {
        b1.setMidX(x+offsetx);
        b1.setMidY(y+offsety);
        //System.out.println("Atempting to set:" + x + ":" + y);
        //System.out.println("rnc_1");
        blankArea.newCirc(b1);
        //System.out.println("rnc_2");
        blankArea.newRect(board,p1,p2,p3,p4);
    }


	public void mousePressed(MouseEvent e) {
        for(int i=0;i<e.getClickCount();i++)
            p1.fired_up();
        // saySomething("Mouse pressed (# of clicks: "
        //            + e.getClickCount() + ")", e);
    }

    public void mouseReleased(MouseEvent e) {
        for(int i=0;i<e.getClickCount();i++)
            p1.fired_down();
       //saySomething("Mouse released (# of clicks: "
        //            + e.getClickCount() + ")", e);
    }

    public void mouseEntered(MouseEvent e) {
     //  saySomething("Mouse entered", e);
    }

    public void mouseExited(MouseEvent e) {
       //saySomething("Mouse exited", e);
    }

    public void mouseClicked(MouseEvent e) {
      // saySomething("Mouse clicked (# of clicks: "
       //             + e.getClickCount() + ")", e);
    }

    public void mouseMoved(MouseEvent e) {
        //saySomething1("Mouse moved", e);
        //System.out.println(((int)(frame.getSize().getWidth()/2) + board_side/2 )+ " " + ((int)(frame.getSize().getWidth()/2) - board_side/2) );
        if (e.getX() <= (int)(frame.getSize().getWidth()/2) + board_side/2 - paddle_length[0]/2)
            if(e.getX() >= (int)(frame.getSize().getWidth()/2) - board_side/2 + paddle_length[0]/2 )
                p1.setMidX(e.getX());
            else
                p1.setMidX((int)(frame.getSize().getWidth()/2) - board_side/2 + paddle_length[0]/2 );
        else
            p1.setMidX((int)(frame.getSize().getWidth()/2) + board_side/2 - paddle_length[0]/2);
        //p1.setMidY(e.getY());
        //System.out.println(e.getX()+"\n" + frame.getSize());
        blankArea.newRect(board,p1,p2,p3,p4);
    }

    public void mouseDragged(MouseEvent e) {
       saySomething1("Mouse dragged", e);
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

    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame.setResizable(false);


        //Create and set up the content pane.
        newContentPane = new MouseClickOrMotion();
        newContentPane.setOpaque(true); //content panes must be opaque


        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                // do stuff
                newContentPane.reInitBoard();    
            }
            public void componentHidden(ComponentEvent e) {
                //System.out.println(e.getComponent().getClass().getName() + " --- Hidden");
            }

            public void componentMoved(ComponentEvent e) {
                //System.out.println(e.getComponent().getClass().getName() + " --- Moved");
            }

            public void componentShown(ComponentEvent e) {
                //System.out.println(e.getComponent().getClass().getName() + " --- Shown");

            }
        });

        //frame.add(rect);
        frame.add(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    //public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        //javax.swing.SwingUtilities.invokeLater(new Runnable() {
        //    public void run() {
                //createAndShowGUI();
        //    }
        //});
    //}
    public static void launch(){
        createAndShowGUI();
    }
}