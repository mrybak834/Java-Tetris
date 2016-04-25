/**
 * Client.java - This class holds all of the information required for GUI display, connections
 * to the server, as well as information relay of names and messages.
 * Many instances of the chat executable are allowed to connect to the server, and all information
 * is abstracted away from clients by passing through the server.
 * A client must first connect to the server with a unique name, then message sending capabilities are
 * enabled, and the connection will close upon clicking the button or the exit field of the GUI.
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
    //A pair of integers that represents the location on the grid
    public int x;
    public int y;

    //Default constructor, dummy coordinates.
    Coordinates(){
        x = -1;
        y = -1;
    }

    Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
}
