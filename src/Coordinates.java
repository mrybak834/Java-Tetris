/**
 * Created by mrybak834 on 4/20/2016.
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
