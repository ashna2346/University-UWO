/**
 * The class Dice has been declared which represents a single six faced die to be used in a game of yahtzee
 * @author Ashna Mittal
 *
 */
public class Dice
{
	/**
	 * Declaration of a private integer global variable named value
	 */
    private int value;

    /**
     * Constructor to initialise value with -1
     */
    public Dice()
    {
        this.value=-1;
    }
    
    /**
     * Constructor to initialise value with a given argument 
     * @param value1
     */
    public Dice(int value1)
    {
        this.value=value1;
    }
    
    /**
     * public method to generate a number between 1-6 and assign it to value
     */
    public void roll()
    {
        RandomNumber rn=new RandomNumber();
        this.value = rn.getRandomNumber(1,6); // calling the getRandomNumber() method from the class RandomNumber
    }
    
    /**
     * public method to return integer type value 
     * @return value
     */
    public int getValue()
    {
        return this.value;
    }
}
        
        