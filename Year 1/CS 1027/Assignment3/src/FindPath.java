/**
 * This class contains the code needed to compute a path from the entrance of the pyramid to all the treasure chambers
 * @author Ashna Mittal
 */
import java.io.FileNotFoundException;
import java.io.IOException;
public class FindPath
{
	// Reference to the object of the class Map
	private Map pyramidMap;
	
	/*
	 *Constructor that receives as input the name of a file containing a description of the chambers of the pyramid 
	 */
	public FindPath(String fileName)
	{
		try
		{
			Map obj = new Map(fileName); // object creation
			pyramidMap = obj;	
		}
		catch(FileNotFoundException e)
		{
		}
		catch(IOException e1)
		{
		}
		
	}
	/*
	 * public method finds a path from the entrance to all the treasure chambers
	 */
	public DLStack path()
	{
		DLStack<Chamber> pathway = new DLStack<Chamber>();
		Chamber entry = pyramidMap.getEntrance();
		int ctr=0, treasure = pyramidMap.getNumTreasures();
		entry.markPushed();
		pathway.push(entry);
		
		while(!pathway.isEmpty()) // loop to iterate till the stack is not empty
		{
			Chamber t = pathway.peek(); // Peek at the top of the stack to get the current chamber
			Chamber best;
			
			if(t.isTreasure()) // check condition
			{
				ctr++; //Incrementing counter
				if(ctr == treasure) // check condition
					break;
			}
			
			best = bestChamber(t);
			
			if(best == null) // check condition
				pathway.pop().markPopped(); // pop the top chamber from the stack and mark it as popped
			else
			{
				pathway.push(best);
				best.markPushed();
			}
		}
		return pathway;
	}
	
	/*
	 * public method that Returns the value of pyramidMap
	 */
	public Map getMap()
	{
		return pyramidMap;
	}
	
	/*
	 * public method that Returns true if currentChamber is dim
	 */
	public boolean isDim(Chamber currentChamber)
	{
		// check condition
		if(currentChamber != null && currentChamber.isSealed() == false && currentChamber.isLighted()== false)
		{
			for(int i=0;i<=5; i++)
			{
				// check condition
				if(currentChamber.getNeighbour(i) != null && currentChamber.getNeighbour(i).isLighted() == true) 
					return true;
			}
		}
		return false;
	}
	
	/*
	 * public method that Selects the best chamber to move to from currentChamber
	 */
	public Chamber bestChamber(Chamber currentChamber)
	{
		for(int i=0;i<=5; i++)
		{
			if(currentChamber.getNeighbour(i)!=null) // check condition to avoid null pointer exception
				// check condition
				if(currentChamber.getNeighbour(i).isTreasure() == true && currentChamber.getNeighbour(i).isMarked() == false)
					return currentChamber.getNeighbour(i);
		}
			
		for(int i=0;i<=5; i++)
		{
			if(currentChamber.getNeighbour(i)!=null) // check condition to avoid null pointer exception
				// check condition
				if(currentChamber.getNeighbour(i).isLighted() == true && currentChamber.getNeighbour(i).isMarked() == false)
					return currentChamber.getNeighbour(i);
		}
			
		for(int i=0;i<=5; i++)
		{
			if(currentChamber.getNeighbour(i)!=null) // check condition to avoid null pointer exception
				// check condition
				if(isDim(currentChamber.getNeighbour(i)) == true && currentChamber.getNeighbour(i).isMarked() == false)
					return currentChamber.getNeighbour(i);
		}
		return null; // if there is no unmarked treasure, lighted or dim chamber, the method returns null
	}
} // class ends