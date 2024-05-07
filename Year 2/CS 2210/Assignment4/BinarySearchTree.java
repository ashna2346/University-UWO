/**
 * This class implements an ordered dictionary using a binary search tree.
 * author: Ashna MIttal
 * ID: 251206758
 */


public class BinarySearchTree implements BinarySearchTreeADT 
{
	private BNode root; // instance variable
	
	public BinarySearchTree() // constructor
	{
		root=new BNode();
	}
	
	/**
	 * Returns the Pel object storing the given key, if the key is stored in the tree; returns null otherwise.
	 */
	public Pel get(BNode r, Location key)
	{
		int a=r.getData().getLocus().compareTo(key);
		if (r.isLeaf()&&a!=0)
			return null;
		else if (a==0)
			return r.getData();
		else if (a==1)
			if (r.leftChild()==null) // check condition if the left child is null
				return null;
			else
				return get(r.leftChild(),key);
		if (r.rightChild()==null) // check condition if the right child is null
			return null;
		else
			return get(r.rightChild(),key); // recursive call to traverse the tree repeatedly
	}

	/**
	 * Inserts newData in the tree if no data item with the same key is already there
	 */
	public void put(BNode r, Pel data) throws DuplicatedKeyException
	{
		if (r==null)
		{
			r= new BNode(); //create a temporary new node
			root=r; // initialize root to the node
		}
		if (r.getData()==null) 
			r.setContent(data);
		else
		{
			int a=r.getData().getLocus().compareTo(data.getLocus());
			if (a==0)
				throw new DuplicatedKeyException("ERROR! Already exists");
			if (r.isLeaf()) // check condition if r is a leaf
			{
				if (a==0)
					throw new DuplicatedKeyException("ERROR! Already exists");
				else
					if (a==1)
					{
						BNode b=new BNode(data,null,null,r);
						r.setLeftChild(b);
					}
					else if (a==-1)
					{
						BNode b=new BNode(data,null,null,r); // creation of a temporary node
						r.setRightChild(b);
					}
			}
			else if (a==1)
			{
				if (r.leftChild()==null)// check condition if the left child of the root node is null
				{ 
					BNode b=new BNode(data,null,null,r); // creation of a temporary node
					r.setLeftChild(b);
				}
				else 
					put(r.leftChild(),data); // recursive call to traverse the tree repeatedly
			}
			else 
				if (r.rightChild()==null) // check condition if the right child of the root node is null
			{
				BNode b=new BNode(data,null,null,r); // creation of a temporary node
				r.setRightChild(b);
			}
			else 
				put(r.rightChild(),data); // recursive call to traverse the tree repeatedly
		}
	}

	/**
	 * Removes the data item with the given key, if the key is stored in the tree
	 */
	public void remove(BNode r, Location key) throws InexistentKeyException
	{
		BNode current=getNode(r,key); // creation of a temporary node
		if (get(r,key)==null) 
			throw new InexistentKeyException("Error! key is not present here.");
		else if (current.parent()==null&&current.isLeaf()) // check condition if parent node is null and current node is null
			root=null;
		else
		{
			if (current.isLeaf()) //check condition if current node is a leaf
			{
				if(current.parent().rightChild()==current)
					current.parent().setRightChild(null);
				else current.parent().setLeftChild(null);
			setNode(current,null);
			}
			else if (current.leftChild()==null)  // check condition if the left child of the current node is null
			setNode(current,current.rightChild());
			else if (current.rightChild()==null) // check condition if the right child of the current node is null
			setNode(current,current.leftChild());
			else if(!current.isLeaf()) // check condition if the current node isn't null
			{
				current.setContent(smallestNode(current.rightChild()).getData());
				remove(current.rightChild(),smallestNode(current.rightChild()).getData().getLocus()); // recursive call to traverse the tree repeatedly
			}
		}
	} 
	
	private void setNode(BNode a,BNode b)
	{ 
		if (b==null)
		{
			a.setRightChild(null);
			a.setLeftChild(null);
			a.setParent(null);
			a.setContent(null);
		}
		else
		{
		a.setRightChild(b.rightChild());
		a.setLeftChild(b.leftChild());
		a.setParent(b.parent());
		a.setContent(b.getData());
		}
	}
	
