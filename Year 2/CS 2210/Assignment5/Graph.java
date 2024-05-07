/**
 * This class represents an undirected graph.
 * @author: Ashna Mittal
 * Student ID: 251206758
 */


import java.util.*;
public class Graph
{
	// Private variables
	private Node nodes[];	
	private Edge edges[][];	

	/**
	 * Constructor that creates a graph with n nodes and no edges.
	 * @param n
	 */
	public Graph(int n)
	{
		nodes = new Node[n]; // Creating an array for the nodes
		edges = new Edge[n][n]; // Creating an array for the edges
		for (int i=0; i < n; i++)
		{
			nodes[i] = new Node(i);
		}
	}
	
	/**
	 * Returns the node with the specified id.
	 * @param id
	 * @return
	 * @throws GraphException
	 */
	public Node getNode(int id) throws GraphException
	{
		if (id>=0 && id<nodes.length) // check condition if the name of the node is valid
			return nodes[id];
		else // if nodes are invalid
			throw new GraphException("Invalid node");
	}

	/**
	 * Adds an edge of the given type connecting u and v.
	 * @param u
	 * @param v
	 * @param edgeType
	 * @throws GraphException
	 */
	public void addEdge(Node u, Node v, String edgeType) throws GraphException{

		// If the nodes are valid
		if (u.getId()>=0 && v.getId()>=0 && u.getId()<nodes.length && v.getId()<nodes.length){

			// check to see if there is no edge between them
			if (edges[u.getId()][v.getId()] == null && edges[v.getId()][u.getId()] == null){

				// form the edges to be inserted
				edges[u.getId()][v.getId()] = new Edge(u, v, edgeType);
				edges[v.getId()][u.getId()] = new Edge(v, u, edgeType);
			}
			else
				// if an edge exists throw an exception
				throw new GraphException("ERROR 404: Edge already exists");
		}
		else
			// if a node is invalid throw an exception
			throw new GraphException("ERROR 404: invalid node");
	}

	

	/**
	 * Returns a Java Iterator storing all the edges incident on node u. 
	 * It returns null if u does not have any edges incident on it.
	 * @param u
	 * @return
	 * @throws GraphException
	 */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException
	{

		if (u.getId() >= 0 && u.getId() < nodes.length) // check condition if nodes are valid
		{
			Stack<Edge> incidentEdges = new Stack<Edge>(); // creates a stack of edges
			// loop for every edge in the graph that are incident on u
			for (int i = 0; i < nodes.length; i++)
			{
				if (edges[u.getId()][i] != null)
					incidentEdges.push(edges[u.getId()][i]); // edge is pushed into the stack
			}
			if (incidentEdges.isEmpty()) // check condition if the stack is empty
				return null;
			else
				return incidentEdges.iterator();
		}
		else //if nodes are invalid
			throw new GraphException("Invalid node");
	}

	/**
	 * Returns the edge connecting nodes u and v.
	 * @param u
	 * @param v
	 * @return
	 * @throws GraphException
	 */
	public Edge getEdge(Node u, Node v) throws GraphException
	{

		// check condition if nodes are valid
		if (u.getId()>=0 && v.getId()>=0 && u.getId()<nodes.length && v.getId()<nodes.length)
		{
			if (edges[u.getId()][v.getId()] == null) // check condition if the edge exists between nodes
				throw new GraphException("No edge between specified nodes");
			else
				return edges[u.getId()][v.getId()];
		}

		else // if nodes are invalid
			throw new GraphException("Invalid node");
		
	}

	/**
	 * Returns true if nodes u and v are adjacent else false
	 * @param u
	 * @param v
	 * @return
	 * @throws GraphException
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException
	{
		 // check condition if the nodes are valid
		if (u.getId()>=0 && v.getId()>=0 && u.getId()<nodes.length && v.getId()<nodes.length) 
		{
			if(edges[u.getId()][v.getId()] != null) // check condition if the edge exists between nodes
				return true;
			else
				return false;
		}

		else // if nodes are invalid
			throw new GraphException("Invalid node");
	}
}