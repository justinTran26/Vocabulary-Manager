import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileInputStream;

/**
 * The {@code Driver} class serves as the control center for managing vocabulary topics and words.
 * Users can perform various operations such as browsing topics, inserting, removing, and modifying topics,
 * searching for words, loading from and saving to files, and displaying words starting with a given letter.
 * <p>
 * The class provides a menu-driven interface for interacting with the {@code VocabList} and {@code WordList} classes.
 * It also includes methods for handling user input, file I/O operations, and topic and word manipulation.
 * </p>
 * <p>
 * Authors: Nicole Koran (40281430) and Justin Tran (40281429)
 * COMP249 Assignment #3
 * Date Due: Apr 15, 2024
 * </p>
 */
public class Driver {
    private static Scanner scanner = new Scanner(System.in);
    private static VocabList vocabList = new VocabList();

    /**
     * Main method to run the Vocabulary Control Center program.
     * It provides a menu-driven interface for users to perform various operations.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
    	
        boolean running = true;
        while (running) {
            System.out.println("------------------------------------------------------");
            System.out.println("  	     Vocabulary Control Center");
            System.out.println("------------------------------------------------------");
            System.out.println(" 1  browse a topic");
            System.out.println(" 2  insert a new topic before another one");
            System.out.println(" 3  insert a new topic after another one");
            System.out.println(" 4  remove a topic");
            System.out.println(" 5  modify a topic");
            System.out.println(" 6  search topics for a word");
            System.out.println(" 7  load from a file");
            System.out.println(" 8  show all words starting with a given letter");
            System.out.println(" 9  save to file");
            System.out.println(" 0  Exit");
            System.out.println("-----------------------------------------------------");
            System.out.print("Enter Your Choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    browseATopic(); 
                    break;
                case 2:
                    insertTopicBefore(); // MAKE SURE THE TOPIC ISNT ALREADY THERE
                    break;
                case 3:
                    insertTopicAfter(); // MAKE SURE THE TOPIC ISNT ALREADY THERE
                    break;
                case 4:
                    removeTopic();
                    break;
                case 5:
                    modifyTopic(); // MAKE SURE NEW TOPIC ISNT THERE, MAKE SURE ADDED WORDS ARENT THERE ALREADY 
                    break;
                case 6:
                    searchTopics(); 
                    break;
                case 7:
                    loadFromFile();
                    break;
                case 8:
                    showWordsStartingWith();
                    break;
                case 9:
                    saveToFile();
                    break;
                case 0:
                	System.out.println("Bye. Thank you for using our program!");
                    running = false;
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 0 and 9.");
                    
            }
        }
        scanner.close();
    }




	private static void printPickATopic() {
    	
    	System.out.println("-------------------------------");
        System.out.println("      Pick a Topic");
        System.out.println("-------------------------------");
        
        for (int i = 0; i < vocabList.size(); i++) {
       	 System.out.println(" " + (i + 1) + "  " + vocabList.printNodeAtIndex(i));
        }
      
		System.out.println(" 0  Exit");
        System.out.println("-------------------------------");
        System.out.print("Enter Your Choice: ");
    	
    }
    
	
	/**
     * Displays the main menu options for the user to choose from.
     */
    private static void browseATopic() {
    	
    	if (vocabList.size() == 0 ) {
    		System.out.println("There are currently no topic yet.");
    		return;
    	}
    	
         boolean showAgain = true;
         
    	 while (showAgain) {
			printPickATopic();
			int choice = scanner.nextInt();
			if (choice == 0) {
				showAgain = false;
				return;
			}
			
			Vocab vocab = vocabList.getVocabAtIndex((choice - 1));
			String topic = vocab.getTopic();
			System.out.println("Topic: " + topic);
			// Check if the Vocab object exists
			if (vocab != null) {
				// Retrieve the WordList associated with the Vocab object
				WordList wordList = vocab.getWords();
				wordList.printAllWords();
			} 
		}    	 
   	}

