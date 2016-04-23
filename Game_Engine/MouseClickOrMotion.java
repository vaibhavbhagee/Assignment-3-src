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

public class MouseClickOrMotion extends JPanel {

	private BlankArea blankArea = new BlankArea(new Color(0.98f, 0.97f, 0.85f));
    private int board_side;
    private int ball_radius=200;
    private int[] paddle_length = new int[4];
    private int[] paddle_height = new int[4];
    private JTextArea textArea;
    private final static String newline = "\n";
    Circle c1;
    private static MouseClickOrMotion newContentPane;
    Timer timer;
    Board board;
    int i=0;

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
        this.setBackground(new Color(123,0,0,255));

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridheight = GridBagConstraints.REMAINDER;

        c.insets = new Insets(1, 1, 1, 1);
        gridbag.setConstraints(blankArea, c);
        blankArea.setBackground(new Color(0,214,0,255));
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
        
   
        

        setPreferredSize(new Dimension(500, 500));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));


        frame.pack();
        board_side = min( (int)(frame.getSize().getHeight()), (int)(frame.getSize().getWidth()) );
       
        System.out.println(board_side + " " + frame.getSize());
        c1 = new Circle(300,400,20);
        ball_radius = 10;
        //Shreyan:
        //This function will take (x,y) and render the ball at the appropriate location. Screen size is approx 1920*1080, and the board is a 1000ish*1000ish square in the center. Put this in the loop.
        //To change ball radius just change ball_radius or something in MouseCLickOrMotion.java
        //There is no boundary checking at the top and bottom edges of the screen
        
        board = new Board(460, 460);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                if(i>-1){
                    //System.out.println(i);
                    ++i;
                    board.update();
                    renderNewCoordinates(board.getX(),board.getY());
                }
            }
        },0,20);

        //c1 = new Rectangle((int)(frame.getSize().getWidth()-board_side) + board_side/2, (int)(frame.getSize().getHeight()-board_side) + board_side-paddle_height[0]/2, paddle_length[0], paddle_height[0]);

        //board = new Rectangle((int)(frame.getSize().getHeight()/2), (int)(frame.getSize().getWidth()/2), board_side, board_side);
    }

    public void reInitBoard() {
        
        frame.pack();
        board_side = min( (int)(frame.getSize().getHeight()), (int)(frame.getSize().getWidth()) );
        
        System.out.println(board_side + " " + frame.getSize());
       //c1 = new Rectangle((int)(frame.getSize().getWidth()-board_side) + board_side/2, (int)(frame.getSize().getHeight()-board_side) + board_side-paddle_height[0]/2, paddle_length[0], paddle_height[0]);
        c1 = new Circle((int)(frame.getSize().getWidth()/2),(int)(frame.getSize().getHeight()/2),ball_radius);
//c1 = new Circle(300,400,100);
        System.out.println("reinitboard");
        blankArea.newCirc(c1);

    }

    public void renderNewCoordinates(int x, int y)
    {
        c1.setMidX(x);
        c1.setMidY(y);
        //System.out.println("Atempting to set:" + x + ":" + y);
        //System.out.println("rnc_1");
        blankArea.newCirc(c1);
        //System.out.println("rnc_2");
    }

  

    public static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame.setResizable(false);
        //c1 = new Circle (0,0,0);
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        newContentPane = new MouseClickOrMotion();


        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                // do stuff
                System.out.println("ye to hua");
                newContentPane.reInitBoard();    
            }
            public void componentHidden(ComponentEvent e) {
                System.out.println(e.getComponent().getClass().getName() + " --- Hidden");
            }

            public void componentMoved(ComponentEvent e) {
                System.out.println(e.getComponent().getClass().getName() + " --- Moved");
            }

            public void componentShown(ComponentEvent e) {
                System.out.println(e.getComponent().getClass().getName() + " --- Shown");

            }
        });

        //Create and set up the content pane.
        newContentPane.setOpaque(true); //content panes must be opaque

//        newContentPane.reInitBoard();    


        //frame.add(rect);
        frame.add(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}