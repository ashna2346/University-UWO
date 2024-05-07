/**
 * 
 * @author ashna
 * This class represents a single letter that will be used in the game
 *
 */
public class Letter
{
	// declaration of global variables
    private char letter;
    private int label;
    private static final int UNSET = 0, UNUSED = 1, USED = 2, CORRECT = 3;
    
    /**
     * constructor
     * @param c
     */
    public Letter(char c)
    {
        label = UNSET;
        letter = c;
    }

    /**
     * this function checks whether otherObject is of the class Letter
     */
    public boolean equals(Object otherObject)
    {
    	// check condition If otherObject is of the class Letter, then the “letter” attributes of otherObject and this object are compared
        if(otherObject instanceof Letter)
        {
            Letter object = (Letter) otherObject;
            if (this.letter == object.letter) // check condition
            {
                return true;
            }
            return false;
        }
        return false;
    }
    
    /**
     * 
     * @return
     */
    public String decorator()
    {
        if(label == UNSET)
            return " ";
        
        if(label == UNUSED)
            return "-";
        
        if(label == USED)
            return "+";
        
        if(label == CORRECT)
            return "!";
        
        return "";
    }
    
    /**
     * an overridden method that gives a representation of letter
     */
    @Override
    public String toString()
    {
        return decorator() + letter + decorator();
    }

    /**
     * setter method
     */
    public void setUnused()
    {
        label = UNUSED;
    }

    /**
     * setter method
     */
    public void setUsed()
    {
        label = USED;
    }

    /**
     *  setter method
     */
    public void setCorrect()
    {
        label = CORRECT;
    }
    
    public boolean isUnused()
    {
        return label == UNUSED;
    }

    /**
     * this function Produces an array of objects of the class Letter
     * @param s
     * @return array
     */
    public static Letter[] fromString(String s)
    {
        Letter[] array = new Letter[s.length()];
        for(int i = 0; i <s.length(); i++)
        {
            array[i] = new Letter(s.charAt(i));
        }
        return array;
    }

}// class closes