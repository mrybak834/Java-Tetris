import javax.swing.*;

/**
 * TetrisPiece.java - Serves as the tetris pieces for the game.
 * The superclass TetrisPiece establishes the orientation of the pieces to the default value.
 * All methods of the superclass are overriden by the subclasses, with there being 1 subclass
 * corresponding to each unique tetris piece for a total of 7 subclasses.
 * The subclasses store the current Coordinates of each of the 4 subsections of the piece on the game board,
 * along with methods for returning the Coordinate array, moving the piece down the field,  and rotating.
 * Each override in a class contains piece specific manipulations.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #05 - Tetris
 * @category    Pieces
 * @author      Marek Rybakiewicz
 * @author      Michael McClory
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class TetrisPiece {
    /**
     * Holds the current orientation of the piece (1-4).
     * Used in rotating the piece.
     * @type int
     */
    int orientation;


    /*
     * Holds the Letter name of the piece
     *
     */
    char name;


    /**
     * Default constructor, initializes the orientation to the default state
     * @type Constructor
     */
    TetrisPiece() {
        //Initial orientation
        this.orientation = 1;
    }

    /**
     * Dummy method, overriden in subclasses
     * @return null
     */
    public Coordinates[] getPositions() {
        return null;
    }

    /**
     * Dummy method, overriden in subclasses
     * @return int
     */
    public int advance(JLabel[][] labelArray) {
        return 1;
    }

    /**
     * Dummy method, overriden in subclasses
     * @return int
     */
    public int rotate(JLabel[][] labelArray, Icon[] iconArray){
        return 1;
    }
}
//End of TetrisPiece class


/**
 * The subclasses of TetrisPiece all override the methods within the superclass,
 * providing information unique to the piece.
 * Each subclass also stores an array of the coordinates of each subsection of the piece.
 *
 * All pieces follow the same pattern, so comments found in this specific subclass are universally applicable.
 */
class PieceI extends TetrisPiece {
    /**
     * Stores the coordinates of each subsection of the current piece.
     * Coordinates are x,y pairs, with x corresponding to the row and y to the column.
     * @type Coordinates
     */
    public Coordinates[] positions;

    /**
     * The default constructor initializes the 4 coordinate pairs that the piece
     * consists of.
     * @type Constructor
     */
    PieceI() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 4);
        positions[1] = new Coordinates(1, 4);
        positions[2] = new Coordinates(2, 4);
        positions[3] = new Coordinates(3, 4);
    }
    //End of PieceI constructor

    /**
     * Returns the coordinate array corresponding to the subsections of the piece.
     * @return Coordinates[]
     */
    @Override
    public Coordinates[] getPositions() {
        return positions;
    }
    //End of getPositions method

    /**
     * Advances the current piece 1 row down the game grid.
     * Checks and reports collisions with the bottom of the grid or with another piece.
     * @param labelArray    Array of labels that acts as the game grid
     * @return int          1 if collision, 0 otherwise
     */
    @Override
    public int advance(JLabel[][] labelArray) {
        //Check for collisions
        for (Coordinates c : this.positions) {
            //If collision, report
            if (!labelArray[c.x + 1][c.y].getIcon().toString().equals("white.jpg")) {
                //Make sure that the position is not part of the piece
                int pieceCollision = 0;
                for (Coordinates d : positions) {
                    if ((d.x == (c.x + 1)) && (d.y == c.y)) {
                        pieceCollision = 1;
                    }
                }

                if (pieceCollision != 1) {
                    return 1;
                }

            }
        }

        //No collisions, advance freely return success
        for (Coordinates c : this.positions) {
            c.x++;
        }
        return 0;
    }
    //End of advance method


    /**
     * Attempts to rotate the piece based upon the current orientation.
     * Any collisions with the game grid or another piece are reported.
     * Every piece has 4 different possible orientations.
     * @param labelArray  Array of labels acting as the game grid
     * @param iconArray   Array of icon images used in display
     * @return int        Returns 1 if rotation did not occur, 0 otherwise
     */
    @Override
    public int rotate(JLabel[][] labelArray, Icon[] iconArray) {
        try {
            //Check if we can rotate
            if (orientation == 1) {
                //*
                //*  -> ****
                //*
                //*
                //Bounds
                if ((positions[0].x + 1 > 19) || (positions[0].y - 1 < 0) ||
                        (positions[2].x - 1 < 0) || (positions[2].y + 1 > 9) ||
                        (positions[3].x - 2 < 0) || (positions[3].y + 2 > 9)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x + 1][positions[0].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x - 1][positions[2].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x - 2][positions[3].y + 2].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 2;

                    positions[0].x = positions[0].x + 1;
                    positions[0].y = positions[0].y - 1;
                    positions[2].x = positions[2].x - 1;
                    positions[2].y = positions[2].y + 1;
                    positions[3].x = positions[3].x - 2;
                    positions[3].y = positions[3].y + 2;
                }
            } else {
                //          *
                //****  ->  *
                //          *
                //          *
                //Bounds
                if ((positions[0].x - 1 < 0) || (positions[0].y + 1 > 9) ||
                        (positions[2].x + 1 > 19) || (positions[2].y - 1 < 0) ||
                        (positions[3].x + 2 > 19) || (positions[3].y - 2 < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x - 1][positions[0].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x + 1][positions[2].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x + 2][positions[3].y - 2].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 1;

                    positions[0].x = positions[0].x - 1;
                    positions[0].y = positions[0].y + 1;
                    positions[2].x = positions[2].x + 1;
                    positions[2].y = positions[2].y - 1;
                    positions[3].x = positions[3].x + 2;
                    positions[3].y = positions[3].y - 2;
                }
            }

            //Successfully rotated
            return 1;
        } catch (Exception e) {
            //Array out of bounds error, should never happen
            System.out.println("0: " + positions[0].x + " " + positions[0].y);
            System.out.println("2: " + positions[2].x + " " + positions[2].y);
            System.out.println("3: " + positions[3].x + " " + positions[3].y);
            return 0;
        }


    }
    //End of rotate method


}
//End of class PieceI


