//Implement singleton and factory pattern

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;

/**
 * Tetris.java - The Tetris class handles the main game loop, GUI creation, and
 * all thread/listener processing. Upon execution of the program, the user is presented
 * with the GUI, and the ability to select from the menu options as well as starting the game.
 *
 * Once the game is started, Tetris pieces are generated, the user can control actions with the keyboard,
 * and the game can be restarted. Score calculation, game speed, and other game related properties are displayed
 * to the user on the top and bottom sides of the game board, and are updated when necessary.
 *
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #05 - Tetris
 * @category    GUI, Main Loop
 * @author      Marek Rybakiewicz
 * @author      Michael McClory
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Tetris extends JFrame implements ActionListener, KeyListener {

    /**
     * An instance of the TetrisPiece class, serves as the current tetris piece in play.
     * The type of subclass is randomly generated upon the start of every piece cycle,
     * and correct functions are inherited by means of polymorphism.
     * @type TetrisPiece
     */
    TetrisPiece currentPiece;

    /**
     * The thread that handles moving the current tetris piece down the gameboard.
     * All manipulation functions are handled by the keylistener and pause execution of this thread
     * until the action is performed.
     * @type PieceThread
     */
    PieceThread pieceThread;

    /**
     * The color of the current piece in play.
     * Used so that every piece may maintain color consistency from game to game,
     * since indices of the icon array correspond with the piece type.
     * @type int
     */
    int currentColor;

    /**
     * The time delay between each descent of the piece on the gameboard.
     * Used for increasing difficulty between levels and calculating score.
     * @type int
     */
    int timeout;

    /**
     * The main loop that generates a piece and creates a PieceThread.
     * Pieces are generated randomly each cycle.
     * @type GameThread
     */
    GameThread gameLoop;

    /**
     * Indicates whether the game is currently in progress or not.
     * Used for starting a new game by triggering a thread flag.
     * @type int
     * @val 0  Indicates game is not running
     * @val 1  Indicates game is running
     */
    int gameRunning;

    /**
     * Indicates what type of gravity (naive or fluid) should be used when
     * moving rows down the game board after a row has been cleared.
     * Naive gravity has the board move down by the number of rows that were cleared
     * Fluid gravity has the pieces fall until they fall on top of another block
     * @type int
     * @val 0  Fluid gravity
     * @val 1  Naive gravity
     */
    int gravityType;

    /**
     * The main GUI frame.
     * Houses all of the gui elements
     * @type JFrame
     */
    static JFrame frame;

    /**
     * The menu bar.
     * Houses all menus and submenus
     * @type JMenuBar
     */
    JMenuBar menuBar;

    /**
     * The game menu.
     * Houses submenus with game actions (Restart and Exit)
     * @type JMenu
     */
    JMenu game;

    /**
     * The help menu.
     * Houses submenus with help actions (About and Help)
     * @type JMenu
     */
    JMenu menuHelp;

    /**
     * The exit submenu.
     * Exits the frame and terminates execution.
     * @type JMenuItem
     */
    JMenuItem exit;

    /**
     * The game reset submenu.
     * Stops the current game in progress and starts a new one.
     * @type JMenuItem
     */
    JMenuItem menuReset;

    /**
     * The help submenu.
     * Displays information on how to play the game.
     * @type JMenuItem
     */
    JMenuItem help;

    /**
     * The about submenu.
     * Displays information about the authors of the game.
     * @type JMenuItem
     */
    JMenuItem about;

    /**
     * The button handling game status.
     * Picked up by an actionlistener, starts the game if not started,
     * restarts the game if already in progress.
     * @type JButton
     */
    JButton startGame;

    /**
     * The button handling gravity choice.
     * Allows the user to switch between naive and flood fill gravity types
     * used when a row is filled.
     * @type JButton
     */
    JButton gravityButton;

    /**
     * The array of labels that serves as the game grid display.
     * Updated every time a function on a piece occurs or the game is reset.
     * @type JLabel
     */
    private JLabel labelArray[][];

    /**
     * The thread that updates the time display label.
     * Incremented every second while the game is active,
     * reset upon game reset.
     * @type Timer
     */
    private Timer timeClock;

    /**
     * The array of icons that will be used for label display purposes.
     * Contains icons of game grid as well as pieces.
     * @type Icon
     */
    private Icon iconArray[];

    /**
     * Displays the time elapsed in the current game on the frame.
     * Updated by timeClock thread.
     * @type JLabel
     */
    private JLabel timer;

    /**
     * Displays the current level of the game.
     * Calculated based on the number of rows cleared
     * @type JLabel
     */
    private JLabel level;

    /**
     * Displays what type of piece will be active next.
     * The piece types are therefore randomized while another piece is active.
     * @type JLabel
     */
    private JLabel nextPiece;

    /**
     * Displays the score of the current game.
     * The score is calculated based upon the number of rows cleared at a
     * certain time and the current level of the game.
     * @type JLabel
     */
    private JLabel score;

    /**
     * Displays the amount of rows cleared so far.
     * @type JLabel
     */
    private JLabel linesCleared;

    /**
     * Stores the current time to be displayed.
     * @type int
     */
    private int time;

    /**
     * Stores the current round to be displayed.
     * @type int
     */
    private int round;

    /**
     * Stores the number of lines cleared to be displayed.
     * @type int
     */
    private int lines;

    /**
     * Stores the piece that is upcoming to be displayed.
     * @type String
     */
    private String piece;

    /**
     * Stores the names of the icons that will be used in display of the game grid.
     * @type String[]
     */
    private String[] names;


    /**
     * The default constructor of the Tetris class.
     * Initializes all of the GUI items and sets fields to default values.
     * Execution is then passed to actionlisteners which await user input to either access
     * menus or start the game.
     */
    public Tetris() {

        //Creates the frame
        frame = new JFrame("Tetris");
        frame.setLayout(new BorderLayout());
        frame.setSize(305, 630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);

        //Creates the container
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        //Creates the menu items and adds to the GUI
        exit = new JMenuItem("Exit", KeyEvent.VK_X);
        menuReset = new JMenuItem("Reset", KeyEvent.VK_R);
        help = new JMenuItem("Help", KeyEvent.VK_L);
        about = new JMenuItem("About", KeyEvent.VK_A);
        menuBar = new JMenuBar();
        game = new JMenu("Game");
        menuHelp = new JMenu("Help");
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


        //Creates the first set of statistics and adds them to the GUI
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2,3));
        timer = new JLabel();
        time = 0;
        timer.setText("   Time: " + time);
        timer.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(timer, BorderLayout.WEST);
        level = new JLabel();
        round = 0;
        level.setText("    Level: " + round);
        level.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(level, BorderLayout.CENTER);
        score = new JLabel();
        score.setText("    Score: " + round);
        score.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(score, BorderLayout.EAST);
        statsPanel.add ( new JLabel ("", JLabel.CENTER));
        startGame = new JButton("New Game");
        startGame.addActionListener(this);
        startGame.setFocusable(false);
        statsPanel.add(startGame);
        statsPanel.add ( new JLabel ("", JLabel.CENTER));
        c.add(statsPanel, BorderLayout.NORTH);


        //Creates the second set of statistics and adds them to the GUI
        JPanel statsPanel2 = new JPanel();
        statsPanel2.setLayout(new GridLayout());
        linesCleared = new JLabel();
        lines = 0;
        linesCleared.setText("   Lines Cleared: " + lines);
        linesCleared.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel2.add(linesCleared, BorderLayout.WEST);
        nextPiece = new JLabel();
        piece = "L";
        nextPiece.setText("     Next Piece: " + piece);
        nextPiece.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel2.add(nextPiece, BorderLayout.EAST);
        c.add(statsPanel2, BorderLayout.SOUTH);

        //Creates the game grid and adds it to the GUI
        JPanel labelPanel = new JPanel();
        int row = 20;
        int column = 10;
        labelPanel.setLayout(new GridLayout(row, column, 0, 0));
        labelPanel.setSize(500, 250);
        c.add(labelPanel, BorderLayout.CENTER);

        //Creates the icons and adds them to the game grid
        names = new String[]{"white.jpg", "blue.jpg", "brown.jpg", "green.jpg", "orange.jpg", "pink.jpg", "red.jpg", "yellow.jpg"};
        iconArray = new Icon[names.length];

        for (int count = 0; count < names.length; count++) {
            iconArray[count] = new ImageIcon(names[count]);
        }

        int count = 0;

        labelArray = new JLabel[20][10];
        for (int r = 0; r < 20; r++) {
            for (int col = 0; col < 10; col++) {
                labelArray[r][col] = new JLabel(iconArray[0]);
                labelPanel.add(labelArray[r][col]);
                count++;
            }
        }

        //Creates the timer
        int delay = 1000;
        timeClock = new Timer(delay, new TimerHandler());

        frame.add(c, BorderLayout.SOUTH);
        frame.setVisible(true);

        //Delay for piece advancement
        timeout = 1000;

        //The game is not running until the user presses the button
        gameRunning = 0;

    }
    //End of Tetris constructor

    /**
     * Thread class that handles the main game loop.
     * A Tetris piece is created if the thread is flagged as active,
     * and execution is passed onto the thread of the piece until it is interrupted.
     * New pieces are created until a flag marks the thread as inactive.
     */
    class GameThread extends Thread {

        /**
         * Flag marking whether the game loop should spawn another piece or not.
         * @type boolean
         */
        boolean shouldContinue;

        /**
         * The default constructor of the GameThread class.
         * Initializes the flag variable to allow for piece creation.
         * This occurs upon the start of a new game.
         */
        GameThread(){
            shouldContinue = true;
        }

        /**
         * Handles execution of the thread while active.
         */
        public void run() {
            //Process game moves
            while (shouldContinue) {
                //Generate a random piece (1-7)
                Random r = new Random();
                currentColor = r.nextInt((7 - 1) + 1) + 1;
                switch (currentColor) {
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
                for (Coordinates c : currentPiece.getPositions()) {
                    labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
                }

                //Wait before updating display
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                }

                //Create a new thread for the current piece
                pieceThread = new PieceThread();
                pieceThread.start();

                //Wait until the thread is interrupted (collision)
                while (!pieceThread.isInterrupted()) {

                }

                //Fully stop the thread
                pieceThread.currentlyRunning = false;
                pieceThread.finishedExecution = true;
                pieceThread.stop();

                //Check for row fill
                rowFillCheck();

            }
            //End of forever while loop
        }
        //End of run method
    }
    //End of GameThread class


    public class PieceThread extends Thread {
        public boolean currentlyRunning;
        public boolean finishedExecution;

        PieceThread() {
            currentlyRunning = true;
            finishedExecution = false;
        }

        public void run() {
            gameRunning = 1;
            System.out.println("VARIABLES: " + finishedExecution + " " + currentlyRunning);
            while (true) {
                //Advance piece until end
                while (finishedExecution == false && currentlyRunning == true) {
                    if (finishedExecution == false) {
                        //White out current piece
                        for (Coordinates c : currentPiece.getPositions()) {
                            labelArray[c.x][c.y].setIcon(iconArray[0]);
                        }
                    }

                    //Check if at end of field
                    for (Coordinates c : currentPiece.getPositions()) {
                        if (c.x == 19) {
                            //Break out
                            finishedExecution = true;
                        }
                    }

                    if (finishedExecution == false) {
                        //Advance piece, check collisions
                        if (currentPiece.advance(labelArray) == 1) {
                            finishedExecution = true;
                        }
                    }

                    //Display
                    for (Coordinates c : currentPiece.getPositions()) {
                        labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
                    }

                    if (finishedExecution == false) {
                        try {
                            Thread.sleep(timeout);
                        } catch (InterruptedException e) {
                        }
                    }

                    if (finishedExecution == true) {
                        pieceThread.interrupt();

                    }

                }
            }
        }

    }


    private void rowFillCheck() {
        Vector<Integer> fullRows = new Vector<>();

        //Get the filled row numbers
        for (int i = 0; i < 20; i++) {
            //Check if the row is filled
            int isNotFilled = 0;
            for (int j = 0; j < 10; j++) {
                if (labelArray[i][j].getIcon().toString().equals("white.jpg")) {
                    isNotFilled = 1;
                }
            }

            //If the row is filled, add it to the vector
            if (isNotFilled == 0) {
                fullRows.add(i);
            }
        }

        //Clear each full row and move whole board down
        //TODO Switch for naive gravity or flood fill
        //Flood fill
        for (int i : fullRows) {
            for (int j = 0; j < 10; j++) {
                //White out
                labelArray[i][j].setIcon(iconArray[0]);
            }

            //For each row above the current row, move it down
            for (int j = i - 1; j >= 0; j--) {
                for (int k = 0; k < 10; k++) {
                    labelArray[j + 1][k].setIcon(labelArray[j][k].getIcon());
                }
            }
        }

        //Naive Gravity


        //TODO Update score, time, level
    }

    private void rotate() {
        //pieceThread.currentlyRunning = false;

        //White out current piece
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[0]);
        }

        //Rotate
        currentPiece.rotate(labelArray, iconArray);


        //Display
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
        }

        //pieceThread.currentlyRunning = true;
    }

    private void softDrop(){

    }

    private void moveRight() {
        //pieceThread.currentlyRunning = false;
        //pieceThread.interrupt();

        //White out current piece
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[0]);
        }

        //Check if move is possible
        int canMove = 0;
        for (Coordinates c : currentPiece.getPositions()) {
            //Out of bounds
            if ((c.y + 1) > 9) {
                canMove = 1;
            }

            //Collision
            if (canMove == 0 && !labelArray[c.x][c.y + 1].getIcon().toString().equals("white.jpg")) {
                canMove = 1;
            }

        }

        //Move the piece if possible
        for (Coordinates c : currentPiece.getPositions()) {
            if (canMove == 0) {
                c.y = c.y + 1;
            }
        }


        //Display
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
        }

