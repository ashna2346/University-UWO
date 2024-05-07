/*
 * Author: Ashna Mittal
 * ID : 251206758
 */

public class MyObject implements MyObjectADT
{
	// Instance Variables
	private int id;
	int width;
	int height;
	String type;
	BinarySearchTree tree;
	Location locus;

		// Constructor
	public MyObject (int id, int width, int height, String type, Location pos)
	{
		this.id = id;
		this.width = width;
		this.height = height;
		this.type = type;
		this.locus = pos;
		tree = new BinarySearchTree();
	}
    /*
	 * Sets the type of this MyObject to the specified value.
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	
	/*
	 * Returns the width of the enclosing rectangle for this MyObject.
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/*
	 * Returns the height of the enclosing rectangle for this MyObject.
	 */
	public int getHeight()
	{
		return this.height;
	}
	
	/*
	 * Returns the type of this Mybject.
	 */
	public String getType()
	{
		return this.type;
	}
	
	/*
	 * Returns the id of this MyObject.
	 */
	public int getId()
	{
		return this.id;
	}
	
	/*
	 * Returns the locus of this MyObject.
	 */
	public Location getLocus()
	{
		return this.locus;
	}
	
	/*
	 * Changes the locus of this MyObject to the specified value
	 */
	public void setLocus(Location value)
	{
		this.locus = value;
	}

	/**
	 * Inserts pix into the binary search tree associated with this MyObject.
	 */
    public void addPel(Pel pix) throws DuplicatedKeyException
    {
    	try
    	{
    		this.tree.put(this.tree.getRoot(), pix); // insertion 
    	}
    	catch (DuplicatedKeyException e)
    	{
    		System.out.println(e);
    	}
    }

    /**
	 * Returns true if this MyObject intersects the one specified in the parameter. It returns false otherwise.
	 */
    public boolean intersects(MyObject otherObject)
    {
        if(checkRectangles(otherObject) )
        {
            Location large = null;
            try
            {
                large = tree.largest(tree.getRoot()).getLocus();
                for (Pel e = tree.smallest(tree.getRoot()); e.getLocus().compareTo(large) < 0; e = tree.successor(tree.getRoot(), e.getLocus()))
                {
                	//checking if the enclosing rectangle of f intersects the enclosing rectangle of another object f′
                    if (otherObject.findPel((genPos(e, otherObject)))&& checkRectangles(otherObject)) // check condition
                        return true; // returns true if no intersection
                }
            } catch (EmptyTreeException e) {}
        }
        return false;
    }


    /**
     * returns true if this MyObject has a Pel object in location p
     * @param p
     * @return
     */
    private boolean findPel(Location p)
    {
    	if (tree.get(tree.getRoot(), p) != null) // check condition if MyObject has a Pel object in location p
    		return true;
    	else
    		return false;
    }
    
    private Location genPos(Pel e, MyObject fig)
    {
    	int x = e.getLocus().getx() + this.getLocus().getx() - fig.getLocus().getx();
    	int y = e.getLocus().gety() + this.getLocus().gety() - fig.getLocus().gety();
    	return new Location(x, y );
    }
    
    private boolean checkRectangles(MyObject fig)
    {
        if(locus.gety() > fig.getLocus().gety() + fig.getHeight())
            return false;
        else if(locus.gety() + getHeight() < fig.getLocus().gety())
            return false;
        else
        {
            if(locus.getx() > fig.getLocus().getx() + fig.getWidth())
                return false;
            else if(locus.getx() + getWidth() < fig.getLocus().getx())
                return false;
            else
                return true;
        }
    }
}
		