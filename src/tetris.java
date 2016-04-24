import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


//TODO Thread suspend
public class tetris extends JFrame implements ActionListener, KeyListener
{

 TetrisPiece currentPiece;

 static JFrame frame = new JFrame("Tetris");
 JMenuBar menuBar = new JMenuBar();
 JMenu game = new JMenu("Game");
 JMenu menuHelp = new JMenu("Help");
 JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X); 
 JMenuItem menuReset = new JMenuItem("Reset",KeyEvent.VK_R);
 JMenuItem help = new JMenuItem("Help",KeyEvent.VK_L);
 JMenuItem about = new JMenuItem("About",KeyEvent.VK_A);
 private JLabel labelArray[][];
 private int lacount;
 private JButton timeButton;
 private boolean timeToggle;
 private Timer timeClock;
 private Icon iconArray[];
 private JLabel timer = new JLabel();
 private JLabel level = new JLabel();
 private JLabel nextPiece = new JLabel();
 private JLabel score = new JLabel();
 private JLabel linesCleared = new JLabel();;
 private int time = 0;
 private int round = 0;
 private int lines = 0;
 private String piece = "L";
 private String names[] = 
  {  "white.jpg", "blue.jpg", "brown.jpg", "green.jpg", "orange.jpg", "pink.jpg", "red.jpg", "yellow.jpg" };


   public tetris(JFrame frame)
   {
    super( "Tetris" );

    Container c = getContentPane();
    c.setLayout( new BorderLayout() );
    
    // add menu bar and its items to gui
    menuBar.add(game);
    menuBar.add(menuHelp);
    exit.addActionListener(this);
    menuReset.addActionListener(this);
    help.addActionListener(this);
    about.addActionListener(this);
    game.add(menuReset);
    game.add(exit);
    menuHelp.add(about);
    menuHelp.add(help);
    frame.setJMenuBar(menuBar);
    
    // set up and add stats panel to main container
    JPanel statsPanel = new JPanel();
    statsPanel.setLayout(new GridLayout());
    timer.setText("   Time: " + time);
    timer.setFont(new Font("Arial",Font.BOLD , 16));
    statsPanel.add(timer,BorderLayout.WEST);
    level.setText("    Level: " + round);
    level.setFont(new Font("Arial",Font.BOLD , 16));
    statsPanel.add(level,BorderLayout.CENTER);
    score.setText("    Score: " + round);
    score.setFont(new Font("Arial",Font.BOLD , 16));
    statsPanel.add(score,BorderLayout.EAST); 
    c.add(statsPanel,BorderLayout.NORTH);
    
    
    // set up and add second stats panel to main container
    JPanel statsPanel2 = new JPanel();
    statsPanel2.setLayout(new GridLayout());
    linesCleared.setText("   Lines Cleared: " + lines);
    linesCleared.setFont(new Font("Arial",Font.BOLD , 16));
    statsPanel2.add(linesCleared,BorderLayout.WEST);
    nextPiece.setText("     Next Piece: " + piece);
    nextPiece.setFont(new Font("Arial",Font.BOLD , 16));
    statsPanel2.add(nextPiece,BorderLayout.EAST);
    c.add(statsPanel2,BorderLayout.SOUTH);
    
    
    JPanel labelPanel = new JPanel ();
    int row = 20;
    int column = 10;
    labelPanel.setLayout (new GridLayout (row, column, 0, 0));
    labelPanel.setSize( 20, 10 );
  
    c.add (labelPanel, BorderLayout.CENTER);
    
    // create and add icons
    iconArray = new Icon [ names.length ];

    for ( int count = 0; count < names.length; count++ ) 
    {
      iconArray[count] = new ImageIcon(names[count]);
    }

    lacount = 0;
    int count = 0;

    labelArray = new JLabel [20][10];
    for ( int r = 0; r < 20; r++ ) 
    {
     for(int col = 0; col < 10; col++)
     {
       labelArray[r][col] = new JLabel ( iconArray [0] );
       labelPanel.add (labelArray [r][col]);
       count++;
     }
    }
    
    int delay = 1000;
    timeClock = new Timer(delay, new TimerHandler () );
    
    frame.add(c,BorderLayout.SOUTH);

    //labelArray[1][4].setIcon(iconArray[1]);

    frame.setVisible(true);


    //TODO
    //BUTTON TO START THE GAME LOOP
    //Start the game
    gameLoop();
   }


   public void gameLoop(){

    //Process game moves
    while (true){
     //Generate a random piece (1-7)
     Random r = new Random();
     int rand = r.nextInt((7-1) + 1) + 1;
     switch(1){
      //I
      case 1: {
       currentPiece = new PieceI();
       break;
      }
      //T
      case 2: {
       currentPiece = new PieceT();
       break;
      }
      //O
      case 3: {
       currentPiece = new PieceO();
       break;
      }
      //L
      case 4: {
       currentPiece = new PieceL();
       break;
      }
      //J
      case 5: {
       currentPiece = new PieceJ();
       break;
      }
      //S
      case 6: {
       currentPiece = new PieceS();
       break;
      }
      //Z
      case 7: {
       currentPiece = new PieceZ();
       break;
      }
      default:
       //Random generation out of bounds, create an I piece
       currentPiece = new PieceI();
       break;
     }

     //TODO Check if the piece can be spawned


     //Display piece
     for(Coordinates c: currentPiece.getPositions()){
      labelArray[c.x][c.y].setIcon(iconArray[rand]);
      System.out.println(currentPiece.getClass());
     }


     //Advance piece until end
     int x = 1;
     while(x == 1){
      //White out current piece
      for(Coordinates c: currentPiece.getPositions()){
       labelArray[c.x][c.y].setIcon(iconArray[0]);
       System.out.println(currentPiece.getClass());
      }

      //Advance piece
      currentPiece.advance(labelArray);

      //Display
      for(Coordinates c: currentPiece.getPositions()){
       labelArray[c.x][c.y].setIcon(iconArray[rand]);
       System.out.println(currentPiece.getClass());
      }

      //Check if at end of field
      for(Coordinates c: currentPiece.getPositions()){
       if (c.x == 19){
        //Break out
        x = 2;
       }
      }

     }

    return;

    }
    //End of forever while loop

   }




   public static void main( String args[] )
   { 
      frame.setLayout(new BorderLayout());
      frame.setSize(300,525);
      
      tetris app = new tetris(frame);

      

      app.addWindowListener(
         new WindowAdapter() {
            public void windowClosing( WindowEvent e )
            {
               System.exit( 0 );
            }
         }
      );
   }

  public void actionPerformed( ActionEvent e)
  {
   GameMenu gm = new GameMenu();
    Object item;
    item = e.getSource(); // get menu item that triggered the event
    
    // match the menu item to the its resulting action
    if(item == menuReset)
    {
         
    }
    else if(item == exit) 
    {
           System.exit(0);
    }
    else if(item == about)
    {
         gm.displayAbout();
    }
    else if(item == help)
    {
         gm.displayHelp();    
    }
    
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }


 // inner class for timer event handling
 private class TimerHandler implements ActionListener 
 {

  // handle button event
  public void actionPerformed( ActionEvent event )
  {
   
  }

 } // end private inner class TimerHandler2
}
