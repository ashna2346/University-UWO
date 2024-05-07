/**
 * Name: Ashna Mittal
 * Student ID: 251206758
 * CS3340B - Assignment 3
 */

// java import statements
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.*;
import java.util.*;

public class prims
{
    
    //Global Variable declaration
    static int numV;
    
    /* MST_Prim method
     * This method creates a minimum spanning tree 1 edge at a time to show each node, it's destination, and it's weight
     */
    public static void MST_Prim(ArrayList<LinkedList<Edge>> Graph, int root)
    {
        // Initialising keys for the heap and parent array
        Edge[] keys = new Edge[numV+1];
        int[] pi = new int[numV+1];
        for (int i = 0; i <= numV; i++){
            keys[i] = new Edge(0,0,Integer.MAX_VALUE); // Defaults as 0,0,and inf
            pi[i] = -1; // The parent is -1
        }

        keys[root].weight = 0; // The root node's weight = 0
        keys[root].u = root; // It is its own parent
        keys[root].v = root;
        pi[root] = root;
        
        Heap heap = new Heap();
        heap.heap_ini(keys, keys.length);

        // List to keep track of the edges added to the MST
        ArrayList<Edge> mstEdges = new ArrayList<>();
        int totalWeight = 0; // Variable to keep track of the total weight of the MST

        while(true){
            Edge minEdge = heap.delete_min();
            int weight = minEdge.getWeight();
            
            if(weight == Integer.MAX_VALUE) break; // If weight is MAX_VALUE, all vertices have been processed
            
            if(minEdge.u != 0 && minEdge.v != 0){
                LinkedList<Edge> list = Graph.get(minEdge.v);
                for(int i = 0; i < list.size(); i++){
                    Edge listEdge = list.get(i);
                    if(!heap.in_heap(listEdge) && listEdge.weight < keys[listEdge.v].weight){
                        pi[listEdge.v] = listEdge.u;
                        keys[listEdge.v] = listEdge;
                        heap.decrease_key(listEdge.v, listEdge.weight);
                    }
                }
                if(minEdge.weight != 0){ // check condition if it is not the root node
                    mstEdges.add(minEdge); // Add edge to MST
                    totalWeight += minEdge.weight; // Add weight to total
                }
            }
        }

        // Print the edges in the MST and their weights
        System.out.println("Edges in the MST:");
        for(Edge edge : mstEdges) {
            System.out.println("(" + edge.u + ", " + edge.v + ") with weight " + edge.weight);
        }
        
        // Print the total weight of the MST
        System.out.println("Total weight of the MST: " + totalWeight);
    }

    
    public static void main(String[] args) {
        int inp = -1;
        File f;
        Scanner in;
        
        
        if(args.length != 1) // If args is not 1 or has no arguments
        {
            System.out.println("Please enter the following: asn3.sh < file");
            inp = 1; // Set inp to 1
        }
        
        try
        {
            
            // check condition if inp is 1, then scanner will come from a file from input
            if(inp == 1)
            {
                in = new Scanner(System.in);
            }
            
            // else part for the scanner that comes from a file from args[0]
            else{
                in = new Scanner(new FileReader(args[0]));
            }
            
            // Reading the first integer from the file, it represents number of vertices
            numV = Integer.parseInt(in.nextLine());
            
            // Initialising the Adjacency list
            ArrayList<LinkedList<Edge>> adj_list = new ArrayList<LinkedList<Edge>>();
            
            for(int i = 0; i <= numV; i++){
                adj_list.add(new LinkedList<Edge>());
            }
            
            // loop for the file to check if it is not at the end
            while(in.hasNext())
            {
                String s = in.nextLine(); // Get the next line
                int u;
                int v;
                int w;
                
                // tokenize the string and set the values to u, v, and w
                StringTokenizer tok = new StringTokenizer(s);
                u = Integer.parseInt(tok.nextToken());
                v = Integer.parseInt(tok.nextToken());
                w = Integer.parseInt(tok.nextToken());
                // Creating an edge going from u to v and v to u
                Edge newEdge = new Edge(u, v, w);
                Edge newEdge2 = new Edge(v, u, w);
                // Adding the edges into the appropriate list
                adj_list.get(u).addLast(newEdge);
                adj_list.get(v).addLast(newEdge2);
            }
            
            // Printing Block
            System.out.println("Adjacency List Representation");
            // Loop through the first lists
            for(int i = 1; i <= numV; i++){
                System.out.print("Adjacent to " + adj_list.get(i).get(0).getFirstEndPoint() + ": ");
                
                // Loop through for the adjacent edges
                for(int j = 0; j < adj_list.get(i).size(); j++){
                    System.out.print(adj_list.get(i).get(j).getLastEndPoint() + " (" + adj_list.get(i).get(j).getWeight() + "), ");
                }
                System.out.println();
            }
            
            // Set the root to be the first possible node in the adjacency list
            int root = adj_list.get(1).get(0).getFirstEndPoint();
            System.out.println();
            
            // Calling Prim's algorithm
            MST_Prim(adj_list, root);
            
        }
        
        // Catch any necessary Exception
        catch(FileNotFoundException e){
            System.out.println("Unable to load File");
            return;
        }
    }
}