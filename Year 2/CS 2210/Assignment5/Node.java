/**
 * This class represent a node of the graph.
 * @author Ashna Mittal
 * Student ID: 251206758
 */

public class Node
{
	// Private variables
	private int nameOfnode;
	private boolean marknode = false;

	/**
	 * Constructor that creates a node with the given id.
	 * @param id
	 */
	public Node(int id)
	{
		nameOfnode = id;
		marknode = false;
	}

	/**
	 * Setter method that marks the node with the specified value, either true or false.
	 * @param mark
	 */
	public void markNode(boolean mark)
	{
		marknode = mark;
	}

	/**
	 * Returns the value with which the node has been marked.
	 * @return either True or False
	 */
	public boolean getMark()
	{
		return marknode;
	}

	/**
	 * Returns the id of this node.
	 * @return node name
	 */
	public int getId()
	{
		return nameOfnode;
	}

}