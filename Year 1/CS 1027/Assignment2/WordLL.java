/**
 * 
 * @author Ashna
 * The  class WordLL stores a mystery word and all word guesses tried
 *
 */
public class WordLL
{
	// declaration of private variable mysteryWord
	private Word mysteryWord;
	// declaration a linked list that stores history words
	private LinearNode<Word> history;
	
	/* constructor 
	 * @param initialize mysteryWord to parameter
	 */
	public WordLL (Word mystery)
	{
		history = new LinearNode<Word>();
		mysteryWord = mystery;
	}
	

	/* takes a Word as an argument to test against this games' mystery word
	 * @return true if mysteryWord is identical to the guess word 
	 */
	public boolean tryWord(Word guess)
	{
		boolean check = guess.labelWord(mysteryWord);
		 boolean indicator = false;
		 LinearNode<Word> guessLL = new LinearNode<Word>();
		 guessLL.setElement(guess);
		 
		 LinearNode<Word> holder = history.getNext();
		 history.setNext(guessLL);
		 guessLL.setNext(holder);

		 if(check == true) //check condition
		 {
			 indicator = true;
		 }
	return indicator;
	}
	
	
	/* Creates a String representation of the past guesses with the most 
	 * recent guess first
	 */
	public String toString()
	{
		LinearNode<Word> newNode = history.getNext();
		String word = "Word: ";
		while( newNode != null)
		{
			Word element = newNode.getElement();
			word = word + element.toString() + "\n";
			newNode = newNode.getNext();
			if(newNode == null) // check condition
			{
				break;
			}
			else
			{
			word = word + element.toString() + "\n";
			}
		}

		return word;
		}
	}// class closes
