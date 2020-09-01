/**
* @(#)Word.java
* This class represents each word will be counted in the text file
* @author Mohannad Alnahhas
* @version 1.00 22/02/2020
**/
public class Word {
	private int counter; // The number of times word repeated in the document will be stored here.
	private final String word; // the actual word we are going to have. its final (won't be changed).

	// Constructor initiates the String word and start counting
	// it takes String word as a parameter to store it in the final String.
public Word(String theWord) {
	word= theWord;
	counter=1; // each time an object is made, this means the word has been occured once.
}

// Method incrementCounter() increments the counter each time the word is found again.
// it takes and returns nothing. but it increments int counter
// @author Mohannad Alnahhas
// @version 1.00 22/02/2020
public void incrementCounter() {
	counter++; // counter = counter + 1
}
// Method getCounter() returns int counter when it is called
//@author Mohannad Alnahhas
//@version 1.00 22/02/2020
public int getCounter() {
	return counter;
}
//Method getWord() returns the String word .
//@author Mohannad Alnahhas
//@version 1.00 22/02/2020
public String getWord() {
	return word;
}
}
