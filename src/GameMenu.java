import javax.swing.*;


/**
 * GameMenu.java - Handles display of the About and Help dialogs.
 * The About dialog displays information about the creators of the program.
 * The Help dialog displays information about how to play the game.
 * The respective dialogs are accesed via submenus instantiated in the Tetris class.
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

    /**
     * Displays the "About" dialog box, which contains information about the creators of the program.
     * @type DialogBox
     */
    public void displayAbout() {
        JOptionPane.showMessageDialog(null, "                        ABOUT Tetris\n\n"
                + "Created by Mike McClory and Marek R.\n\n"
                + "Created for CS342: Software Design with Prof. Troy.        \n\n");


    }
    // end of displayAbout


    /**
     * Displays  the "Help" dialog box, which contains information about how to play the game.
     * @type DialogBox
     */
    public void displayHelp() {
        JOptionPane.showMessageDialog(null, "                                              HELP CENTER\n\n "
                + "Left-click a square to find out if it is a mine or not.\n\n"
                + "Right-click a square once to tag it as definitley holding a mine.\n\n"
                + "Right-click a square a second time to tag it as maybe having a mine.\n\n"
                + "Right-click a square a third time will make the square look like you didn't touch it.        \n");


    }
    // end of displayHelp
} // end of GameMenu class




