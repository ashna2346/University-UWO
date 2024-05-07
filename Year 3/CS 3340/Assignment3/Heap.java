/**
 * Name: Ashna Mittal
 * Student ID: 251206758
 * CS3340B - Assignment 3
 */

public class Heap implements HeapADT{
    
    // global variables declaration
    public Edge[] A;
    public int[] H;
    private int max;
    private int hMax;
    
    // Heap constructor
    public Heap(){
        
    }
    
    /*
     * heap_ini method
     *  This method initialises a heap with the array keys of n elements indexed from 1 to n, where key[i] is the key of the element whose id is i
     */
    public void heap_ini(Edge keys[], int n)
    {
        A = keys; // Setting A to reference the array of edges
        max = n-1; // Setting the max to be n-1
        H = new int[2*max]; // Setting H to be an array of 2*max elements
        hMax = 2*max-1; // hMax refers to 2n-1 --> 2max - 1
        heapify(); // Calling heapify to create the heap
    }
    
    /*
     * private heapify method that creates the heap, by sorting and rearranging the id
     */
    private void heapify()
    {
        for(int i = max; i <= hMax; i++)
        {
            H[i] = i - max + 1;
        }
        for(int i = max - 1; i >= 1; i--)
        {
            if(A[H[2*i]].getWeight() < A[H[2*i+1]].getWeight()){
                H[i] = H[2*i];
            }
            
            else
                H[i] = H[2*i+1];
            
        }
    }
    
    /*
     * in_heap method that returns true if the element whose id isin the heap
     */
    public boolean in_heap(Edge id)
    {
        Edge check = new Edge(0,0,0);
        // looping through the array of keys
        for(int i = 0; i < A.length; i++)
        {
            check  = A[i];
            
            // check condition that returns true if the end points and weight of check match id
            if(check.u == id.u && check.v == id.v && check.weight == id.weight){
                return true;
            }
            
            // check condition that returns true If end points are reversed but still match
            else if (check.u == id.v && check.v == id.u && check.weight == id.weight){
                return true;
            }
        }
        return false;
    }
    
    /*
     * min_key method that returns the minimum key of the heap
     */
    public int min_key(){
        return A[H[1]].getWeight();
    }
    
    /*
     * min_id method that returns the minimum id of the heap
     */
    public int min_id(){
        return H[1];
    }
    
    /*
     * key method that returns the key of the element whose id is id in the heap
     */
    public int key(int id){
        return A[id].getWeight();
    }
    
    /*
     * delete_min method that deletes the element with minimum key from the heap
     */
    public Edge delete_min(){
        Edge removed = new Edge(0,0,Integer.MAX_VALUE);
        A[0] = removed;
        H[H[1] + max - 1] = 0;
        Edge v = A[H[1]];
        int i = (int)Math.floor((H[1]+max-1)/2);
        while (i >=1)
        {
            if(A[H[2*i]].getWeight() < A[H[2*i+1]].getWeight()){
                H[i] = H[2*i];
            }
            
            else{
                H[i] = H[2*i+1];
            }
            i = (int)Math.floor(i/2);
        }
        
        // returning the element that has been removed
        return v;
        
    }
    
    /*
     * decrease_key method that sets the key of the element whose id is id to new_key if its current key is greater than new_key
     */
    public void decrease_key(int id, int new_key){
        A[id].weight = new_key;
        int i = (int)Math.floor((id+max-1)/2);
        // heapify again
        while (i >=1){
            if (A[H[2*i]].weight < A[H[2*i+1]].weight){
                H[i] = H[2*i];
            }
            
            else{
                H[i] = H[2*i+1];
            }
            i = (int)Math.floor(i/2);
        }
    }
}