/**
 * 
 * Student Name: Ashna Mittal
 * Student ID: 251206758
 */

public class uandf {
    // Arrays to hold the parent, rank, and size of each element in the set
    private int[] parent, rank, size;
    // Count of disjoint sets
    private int count;

    // Constructor for initialising the disjoint set with n elements
    public uandf(int n) {
        parent = new int[n];
        rank = new int[n];
        size = new int[n];
        count = n; // Initially, the number of disjoint sets equals the number of elements
        
        // Initialising each element to be its own parent (self loop), with rank 0 and size 1
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }

    // Method to find the root (representative) of the set that element i belongs to
    public int find_set(int i) {
        // Path compression: update parent[i] to its root to flatten the tree
        if (parent[i] != i) {
            parent[i] = find_set(parent[i]);
        }
        return parent[i];
    }

    // Method to merge (union) the sets containing elements i and j
    public void union_sets(int i, int j) {
        int iRoot = find_set(i); // Find root of the set containing i
        int jRoot = find_set(j); // Find root of the set containing j
        if (iRoot == jRoot) return; // i and j are already in the same set

        // Union by rank: attach the tree with lower rank to the root of the tree with higher rank
        if (rank[iRoot] < rank[jRoot]) {
            parent[iRoot] = jRoot; // Make jRoot the parent of iRoot
            size[jRoot] += size[iRoot]; // Update the size of the set containing jRoot
        } else if (rank[jRoot] < rank[iRoot]) {
            parent[jRoot] = iRoot; // Make iRoot the parent of jRoot
            size[iRoot] += size[jRoot]; // Update the size of the set containing iRoot
        } else {
            parent[jRoot] = iRoot; // Make iRoot the parent of jRoot
            rank[iRoot]++; // Increment the rank of iRoot as the trees are of equal rank
            size[iRoot] += size[jRoot]; // Update the size of the set containing iRoot
        }
        count--; // Decrease the count of disjoint sets as two sets are merged into one
    }

    // Method to create a new set whose only member is i
    public void make_set(int i) 
    {
        parent[i] = i;
        rank[i] = 0;
    }  


    // Method to consolidate the disjoint sets and return the final count of disjoint sets
    public int final_sets() {
        int[] newRepresentatives = new int[parent.length];
        int current = 1;
        // Assign new representative numbers to root elements
        for (int i = 0; i < parent.length; i++) {
            if (i == find_set(i)) { // If the element is a root
                newRepresentatives[i] = current++;
            }
        }
        // Update all elements to point to the new representatives
        for (int i = 0; i < parent.length; i++) {
            parent[i] = newRepresentatives[find_set(i)];
        }
        return count; // Return the final count of disjoint sets
    }
}
