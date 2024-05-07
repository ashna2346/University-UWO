/**
 * This class represents an extended stack ADT implemented using a doubly linked list
 * @author Ashna Mittal
 */
public class DLStack<T> implements DLStackADT<T>
{
	// Declaration of private instance variables
	private DoubleLinkedNode<T> top;
	int numItems;
	
	/*
	 * Constructor 
	 */
	public DLStack()
	{
		top = null;
		numItems = 0;
	}

	/*
	 * public method that Adds the given dataItem to the top of the stack
	 */
	public void push(T dataItem)
	{
		DoubleLinkedNode<T> val = new DoubleLinkedNode<T> (dataItem); // local node
		if(top==null) // check condition
			top=val;
		else
		{
			val.setPrevious(top);
			top.setNext(val);
		}
		top=val; // setting the new added value as top
		numItems++; // increasing the count of items in the stack
	}
	
	/*
	 * public method that Removes and returns the data item at the top of the stack
	 */
	public T pop() throws EmptyStackException
	{
		if (isEmpty()) // check condition for an empty stack
			throw new EmptyStackException("Empty DL stack");
		T topItem = top.getElement(); // storing the topmost element in a local variable
		if(top.getPrevious()!=null) // check condition
		{
			top=top.getPrevious();
			top.setNext(null);
		}
		else
			top=null;
		numItems--; // decreasing the count of items in the stack after the deletion process
		return topItem; // returning the new top element after the deletion process
	}
	
	/*
	 * public method that Removes and returns the k-th data item from the top of the stack
	 */
	public T pop(int k) throws InvalidItemException
	{
		if(k>numItems || k<=0) // check condition for an invalid stack
			throw new InvalidItemException("Error in DL stack");
		else if(isEmpty())// check condition for an empty stack
			throw new EmptyStackException("Empty DL stack");
		else
		{
			DoubleLinkedNode<T> val = top; // local node
			T temp;
			if(k==1) // check condition
				return pop();
			for(int i=1;i<k; i++)
			{
				val=val.getPrevious();
			}	
			if(val.getPrevious()==null)
			{
				temp = val.getElement();
				val=val.getNext(); // gets rid of the last element
				val.setPrevious(null); // setting the previous node to null
			}
			else
			{
				temp = val.getElement();
				val.getNext().setPrevious(val.getPrevious()); // gets rid of the last element
				val.getPrevious().setNext(val.getNext());; // setting the previous node to null
			}
			numItems--; // decreasing the count of items in the stack after the deletion process
			return temp;
		}
	}


	/*
	 * public method that Returns the data item at the top of the stack without removing it
	 */
	public T peek() throws EmptyStackException
	{
		if (isEmpty()) // check condition for an empty stack
			throw new EmptyStackException("Empty DL stack");
		else
			return top.getElement(); // returning the top most element without removing it
	}


	/*
	 * public method that Returns true if the stack is empty
	 */
	public boolean isEmpty()
	{
		if(top==null)//check condition to see if the topmost element is null, then the stack is empty
			return true;
		else
			return false;
	}

	/*
	 * public method that Returns the number of data items in the stack
	 */
	public int size()
	{
		return numItems; 
	}

	/*
	 * public method that Returns top
	 */
	public DoubleLinkedNode<T> getTop()
	{
		return top;
	}
	
	/*
	 * public method that Returns a string form
	 */
	public String toString()
	{
		DoubleLinkedNode<T> val = top;
		String s= "[";
		for(int i=0;i<numItems-1;i++) // loop to iterate over the stack
		{
			s+= val.getElement()+ ","; // storing the elements of the stack in the string
			val=val.getPrevious();
	    }
		s+=val.getElement();
	    s+="]";
	    return s;
	}
}// class ends