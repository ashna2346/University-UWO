/**
 * This class is a subclass of Letter and extends the functionality
 * @author Ashna
 *
 */
public class ExtendedLetter extends Letter
{
	// Declaration of Global variables
    private String content;
    private int family;
    private boolean related;
    private final int SINGLETON = -1;

    /**
     * public method to Initialise instance variables
     * @param s
     */
    public ExtendedLetter(String s)
    {
        super('a');
        this.content = s;
        this.related = false;
        this.family = this.SINGLETON;
    }

    /**
     * constructor to Initialise instance variables
     * @param s
     * @param fam
     */
    public ExtendedLetter(String s, int fam)
    {
        super('a');
        this.content = s;
        this.related = false;
        this.family = fam;
    }


    public boolean equals(Object other)
    {
        if (other instanceof ExtendedLetter)
        {
            ExtendedLetter otherExtendedLetter = (ExtendedLetter) other;
            // check condition 
            if(this.family == otherExtendedLetter.family)
            {
                this.related = true;
            }
            // check condition
            if(this.content == otherExtendedLetter.content)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * public method an overridden method that gives a String representation of this ExtendedLetter Object
     */

    public String toString()
    {
    	//check condition
        if(decorator() == "-" && this.related)
        {
            return "." + this.content + ".";
        }
        return decorator() + this.content + decorator();
    }

    public static Letter[] fromStrings(String[] content, int[] codes)
    {
        Letter[] letters = new Letter[content.length];
        if(codes == null)// check condition if i-th entry of array letters will store an ExtendedLetter object
        {
            for(int i = 0; i < content.length; i++)
            {
                letters[i] = new ExtendedLetter(content[i]);
            }
        }
        else
        {
            for(int i = 0; i  < content.length; i++)
            {
                letters[i] = new ExtendedLetter(content[i], codes[i]);
            }
        }
        return letters;
    }

}// class closes