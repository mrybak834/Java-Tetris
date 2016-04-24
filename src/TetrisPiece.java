import javax.swing.*;

/**
 * Created by mrybak834 on 4/20/2016.
 */

//Rotate around second array indice

public class TetrisPiece {
    //The current orientation of the piece (1-4);
    int orientation;
    public Coordinates[] positions;

    //Creates a tetris piece
    TetrisPiece() {

        //Initial orientation
        this.orientation = 1;
    }

    public int checkSurrounding() {
        return 0;
    }

    public Coordinates[] getPositions() {
        return null;
    }


    public int advance(JLabel[][] labelArray) {
        return 1;
    }

    public int rotate(JLabel[][] labelArray){
        return 1;
    }
}

class PieceI extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceI() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 4);
        positions[1] = new Coordinates(1, 4);
        positions[2] = new Coordinates(2, 4);
        positions[3] = new Coordinates(3, 4);
    }

    //Check the surrounding grid, return 1 if we cannot move more
    @Override
    public int checkSurrounding() {

        return 0;
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
    public int rotate(JLabel[][] labelArray){

        return 1;
    }

}

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

    //Check the surrounding grid, return 1 if we cannot move more
    @Override
    public int checkSurrounding() {


        return 0;
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

    //Check the surrounding grid, return 1 if we cannot move more
    @Override
    public int checkSurrounding() {


        return 0;
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

    //Check the surrounding grid, return 1 if we cannot move more
    @Override
    public int checkSurrounding() {


        return 0;
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

class PieceJ extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceJ() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 4);
        positions[1] = new Coordinates(1, 4);
        positions[2] = new Coordinates(2, 4);
        positions[3] = new Coordinates(2, 3);
    }

    //Check the surrounding grid, return 1 if we cannot move more
    @Override
    public int checkSurrounding() {


        return 0;
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

class PieceS extends TetrisPiece {
    //Array of Coordinates of the piece on the grid
    public Coordinates[] positions;

    PieceS() {
        positions = new Coordinates[4];

        //Set start positions
        positions[0] = new Coordinates(0, 4);
        positions[1] = new Coordinates(0, 5);
        positions[2] = new Coordinates(1, 4);
        positions[3] = new Coordinates(1, 3);
    }

    //Check the surrounding grid, return 1 if we cannot move more
    @Override
    public int checkSurrounding() {


        return 0;
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

    //Check the surrounding grid, return 1 if we cannot move more
    @Override
    public int checkSurrounding() {


        return 0;
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

