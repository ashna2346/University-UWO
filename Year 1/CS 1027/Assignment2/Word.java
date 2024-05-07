/**
 * This class represents a word in the game that is comprised of any number of letters
 * @author ashna
 */
public class Word
{
	//Declaration of global private variables
    private LinearNode<Letter> firstLetter;

    /**
     * Initialise the Word object so the Letter objects in array “letters” is stored within its linked structure
     * @param letters
     */
    public Word (Letter[] letters)
    {
        this.firstLetter = new LinearNode<Letter>(letters[0]);
        LinearNode<Letter> curr = this.firstLetter;
        LinearNode<Letter> node;
        for(int i = 1; i < letters.length; i++)
        {
            node = new LinearNode<Letter>(letters[i]);
            curr.setNext(node);
            curr = curr.getNext();
        }
    }

    @Override
    public String toString()
    {
        String st = "Word: "; // creation of a local String variable
        LinearNode<Letter> currentNode = this.firstLetter;
        while(currentNode != null)
        {
            st += currentNode.getElement().toString() + " ";
            currentNode = currentNode.getNext();
        }
        return st;
    }
	
	/*
	 * takes a mystery word as a parameter and updates each of Letters' "label" attribute
	 * contained in "this" Word object with respect to the mystery word
	 * 
	 * @return true if "this" word is identical in content to the mystery word
	 */
public boolean labelWord(Word mystery)
{
        boolean choice = false;
        boolean isEqual = true;
        
        LinearNode<Letter> other = mystery.firstLetter; 
        LinearNode<Letter> currUser = this.firstLetter;
        int m = 0;
        while (currUser != null)
        {
            choice = false; 
            other = mystery.firstLetter;
            int n = 0;
            while (other != null)
            {
                if (currUser.getElement().equals(other.getElement())) // check condition
                {
                    choice = true; // if check condition is true then choice changes to true
                    if (m==n) // check condition 
                    {
                        currUser.getElement().setCorrect();   
                        break; 
                    }
                    else
                        currUser.getElement().setUsed();
                }
                other = other.getNext(); 
                n++;
            }
            if (choice == false) // check condition
            {
                currUser.getElement().setUnused(); 
                isEqual = false;
            }
            currUser = currUser.getNext();
            m++; 
        }
        return isEqual;
    }
}//class closes