    /**
     * Inserts a new topic before an existing one chosen by the user.
     */
	private static void insertTopicBefore() {
    	
   	 	printPickATopic();
   	 	
   	 	// if no topics exist yet
   	 	if (vocabList.size() == 0) {
   	 		System.out.println("");
   	 		System.out.println("Enter any number to add the first topic: ");
   	   	 	int num = scanner.nextInt();
   	        scanner.nextLine(); // Consume the newline character
   	        
   	        if(num == 0)
   	        	return;

   	   	 	System.out.println("Please enter the first topic you would like to add: ");
   	   	 	String newTopic = scanner.nextLine().trim();

   	   	 	vocabList.add(newTopic);
   	   	    addWordsToVocab(0);
   	 		
   	 		return;
   	 	}
   	 	
   	 	// minus 1 on index because we enter topic before the chosen index
   	    int index = (scanner.nextInt()-1);
	    scanner.nextLine(); // Consume the newline character

   	 	if ((index+1) == 0)
   	 		return;

   	 	System.out.println("Please enter the topic you would like to add: ");
   	 	String newTopic = scanner.next().trim();
   	    scanner.nextLine();
   	    
   	    // do not add topic if it already exists
   	    if (vocabList.containsTopic(newTopic)) {
   	    	System.out.println("The topic "+newTopic + " alredy exists.");
   	    	return;
   	    }
   	 	
   	 	if (vocabList.containsTopic(newTopic)) {
   	 		System.out.println("Sorry, this topic already exists.");
   	 		return;
   	 	}
   	 
   	 	vocabList.addAtIndex(index, newTopic);
   	 	
   	 	addWordsToVocab(index);

	}
	
	
	/**
	 * Adds words to the WordList associated with the Vocab object at the specified index.
	 * <p>
	 * This method prompts the user to enter words to be added to the WordList of a specific topic,
	 * identified by its index in the VocabList. The user can enter multiple words separated by spaces.
	 * Each word is then added to the WordList of the corresponding topic.
	 * </p>
	 *
	 * @param index the index of the topic in the VocabList
	 */
	public static void addWordsToVocab(int index) {
		
		System.out.println("Enter any words you would like to add to this topic - to quit press enter: ");
        String wordsToAdd = scanner.nextLine();

        // Retrieve the Vocab object at the specified index
        Vocab vocab = vocabList.getVocabAtIndex(index);

        // Check if the Vocab object exists
        if (vocab != null) {
            // Retrieve the WordList associated with the Vocab object
            WordList wordList = vocab.getWords();

            // Split the words entered by the user
            String[] wordsArray = wordsToAdd.split("\\s+");

            // Add each word to the WordList
            for (String word : wordsArray) {
                wordList.add(word);
            }
            
        } else {
            System.out.println("Invalid index. Topic not found.");
        }
		
		
	}


	/**
     * Inserts a new topic after an existing one chosen by the user.
     */
	private static void insertTopicAfter() {
    	
    	printPickATopic();
   	 	
   	 	// if no topics exist yet
   	 	if (vocabList.size() == 0) {
   	 		System.out.println("");
   	 		System.out.println("Enter any number to add the first topic: ");
   	   	 	int num = scanner.nextInt();
   	        scanner.nextLine(); // Consume the newline character

   	   	 	if (num == 0)
   	   	 		return; 
   	   	 	System.out.println("Please enter the first topic you would like to add: ");
   	   	 	String newTopic = scanner.nextLine().trim();

   	   	 	vocabList.add(newTopic);
   	   	    addWordsToVocab(0);
   	 		
   	 		return;
   	 	}
   	 	
   	    int index = scanner.nextInt();
	    scanner.nextLine(); // Consume the newline character

   	 	if (index == 0)
   	 		return;

   	 	System.out.println("Please enter the topic you would like to add: ");
   	 	String newTopic = scanner.next().trim();
   	    scanner.nextLine();
   	 	
   	 	if (vocabList.containsTopic(newTopic)) {
   	 		System.out.println("Sorry, this topic already exists.");
   	 		return;
   	 	}
   	 
   	 	vocabList.addAtIndex(index, newTopic);
   	 	
   	 	addWordsToVocab(index);
        
    }
    
