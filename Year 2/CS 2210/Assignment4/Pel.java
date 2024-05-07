/*
 * This class represents the data items to be stored in the nodes of the binary search tree.
 * Author: Ashna Mittal
 * ID : 251206758
 */
public class Pel 
{
	// instance variables
    Location p;
    int color;
    
    /**
     * A constructor which initialises the new Pel with the specified coordinates and color
     * @param p
     * @param color
     */
    public Pel(Location p, int color)
    {
        this.p = p;
        this.color = color;
    }

    /**
     * Returns the Location of this Pel
     * @return p
     */
    public Location getLocus()
    {
        return p;
    }

    /**
     * Returns the colour of this Pel object
     * @return colour
     */
    public int getColor()
    {
        return color;
    }
}
