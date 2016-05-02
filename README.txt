The program is run using the main in SingletonGUI.java.

The design patterns used are:

Singleton.
A SingletonGUI is responsible for creating the Tetris object and that only one is created.
This class then provides a way to access the object without needing to instantiate it.

    //Tetris
    //Singleton design pattern instance of Tetris
    public static Tetris instance = new Tetris();

    //Returns the instance of Tetris
    public static Tetris getInstance(){
        return instance;
    }

    //SingletonGUI
    public static void main(String[] args) {
        //Get the only object available
        Tetris object = Tetris.getInstance();
    }


Factory.
We create an object without directly exposing the logic, and all subclasses are
referred to using this main objects common instance, giving the user the ability
of making universal calls.

//Tetris
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

                    ..etc

The rest of the program then refers to the polymorphic class using common functions for the rest of its
life cycle, irregardless of which specific subclass was actually instantiated.
