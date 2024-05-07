/**
 * @author Ashna Mittal
 *This class is used to create the binary trees using a queue-based approach
 * @param <T>
 */
public class TreeBuilder<T>
{
	/*
	 * This method takes in a T array input parameter which contains the set of values that will be inserted in the new tree's nodes
	 */
   public LinkedBinaryTree<T> buildTree(T[] data)
   {
       LinkedQueue<T> dataQueue = new LinkedQueue<T>();
       //loop to input value in queue in order
       
       for(int i=0;i<data.length;i++)
       {
           dataQueue.enqueue(data[i]);
       }
       
       LinkedQueue<BinaryTreeNode<T>> parentQueue = new LinkedQueue<BinaryTreeNode<T>>();
       BinaryTreeNode<T> root = new BinaryTreeNode<T>(dataQueue.dequeue()); // node storing the first element of dataQueue
       parentQueue.enqueue(root); // enqueue the root node on parentQueue
      
       while(!dataQueue.isEmpty())
       {
    	   // dequeue from dataQueue
           T a = dataQueue.dequeue();
           T b = dataQueue.dequeue();
           // dequeue from parentQueue and storing it in a node
           BinaryTreeNode<T> parent = parentQueue.dequeue();
           
           if(a != null) // check condition if a is not null
           {
               BinaryTreeNode<T> node = new BinaryTreeNode<T>(a);
               parent.setLeft(node); // setting left child of parent
               parentQueue.enqueue(node); // enqueue on parentQueue
           }
          
           if(b != null) // check condition if b is not null
           {
               BinaryTreeNode<T> node = new BinaryTreeNode<T>(b);
               parent.setRight(node); // setting right child of parent
               parentQueue.enqueue(node); // enqueue on parentQueue
           }
       }
       //returns the LinkedBinaryTree object with the root node from above
       return new LinkedBinaryTree<T>(root);
   }
} // class closes