import java.io.File;
import java.util.Scanner;
public class edit {
	Scanner input= new Scanner(System.in); // To get input from the user
	String Command="z";
	String name;
	int charPointer=0;
	int lineCounter=1;
	int currentline=0;
	Buffer list;
	
	// The constructor edit will take two String parameters.
	// inputName is the .txt file we want to read.
	// outputName is the file name the user want to create for saving.
	// then invoke method takeFromUser() to start editing the text.
	// @author Mohannad Alnahhas
	// @version 1.00 28/03/2020
	public edit(String inputName, String outputName) {
		while(!isSafe(outputName)) {
			System.out.println("a file with the specified output name is found would you like to overwrite it?");
			System.out.println("Enter Y/N: ");
			Command= input.nextLine().toUpperCase();
			switch(Command) {
			case("Y"):
				break;
			case("N"):
				System.out.println("Enter another output name: ");
			outputName=input.nextLine();
			break;
			default:
				break;
			}
		}
		this.name= outputName;
		 list = new Buffer(inputName,outputName);
		takeFromUser();
	}
	
	// The constructor Buffer will take one String parameter.
	// inputName is the .txt file we want to read.
	// then invoke method takeFromUser() to start editing the text.
	// @author Mohannad Alnahhas
	// @version 1.00 28/03/2020
	public edit(String inputName) {
		list = new Buffer(inputName);
			takeFromUser();
	}
	
	
	// The constructor Buffer will take nothing.
	// creates a new empty buffer
	// then invoke method takeFromUser() to start editing the text.
	// @author Mohannad Alnahhas
	// @version 1.00 28/03/2020
	public edit() {
		 list = new Buffer();
		 takeFromUser();
	}
	
	
	// method takeFromUser()
	// it is the core of the editor.
	// it implements all the supported commands when the user type them
	// The constructor Buffer will take one String parameter.
	// inputName is the .txt file we want to read.
	// then invoke method takeFromUser() to start editing the text.
	// @author Mohannad Alnahhas
	// @version 2.00 22/03/2020, 28/03/2020
	private void takeFromUser() {
		while(!Command.equals("Q")) {
			System.out.print("Enter a command: ");
			Command= input.nextLine().toUpperCase().replaceAll(" ","");
			switch(Command) {
			case("A"):
				System.out.println("you can now add a bunch of lines (Enter A to exit append mode)");
				Command="lock";
				while(!Command.equalsIgnoreCase("A")) {
					System.out.print("Enter the new line: ");
					Command= input.nextLine();
					if(!Command.equalsIgnoreCase("A")) {list.addLast(Command);}
					else {System.out.println("Exiting append mode");}
				}
				break;
			case("D"):
				if(currentline>list.size()) {
					System.out.println("Cannot find a line to delete, current pointer is invalid");
					break;
				}
				else if(list.isEmpty()) {
					System.out.println("The buffer is empty, there is no line to delete "); 
					break;
					}
				else if(currentline==list.size()) {currentline--;}
				System.out.println("Are you sure you want to delete this line? ");
				displayLine(list.get(currentline));
				System.out.print("Enter Y/N: ");
				Command= input.nextLine().toUpperCase();
				switch(Command) {
					case("Y"):
						list.remove(currentline);
					if (currentline+1<list.size()) {currentline++;}
						System.out.println("The line is deleted");
						break;
					case("N"):
						System.out.println("The line won't be deleted");
						break;
					default:
						System.out.println("you have entered invalid input, exiting deleting mode");
						break;
				}
				break;
			case("E"):
				if(list.size()==0 | currentline>list.size()| currentline<0) {
					System.out.println("Cannot enter character editing mode, there is no valid line to read.");
					break;}
				System.out.println("Character editing mode (enter E to exit)");
				Command="lock";
				charPointer=0;
				CommandE(charPointer);
				break;
			case("F"):
				currentline=0;
				if(!list.isEmpty()) {
					displayLine(list.get(currentline));
				}
				else {System.out.println("The list is empty, there is no line to show");}
				break;
			case("L"):
				if(!list.isEmpty()) {currentline=list.size()-1; displayLine(list.get(currentline));}
				else {System.out.println("The buffer is empty, there is no line to show");}
				break;
			case("G"):
				System.out.print("N: ");
				try {
				currentline=Integer.parseInt(input.nextLine())-1;} catch(NumberFormatException e) {System.out.println("Only numbers are accepted");}
				if(currentline>=0 && currentline<(list.size()) ){displayLine(list.get(currentline));}
				else {System.out.println("you have entered an invalid line number");}
				Command="text";
				break;
			case("S"):
				System.out.print("W: ");
			String word = input.nextLine();
			charPointer=-1;
			for(int i=0;i<=list.size()-1;i++) {
				String here = list.get(i);
				if(here.indexOf(word)>=0) {
					charPointer=here.indexOf(word);
					currentline= i;
					displayLine(here);
					printCursor(charPointer);
					CommandE(charPointer); // invoking character editeng mode, because it is more likely to edit a text after searching for it
					break;
				}
			}
			if(charPointer==-1) {System.out.println("Could not find word \""+ word +"\" in the document."); charPointer=0;}
				break;
			case("H"):
				commandsGuide();
				break;
			case("?"):
				commandsGuide();
			break;
			case("I"):
				System.out.print("L: ");
			String newLine= input.nextLine();
			list.add(currentline+1, newLine);
				break;
			case("P"):
				list.printList();
				break;
			case("N"):
				if(list.size()==0) {System.out.println("The buffer is empty, enter command \"A\" to add lines"); break;}
				else if(currentline+1<list.size()) {currentline++;}
			displayLine(list.get(currentline));
				break;
			case("B"):
				if(list.size()==0) {System.out.println("The buffer is empty, enter command \"A\" to add lines"); break;}
				else if(currentline-1>=0) {currentline--;}
			displayLine(list.get(currentline));
				break;
			case("Q"):
				CommandQ();		
				break;
			case("R"):
				System.out.print("F: ");
				Command=input.nextLine();
				if(Command.equals("<escape>")) {break;} // I could not find a way to respond for escape button 
				list= new Buffer(Command);
				break;
			case("W"):
				CommandW();
				break;
			case("X"):
				CommandW();
				CommandQ();
				break;
			case(""):
				if(list.size()==0) {System.out.println("The buffer is empty, enter command \"A\" to add lines"); break;}
				else if(currentline+1<list.size()) {currentline++;}
			displayLine(list.get(currentline));
				break;
			case("COUNT"):
				wordCounterMethod();
			break;
			
				default:
					System.out.println("You have entered invalid operation, try again.");
				break;
			}
		}
	}
	
	
	// method wordCounterMethod()
	// this method creates an object of wrodCounter class to count the occurances of each word in the file
	// @author Mohannad Alnahhas
	// @version 1.00 24/02/2020
	private void wordCounterMethod() {
		System.out.println("You will have to save the file at first (don't forget the extention .txt)");
		CommandW();
		
		StringBuilder inputname= new StringBuilder(name);
		System.out.println(name);
		try {
		inputname= inputname.replace(inputname.length()-5, inputname.length()-1, "");}
		catch(StringIndexOutOfBoundsException e) { System.out.println("Write the file with the extention");}
		wordCounter counter = new wordCounter(name, inputname.toString()+" Counter.txt");
		
	}

