import javax.swing.*;


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
 * @category    Menu
 * @author      Marek Rybakiewicz
 * @author      Michael McClory
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class GameMenu {

    //
    // display the about page
    //
    public void displayAbout() {
        JOptionPane.showMessageDialog(null, "                        ABOUT Tetris\n\n"
                + "Created by Mike McClory and Marek R.\n\n"
                + "Created for CS342: Software Design with Prof. Troy.        \n\n");


    } // end of displayAbout


    //
    // display the help page
    //
    public void displayHelp() {
        JOptionPane.showMessageDialog(null, "                                              HELP CENTER\n\n "
                + "Left-click a square to find out if it is a mine or not.\n\n"
                + "Right-click a square once to tag it as definitley holding a mine.\n\n"
                + "Right-click a square a second time to tag it as maybe having a mine.\n\n"
                + "Right-click a square a third time will make the square look like you didn't touch it.        \n");


    } // end of displayHelp


} // end of GameMenu class




