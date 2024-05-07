/*
 * This class represents the location (x, y) of a pel.
 * Author: Ashna Mittal
 * ID : 251206758
 */
public class Location
{
	private int x; //private coordinate variable
	private int y; //private coordinate variable

	/*
	 * A constructor that initializes Location object with the specified coordinates.
	 */
	public Location(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter method that returns the x coordinate of this location
	 * @return x
	 */
	public int getx()
	{
		return x;
	}

	/**
	 * Getter method that returns the y coordinate of this location
	 * @return Y
	 */
	public int gety()
	{
		return y;
	}

	/**
	 * Compares this Location with Position p using row-order
	 * @param p
	 * @return
	 */
	public int compareTo(Location p)
	{
		// this location is greater than location p 
		if((this.gety() > p.gety()) || (this.gety() == p.gety() && this.getx() > p.getx()))
			return 1;
		// two locations are equal 
		else if(this.getx() == p.getx() && this.gety() == p.gety())
			return 0;
		// location p is greater than this position
		else
			return -1;
	}
}