	/**
     * Removes a topic chosen by the user.
     */
	private static void removeTopic() {
		if (vocabList.size() == 0) {
			System.out.println("Sorry, there are currently no topics.");
			return;
		}
		
		printPickATopic();
		int index = scanner.nextInt();
		
		if (index == 0)
			return;
		
		vocabList.deleteAtIndex(index-1);
		System.out.println("Topic deleted.");
		
	}
	
	

	/**
     * Modifies a topic chosen by the user by adding, removing, or changing words.
     */
	private static void modifyTopic() {
		printPickATopic();
		
		int topicIndex = scanner.nextInt();
	    scanner.nextLine(); // Consume the newline left-over

		Vocab currVocab = vocabList.getVocabAtIndex(topicIndex-1);
		WordList words = currVocab.getWords();
		
		if (topicIndex == 0)
			return;
		
		// Display the menu
        System.out.println("-------------------------------");
        System.out.println("    Modify Topics Menu");
        System.out.println("-------------------------------");
        System.out.println(" a add a word");
        System.out.println(" r remove a word");
        System.out.println(" c change a word");
        System.out.println(" 0 Exit");
        System.out.println("-------------------------------");
        
        System.out.println("Enter your choice: ");
        String choice = scanner.nextLine().trim().toLowerCase();
        scanner.nextLine();
        
        switch (choice) {
            case "a":
                addWord(words);
                break;
            case "r":
                removeWord(words);
                break;
            case "c":
                changeWord(words);
                break;
            case "0":
                return; // Exit the program
            default:
                System.out.println("Invalid choice.");
                break;
        }
    
	}
     

	//helper method for modify a topic 
	private static void addWord(WordList wordList) {
        System.out.println("Please enter the word you would like to add (press enter to end input): ");
        String word = scanner.nextLine();
        
        if (wordList.contains(word)) {
			System.out.println("The word '"+word+"' already exists in the list of words under this topic.");
			return;
        }
        
        wordList.add(word);
		System.out.println("The word '"+word+"' was added to the list of words under this topic.");

	}
	
	//helper method for modify a topic 
	private static void removeWord(WordList wordList) {
		System.out.println("Please enter the word you would like to delete (press enter to end input): ");
        String word = scanner.nextLine();
		
		if (!wordList.contains(word)) {
			System.out.println("The word '"+word+"' does not exist in the list of words under this topic.");
			return;
		}
		
		wordList.delete(word);
		System.out.println("The word '"+word+"' was deleted from the list of words under this topic.");

	}
	
	//helper method for modify a topic 
	private static void changeWord(WordList wordList) {
		System.out.println("Please enter the word you would like to change (press enter to end input): ");
        String word = scanner.nextLine();
		
        if (!wordList.contains(word)) {
			System.out.println("The word '"+word+"' does not exist in the list of words under this topic.");
			return;
		}
        
        wordList.delete(word);
        
        
		System.out.println("Enter the word you would like to change it to: ");
		String newWord = scanner.next();

		wordList.add(newWord);
		System.out.println("The word '"+word+"' was changed to "+newWord+".");
	}

	/**
     * Searches all topics for a specific word entered by the user.
     */
	private static void searchTopics() {
    	System.out.println("Please enter the word you are looking for: ");
    	String word = scanner.next();
    	
    	boolean wordFound = false;

        // Iterate through each topic in the VocabList
        for (int i = 0; i < vocabList.size(); i++) {
            Vocab vocab = vocabList.getVocabAtIndex(i);
            WordList words = vocab.getWords();

            // Check if the word is contained in the WordList of the current topic
            if (words.contains(word)) {
                System.out.println("Word '" + word + "' found in topic: " + vocab.getTopic());
                wordFound = true;
            }
        }

        // If the word is not found in any topic
        if (!wordFound) {
            System.out.println("Word '" + word + "' not found in any topic.");
        }

	}
    
	/**
     * Loads topics and words from a file chosen by the user.
     */
    private static void loadFromFile() {
    	
    	scanner.nextLine();
        System.out.println("Please enter the name of the file: ");
        String fileName = scanner.nextLine();

        if (!isReadableFile(fileName)) {
            return;
        }

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new FileInputStream(fileName));

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                
                // Skip blank lines
                if (line.isEmpty()) {
                    continue;
                }