	/**
	 * Returns the Pel object with the smallest key larger than the given one
	 */
	public Pel successor(BNode r, Location key)
	{

		// if the root is a leaf return null
		if(root.isLeaf())
			return null;
		BNode sNode = getNode(root, key); // create a successor node that references an element with the key

		
		if(sNode.rightChild() != null)// if the right child is not null
			return smallestNode(sNode.rightChild()).getData();
		else 
		{
			// create a parent node that references to the parent of successor node
			BNode parent = sNode.parent();
			while(!root.equals(sNode)  && parent.rightChild() != null && parent.rightChild().equals(sNode))
			{
				sNode = sNode.parent(); // set the successor node to reference its parent
				parent = sNode.parent(); // set the parent to reference the new parent of the successor node
			}
			if(root.equals(sNode))// if the root is equal to the successor node
				return null;
			else 
			{
				sNode = sNode.parent(); // set the successor node to reference its parent
				return sNode.getData(); // return the element of the successor node
			}
		}
	}

	/**
	 * Returns the Pel object with the largest key smaller than the given one
	 */
	public Pel predecessor(BNode r, Location key)
	{
		BNode pNode = getNode(root, key); // create a predecessor node that equals the node with the key

		if(pNode.leftChild() != null) // if the left child of the predecessor node is null
					return largestNode(pNode.leftChild()).getData();
		else 
			{
				if(root.equals(pNode))// if the root equals the predecessor node
					return null;
				BNode parent = pNode.parent(); // create a parent that references the parent node of the predecessor node
				while(!root.equals(pNode)  && parent.leftChild() != null && parent.leftChild().equals(pNode))
				{
					pNode = pNode.parent(); // set the predecessor node to reference its parent
					parent = pNode.parent(); // set the parent node to reference the new parent of the predecessor node
				}
				if(root.equals(pNode)) // if the root equals the predecessor node
						return null;
				else 
				{
					pNode = pNode.parent(); // set the predecessor node to its parent
					return pNode.getData(); // return the element of the predecessor node
				}
			}
		}


	/**
	 * Returns the Pel object in the tree with the smallest key.
	 */
	public Pel smallest(BNode r) throws EmptyTreeException
	{
		if(r.isLeaf()&&r.getData()==null) // check condition if root is a leaf and is null
			throw new EmptyTreeException("empty"); // throws exception if the tree is empty
		else
		{
			while (!r.isLeaf()) // loop till the node is not a leaf
			{
				if (r.leftChild()!=null) // check condition if the left child isn't null
					r=r.leftChild();
				else break;
			}
			return r.getData();
		}
	}

	/**
	 * Returns the Pel object in the tree with the largest key.
	 */
	public Pel largest(BNode r) throws EmptyTreeException
	{
		if(r.isLeaf()&&r.getData()==null) // check condition if root is a leaf and is null
			throw new EmptyTreeException("empty"); // throws exception if the tree is empty
		else
		{
			while (!r.isLeaf()) // loop till the node is not a leaf
			{
				if (r.rightChild()!=null) // check condition if the right child isn't null
					r=r.rightChild();
				else 
					break;
			}
			return r.getData();
		}
	}
	
	/**
	 * Returns the root of the binary search tree.
	 */
	public BNode getRoot()
	{

		return root;
	}


	private BNode smallestNode(BNode r) // private method to find the smallest node in the tree
	{
		
		if(r.isLeaf()&&r.getData()==null) // check condition if root is a leaf and is null
			return r;
		else
		{
			while (!r.isLeaf()) // loop till the node is not a leaf
			{
				if (r.leftChild()!=null) // check condition if the left child isn't null
					r=r.leftChild();
				else 
					break;
			}
			return r;
		}
	}


	private BNode largestNode(BNode r) // private method to find the largest node in the tree
	{
		if (r==null) // check condition if root is null
			return r;
		if(r.isLeaf()&&r.getData()==null) // check condition if root is a leaf and is null
			return r;
		else
		{
			while (!r.isLeaf()) // loop till the node is not a leaf
			{
				if (r.rightChild()!=null) // check condition if the right child isn't null
					r=r.rightChild();
				else break;
			}
			return r;
		}
	}
	
	private BNode getNode(BNode r, Location key)
	{
		int a=r.getData().getLocus().compareTo(key);
		if (r.isLeaf()&&a!=0)
			return r;
		else if (a==0) // check condition if key is found at the root
			return r;
		else if (a==1)
			if (r.leftChild()==null) // check condition if the left child isn't null
				return r;
			else
				return getNode(r.leftChild(),key); // recursive call to traverse the tree repeatedly
		if (r.rightChild()==null) // check condition if the right child is null
			return r;
		else 
			return getNode(r.rightChild(),key); // recursive call to traverse the tree repeatedly
	}
}

