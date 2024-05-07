/*
This class implements a dictionary using a hash table in which collisions are resolved using separate chaining.
Author: Ashna Mittal
Student ID: 251206758
 */

public class Dictionary implements DictionaryADT // Since this class must implement all the methods of the DictionaryADT interface
{
	private Node[] table; // instance variable
	private int size; // instance variable
	private int records; // instance variable
	
	public Dictionary(int size) // Initialises a dictionary with an empty hash table of the specified size
	{
		this.size=size;
		table=new Node[size];
	}
	
	private int hashFunction(String key)
	{
		int hashpointer=0;
		for(int i=0;i<key.length();i++)
		{
			hashpointer=(hashpointer*43)%size;
			hashpointer += (int)key.charAt(i);
		}
		return hashpointer % size;
	}
	
	public int put(Record rec) throws DuplicatedKeyException // This Method Inserts the given Record object referenced by rec in the dictionary
	{
		int hashkey = hashFunction(rec.getKey());
		if(get(rec.getKey()) != null) // checks is key is already in the table
			// throws a DuplicatedKeyException if the string stored in the object referenced by rec is already in the dictionary.
			throw new DuplicatedKeyException("key is already in the table");
		if(table[hashkey] == null) 
		{
			table[hashkey] = new Node(rec); // if the key is empty, a new node is created
			return 0;
		}
		else
		{
			Node currentRecord = table[hashkey]; // Takes to the front of the array
			while(currentRecord.getNext() != null) // iterates till the end of the linkedlist
				currentRecord = currentRecord.getNext();
			currentRecord.setNext(new Node(rec));
			this.records = numRecords() + 1;
		}
		return 1;
	}
	

	public void remove(String key) throws InexistentKeyException // This method Removes the Record object containing string key from the dictionary
	{
		int hashkey = hashFunction(key);
		Node currentNode = table[hashkey];
		if (currentNode == null) 
			//throws an InexistentKeyException if the hash table does not store any Record object with the given key value
			throw new InexistentKeyException("key not found in the table");
		while(currentNode != null)
		{
			if(currentNode.getRecord().getKey().equals(key))
			{
				table[hashkey] = currentNode.getNext();
				break;
			}
			else
				currentNode = currentNode.getNext();
		}
			
	}
	
	public Record get(String key) // This method returns the Record object stored in the hash table containing the given key value
	{
		int hashkey = hashFunction(key);
		Node currentNode = table[hashkey];
		while(currentNode != null)
		{
			if(currentNode.getRecord().getKey().equals(key))
				return currentNode.getRecord();
			else
				currentNode = currentNode.getNext();
		}
		return null; // returns null if no Record object stored in the hash table contains the given key value
	}
	
	public int numRecords() // This method Returns the number of Record objects stored in the hash table
	{
		return records;
	}
}