class PieceT extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceT() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 3);
        positions[1] = new Coordinates(0, 4);
        positions[2] = new Coordinates(0, 5);
        positions[3] = new Coordinates(1, 4);
    }

    //Return the coordinate array
    @Override
    public Coordinates[] getPositions() {
        return positions;
    }


    //Advance the piece down by 1 position on the field, if possible
    //Return 1 if collision
    @Override
    public int advance(JLabel[][] labelArray) {
        //Check for collisions
        for (Coordinates c : this.positions) {
            //If collision, report
            if (!labelArray[c.x + 1][c.y].getIcon().toString().equals("white.jpg")) {
                //Make sure that the position is not part of the piece
                int pieceCollision = 0;
                for (Coordinates d : positions) {
                    if ((d.x == (c.x + 1)) && (d.y == c.y)) {
                        pieceCollision = 1;
                    }
                }

                if (pieceCollision != 1) {
                    return 1;
                }

            }
        }

        //No collisions, advance freely return success
        for (Coordinates c : this.positions) {
            c.x++;
        }
        return 0;
    }

    @Override
    public int rotate(JLabel[][] labelArray, Icon[] iconArray) {
        try {
            //Check if we can rotate
            if (orientation == 1) {
                //          1
                //123  ->  32
                // 4        4
                //
                //Bounds
                if ((positions[0].x - 1 < 0) || (positions[0].y + 1 > 9) ||
                        (positions[2].x < 0) || (positions[2].y - 2 < 0) ||
                        (positions[3].x > 19) || (positions[3].y < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x - 1][positions[0].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x][positions[2].y - 2].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x][positions[3].y].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 2;

                    positions[0].x = positions[0].x - 1;
                    positions[0].y = positions[0].y + 1;
                    positions[2].x = positions[2].x;
                    positions[2].y = positions[2].y - 2;
                    positions[3].x = positions[3].x;
                    positions[3].y = positions[3].y;
                }
            } else if (orientation == 2) {
                // 1      1
                //32  -> 324
                // 4
                //
                //Bounds
                if ((positions[3].x - 1 < 0) || (positions[3].y + 1 > 9)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[3].x - 1][positions[3].y + 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 3;

                    positions[3].x = positions[3].x - 1;
                    positions[3].y = positions[3].y + 1;
                }
            } else if (orientation == 3) {
                // 1      1
                //324  -> 24
                //        3
                //
                //Bounds
                if ((positions[2].x + 1 > 19) || (positions[2].y + 1 > 9) ) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[2].x + 1][positions[2].y + 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 4;

                    positions[2].x = positions[2].x + 1;
                    positions[2].y = positions[2].y + 1;
                }
            }
            else if (orientation == 4) {
                // 1
                // 24  -> 123
                // 3       4
                //
                //Bounds
                if ((positions[0].x + 1 > 19) || (positions[0].y - 1 < 0) ||
                        (positions[2].x - 1 < 0) || (positions[2].y + 1 > 9) ||
                        (positions[3].x + 1 > 19) || (positions[3].y - 1 < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x + 1][positions[0].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x - 1][positions[2].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x + 1][positions[3].y - 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 1;

                    positions[0].x = positions[0].x + 1;
                    positions[0].y = positions[0].y - 1;
                    positions[2].x = positions[2].x - 1;
                    positions[2].y = positions[2].y + 1;
                    positions[3].x = positions[3].x + 1;
                    positions[3].y = positions[3].y - 1;
                }
            }

            return 1;
        } catch (Exception e) {
            System.out.println("0: " + positions[0].x + " " + positions[0].y);
            System.out.println("2: " + positions[2].x + " " + positions[2].y);
            System.out.println("3: " + positions[3].x + " " + positions[3].y);
            return 0;
        }


    }

}

