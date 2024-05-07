/**
 * The class Yahtzee is a game which includes 5 dices and their scoring cases
 * @author Ashna Mittal
 *
 */
public class Yahtzee
{
	/**
	 * Declaration of a private integer global array named dice
	 */
    private Dice dice[];

    /**
     * Constructor to roll for random values and initialise dice array 
     */
    public Yahtzee()
    {
        dice = new Dice[5];
        for(int i=0;i<5;i++)
        {
            Dice d = new Dice();
            d.roll();
            dice[i] = d;
        }
    }

    /**
     * Constructor to initialise the dice array with a given parameter
     * @param dice
     */
    public Yahtzee(Dice dice[])
    {
        this.dice = dice;
    }
    
    /**
     * public method to count the dice values and return an integer array
     * @return countval
     */
    public int[] getValueCount()
    {
        int countval[] = new int[6];// initialisation of a local integer array
        for(int i=0;i<5;i++)
        {
            countval[dice[i].getValue()-1]++;
        }
        return countval;
    }

    /**
     * public method to record 13 possible scores and store them in an integer array which is further returned
     * @return count
     */
    public int[] getScoreOptions()
    {
        int count[] = new int[13]; // initialisation of a local integer array
        int valueCount[] = getValueCount(); // calling public method getValueCount()

        count[0] = valueCount[0]*1;
        count[1] = valueCount[1]*2;
        count[2] = valueCount[2]*3;
        count[3] = valueCount[3]*4;
        count[4] = valueCount[4]*5;
        count[5] = valueCount[5]*6;
        //calling private methods
        count[6] = three_Ofa_kind(valueCount)!=-1?sum():0;
        count[7] = four_Ofa_kind(valueCount)!=-1?sum():0;
        count[8] = fullHouse(valueCount)!=-1?25:0;
        count[9] = smallStraight(valueCount)!=-1?30:0;
        count[10] = largeStraight(valueCount)!=-1?40:0;
        count[11] = yahtzee(valueCount)!=-1?50:0;
        count[12] = sum();

        return  count;
    }
    
    /**
     * public method to determine the highest possible score and return the maximum score along with its position number
     * @return scoreval
     */
    public int[] score()
    {
        int scoreval[] = new int[2]; // initialisation of a local integer array
        int scoreOption[] = getScoreOptions(); //calling public method getScoreOptions()
        int maxVal = scoreOption[0]; // initialising the maximum value with the first element
        int maxIndex = 0; // initialising the position of the maximum value as the position of the first element
        for(int i=1;i<13;i++)
        {
            if(scoreOption[i]>maxVal)
            {
                maxVal = scoreOption[i];// assigning the maximum value
                maxIndex = i;// assigning the maximum value position
            }
        }
        scoreval[0] = maxVal; // storing the maximum value in the local array
        scoreval[1] = maxIndex;// storing the maximum value position in the local array
        return scoreval;
    }

    /**
     * public method to Compare the given Yahtzee object from the argument and return a boolean answer
     * @param yahtzee
     * @return true/false
     */
    public boolean equals(Yahtzee yahtzee)
    {
        int originalValueCount[] =this.getValueCount();
        int otherValueCount[] = yahtzee.getValueCount();
        for(int i=0;i<6;i++)
        {
            if(originalValueCount[i] != otherValueCount[i]) // compare condition
                return false;
        }
        return true;
    }

    /**
     * public method to return a string of the dice values formatted 
     * @return str
     */
    public String toString()
    {
        String str = "Dice: {";
        str += dice[0].getValue() + ", " + dice[1].getValue() + ", " + dice[2].getValue() + ", " + dice[3].getValue() + ", " + dice[4].getValue();
        str += "}";
        return  str;
    }

    /**
     * private method to find 3 dice showing the same value
     * @param valueCount
     * @return
     */
    private int three_Ofa_kind(int[] valueCount)
    {
        for(int i=0;i<6;i++)
        {
            if(valueCount[i]>=3) // check condition
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * private method to find 4 dice showing the same value
     * @param valueCount
     * @return
     */
    private int four_Ofa_kind(int[] valueCount)
    {
        for(int i=0;i<6;i++)
        {
            if(valueCount[i]>=4)// check condition
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * private method to find 3 dice showing the same value and the other 2 showing a different same value
     * @param valueCount
     * @return
     */
    private int fullHouse(int[] valueCount)
    {
        int t = three_Ofa_kind(valueCount);
        if(t!=-1)// check condition
        {
            for(int i=0;i<6;i++)
            {
                if(i!=t && valueCount[i]>=2)// check condition
                {
                    return 1;
                }
            }
        }
        return -1;
    }

    /**
     * private method to find 4 dice showing the consecutive values
     * @param valueCount
     * @return
     */
    private int smallStraight(int[] valueCount)
    {
        int k=4;
        for(int i=0;i<6;i++)
        {
            if(valueCount[i]==0)// check condition
            {
                k=4;
                continue;
            }
            k--;
            if(k==0)// check condition
            {
                return 1;
            }
        }
        return -1;
    }

    /**
     * private method to find 5 dice showing the consecutive values
     * @param valueCount
     * @return
     */
    private int largeStraight(int[] valueCount)
    {
        int k=5;
        for(int i=0;i<6;i++)
        {
            if(valueCount[i]==0)// check condition
            {
                k=5;
                continue;
            }
            k--;
            if(k==0)// check condition
            {
                return 1;
            }
        }
        return -1;
    }
    
    /**
     * private method to find 5 dice showing the same value
     * @param valueCount
     * @return
     */
    private int yahtzee(int[] valueCount)
    {
        for(int i=0;i<6;i++)
        {
            if(valueCount[i]==5)// check condition
            {
                return 1;
            }
        }
        return -1;
    }

    /**
     * private method to return the sum of values of 5 dice 
     * @return s
     */
    private int sum()
    {
        int s = 0;// initialising a local integer variable for sum calculation
        for(int i=0;i<5;i++)
        {
            s += dice[i].getValue();// calculation of sum of numbers
        }
        return  s;
    }
    
} // class closes