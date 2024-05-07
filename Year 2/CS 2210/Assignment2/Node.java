/*
This class Node stores an object of the class Record to construct the Linklists associated to the entries of the hash table
Author: Ashna Mittal
Student ID: 251206758
 */

public class Node
{
	private Record record; // Info that gets stored in the Node
	private Node nextNode; // Connected node
	
	public Node(Record record) // Constructor 
	{
		this.record = record;
		this.nextNode = null;
	}
	
	public Node getNext() // getNext() method
	{
		return nextNode;
	}

	public void setNext(Node next) // setNext() method
	{
		this.nextNode = next;
	}
	
	public Record getRecord() // getRecord() method
	{
		return record;
	}
	
	public void setRecord(Record newRecord) // setRecord() method
	{
		this.record = newRecord;
	}
}