class PieceO extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceO() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 4);
        positions[1] = new Coordinates(0, 5);
        positions[2] = new Coordinates(1, 4);
        positions[3] = new Coordinates(1, 5);
    }

    //Return the coordinate array
    @Override
    public Coordinates[] getPositions() {
        return positions;
    }

    //Advance the piece down by 1 position on the field, if possible
    //Return 1 if collision
    @Override
    public int advance(JLabel[][] labelArray) {
        //Check for collisions
        for (Coordinates c : this.positions) {
            //If collision, report
            if (!labelArray[c.x + 1][c.y].getIcon().toString().equals("white.jpg")) {
                //Make sure that the position is not part of the piece
                int pieceCollision = 0;
                for (Coordinates d : positions) {
                    if ((d.x == (c.x + 1)) && (d.y == c.y)) {
                        pieceCollision = 1;
                    }
                }

                if (pieceCollision != 1) {
                    return 1;
                }

            }
        }

        //No collisions, advance freely return success
        for (Coordinates c : this.positions) {
            c.x++;
        }
        return 0;
    }

}

class PieceL extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceL() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 4);
        positions[1] = new Coordinates(1, 4);
        positions[2] = new Coordinates(2, 4);
        positions[3] = new Coordinates(2, 5);
    }

    //Return the coordinate array
    @Override
    public Coordinates[] getPositions() {
        return positions;
    }

    //Advance the piece down by 1 position on the field, if possible
    //Return 1 if collision
    @Override
    public int advance(JLabel[][] labelArray) {
        //Check for collisions
        for (Coordinates c : this.positions) {
            //If collision, report
            if (!labelArray[c.x + 1][c.y].getIcon().toString().equals("white.jpg")) {
                //Make sure that the position is not part of the piece
                int pieceCollision = 0;
                for (Coordinates d : positions) {
                    if ((d.x == (c.x + 1)) && (d.y == c.y)) {
                        pieceCollision = 1;
                    }
                }

                if (pieceCollision != 1) {
                    return 1;
                }

            }
        }

        //No collisions, advance freely return success
        for (Coordinates c : this.positions) {
            c.x++;
        }
        return 0;
    }


    @Override
    public int rotate(JLabel[][] labelArray, Icon[] iconArray) {
        try {
            //Check if we can rotate
            if (orientation == 1) {
                //1
                //2  ->  124
                //34     3
                //
                //Bounds
                if ((positions[0].x + 1 > 19) || (positions[0].y - 1 < 0) ||
                        (positions[2].x < 0) || (positions[2].y - 1 < 0) ||
                        (positions[3].x - 1 < 0) || (positions[3].y < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x + 1][positions[0].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x][positions[2].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x - 1][positions[3].y].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 2;

                    positions[0].x = positions[0].x + 1;
                    positions[0].y = positions[0].y - 1;
                    positions[2].x = positions[2].x;
                    positions[2].y = positions[2].y - 1;
                    positions[3].x = positions[3].x - 1;
                    positions[3].y = positions[3].y;
                }
            } else if (orientation == 2) {
                //        13
                //124  ->  2
                //3        4
                //
                //Bounds
                if ((positions[0].x - 1 < 0) ||
                        (positions[2].x - 2 < 0) || (positions[2].y + 1 > 9) ||
                        (positions[3].x + 1 > 19) || (positions[3].y - 1 < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x - 1][positions[0].y].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x - 2][positions[2].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x + 1][positions[3].y - 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 3;

                    positions[0].x = positions[0].x - 1;
                    positions[0].y = positions[0].y;
                    positions[2].x = positions[2].x - 2;
                    positions[2].y = positions[2].y + 1;
                    positions[3].x = positions[3].x + 1;
                    positions[3].y = positions[3].y - 1;
                }
            } else if (orientation == 3) {
                //13       3
                // 2  -> 124
                // 4
                //
                //Bounds
                if ((positions[0].x + 1 > 19) ||
                        (positions[2].y + 1 > 9) ||
                        (positions[3].x - 1 < 0) || (positions[3].y + 1 > 9)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x + 1][positions[0].y].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x][positions[2].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x - 1][positions[3].y + 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 4;

                    positions[0].x = positions[0].x + 1;
                    positions[0].y = positions[0].y;
                    positions[2].x = positions[2].x;
                    positions[2].y = positions[2].y + 1;
                    positions[3].x = positions[3].x - 1;
                    positions[3].y = positions[3].y + 1;
                }
            }
            else if (orientation == 4) {
                //   3      1
                // 124  ->  2
                //          34
                //
                //Bounds
                if ((positions[0].x - 1 < 0) || (positions[0].y + 1 > 9) ||
                        (positions[2].x + 2 > 19) || (positions[2].y - 1 < 0) ||
                        (positions[3].x + 1 > 19)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x - 1][positions[0].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x + 2][positions[2].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x + 1][positions[3].y].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 1;

                    positions[0].x = positions[0].x - 1;
                    positions[0].y = positions[0].y + 1;
                    positions[2].x = positions[2].x + 2;
                    positions[2].y = positions[2].y - 1;
                    positions[3].x = positions[3].x + 1;
                    positions[3].y = positions[3].y;
                }
            }

            return 1;
        } catch (Exception e) {
            System.out.println("0: " + positions[0].x + " " + positions[0].y);
            System.out.println("2: " + positions[2].x + " " + positions[2].y);
            System.out.println("3: " + positions[3].x + " " + positions[3].y);
            return 0;
        }


    }


}

