/**
* @(#)wordCounter.java
* The class will analyse text file and will find how many times each word has been occured
* it will filter the file from any unwanted characters such as -_+.,&() etc...
* then it will create another text file to write the results
* @author Mohannad Alnahhas
* @version 1.00 22/02/2020
**/
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class wordCounter{
	
	ArrayList<Word> listofwords = new ArrayList<Word>(); // creates an array list of the words that the text file has
	private String outputName; // output file name will be stored here.
	
	// The only constructor wordCounter will take two String parameters.
	// inputName is the .txt file we want to analyse.
	// outputName is the .txt file that will have the results
	// @author Mohannad Alnahhas
	// @version 1.50 22/02/2020
	public wordCounter(String inputName,String outputName) {

		System.out.println("Word counter will read file "+ inputName +" and will write the results in file "+ outputName);
		this.outputName= outputName; // initiates the field outputName with the String outputName from the constructor
		try {Scanner File = new Scanner(new File(inputName)); // Creates an object of Scanner which is an object File which is the .txt file.
		// the try and catch in case if the inputName entered is not found
		System.out.println("Loading Reading file...");
		while(File.hasNext()) { // while loop, whenever the text file has a word to be read (each time hasNext() returns true).
			String Readword = File.next().toLowerCase().replaceAll("[^a-zA-Z0-9]", ""); // creates a String with the word. but filter any character except English alphabet and numbers from 0 to 9
			if(doesExist(Readword)==-1) {
				listofwords.add(new Word(Readword)); 
			} // if doesExist() returns -1 means there is an new word to the list. create an object of that word and add it to the list.
			else { // else means the word is found in the list
			int currentword = doesExist(Readword); // save the word's index in the list
			int prevword = currentword-1; // saves the previous word's index in the list
			int currentcount = listofwords.get(currentword).getCounter(); // save the counter of Readword (how many times it has occured).
			listofwords.get(currentword).incrementCounter(); // increment the counter of the word by 1
			if(prevword>=0 && currentcount>listofwords.get(prevword).getCounter()) // prevword>=0 insures to stay inside array index bounds.
				// the next condition compares the counter of current word vs previous.
				Swap(currentword,prevword); // if both conditions are correct, then rank up current word (to find it easier next time)
			}
		}
		System.out.println("File has been read and closed successfully");
		File.close(); //  close the file after reading it
		System.out.println("Sorting results");
		insertionSort(); // Sort the results in descending order according to the counter of each word.
		System.out.println("Insertion sort is compeleted. Writing output");
		WriteOutput(); // write the sorted listofwords inside .txt file
		
		}catch(FileNotFoundException e){ System.out.println("ERROR: Cannot find file "+ inputName); System.exit(0);} // in case file isn't found, print message then exit program
	}
	
	// Method Swap() takes two int parameters.
	// returns nothing.
	// it swaps between current word of index counter with previous word with index counter2
	// counter is the index of current word in the list
	// counter2 is the index of previous word in the list
	// @author Mohannad Alnahhas
	// @version 1.02 22/02/2020
private void Swap(int counter, int counter2) {
		Word temp = listofwords.get(counter); // temporary place ( we don't wont to lose the data in index counter)
		Word slot = listofwords.get(counter2);
		listofwords.set(counter2, temp); // put the Word temp to index counter2
		listofwords.set(counter, slot); // put the Word in counter2 to index counter
		
	}

// Method doesExist(String s) takes one String parameter.
// returns an integer (index of the word).
// if return is -1, means the word is ot found
// else, it returns the index of the word
// @author Mohannad Alnahhas
// @version 1.00 22/02/2020
public int doesExist(String s) {
		int indicator = -1; //create an integer and assume the word isn't found
		
		for(int i=0;i<listofwords.size();i++ ) // loop all over listofwords
			if(listofwords.get(i).getWord().equals(s)) // compare the content s with the one in the list
				indicator =i; // if condition is true, return the index where the word is found
			return indicator; // return indicator value
}

// Method WriteOutput() to begin writing the data in listofword into a .txt file
// it takes and returns nothing.
// @author Mohannad Alnahhas
// @version 1.0022/02/2020
public void WriteOutput() {
	int totalwords=0; // to count how many words inside the file
		try { // in case there is IOException
			PrintWriter pw = new PrintWriter(new FileWriter(outputName)); // create and object of PrintWriter  with anonymous object FileWriter, that has the name entered by the user
			for(int i=0;i<listofwords.size();i++) { // loop all over listofwords list
				pw.println("Word: "+ listofwords.get(i).getWord()+ " Frequency: "+ listofwords.get(i).getCounter()); // prints the format Word: String word Frequency: int counter
				totalwords+= listofwords.get(i).getCounter(); // increment total words each time a word is written
			}
			pw.println("Total words is "+ totalwords); // after printing in the list, print total words
			pw.close(); // close Printwriter (finished writing
			System.out.println("Done writing .txt file"); // user message
		} catch (IOException e) { // handling exception
			e.printStackTrace();
		}
}

// Method insetionSort() it sorts listofwords according to highest counter (descending order)
// it takes and returns nothing.
//@author Mohannad Alnahhas
//@version 1.0022/02/2020
public void insertionSort() {
	int n = listofwords.size(); // get size;
	int i, j; // create int i j
	Word temp; // temporary storage
	if (n < 2) return; // nothing to sort!!
	for (i = 1; i < n; ++i) { // take next element at front of unsorted part of array
	// and insert it in appropriate location in sorted part of array
	temp = listofwords.get(i);
	for (j = i;(j > 0) && (compare(listofwords.get(j-1), temp) > 0) ; --j)
	listofwords.set(j, listofwords.get(j-1)); // shift element forward
	listofwords.set(j, temp);
	}
	}

// Method compare() is a comparable to compare between two Words according to their  counter
// it takes two parameters current word and previous word.
// returns int either -1. 0, 1
private int compare(Word prev, Word current) {
	if(prev.getCounter()>current.getCounter()) return -1; // if the counter of the previous word is bigger return -1
	else if (prev.getCounter()<current.getCounter()) return 1; // if counter of current word is bigger return 1
	else return 0; // else return 0 , means both counters are equal.
}
public static void main(String[] args){
	try {
	wordCounter test = new wordCounter(args[0],args[1]);
	}
	catch(ArrayIndexOutOfBoundsException e) {
	System.out.println("Please, enter parameters as follows");
	System.out.println("java wordCounter inputName.txt outputName.txt");
		
	}
	
}

}