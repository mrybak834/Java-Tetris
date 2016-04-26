/**
 * Coordinates.java - A pair of x,y coordinates corresponding to the row,column
 * that indicates a subsection of a tetris piece.
 * Coordinates are constantly updated by the piece manipulation methods and
 * each piece consists of an array of 4 coordinates.
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #05 - Tetris
 * @category    Coordinates
 * @author      Marek Rybakiewicz
 * @author      Michael McClory
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class Coordinates {

    /**
     * The x coordinate of the current subsection of the game piece.
     * @type int
     */
    public int x;

    /**
     * The y coordinate of the current subsection of the game piece.
     * @type int
     */
    public int y;

    /**
     * The default constructor takes two integer values and initializes the data members to their
     * corresponding values.
     * @param x   X coordinate
     * @param y   Y coordinate
     * @type Constructor
     */
    Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    //End of Coordinates constructor
}
//End of Coordinates class