	// method displayLine(String)
	// it takes an index in the list, then print it besides the line number
	// @author Mohannad Alnahhas
	// @version 1.00 22/03/2020
	private void displayLine(String line) {
		if(list.size()>0) {
		System.out.println((currentline+1)+"> "+ line);}
	}
	
	// method commandsGuide()
	// this method prints all the commands supported by the editor
	// @author Mohannad Alnahhas
	// @version 1.00 22/03/2020
	private void commandsGuide() {
		System.out.println("\tThe program supports the following operations (modes):\n"+"\tA: Adding mode, allows the user to add lines at the last of the document.\n"
	             +"\tD: Deletes the current line.\n"+"\tE: Character editing mode, allows the user to edit the character that has the pointer \"^\" \n\t   by the following operations: \n"
				 +"\t\tL    Move cursor to left.\n\t\tR    Move cursor to right.\n\t\tAs   Add a string s at the end of the line.\n\t\tIs   Append string s after the cursor."
				 + "\n\t\tCs   Change all the characters after the cursor to string s.\n\t\tD    Delete the character after the cursor.\n\t\tE    Exit character editing mode.\n"
				 +"\tF: Sets the current line as the first in the document, and displays it as well.\n"+"\tL: Sets the current line as the last in the document, and displays it as well.\n"
				 +"\tG: Sets the current line as required from the user.\n"+"\tS: Word search mode, returns line which has the word that the user entered.\n"+"\tH: Prints commands guide list.\n"
				 +"\tI: Adding mode, adds a line before the current line as the user write.\n"+"\tP: Prints all the lines in the buffer.\n"+"\tN: Goes to the next line (can also be executed by pressing space or enter keys).\n"
				 +"\tB: Goes to the previous line.\n"+"\tQ: Closes the program.\n"+"\tR: Write output\n"+"\tW: Write output file withoud quitting.\n"+"\tX: Save and quit editor.\n\tCount:    produces a file counter that has all the words in the text file and their occurances.\n\tNEW FEATURE ADDED: COMMAND \"COUNT\" ! ");

	}
	
