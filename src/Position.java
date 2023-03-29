/**
 * The Position class is used to store information about positions, ie coordinates.
 */
public class Position {
    int x;
    int y;


    /**
     * <p>
     *     Construct a new Position object with x- and y-coordinates.
     * </p>
     * @param x coordinate in x-dimension
     * @param y coordinate in y-dimension
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * <p>
     *     Function to obtain the x-coordinate of a position.
     * </p>
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * <p>
     *     Function to obtain the y-coordinate of a position.
     * </p>
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }


}