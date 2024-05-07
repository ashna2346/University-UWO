/**
 * Name: Ashna Mittal
 * Student ID: 251206758
 * CS3340B - Assignment 3
 */

public interface HeapADT{
    
    void heap_ini(Edge keys[], int n); // initialising a heap
    boolean in_heap(Edge id); // checking if the edge exists in the heap
    int min_key(); // returning the minimum key
    int min_id(); // returning the minimum id
    int key(int id); // returning the key at position id
    Edge delete_min(); // deleting the minimum edge
    void decrease_key(int id, int new_key); // decreasing the key at position id
}