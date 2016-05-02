/**
 * SingletonGUI.java - Grabs the singleton instance of the GUI. Main execution path.
 *
 * @version     1.0.0
 * @university  University of Illinois at Chicago
 * @course      CS342 - Software Design
 * @package     Project #05 - Tetris
 * @category    Singleton
 * @author      Marek Rybakiewicz
 * @author      Michael McClory
 * @license     GNU Public License <http://www.gnu.org/licenses/gpl-3.0.txt>
 */
public class SingletonGUI {
    public static void main(String[] args) {
        //Get the only object available
        Tetris object = Tetris.getInstance();
    }
}