//        try {
//            pieceThread.sleep(timeout);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //pieceThread = new PieceThread();
        //pieceThread.start();
        //pieceThread.currentlyRunning = true;
    }

    private void moveLeft() {
        //pieceThread.currentlyRunning = false;
        //pieceThread.interrupt();

        //White out current piece
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[0]);
        }

        //Check if move is possible
        int canMove = 0;
        for (Coordinates c : currentPiece.getPositions()) {
            //Out of bounds
            if ((c.y - 1) < 0) {
                canMove = 1;
            }

            //Collision
            if (canMove == 0 && !labelArray[c.x][c.y - 1].getIcon().toString().equals("white.jpg")) {
                canMove = 1;
            }

        }

        //Move the piece if possible
        for (Coordinates c : currentPiece.getPositions()) {
            if (canMove == 0) {
                c.y = c.y - 1;
            }
        }


        //Display
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
        }

        //pieceThread = new PieceThread();
        //pieceThread.start();
        //pieceThread.currentlyRunning = true;
    }

    private void slamDown() {
        //pieceThread.currentlyRunning = false;
        //pieceThread.interrupt();

        //Advance piece until end
        int x = 1;
        //Advance piece until end
        while (x == 1) {
            if (x == 1) {
                //White out current piece
                for (Coordinates c : currentPiece.getPositions()) {
                    labelArray[c.x][c.y].setIcon(iconArray[0]);
                }
            }

            //Check if at end of field
            for (Coordinates c : currentPiece.getPositions()) {
                if (c.x == 19) {
                    //Break out
                    x = 2;
                }
            }

            if(x != 2) {
                //Advance piece, check collisions
                if (currentPiece.advance(labelArray) == 1) {
                    x = 2;
                }
            }

            //Display
            for (Coordinates c : currentPiece.getPositions()) {
                labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
            }

        }

        //pieceThread = new PieceThread();
        //pieceThread.start();
        //pieceThread.currentlyRunning = true;

    }


    public static void main(String args[]) {
        Tetris app = new Tetris();
    }

    public void actionPerformed(ActionEvent e) {
        GameMenu gm = new GameMenu();
        Object item;
        item = e.getSource(); // get menu item that triggered the event

        if(e.getSource() == startGame){
            System.out.println("CLICKED");
            if(gameRunning == 0) {
                gameLoop = new GameThread();
                gameLoop.start();

                //Change button text
                startGame.setText("New game");
            }
            else{

                //TODO
                //Stop game
                //gameLoop.shouldContinue = false;

                gameRunning = 0;

                //White out the game board
                for(JLabel[] l : labelArray){
                    for(JLabel k : l){
                        k.setIcon(iconArray[0]);
                    }
                }

            }
        }

        // match the menu item to the its resulting action
        if (item == menuReset) {
        } else if (item == exit) {
            System.exit(0);
        } else if (item == about) {
            gm.displayAbout();
        } else if (item == help) {
            gm.displayHelp();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    rotate();
                    break;
                case KeyEvent.VK_DOWN:
                    softDrop();
                    break;
                case KeyEvent.VK_LEFT:
                    moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    moveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    //Push the current piece down until a collision
                    slamDown();
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    // inner class for timer event handling
    private class TimerHandler implements ActionListener {

        // handle button event
        public void actionPerformed(ActionEvent event) {

        }

    } // end private inner class TimerHandler2
}
