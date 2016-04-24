import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


//TODO Thread suspend for keys
public class tetris extends JFrame implements ActionListener, KeyListener {

    TetrisPiece currentPiece;
    PieceThread pieceThread;
    int currentColor;

    static JFrame frame = new JFrame("Tetris");
    JMenuBar menuBar = new JMenuBar();
    JMenu game = new JMenu("Game");
    JMenu menuHelp = new JMenu("Help");
    JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);
    JMenuItem menuReset = new JMenuItem("Reset", KeyEvent.VK_R);
    JMenuItem help = new JMenuItem("Help", KeyEvent.VK_L);
    JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
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
    private JLabel linesCleared = new JLabel();
    private int time = 0;
    private int round = 0;
    private int lines = 0;
    private String piece = "L";
    private String names[] =
            {"white.jpg", "blue.jpg", "brown.jpg", "green.jpg", "orange.jpg", "pink.jpg", "red.jpg", "yellow.jpg"};


    public tetris() {
        super("Tetris");

        frame.setLayout(new BorderLayout());
        frame.setSize(300, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(this);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

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
        timer.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(timer, BorderLayout.WEST);
        level.setText("    Level: " + round);
        level.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(level, BorderLayout.CENTER);
        score.setText("    Score: " + round);
        score.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel.add(score, BorderLayout.EAST);
        c.add(statsPanel, BorderLayout.NORTH);


        // set up and add second stats panel to main container
        JPanel statsPanel2 = new JPanel();
        statsPanel2.setLayout(new GridLayout());
        linesCleared.setText("   Lines Cleared: " + lines);
        linesCleared.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel2.add(linesCleared, BorderLayout.WEST);
        nextPiece.setText("     Next Piece: " + piece);
        nextPiece.setFont(new Font("Arial", Font.BOLD, 16));
        statsPanel2.add(nextPiece, BorderLayout.EAST);
        c.add(statsPanel2, BorderLayout.SOUTH);


        JPanel labelPanel = new JPanel();
        int row = 20;
        int column = 10;
        labelPanel.setLayout(new GridLayout(row, column, 0, 0));
        labelPanel.setSize(500, 250);

        c.add(labelPanel, BorderLayout.CENTER);

        // create and add icons
        iconArray = new Icon[names.length];

        for (int count = 0; count < names.length; count++) {
            iconArray[count] = new ImageIcon(names[count]);
        }

        lacount = 0;
        int count = 0;

        labelArray = new JLabel[20][10];
        for (int r = 0; r < 20; r++) {
            for (int col = 0; col < 10; col++) {
                labelArray[r][col] = new JLabel(iconArray[0]);
                labelPanel.add(labelArray[r][col]);
                count++;
            }
        }

        int delay = 1000;
        timeClock = new Timer(delay, new TimerHandler());

        frame.add(c, BorderLayout.SOUTH);

        //labelArray[1][4].setIcon(iconArray[1]);

        frame.setVisible(true);


        //TODO
        //BUTTON TO START THE GAME LOOP
        //Start the game

            gameLoop();


    }


    public void gameLoop() {

        //Process game moves
        while (true) {
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

            pieceThread = new PieceThread();
            pieceThread.start();

            while(!pieceThread.isInterrupted()){

            }
            System.out.println("Got here 1");
            pieceThread.currentlyRunning = false;
            pieceThread.finishedExecution = true;
            pieceThread.stop();

        }
        //End of forever while loop

    }

    public class PieceThread extends Thread{
        public boolean currentlyRunning;
        public boolean finishedExecution;

        PieceThread(){
            currentlyRunning = true;
            finishedExecution = false;
        }
        public void run(){

            //TODO Check if the piece can be spawned

            //Display piece
            for (Coordinates c : currentPiece.getPositions()) {
                labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            //Advance piece until end
            while (finishedExecution == false) {
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
                        System.out.println("Got here 2");
                    }
                }

                if(finishedExecution == false) {
                    //Advance piece, check collisions
                    if (currentPiece.advance(labelArray) == 1) {
                        finishedExecution = true;
                        System.out.println("Got here 3");
                    }
                }

                //Display
                for (Coordinates c : currentPiece.getPositions()) {
                    labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
                }

                if (finishedExecution == false)
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
            }

            pieceThread.interrupt();
        }

    }

    private void rotate(){
        pieceThread.currentlyRunning = false;

        currentPiece.rotate();

        pieceThread.currentlyRunning = true;
    }

    private void moveRight(){
        pieceThread.currentlyRunning = false;

        //White out current piece
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[0]);
        }

        //Check if move is possible
        int canMove = 0;
        for (Coordinates c : currentPiece.getPositions()) {
            //Out of bounds
            if( (c.y + 1) > 9 ) {
                canMove = 1;
            }

            //Collision
            if( canMove == 0 && !labelArray[c.x][c.y + 1].getIcon().toString().equals("white.jpg") ) {
                canMove = 1;
            }

        }

        //Move the piece if possible
        if(canMove == 0){
            for (Coordinates c : currentPiece.getPositions()){
                c.y = c.y + 1;
            }
        }

        //Display
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
        }

        pieceThread.currentlyRunning = true;
    }
    
    private void moveLeft(){
        pieceThread.currentlyRunning = false;

        //White out current piece
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[0]);
        }
        
        //Check if move is possible
        int canMove = 0;
        for (Coordinates c : currentPiece.getPositions()) {
            //Out of bounds
            if( (c.y - 1) < 0 ) {
                canMove = 1;
            }

            //Collision
            if( canMove == 0 && !labelArray[c.x][c.y - 1].getIcon().toString().equals("white.jpg") ) {
                canMove = 1;
            }

        }

        //Move the piece if possible
        if(canMove == 0){
            for (Coordinates c : currentPiece.getPositions()){
                c.y = c.y - 1;
            }
        }

        //Display
        for (Coordinates c : currentPiece.getPositions()) {
            labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
        }

        pieceThread.currentlyRunning = true;
    }

    private void slamDown(){

        pieceThread.currentlyRunning = false;

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
                    System.out.println("Got here 2");
                }
            }

            if(x != 2) {
                //Advance piece, check collisions
                if (currentPiece.advance(labelArray) == 1) {
                    x = 2;
                    System.out.println("Got here 3");
                }
            }

            //Display
            for (Coordinates c : currentPiece.getPositions()) {
                labelArray[c.x][c.y].setIcon(iconArray[currentColor]);
            }

        }

        pieceThread.currentlyRunning = true;
    }


    public static void main(String args[]) {
        tetris app = new tetris();

    }

    public void actionPerformed(ActionEvent e) {
        GameMenu gm = new GameMenu();
        Object item;
        item = e.getSource(); // get menu item that triggered the event

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
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                rotate();
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("GOt here 2");
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
