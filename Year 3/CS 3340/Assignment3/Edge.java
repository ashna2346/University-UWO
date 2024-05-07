/**
 * Name: Ashna Mittal
 * Student ID: 251206758
 * CS3340B - Assignment 3
 */
public class Edge{

    // Components of an Edge
    public int u;
    public int v;
    public int weight;
    
    /*
     * Edge Constructor that creates an edge with endpoints and weight
     */
    public Edge(int s, int f, int weight){
        this.u = s;
        this.v = f;
        this.weight = weight;
    }
    
    /*
     * getFirstEndPoint method that returns the first end point of an edge
     */
    public int getFirstEndPoint(){
        return this.u;
    }
    
    /*
     * getLastEndPoint method that returns the last end point of an edge
     */
    public int getLastEndPoint(){
        return this.v;
    }
    
    /*
     * getWeight method that returns the weight of the edge
     */
    public int getWeight(){
        return this.weight;
    }
    
    /*
     * toString method that prints out the components of an edge
     */
    public String toString(){
        return "U = " + this.u + " V = " + this.v + " Weight = " + this.weight + "\n";
    }
}