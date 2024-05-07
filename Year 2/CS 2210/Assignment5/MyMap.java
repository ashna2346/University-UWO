/**
 * This class represents the road map. A graph will be used to store the map and to try 
 * to find a path from the starting point to the destination.
 * @author Ashna Mittal
 * Student ID: 251206758
 */

import java.io.File;
import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;
public class MyMap
{
	//private variables
    private int start, end, width, length, pvtRoads, constRoads;
    private Graph graph;

    //constructor
    public MyMap(String inputFile) throws MapException
    {
        try
        {
        	//Initialising private variables
            File inputRead = new File(inputFile);
            Scanner input = new Scanner(inputRead);
            input.nextLine();
            this.start = Integer.parseInt(input.nextLine());
            this.end = Integer.parseInt(input.nextLine());
            this.width = Integer.parseInt(input.nextLine());
            this.length = Integer.parseInt(input.nextLine());
            this.pvtRoads = Integer.parseInt(input.nextLine());
            this.constRoads = Integer.parseInt(input.nextLine());
            String[] inputMap = new String[(length * 2) - 1];
            for (int i = 0; i < inputMap.length; i++)
            {
                inputMap[i] = input.nextLine();//inputting map 
            }
            this.graph = buildGraph(inputMap);
            input.close();
        } 
        catch (Exception e)
        {
        	// If file not found
            throw new MapException("Input file doesn't exist.");
        }
    }
    
    /**
     * This private helper method helps to construct the graph
     * @param inputMap
     * @return
     */
    private Graph buildGraph(String[] inputMap)
    {
        Graph inputGraph = new Graph(width * length);//initializing graph
        int nodeCount = -1;
        for (int y = 0; y < inputMap.length; y++)
        {
            for (int x = 0; x < inputMap[y].length(); x++)
            {
                if (y % 2 == 0) // check condition
                {
                    if (inputMap[y].charAt(x) == '+') // check condition if node is found
                    {
                        nodeCount++;//increase nodeNum to keep track of the current node id
                        try
                        {
                            Node currNode = inputGraph.getNode(nodeCount);//get the current node from the graph
                            if (x - 1 > 0) // check condition for left road
                            {
                                if (inputMap[y].charAt(x - 1) == 'V') // check condition
                                    inputGraph.addEdge(currNode, inputGraph.getNode(nodeCount - 1), "private");
                                else if (inputMap[y].charAt(x - 1) == 'P') // check condition
                                    inputGraph.addEdge(currNode, inputGraph.getNode(nodeCount - 1), "public");
                                else if (inputMap[y].charAt(x - 1) == 'C') // check condition
                                    inputGraph.addEdge(currNode, inputGraph.getNode(nodeCount - 1), "construction");
                            }
                            if (y - 1 > 0) // check condition for top road
                            {
                                if (inputMap[y - 1].charAt(x) == 'V') // check condition
                                    inputGraph.addEdge(currNode, inputGraph.getNode(nodeCount - width), "private");
                                else if (inputMap[y - 1].charAt(x) == 'P') // check condition
                                    inputGraph.addEdge(currNode, inputGraph.getNode(nodeCount - width), "public");
                                else if (inputMap[y - 1].charAt(x) == 'C') // check condition
                                    inputGraph.addEdge(currNode, inputGraph.getNode(nodeCount - width), "construction");
                            }
                        } 
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                } 
                else 
                    break;  
            }
        }
        return inputGraph;//return the completely constructed graph
    }
    
    /**
     * This method Returns the graph representing the road map.
     * @return
     */
    public Graph getGraph()
    {
        return graph;
    }

    /**
     * This method Returns the id of the starting node
     * @return
     */
    public int getStartingNode()
    {
        return start;
    }

    /**
     * This method Returns the id of the destination node.
     * @return
     */
    public int getDestinationNode()
    {
        return end;
    }

    /**
     * This method Returns the maximum number allowed of private roads in the path
     * @return
     */
    public int maxPrivateRoads()
    {
        return pvtRoads;
    }

    /**
     * This method Returns the maximum number allowed of construction roads in the path
     * @return
     */
    public int maxConstructionRoads()
    {
        return constRoads;
    }
    
    /**
     * Re- turns a Java Iterator containing the nodes of a path from the start node to the destination node
     * @param start
     * @param destination
     * @param maxPrivate
     * @param maxConstruction
     * @return
     */
    public Iterator findPath(int start, int destination, int maxPrivate, int maxConstruction)
    {
        try
        {
            Node startNode = getGraph().getNode(start);
            Node endNode = getGraph().getNode(destination);
            ArrayList<Node> nodeList =  new ArrayList<Node>();//an array list that holds the path from the start point to the destination point
            nodeList.add(startNode);//add the start node to the list of path
            return pathFinding(startNode, endNode, maxPrivate, maxConstruction, 0, 0, nodeList).iterator();//calling private recursive method
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }
    
    /**
     * This private helper function recursively finds path
     * @param curr
     * @param dest
     * @param maxPvt
     * @param maxConst
     * @param curPvt
     * @param curConst
     * @param currNodes
     * @return
     */
    private ArrayList<Node> pathFinding(Node curr, Node dest, int maxPvt, int maxConst, int curPvt, int curConst, ArrayList<Node> currNodes)
    {
        if (curPvt > maxPvt || curConst > maxConst) //check condition if path is invalid
            return null;
        
        if (curr == dest) // check condition if the path to the destination has been found
            return currNodes;
        curr.markNode(true);//mark current node as visited so that it is not visited again 
        try
        {
            Iterator<Edge> iter = graph.incidentEdges(curr);//getting all  adjacent edges to the current node
            while (iter.hasNext())
            {
                Edge curEdge = iter.next();
                Node nextNode = curEdge.secondNode();//getting the attached node
                if (nextNode.getMark()) // check condition to see if this node was visited before
                    continue;
                ArrayList<Node> clone = (ArrayList<Node>) currNodes.clone();//copy of the array list so that the original list is unaffected
                clone.add(nextNode);//adding the other node to the copy of array list
                ArrayList<Node> ans;//an arraylist that holds the solved path
                if(curEdge.getType() == "private") // check condition
                    ans = pathFinding(nextNode, dest, maxPvt, maxConst, curPvt+1, curConst, clone);
                
                else if(curEdge.getType() == "construction") // check condition
                    ans =  pathFinding(nextNode, dest, maxPvt, maxConst, curPvt, curConst+1, clone);
                
                else
                    ans =  pathFinding(nextNode, dest, maxPvt, maxConst, curPvt, curConst, clone);
                
                if(ans != null)
                    return ans;
                
            }
        }
        catch (Exception e) 
        {
            System.out.println(e);
        }
        curr.markNode(false);//if path is invalid, current node is marked as not visited 
        return null;
    }
}