class PieceJ extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceJ() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 4);
        positions[1] = new Coordinates(1, 4);
        positions[2] = new Coordinates(2, 3);
        positions[3] = new Coordinates(2, 4);
    }

    //Return the coordinate array
    @Override
    public Coordinates[] getPositions() {
        return positions;
    }


    //Advance the piece down by 1 position on the field, if possible
    //Return 1 if collision
    @Override
    public int advance(JLabel[][] labelArray) {
        //Check for collisions
        for (Coordinates c : this.positions) {
            //If collision, report
            if (!labelArray[c.x + 1][c.y].getIcon().toString().equals("white.jpg")) {
                //Make sure that the position is not part of the piece
                int pieceCollision = 0;
                for (Coordinates d : positions) {
                    if ((d.x == (c.x + 1)) && (d.y == c.y)) {
                        pieceCollision = 1;
                    }
                }

                if (pieceCollision != 1) {
                    return 1;
                }

            }
        }

        //No collisions, advance freely return success
        for (Coordinates c : this.positions) {
            c.x++;
        }
        return 0;
    }


    @Override
    public int rotate(JLabel[][] labelArray, Icon[] iconArray) {
        try {
            //Check if we can rotate
            if (orientation == 1) {
                // 1     1
                // 2  -> 324
                //34
                //
                //Bounds
                if ((positions[0].y - 1 < 0) ||
                        (positions[2].x - 1 < 0) ||
                        (positions[3].x - 1 < 0) || (positions[3].y + 1 > 9)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x][positions[0].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x - 1][positions[2].y].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x - 1][positions[3].y + 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 2;

                    positions[0].x = positions[0].x;
                    positions[0].y = positions[0].y - 1;
                    positions[2].x = positions[2].x - 1;
                    positions[2].y = positions[2].y;
                    positions[3].x = positions[3].x - 1;
                    positions[3].y = positions[3].y + 1;
                }
            } else if (orientation == 2) {
                //1        13
                //324  ->  2
                //         4
                //
                //Bounds
                if ((positions[0].y + 1 > 9) ||
                        (positions[2].x - 1 < 0) || (positions[2].y + 2 > 9) ||
                        (positions[3].x + 1 > 19) || (positions[3].y - 1 < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x][positions[0].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x - 1][positions[2].y + 2].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x + 1][positions[3].y - 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 3;

                    positions[0].x = positions[0].x;
                    positions[0].y = positions[0].y + 1;
                    positions[2].x = positions[2].x - 1;
                    positions[2].y = positions[2].y + 2;
                    positions[3].x = positions[3].x + 1;
                    positions[3].y = positions[3].y - 1;
                }
            } else if (orientation == 3) {
                // 13
                // 2  -> 123
                // 4       4
                //
                //Bounds
                if ((positions[0].x + 1 > 19) || (positions[0].y - 1 < 0) ||
                        (positions[2].x + 1 > 19)  ||
                        (positions[3].y + 1 > 9)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x + 1][positions[0].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x + 1][positions[2].y].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x][positions[3].y + 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 4;

                    positions[0].x = positions[0].x + 1;
                    positions[0].y = positions[0].y - 1;
                    positions[2].x = positions[2].x + 1;
                    positions[2].y = positions[2].y;
                    positions[3].x = positions[3].x;
                    positions[3].y = positions[3].y + 1;
                }
            }
            else if (orientation == 4) {
                //          1
                // 123  ->  2
                //   4     34
                //
                //Bounds
                if ((positions[0].x - 1 < 0) || (positions[0].y + 1 > 9) ||
                        (positions[2].x + 1 > 19) || (positions[2].y - 2 < 0) ||
                        (positions[3].y - 1 < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x - 1][positions[0].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x + 1][positions[2].y - 2].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x][positions[3].y - 1].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 1;

                    positions[0].x = positions[0].x - 1;
                    positions[0].y = positions[0].y + 1;
                    positions[2].x = positions[2].x + 1;
                    positions[2].y = positions[2].y - 2;
                    positions[3].x = positions[3].x;
                    positions[3].y = positions[3].y - 1;
                }
            }

            return 1;
        } catch (Exception e) {
            System.out.println("0: " + positions[0].x + " " + positions[0].y);
            System.out.println("2: " + positions[2].x + " " + positions[2].y);
            System.out.println("3: " + positions[3].x + " " + positions[3].y);
            return 0;
        }


    }

}

