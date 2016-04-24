/* Authors: Mike McClory and Marek R.
 * Class: CS342 Software Design
 * Instructor: P.Troy
 * Project 5: Tetris
 * 
 * Conatins: Game class (for the menu items ) 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

//
// class that contains the functions for the Game Menu's options
// i.e. help, about, top ten
//
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




