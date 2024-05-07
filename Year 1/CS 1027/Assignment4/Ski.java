/**
 * This class has the skier going down the hill one step at a time
 * @author Ashna Mittal
 *
 */
public class Ski
{
	//declaration of private instance variable
    private BinaryTreeNode<SkiSegment> root;

    /*
     * This method takes in a String array with data about the node types
     */
    public Ski(String[] data)
    {
        SkiSegment[] segments = new SkiSegment[data.length];// an array of objects 
        
        for (int i = 0; i < data.length; i++)
        {
            if (data[i] == null)//check condition
                segments[i] = null;
            else if (data[i].contains("jump"))//check condition
                segments[i] = new JumpSegment(String.valueOf(i), data[i]);
            else if (data[i].contains("slalom"))//check condition
                segments[i] = new SlalomSegment(String.valueOf(i), data[i]);
            else
                segments[i] = new SkiSegment(String.valueOf(i), data[i]);
        }
        
        TreeBuilder<SkiSegment> treeobj = new TreeBuilder<SkiSegment>(); // an object of Treebuilder
        root = treeobj.buildTree(segments).getRoot();
    }

    public BinaryTreeNode<SkiSegment> getRoot()
    {
        return root; // returning the tree's root node
    }

    public void skiNextSegment(BinaryTreeNode<SkiSegment> node, ArrayUnorderedList<SkiSegment> sequence)
    {
        sequence.addToRear(node.getData()); //adding the data stored in parameter node to the end of the sequence
        
        if (node.getLeft() == null && node.getRight() == null)//check condition to determine the next node to access from the node passed as parameter
            return;
        
        skiNextSegment(next(node), sequence); // recursive function calling itself
    }

    /*
     * Private method that uses recursion for continuing the path to the next node
     */
    private BinaryTreeNode<SkiSegment> next(BinaryTreeNode<SkiSegment> node)
    {
        if (node.getLeft() == null)//check condition
            return node.getRight();
        if (node.getRight() == null)//check condition
            return node.getLeft();
        
        SkiSegment right = node.getRight().getData(); // storing the right node
        SkiSegment left = node.getLeft().getData(); // storing the left node
        
        if (right instanceof JumpSegment && left instanceof JumpSegment) // check condition
        {
            JumpSegment right_Jump = (JumpSegment) right;
            JumpSegment left_Jump = (JumpSegment) left;
            
            if (right_Jump.getHeight() >= left_Jump.getHeight()) // check condition for comparing the heights of the right and left jumps
                return node.getRight();
            else
                return node.getLeft();
        }
        
        if (right instanceof JumpSegment) // check condition
            return node.getRight();
        if (left instanceof JumpSegment) // check condition
            return node.getLeft();
        
        if (left instanceof SlalomSegment && right instanceof SlalomSegment) // check condition
        {
            SlalomSegment leftSlalom = (SlalomSegment) left; // object creation
            if (leftSlalom.getDirection().equals("L")) // check condition
                return node.getLeft();
            else
                return node.getRight();
        }
        
        if (left instanceof SlalomSegment || right instanceof SlalomSegment)// check condition
        {
            if (left instanceof SlalomSegment)// check condition
            {
                SlalomSegment leftSlalom = (SlalomSegment) left; // object creation
                if (leftSlalom.getDirection().equals("L")) // check condition
                    return node.getLeft();
                else
                    return node.getRight();
            } 
            else
            {
                SlalomSegment rightSlalom = (SlalomSegment) right; // object creation
                if (rightSlalom.getDirection().equals("L")) // check condition for leeward(safe) side
                    return node.getRight();
                else
                    return node.getLeft();
            }
        }
        return node.getRight();
    }
} // class closes