	// method printCursor(int)
	// this method prints the cursor "^" under the line
	// it takes the index of the cursor in the line, calculates the shift then display it
	// @author Mohannad Alnahhas
	// @version 1.00 22/03/2020
	private void printCursor(int cursor) {
		if(currentline>8) {cursor+=1;}
		else if(currentline>98) {cursor+=2;}
		else if(currentline>998) {cursor+=3;}

		if(list.get(currentline).length()>=0) {
		for(int i=0;i<(cursor+3);i++) {
			System.out.print(" ");
		}
		System.out.println("^");
		}
	}
	
	// method CommandE(int)
	// this method does all the commands supported by the editor to edit characters
	// since this character editing mode is needed in other case (when searching for a word)
	// I had to make a method of the Command.
	// it supports deleting character, appending string in the last of the line, appending a string after a cursor
	// and jumping right and left using the cursor
	// @author Mohannad Alnahhas
	// @version 1.00 22/03/2020
	private void CommandE(int charPointer) {
		while(!Command.equalsIgnoreCase("E")) {
			System.out.println("Enter a character command: ");
			Command=input.nextLine();
			String line="";
			if(!Command.isEmpty()) {line = Command.replaceFirst(String.valueOf(Command.charAt(0)),"");}
			if(!Command.isEmpty()) {Command=String.valueOf(Command.charAt(0)).toUpperCase();}
			switch(Command) {
			case("L"):
				if(charPointer>0) {
					charPointer-=1;
				}
			displayLine(list.get(currentline));
			printCursor(charPointer);
			System.out.println(charPointer);
				break;
			case("R"):
				if(charPointer<list.get(currentline).length()-1) {
					charPointer+=1;
				}
			displayLine(list.get(currentline));
			printCursor(charPointer);
			System.out.println(charPointer);
				break;
			case("A"):
				list.set(currentline, list.get(currentline)+line);
				displayLine(list.get(currentline));
				break;
			case("I"):
				String currenLine=list.get(currentline);
			if(charPointer>=0 && charPointer<currenLine.length()) {charPointer++;}
				String holeLine=currenLine.substring(0, charPointer)+line+currenLine.substring(charPointer, list.get(currentline).length());
				list.set(currentline, holeLine);
				displayLine(list.get(currentline));
				break;
			case("C"):
				StringBuffer edited = new StringBuffer(list.get(currentline));
			edited.replace(charPointer, edited.length(), line);
			list.set(currentline, edited.toString());
			displayLine(list.get(currentline));
			break;
			case("D"):
				StringBuilder str = new StringBuilder(list.get(currentline));
			System.out.println(str.length());
			if(charPointer+1<str.length()) {
			str.deleteCharAt(charPointer+1);}
			else {System.out.println("Error there is no character to delete.");}
			list.set(currentline, str.toString());
			displayLine(list.get(currentline));
				break;
			case("E"): // I have to put this here, because when command = E i don't want to call the default case of the switch
				break;
				default:
					System.out.println("You have entered invalid input, please try again");
					break;
			}

		}
		System.out.println("Exiting character editing mode");
	}
	
	
	// method is safe(String)
	// we have to check if there is an output file exist or not
	// this method checks whther it is safe to write an output file or not
	// returns boolean true if there is NO output file exists in the directory
	// returns false if there is a file in the directory with the same name
	// @author Mohannad Alnahhas
	// @version 1.00 28/03/2020
	public boolean isSafe(String fileToCheck) {
		File file = new File(fileToCheck);
		return !file.exists();
	}
	