                String currTopic = null;
                if (line.startsWith("#")) {
                	// Extract the topic name
                    currTopic = line.substring(1).trim();
                    // Add the topic to the VocabList
                    vocabList.add(currTopic);
                    continue;
                }
                
                // add words to the topic
                vocabList.getVocabAtIndex(vocabList.size() - 1).getWords().add(line);
            }

            System.out.println("Done loading.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
    }

    
    /**
     * Checks if the specified file is readable.
     * <p>
     * This method takes a file name as input and verifies if the file exists and is readable.
     * If the file does not exist or is not readable, appropriate exceptions are thrown.
     * </p>
     *
     * @param fileName the name of the file to be checked for readability
     * @return {@code true} if the file is readable, {@code false} otherwise
     */
    public static boolean isReadableFile(String fileName) {
    	try {
    		if(fileName == null) {
    			throw new IllegalArgumentException("File name undefined");
    			}
    		File file = new File(fileName);
    		if (!file.exists()) {
    			throw (new FileNotFoundException("No such file : " + fileName));
    		}
    		if (!file.canRead()) {
    			throw (new IOException("File is not readable : " + fileName));
    		}
    		
    		return true;

    	} catch (FileNotFoundException e) {
    		System.out.println("I/O error : File not found : " + fileName);
    		return false;
    	} catch (IOException e) {
    		System.out.println("I/O error: " + e.getMessage());
    		return false;
    	} catch (IllegalArgumentException e) {
    		System.out.println(e.getMessage());
    		return false;
    	} 
    }
    
    
    /**
     * Displays all words starting with a specific letter entered by the user.
     */
	private static void showWordsStartingWith() {
		System.out.println("Enter the letter you would like to find the words that start with: ");
		String readletter =  scanner.next();
		String input = readletter.trim();
		char letter = input.charAt(0);
		
		// create an arrayList
		ArrayList<String> foundWords = new ArrayList<>();
		
		boolean wordsFound = false;

        // Iterate through each topic in the VocabList
        for (int i = 0; i < vocabList.size(); i++) {
            Vocab vocab = vocabList.getVocabAtIndex(i);
            
            WordList words = vocab.getWords();
            
            // iterate through each wordList
            for (int j = 0; j<words.size(); j++) {
            	String currWord = words.getWordAtIndex(j);
                char firstLetter = currWord.charAt(0);
                
                if(Character.toLowerCase(firstLetter) == Character.toLowerCase(letter)) {
                	foundWords.add(currWord);
                	wordsFound = true;
                }
            }  
        }

        // Sort the ArrayList alphabetically
        Collections.sort(foundWords);
        
        //print the arrayList
        System.out.println("The words starting with the letter '"+letter+"' :");
        for (String word : foundWords) {
        	System.out.println(word);
        }

        // If no words were found in any topic 
        if (!wordsFound) {
            System.out.println("Words starting with the letter '" + letter + "' were not found in any topics.");
        }
		
	}



	/**
     * Saves the current topics and words to a file named "A3_output_file.txt".
     */
	private static void saveToFile() {
		PrintWriter pw = null;
		
        System.out.println("Please enter the name of the text file you would like to save to: ");
        String fileName = scanner.next();
        scanner.nextLine();
        
        File file = new File(fileName);
        
        if (file.exists()) {
            System.out.println("This file already exists would you like to overide it? (yes/no): ");
            String choice = scanner.next();
            scanner.nextLine();
            
            if (choice.equalsIgnoreCase("no")) {
            	System.out.println("exiting, please retry with another name.");
            	return;
            }
            
        }
        		
		try {
			pw = new PrintWriter(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		// Iterate through each topic in the VocabList
        for (int i = 0; i < vocabList.size(); i++) {
            Vocab vocab = vocabList.getVocabAtIndex(i);
            
            pw.println("#"+vocab.getTopic());
            
            WordList words = vocab.getWords();
            
            // iterate through each word in the wordList
            for (int j = 0; j < words.size(); j++) {
            	String currWord = words.getWordAtIndex(j);
                
            	pw.println(currWord);
            	
                }
            } 
         pw.close();
         System.out.println("Saved to the file: "+fileName);
        
        }
    
}