class PieceS extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceS() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 5);
        positions[1] = new Coordinates(0, 4);
        positions[2] = new Coordinates(1, 3);
        positions[3] = new Coordinates(1, 4);
    }


    //Return the coordinate array
    @Override
    public Coordinates[] getPositions() {
        return positions;
    }

    //Advance the piece down by 1 position on the field, if possible
    //Return 1 if collision
    @Override
    public int advance(JLabel[][] labelArray) {
        //Check for collisions
        for (Coordinates c : this.positions) {
            //If collision, report
            if (!labelArray[c.x + 1][c.y].getIcon().toString().equals("white.jpg")) {
                //Make sure that the position is not part of the piece
                int pieceCollision = 0;
                for (Coordinates d : positions) {
                    if ((d.x == (c.x + 1)) && (d.y == c.y)) {
                        pieceCollision = 1;
                    }
                }

                if (pieceCollision != 1) {
                    return 1;
                }

            }
        }

        //No collisions, advance freely return success
        for (Coordinates c : this.positions) {
            c.x++;
        }
        return 0;
    }

    @Override
    public int rotate(JLabel[][] labelArray, Icon[] iconArray) {
        try {
            //Check if we can rotate
            if (orientation == 1) {
                //         1
                // 21      32
                //34  ->    4
                //
                //
                //
                //Bounds
                if ((positions[0].x - 1 < 0) || (positions[0].y - 2 < 0) ||
                        (positions[2].x - 1 < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x - 1][positions[0].y - 2].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x - 1][positions[2].y].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 2;

                    positions[0].x = positions[0].x - 1;
                    positions[0].y = positions[0].y - 2;
                    positions[2].x = positions[2].x - 1;
                    positions[2].y = positions[2].y;
                }
            } else if (orientation == 2) {
                //1
                //32  ->  21
                // 4     34
                //
                //Bounds
                if ((positions[0].x + 1 > 19) || (positions[0].y + 2 > 9) ||
                        (positions[2].x + 1 > 19)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x + 1][positions[0].y + 2].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x + 1][positions[2].y].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 1;

                    positions[0].x = positions[0].x + 1;
                    positions[0].y = positions[0].y + 2;
                    positions[2].x = positions[2].x + 1;
                    positions[2].y = positions[2].y;
                }
            }

            return 1;
        } catch (Exception e) {
            System.out.println("0: " + positions[0].x + " " + positions[0].y);
            System.out.println("2: " + positions[2].x + " " + positions[2].y);
            System.out.println("3: " + positions[3].x + " " + positions[3].y);
            return 0;
        }


    }

}