	//method CommandQ()
	// this method implements the Command Q which is asking the user to quit the program
	// @author Mohannad Alnahhas
	// @version 1.00 28/03/2020
	public void CommandQ() {
		System.out.println("Are you sure you want to quit?");
		System.out.print("Enter Y/N: ");
		Command= input.nextLine().toUpperCase();
		switch(Command) {
		case("Y"):
			Command="Q";
		break;
		case("N"):
			Command="Z";
		break;
		default:
		System.out.println("Invalid input");
		break;
		}
	}
	
	
	// method CommandW()
	// this method implements the command W, which is checking for file output name and saving it
	// @author Mohannad Alnahhas
	// @version 1.00 28/03/2020
	public void CommandW() {
		if(list.outputfile!=null && !list.outputExist()) {list.writeBuffer(); }
		else if(list.fileName==null) {System.out.println("Enter name to save the file: ");
		String name = input.nextLine();
		int overwrite=0;
		while(overwrite!=1 && !isSafe(name)) {
			System.out.println("a file with the specified output name is found would you like to overwrite it?");
			System.out.println("Enter Y/N: ");
			Command= input.nextLine().toUpperCase();
			switch(Command) {
			case("Y"):
				overwrite=1;
				break;
			case("N"):
				System.out.println("Enter another output name: ");
			name=input.nextLine();
			break;
			default:
				System.out.println("Invalid input, try again");
				break;
			}
		}
		this.name=name;
		list.writeBuffer(name);}
		else if(list.outputfile==null) {
			System.out.println("Are you sure you want to overwrite the current file? "+ list.fileName.getName());
			System.out.print("Enter Y/N: ");
			Command= input.nextLine().toUpperCase();
			switch(Command) {
			case("Y"):
				list.writeBuffer(list.fileName.getName());
			break;
			case("N"):
				System.out.println("Enter another output file name: ");
			String name= input.nextLine();
			while(!isSafe(name)) {
				System.out.println("a file with the specified output name is found would you like to overwrite it?");
				System.out.println("Enter Y/N: ");
				Command= input.nextLine().toUpperCase();
				switch(Command) {
				case("Y"):
					break;
				case("N"):
					System.out.println("Enter another output name: ");
				name=input.nextLine();
				break;
				default:
					System.out.println("Invalid input, try again");
					break;
				}
			}
			this.name=name;
			list.writeBuffer(name);
			
				break;
			default:
				System.out.println("Invalid input exiting save.");
			}
		}
	}
	
	
	public static void main(String[] args) {
		if(args.length==0) {
			edit editor= new edit();
		}
		else if(args.length==1) {
			edit editor = new edit(args[0]);
		}
		else if(args.length==2) {
			edit editor = new edit(args[0],args[1]);
		}
	}

}
