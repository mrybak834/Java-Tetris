import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

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

    //Singleton design pattern instance of Tetris
    public static Tetris instance = new Tetris();

    //Returns the instance of Tetris
    public static Tetris getInstance(){
        return instance;
    }

    /**
     * An instance of the TetrisPiece class, serves as the current tetris piece in play.
     * The type of subclass is randomly generated upon the start of every piece cycle,
     * and correct functions are inherited by means of polymorphism.
     * @type TetrisPiece
     */
    TetrisPiece currentPiece;

    int threadCOunt = 0;

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
     * The menu item handling gravity choice.
     * Allows the user to switch between naive and flood fill gravity types
     * used when a row is filled.
     * @type JButton
     */
    JMenuItem gravity;

    /**
     * The array of labels that serves as the game grid display.
     * Updated every time a function on a piece occurs or the game is reset.
     * @type JLabel
     */
    private JLabel labelArray[][];

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
     * Stores the number of lines cleared to be displayed.
     * @type int
     */
    private int num;

    /**
     * Value is true if user paused the game and false otherwise
     *
     */
    private boolean isPaused;

    /*
     * Timer used for keeping track of the game time
     * 
     */
    Timer timeClock;

    /**
     * Stores the piece that is upcoming to be displayed.
     * @type String
     */
    private char piece;

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
    private Tetris() {

        //Creates the frame
        frame = new JFrame("Tetris");
        frame.setLayout(new BorderLayout());
        frame.setSize(320, 640);
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
        gravity = new JMenuItem("Naive Gravity On");
        menuBar = new JMenuBar();
        game = new JMenu("Game");
        menuHelp = new JMenu("Help");
        menuBar.add(game);
        menuBar.add(menuHelp);
        exit.addActionListener(this);
        menuReset.addActionListener(this);
        help.addActionListener(this);
        about.addActionListener(this);
        gravity.addActionListener(this);
        game.add(menuReset);
        game.add(gravity);
        game.add(exit);
        menuHelp.add(about);
        menuHelp.add(help);
        frame.setJMenuBar(menuBar);


        //Creates the first set of statistics and adds them to the GUI
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2,3));
        timer = new JLabel();
        time = 0;
        timer.setText("  Time: " + time);
        timer.setFont(new Font("Arial", Font.BOLD, 16));
        timer.setHorizontalAlignment(JLabel.LEFT);
        statsPanel.add(timer, BorderLayout.WEST);
        level = new JLabel();
        round = 1;
        level.setText("  Level: " + round);
        level.setFont(new Font("Arial", Font.BOLD, 16));
        level.setHorizontalAlignment(JLabel.LEFT);
        statsPanel.add(level, BorderLayout.CENTER);
        num = 0;
        score = new JLabel();
        score.setText("Score: " + num);
        score.setFont(new Font("Arial", Font.BOLD, 16));
        score.setHorizontalAlignment(JLabel.LEFT);
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
        linesCleared.setText("  Lines Cleared: " + lines);
        linesCleared.setHorizontalAlignment(JLabel.LEFT);
        linesCleared.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel2.add(linesCleared, BorderLayout.WEST);
        nextPiece = new JLabel();
        piece = ' ';
        nextPiece.setText("    Next Piece: " + piece);
        nextPiece.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel2.add(nextPiece, BorderLayout.EAST);
        c.add(statsPanel2, BorderLayout.CENTER);

        //Creates the game grid and adds it to the GUI
        JPanel labelPanel = new JPanel();
        int row = 20;
        int column = 10;
        labelPanel.setLayout(new GridLayout(row, column, 0, 0));
        labelPanel.setSize(20, 10);
        c.add(labelPanel, BorderLayout.SOUTH);

        //Creates the icons and adds them to the game grid
        names = new String[]{"white.jpg", "blue.jpg", "brown.jpg", "green.jpg", "orange.jpg", "black.jpg", "red.jpg", "yellow.jpg"};
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

        timeClock = new Timer();
        frame.add(c, BorderLayout.NORTH);
        frame.setVisible(true);

        //Delay for piece advancement
        timeout = 800;

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
                //Generate a random piece (1-7)
                Random r = new Random();
                currentColor = r.nextInt((7 - 1) + 1) + 1;
                switch (currentColor) {
                    //I
                    case 1: {
                        currentPiece = new PieceI();
                        piece = 'I';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //T
                    case 2: {
                        currentPiece = new PieceT();
                        piece = 'T';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //O
                    case 3: {
                        currentPiece = new PieceO();
                        piece = 'O';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //L
                    case 4: {
                        currentPiece = new PieceL();
                        piece = 'L';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //J
                    case 5: {
                        currentPiece = new PieceJ();
                        piece = 'J';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //S
                    case 6: {
                        currentPiece = new PieceS();
                        piece = 'S';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //Z
                    case 7: {
                        currentPiece = new PieceZ();
                        piece = 'Z';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    default:
                        //Random generation out of bounds, create an I piece
                        currentPiece = new PieceI();
                        piece = 'I';
                        nextPiece.setText("     Next Piece: " + piece);

                        break;
                }


            //Generate new pieces while the game is active
            while(shouldContinue){
                int currentColor = getNextPiece();

                    //Check if can be spawned
                    for (Coordinates c : currentPiece.getPositions()){
                        if(!labelArray[c.x][c.y].getIcon().toString().equals("white.jpg")){
                            JOptionPane.showMessageDialog(null, "Game Over");
                            newGame();
                            this.interrupt();
                            return;
                        }
                    }

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

                //Generate the new piece
                switch (currentColor) {
                    //I
                    case 1: {
                        currentPiece = new PieceI();
                        piece = 'I';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //T
                    case 2: {
                        currentPiece = new PieceT();
                        piece = 'T';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //O
                    case 3: {
                        currentPiece = new PieceO();
                        piece = 'O';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //L
                    case 4: {
                        currentPiece = new PieceL();
                        piece = 'L';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //J
                    case 5: {
                        currentPiece = new PieceJ();
                        piece = 'J';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //S
                    case 6: {
                        currentPiece = new PieceS();
                        piece = 'S';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    //Z
                    case 7: {
                        currentPiece = new PieceZ();
                        piece = 'Z';
                        nextPiece.setText("     Next Piece: " + piece);
                        break;
                    }
                    default:
                        //Random generation out of bounds, create an I piece
                        currentPiece = new PieceI();
                        piece = 'I';
                        nextPiece.setText("     Next Piece: " + piece);

                        break;
                }

            }
            //End of forever while loop
        }
        //End of run method
    }
    //End of GameThread class

    // Gets the next piece for display
    public int getNextPiece(){
        //Generate a random piece (1-7)
        Random r = new Random();
        currentColor = r.nextInt((7 - 1) + 1) + 1;
        switch (currentColor) {
            //I
            case 1: {
                piece = 'I';
                nextPiece.setText("     Next Piece: " + piece);
                return 1;
            }
            //T
            case 2: {
                piece = 'T';
                nextPiece.setText("     Next Piece: " + piece);
                return 2;
            }
            //O
            case 3: {
                piece = 'O';
                nextPiece.setText("     Next Piece: " + piece);
                return 3;
            }
            //L
            case 4: {
                piece = 'L';
                nextPiece.setText("     Next Piece: " + piece);
                return 4;
            }
            //J
            case 5: {
                piece = 'J';
                nextPiece.setText("     Next Piece: " + piece);
                return 5;
            }
            //S
            case 6: {
                piece = 'S';
                nextPiece.setText("     Next Piece: " + piece);
                return 6;
            }
            //Z
            case 7: {
                piece = 'Z';
                nextPiece.setText("     Next Piece: " + piece);
                return 7;
            }
            default:
                piece = 'I';
                nextPiece.setText("     Next Piece: " + piece);
                return 1;
        }
    }


    /**
     * Handles moving the current active piece down the board.
     * A new thread is created for each piece, and execution can be paused
     * by setting the flags.
     * The system pauses execution whenever a user issues a keybaord command.
     * @var currentlyRunning Indicates whether the thread is running or paused
     * @var finishedExecution Indicates if the thread has completely finished and a new one
     *                        should be started.
     *@type Thread
     */
    public class PieceThread extends Thread {
        /**
         * Indicates whether the thread is active or paused.
         * If paused, causes the thread to loop and do nothing until the thread is reset.
         * @type boolean
         */
        public boolean currentlyRunning;

        /**
         * Indicates whether the thread has fully finished or not.
         * If fully finished, notifies the gameLoop.
         * @type boolean
         */
        public boolean finishedExecution;

        /**
         * Default constructor for the PieceThread class.
         * Initialized both flags to active
         * @type Constructor
         */
        PieceThread() {
            currentlyRunning = true;
            finishedExecution = false;
        }

        /**
         * Main execution loop for the thread.
         * Moves the game piece down the board until interrupted or a collision occurs.
         * @type void
         */
        public void run() {
            //Indicates to the start button that the game is running.
            gameRunning = 1;
            //Run forever
            while (true) {
                //Advance piece until end of board or collision
                while (finishedExecution == false && currentlyRunning == true) {
                    //Needed to make sure thread stays executing
                    System.out.println("");
                    if(!isPaused) {
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

                        if ((finishedExecution == false)) {
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

                        //If we are done, notify the gameLoop
                        if (finishedExecution == true) {
                            pieceThread.interrupt();

                        }
                    }

                } //End of inner while loop
            } // End of outer while loop
        }//End of thread runner

    }//End of PieceThread class


    /**
     * Checks to see if a row was filled with colored blocks upon the end of a piece cycle.
     * If a row was filled, blocks above the row fall down based on the gravity type enabled
     * by a switch statement based on the gravity button (naive or fluid).
     * Multiple rows are handled simultaneously.
     * Score is updated upon filling the row and all related variables are also updated.
     * @type void
     */
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
                lines++;
            }
        }


        // updates the amount of lines cleared
        linesCleared.setText("   Lines Cleared: " + lines);
        updateScore(fullRows.size());

        // updates the level of the game for every ten lines cleared
        if(lines >= 10)
        {
            round = (lines/10) + 1;
            level.setText("    Level: " + round);
            timeout = (int)(1000 * ((50 - ((double)round * 2)) / 60));
        }


        //Clear each full row and move whole board down
        //Naive Gravity
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
    }
    //End of rowFillCheck method

    /*
 * Method for keeping track of the score
 *
 */
    public void updateScore(int numCleared)
    {

        if(numCleared == 1)
        {
            num = num + (round * 40);
            score.setText("    Score: " + num);
        }
        else if(numCleared == 2)
        {
            num = num + (round *100);
            score.setText("    Score: " + num);
        }
        else if(numCleared == 3)
        {
            num = num + (round *300);
            score.setText("    Score: " + num);
        }
        else if(numCleared == 4)
        {
            num = num + (round *1200);
            score.setText("    Score: " + num);
        }

    }

    /**
     * Rotates the current piece based upon current orientation.
     * Called from the keylistener (UP), and calls the method of the respective piece class
     * @type void
     */
    private void rotate() {
        if (!isPaused) {
            isPaused = !isPaused;

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


            isPaused = !isPaused;
        }

    }
    //End of rotate method

    /**
     * Drops the current piece down faster than the regular interval.
     * Triggered by the keylistener, and occurs while the key is pressed down by decreasing
     * the timeout time for the sleeping of the piece thread.
     * @type void
     */
    private void softDrop(){
        //White out current piece
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[0]);
        }



                try {
                    isPaused = !isPaused;
                    currentPiece.advance(labelArray);
                    isPaused = !isPaused;

                }
                catch(ArrayIndexOutOfBoundsException e) {
                    //Do nothing, should not occur but does not matter if it does.
                }


        //Display
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
        }

    }
    //End of softDrop method

    /**
     * Moves the current piece to the right if possible.
     * Processing occurs in this method rather than in the respective piece class because
     * every piece has the same type of movement right.
     * Execution of the PieceThread is paused until the user stops providing input.
     * @type void
     */
    private void moveRight() {

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

    }
    //End of moveRight method


    /**
     * Moves the current piece to the left if possible.
     * Processing occurs in this method rather than in the respective piece class because
     * every piece has the same type of movement left.
     * Execution of the PieceThread is paused until the user stops providing input.
     * @type void
     */
    private void moveLeft() {

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

    }
    //End of moveLeft method

    /**
     * Moves the current piece to the bottom of the game grid,
     * or down until a collision is detected.
     * Calls the advance method of the respective piece until the collision occurs.
     * Thread execution is not halted during this time.
     * @type void
     */
    private void slamDown() {

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

            if( x != 2 ) {
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


    }
    //End of slamDown method

    //Sets up a new game
    public void newGame(){
        time = -1;
        //TODO
        //Stop game
        //gameLoop.shouldContinue = false;
        time = 0;
        timer.setText("     Time: \n" + time);
        round = 1;
        level.setText("  Level: " + round);
        score.setText("Score: " + 0);
        gameRunning = 0;
        isPaused = true;
        lines = 0;
        linesCleared.setText("  Lines Cleared: " + lines);

        gameRunning = 0;

        //White out the game board
        for(JLabel[] l : labelArray){
            for(JLabel k : l){
                k.setIcon(iconArray[0]);
            }
        }

    }

    /**
     * Handles button clicks and menu selects.
     * Checks which item was pressed and performs the corresponding action.
     * Starts/restarts a game when the start button is pressed,
     * Changes gravity type when the gravity button is pressed,
     * and displays menu information for menus and submenus.
     * @type void
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        //Get menu item that triggered the event
        GameMenu gm = new GameMenu();
        Object item;
        item = e.getSource();

        //Handle game start button
        if(e.getSource() == startGame){
            //Game is not currently running start
            if(gameRunning == 0) {
                isPaused = false;
                gameLoop = new GameThread();
                gameLoop.start();
                new Reminder(1);

                //Change button text
                startGame.setText("New game");
            }
            //Game is currently running, stop
            else {
                newGame();
            }
        }

        //Determine which menu item was pressed
        if (item == menuReset) {
            time = 0;
            timer.setText("     Time: \n" + time);
            round = 1;
            level.setText("    Level: " + round);
            num = 0;
            score.setText("    Score: " + num);
            lines = 0;
            linesCleared.setText("   Lines Cleared: " + lines);

        }
        else if (item == gravity)
        {
            if(gravity.getText() == "Naive Gravity On")
            {
                gravity.setText("Flood Fill On");
                //turn on flood fill
            }
            else if(gravity.getText() == "Flood Fill On")
            {
                gravity.setText("Naive Gravity On");
                //turn on naive gravity

            }

        }
        else if (item == exit) {
                System.exit(0);
            } else if (item == about) {
                gm.displayAbout();
            } else if (item == help) {
                gm.displayHelp();
            }

    }
    //End of actionPerformed method


    /**
     * Method needed to implement keyListener, does nothing.
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //End of keyTyped method


    /**
     * Handles the key presses during gameplay.
     * Up key results in piece rotation,
     * left or right keys move the piece left or right while also pausing the game until done moving,
     * down results in a soft drop (faster descent) of the piece until a collision,
     * and the space bar results in a hard drop (instantaneous) of the piece until the collision.
     * @type KeyListener
     * @param e
     */
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
                slamDown();
                break;
            case KeyEvent.VK_P:
                isPaused = !isPaused;
                return;

        }
    }
    //End of keyPressed method

    /**
     * Method needed for keylistener implementation, not used.
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
    //End of keyReleased method



    //
// class for keeping track of the time elapsed
//
    public class Reminder
    {

        public Reminder(int seconds)
        {
            if(!isPaused)
                timeClock.schedule(new RemindTask(),0, 1*1000);
        }

        class RemindTask extends TimerTask
        {
            public void run()
            {
                if(!isPaused)
                    time++;

                timer.setText("     Time: \n" + time);

            }
        }
    }// end of Reminder class


}//End of Tetris class