class PieceZ extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceZ() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 3);
        positions[1] = new Coordinates(0, 4);
        positions[2] = new Coordinates(1, 4);
        positions[3] = new Coordinates(1, 5);
    }

    //Return the coordinate array
    @Override
    public Coordinates[] getPositions() {
        return positions;
    }

    //Advance the piece down by 1 position on the field, if possible
    //Return 1 if collision
    @Override
    public int advance(JLabel[][] labelArray) {
        //Check for collisions
        for (Coordinates c : this.positions) {
            //If collision, report
            if (!labelArray[c.x + 1][c.y].getIcon().toString().equals("white.jpg")) {
                //Make sure that the position is not part of the piece
                int pieceCollision = 0;
                for (Coordinates d : positions) {
                    if ((d.x == (c.x + 1)) && (d.y == c.y)) {
                        pieceCollision = 1;
                    }
                }

                if (pieceCollision != 1) {
                    return 1;
                }

            }
        }

        //No collisions, advance freely return success
        for (Coordinates c : this.positions) {
            c.x++;
        }
        return 0;
    }

    @Override
    public int rotate(JLabel[][] labelArray, Icon[] iconArray) {
        try {
            //Check if we can rotate
            if (orientation == 1) {
                //         1
                // 12  -> 32
                //  34    4
                //
                //Bounds
                if ((positions[0].x - 1 < 0) || (positions[0].y + 1 > 19) ||
                        (positions[2].x - 1 < 0) || (positions[2].y - 1 < 0) ||
                        (positions[3].y - 2 < 0)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x - 1][positions[0].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x - 1][positions[2].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x][positions[3].y - 2].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 2;

                    positions[0].x = positions[0].x - 1;
                    positions[0].y = positions[0].y + 1;
                    positions[2].x = positions[2].x - 1;
                    positions[2].y = positions[2].y - 1;
                    positions[3].x = positions[3].x;
                    positions[3].y = positions[3].y - 2;
                }
            } else if (orientation == 2) {
                // 1
                //32  ->  12
                //4        34
                //
                //Bounds
                if ((positions[0].x + 1 > 19) || (positions[0].y - 1 < 0) ||
                        (positions[2].x + 1 > 19) || (positions[2].y + 1 > 9) ||
                        (positions[3].y + 2 > 9)) {
                    //Can't rotate
                    return 1;
                }
                //Collision
                else if (!labelArray[positions[0].x + 1][positions[0].y - 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[2].x + 1][positions[2].y + 1].getIcon().toString().equals("white.jpg") ||
                        !labelArray[positions[3].x][positions[3].y + 2].getIcon().toString().equals("white.jpg")) {
                    return 1;
                }
                //Rotate
                else {
                    orientation = 1;

                    positions[0].x = positions[0].x + 1;
                    positions[0].y = positions[0].y - 1;
                    positions[2].x = positions[2].x + 1;
                    positions[2].y = positions[2].y + 1;
                    positions[3].x = positions[3].x;
                    positions[3].y = positions[3].y + 2;
                }
            }

            return 1;
        } catch (Exception e) {
            System.out.println("0: " + positions[0].x + " " + positions[0].y);
            System.out.println("2: " + positions[2].x + " " + positions[2].y);
            System.out.println("3: " + positions[3].x + " " + positions[3].y);
            return 0;
        }


    }

}
