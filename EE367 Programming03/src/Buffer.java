import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
/**
* @(#)Buffer.java
* The class will is a buffer that will be used in class edit.
* it reads a .txt file, then copies the information to a linked list
* where class edit may manipulate the text later
* @author Mohannad Alnahhas
* @version 1.00 22/03/2020
**/
public class Buffer extends LinkedList {
	LinkedList<String> list = new LinkedList<String>(); // to save the text file contents here
	Scanner file; // to read the text file
	File fileName;
	File outputfile;
	// The constructor Buffer will take two String parameters.
	// inputName is the .txt file we want to read.
	// outputName is the .txt file that will have the results
	// @author Mohannad Alnahhas
	// @version 1.00 22/03/2020
		public Buffer(String inputName,String outputName) {
			fileName= new File(inputName);
			outputfile=(outputName!=null)? new File(outputName):null;
			toList(fileName);
		}
		
	// The constructor Buffer will take one String parameter.
	// inputName is the .txt file we want to read.
	// @author Mohannad Alnahhas
	// @version 1.00 22/03/2020
		public Buffer(String inputName) {
			this(inputName,null);
		}
	// The constructor Buffer will not take any parameter.
	// this is used to create an empty buffer.
	// @author Mohannad Alnahhas
	// @version 1.00 22/03/2020
		public Buffer() {
			fileName=null;
			outputfile=null;
			System.out.println("An empty buffer is created");
		}
		
		// method printList()
		// takes nothing, returns void.
		// it prints the content in the buffer,
		// if the buffer is empty, it tells the user.
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public void printList() {
			int lineCount=1;
			if(!list.isEmpty()) {
				for(String i: list) {
					System.out.println(lineCount++ +"> " + i);
				}
			}
			else {System.out.println("The buffer is empty");}
		}
		
		// method toList(File)
		// it takes File as an input, read the file, then copies the content to the buffer (linked list)
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		private void toList(File Directory) {
				try {
					file= new Scanner(Directory);
					while(file.hasNext()) {
						String line = file.nextLine();
						list.addLast(line);
					}
					System.out.println("File has been read and saved in buffer");
				} catch (FileNotFoundException e) {
					System.out.println("Cannot find a file with name "+ Directory.getName() + " an empty buffer has been created");
				}
				finally {
					if(fileName.exists()) {file.close();}
				}
				
		}
		
		// method push(String)
		// it adds a new String in the linked list
		// ( it is added in the last of the list)
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public void push(String newLine) {
			list.push(newLine);
		}
		
		// method get(int)
		// takes int index
		// and returns the string content in an index in the list
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public String get(int index) {
			return list.get(index);
		}
		
		// method remove(int)
		// it takes an index
		// removes it from the list then return it back
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public String remove(int index) {
			return list.remove(index);
		}
		
		// method size()
		// it returns the int size of the list
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public int size() {
			return list.size();
		}
		
		// method isEmpty()
		// it return boolean, whether the list is empty or not.
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public boolean isEmpty() {
			return list.isEmpty();
		}
		
		// method addLast(String)
		// it adds a string entry to the list (similar to push)
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public void addLast(String entry) {
			list.addLast(entry);
		}
		
		// method set(int, String)
		// it overwrites list[index] with String line
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public void set(int index, String line) {
			list.set(index, line);
		}
		
		// method add(int,String)
		// it adds a new line before the index in the list.
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public void add(int index, String line) {
			list.add(index, line);
		}
		
		//method outputExist()
		// it returns boolean whether there is an output file with the same name in the directory or not
		// @author Mohannad Alnahhas
		// @version 1.00 22/03/2020
		public boolean outputExist() {
			return outputfile.exists();
		}

		// method writeBuffer
		// it writes the information in the list to a text file with name outputName
		// @author Mohannad Alnahhas
		// @version 1.00 28/03/2020
		public void writeBuffer() {
			Object[] hole= list.toArray();
			try {
				PrintWriter write = new PrintWriter(outputfile);
				for(Object line:hole) {write.println(line);}
				System.out.println("output file is written successfully.");
				write.close();
			} catch (FileNotFoundException e) {
				System.out.println("Couldn't find output file");
			}
				
			
		}

		
		// an overloaded version of the above method
		// writeBuffer(String)
		// it writes the content in the list to a text file that has that has name name
		// @author Mohannad Alnahhas
		// @version 1.00 28/03/2020
		public void writeBuffer(String name) {
			Object[] hole= list.toArray();
			try {
				PrintWriter write = new PrintWriter(new File(name));
				for(Object line:hole) {write.println(line);}
				System.out.println("output file is written successfully.");
				write.close();
			} catch (FileNotFoundException e) {
			}
			
		}


}
