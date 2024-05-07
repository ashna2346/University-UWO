/*
This class implements all the auxiliary methods needed by the algorithm that plays the game.
Author: Ashna Mittal
Student ID: 251206758
*/

public class Evaluate
{
	private char[][]gameBoard; // instance variables
	private int toWinTiles; // instance variables
	
	public Evaluate (int size, int tilesToWin, int maxLevels) // constructor
	{
		gameBoard = new char[size][size];
		for (int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				gameBoard[i][j] = 'e'; // Indicates that every position of the board is empty
			}
		}
		toWinTiles = tilesToWin; // number of adjacent tiles needed to win the game
	}
	
	public Dictionary createDictionary() // This method returns an empty Dictionary of the size that is selected
	{
		Dictionary dictionary = new Dictionary (6269);
		return dictionary;
	}
	
	public Record repeatedState(Dictionary dict)// This method represents the content of the 2-D array gameBoard as a string
	{
		String stringGboard = getGBString();
		if(dict.get(stringGboard) != null) // checks whether there is a record in dict with this string as key attribute
			return dict.get(stringGboard);
		else
			return null;	
	}
	
	public void insertState(Dictionary dict, int score, int level) // This method represents the content of gameBoard as a string
	{
		String stringGboard = getGBString();
		try
		{
			dict.put(new Record(stringGboard, score, level)); // creates an object of the class Record storing string, score, and level
		}
		catch(DuplicatedKeyException e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public void storePlay(int row, int col, char symbol) // This method stores symbol in gameBoard[row][col]
	{
		gameBoard[row][col] = symbol;
	}
	
	public boolean squareIsEmpty(int row, int col) // This method returns true if the gameBoard[row][col] is 'e'
	{
		try
		{
		return gameBoard[row][col] == 'e';
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean tileOfComputer(int row, int col) //This method returns true if the gameBoard[row][col] is 'c'
	{
		try
		{
			return gameBoard[row][col] == 'c';
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean tileOfHuman(int row, int col) // This method returns true if the gameBoard[row][col] is 'h'
	{
		try
		{
			return gameBoard[row][col] == 'h';
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean wins (char symbol) // This method Returns true if there are the required number of adjacent tiles of type symbol in the same row, column, or diagonal of gameBoard
	{
		int ct = 0; // counter that Records the number of adjacent tiles in a row
		
		// nested loop that Checks each Row for a win
		for (int i = 0; i < gameBoard.length; i++)
		{
			ct = 0; // Reset the counter for the row
			for (int j = 0; j < gameBoard.length; j++)
			{
					if (gameBoard[i][j] == symbol)
						ct = ct + 1; // Incrementing counter
					else
						ct = 0; // Reset counter
					if (ct == getTile())  // Checks if counter value is enough to win
						return true;
			}
		}
		
		// nested loop that Checks each Column for a win
		for (int i = 0; i < gameBoard.length; i++)
		{
			ct = 0;// Reset the counter for the column
			for (int j = 0; j < gameBoard.length; j++)
			{
				if (gameBoard[j][i] == symbol)
					ct = ct + 1; // incrementing counter
				else
					ct = 0; // Reset counter
				if (ct == getTile()) // Checks if counter value is enough to win
					return true;
			}
		}
		
		// nested loop that Checks the Left Diagonal (top half - Along rows - Descending)
		for (int i = 0; i < gameBoard.length; i++)
		{
			ct = 0; // Reset the counter for diagonal
			if((gameBoard.length - i) >= toWinTiles)
			{
				for (int j = 0; j < gameBoard.length; j++)
				{
					if ( (i + j) < gameBoard.length)
					{ // Keep index in array bounds
						if (gameBoard[j][j + i] == symbol)
							ct = ct + 1; // incrementing counter
						else
							ct = 0; // Reset counter
						if (ct == getTile())  // Checks if counter value is enough to win
							return true;
					}
					else 
						ct = 0; // Reset counter
				}
			}
		}

		// nested loop that Checks Left Diagonal (bottom half - Along Columns - Descending)
		for (int i = 0; i < gameBoard.length; i++)
		{
			ct = 0; // Reset counter for diagonal
			if((gameBoard.length - i) >= toWinTiles)
			{
				for (int j = 0; j < gameBoard.length; j++)
				{
					if ( (i + j) < gameBoard.length)
					{ // Keep index in array bounds
						if (gameBoard[j + i][j] == symbol)
							ct = ct + 1; // incrementing
						else 
							ct = 0; // Reset counter
						if (ct == getTile())  // Checks if counter value is enough to win
							return true;
					}
					else 
						ct = 0; // Reset counter
		
				}
			}
		}
		
		// nested loop that Checks Left Diagonal (top half - Along Rows - Ascending)
		for (int i = 0; i < gameBoard.length; i++)
		{
			ct = 0; // Reset counter
			if((gameBoard.length - i) >= getTile())
			{
				for (int j = gameBoard.length - 1; j >= 0; j--)
				{
					if ( (j - i) >= 0)
					{ // Keep index in array bounds
						if (gameBoard[gameBoard.length-j-1][j - i] == symbol) 
							ct = ct + 1; // Add one onto count
						else 
							ct = 0; // Reset counter
						if (ct == getTile())  // Check if count is enough to win
							return true;
					}

				}
			}
		}
		
		// nested loop that Checks Left Diagonal (top half - Along Columns - Ascending)
		for (int i = 0; i < gameBoard.length; i++)
		{
			ct = 0; // Reset counter
			if((gameBoard.length - i) >= getTile())
			{
				for (int j = gameBoard.length - 1; j >= 0; j--)
				{
					if ( (j - i) >= 0 && gameBoard.length-j < gameBoard.length)
					{ // Keep index in array bounds
						if (gameBoard[j-i][gameBoard.length-j] == symbol)
							ct = ct + 1; // incrementing counter
						else 
							ct = 0; // Reset counter
						if (ct == getTile()) // Checks if counter value is enough to win
							return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isDraw() // This method Returns true if there are no empty positions left in gameBoard
	{
	    for (int i=0; i<gameBoard.length; i++)
	    {
	        for (int j=0; j<gameBoard.length; j++)
	        {
	            if (gameBoard[i][j] == 'e') // check condition for empty positions
	                    return false;
	        }
	    }
	    return true;
	}

	
	public int evalBoard() // This method returns various values for different conditions
	{
		if (wins('c')) // returns 3, if the computer has won
			return 3;
		else if (wins('h')) // returns 0, if the human player has won
			return 0;
		else if (isDraw()) // returns 2, if the game is a draw
			return 2;
		else // returns 1, if the game is still undecided
			return 1;
	}
	

	private String getGBString()
	{
		String board = "";
		// nested loop that Iterates through the 2-D game board array
		for (int i = 0; i < gameBoard.length; i++)
		{
			for (int j = 0; j < gameBoard.length; j++)
			{
				board = board + gameBoard[i][j]; // Add onto board string
			}
		}
		return board;
	}
	
	private int getTile()
	{
		return toWinTiles;
	}
	
}
