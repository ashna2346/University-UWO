/*
This class represents the records that will be stored in the hash table
Author: Ashna Mittal
Student ID: 251206758
 */

public class Record
{
	private String key; // instance variable
	private int score; // instance variable
	private int level; // instance variables

	public Record(String key, int score, int level) // constructor of the class
	{
		this.key = key;
		this.score = score;
		this.level = level;
	}
	
	public String getKey() // This method Returns the string stored in this Record object
	{
		return this.key;
	}	
	
	public int getScore() // This method Returns the first integer stored in this Record object
	{
		return this.score;
	}
	
	public int getLevel() // This method Returns the second integer stored in this Record object
	{
		return this.level;
	}
}
