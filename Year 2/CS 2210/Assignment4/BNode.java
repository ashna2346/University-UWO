/*
 * This class represents the nodes of the binary search tree.
 * Author: Ashna Mittal
 * ID : 251206758
 */
public class BNode
{
	private Pel value;
	private BNode left, right, parent;

    /**
     * A constructor for the class that Stores the Pel value in the node and sets left child, right child, and parent to the specified values.
     * @param value
     */
    public BNode(Pel value,BNode left, BNode right, BNode parent)
    {
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
    
    /**
     * / A constructor for the class that initialises a leaf node. The data, children and parent attributes are set to null.
     */
    public BNode()  
    {
        this.value = null;
        this.left = this.right = this.parent = null;
    }

    /**
     * Returns the parent of this node.
     * @return parent
     */
    public BNode parent()   
    {
        return this.parent;
    }

    /**
     * Sets the parent of this node to the specified value.
     * @param newParent
     */
    public void setParent(BNode newParent) 
    {
        this.parent = newParent;
    }
    
    /**
     * Sets the left child of this node to the specified value.
     * @param p
     */
    public void setLeftChild (BNode p)  
    {
        this.left = p;
    }

    /**
     * Sets the right child of this node to the specified value.
     * @param p
     */
    public void setRightChild (BNode p)
    {
        this.right = p;
    }

    /**
     * Stores the given Pel object in this node.
     * @param value
     */
    public void setContent (Pel value)  
    {
        this.value = value;
    }

    /**
     * Returns true if this node is a leaf; returns false otherwise.
     * @return
     */
    public boolean isLeaf() 
    {
    	if(left == null && right == null)
    		return true;
    	else
    		return false;
    		
    }

    /**
     * Returns the Pel object stored in this node.
     * @return
     */
    public Pel getData () 
    {
        return this.value;
    }
    
    /**
     * Returns the left child of this node.
     * @return
     */
    public BNode leftChild()    
    {
        return this.left;
    }

    /**
     * Returns the right child of this node.
     * @return
     */
    public BNode rightChild()
    {
        return this.right;
    }

}
