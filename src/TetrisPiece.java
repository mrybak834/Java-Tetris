import javax.swing.*;

/**
 * Created by mrybak834 on 4/20/2016.
 */

public class TetrisPiece {
    //The current orientation of the piece (1-4);
    int orientation;
    public Coordinates[] positions;

    //Creates a tetris piece
    TetrisPiece() {

        //Initial orientation
        this.orientation = 1;
    }

    public Coordinates[] getPositions() {
        return null;
    }


    public int advance(JLabel[][] labelArray) {
        return 1;
    }

    public int rotate(JLabel[][] labelArray, Icon[] iconArray){
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


            return 1;
        } catch (Exception e) {
            System.out.println("0: " + positions[0].x + " " + positions[0].y);
            System.out.println("2: " + positions[2].x + " " + positions[2].y);
            System.out.println("3: " + positions[3].x + " " + positions[3].y);
            return 0;
        }


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
                System.out.println("0:" +  positions[0].x + " " + positions[0].y);
                System.out.println("1:" +  positions[1].x + " " + positions[1].y);
                System.out.println("2:" +  positions[2].x + " " + positions[2].y);
                System.out.println("3:" +  positions[3].x + " " + positions[3].y);
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

