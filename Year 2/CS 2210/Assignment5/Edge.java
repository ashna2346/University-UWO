/** 
 * This class represents the edge of a graph
 * @author: Ashna Mittal
 * Student ID: 251206758
 */
public class Edge
{
	// Private variables
	private Node uEdge;
	private Node vEdge;
	private String typeEdge;

	/**
	 * Constructor 
	 * @param u Node that represents First Node
	 * @param v Node that represents Second Node
	 * @param type String that represents the edge type that is associated with the edge
	 */
	public Edge(Node u, Node v, String type)
	{
		uEdge = u;
		vEdge = v;
		typeEdge = type;
	}
	
	/**
	 * Returns the first end point of the edge.
	 * @return
	 */
	public Node firstNode()
	{
		return uEdge;
	}

	/**
	 * Returns the second end point of the edge.
	 * @return
	 */
	public Node secondNode()
	{
		return vEdge;
	}

	/**
	 * Returns the type of the edge.
	 * @return 
	 */
	public String getType()
	{
		return typeEdge;
	}
}