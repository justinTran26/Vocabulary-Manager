import java.util.ArrayList;

/**
 * The {@code WordList} class represents a list of words organized in a linked list. 
 * This class provides methods to add, delete, and modify words within the list, as well as 
 * to search for words and print all words starting with a given letter.
 */
class WordList {
	
	/**
     * Represents a node in the linked list containing a single word and a reference to the next node.
     */
	private class WordNode {
		
		String word;
		WordNode next;
		
		/**
         * Default constructor for creating a node with no word and no next node.
         */
		public WordNode(){
			word = null;
			next = null;
		}
		
		/**
         * Constructs a new node with specified word and next node.
         *
         * @param word the word the node will hold
         * @param nextValue the next node in the linked list
         */
		public WordNode(String word, WordNode nextValue){
			this.word = word;
			this.next = nextValue;
		}
	}
	
	// the only attributes in this linked list class
	private WordNode head;
	private WordNode tail;
	
	/**
     * Constructs an empty {@code WordList}.
     */
	public WordList() {
		head = null;
		tail = null;
	}
	
	
	/**
     * Returns the number of words in the list.
     *
     * @return the size of the list as an integer
     */
	public int size() {
		int count = 0;
		WordNode position = head;
		
		while (position != null) {
			count++;
			position = position.next;
		}
		return count;
	}
	
	/**
     * Finds the first node containing the specified word.
     *
     * @param target the word to search for
     * @return the node containing the specified word, or {@code null} if the word is not found
     */
	private WordNode find(String target) {
		WordNode position = head;
		String wordAtPosition;
		
		while( position != null) {
			wordAtPosition = position.word;
			
			if(wordAtPosition.equals(target))
				return position;
			position = position.next;
		}
		return null;
	}
	
	
	/**
     * Determines if the list contains the specified word.
     *
     * @param word the word to search for in the list
     * @return {@code true} if the word is in the list; {@code false} otherwise
     */
	public boolean contains(String word) {
		return (find(word) != null);
	}
	
	
	/**
     * Adds a word to the list in its sorted position.
     *
     * @param wordToAdd the word to be added to the list
     */
	public void add(String wordToAdd) {
		String word = wordToAdd.toLowerCase();
		
		// If the list is empty or the word should be inserted at the beginning
	    if (head == null || head.word.compareTo(word) > 0) {
	        head = new WordNode(word, head);
	        // If the list is empty, set tail to head
	        if (tail == null) {
	            tail = head;
	        }
	        return;
	    }
	    
	    // Traverse the list to find the correct position to insert the word
	    WordNode current = head;
	    WordNode prev = null;
	    while(current != null && current.word.compareTo(word) < 0) {
	    	prev = current;
	    	current = current.next;
	    }
	    
	    // insert the word at the correct position
	    if (current == null) { // insert at the end
	    	tail.next = new WordNode(word, null);
	    	tail = tail.next;
	    } else { // insert in the middle at the right position
	    	prev.next = new WordNode(word, current);
	    }

	}
	
	/**
     * Deletes the first occurrence of the specified word from the list.
     *
     * @param word the word to be deleted from the list
     */
	public void delete(String word) {
		// If the list is empty, do nothing
	    if (head == null)
	        return;

	    // If the word to be deleted is at the head of the list
	    if (head.word.equals(word)) {
	    	head = head.next;
	    	
	    	// if there was only one word in the list and now the head is null, update the tail to null as well
	    	if (head == null)
	    		tail = null;
	    	return;
	    }
	    
	    // Traverse the list to find the node before the one containing the word
	    WordNode prev = head;
	    if (prev.next != null && prev.next.word.equals(word)) {
	    	prev = prev.next;
	    }
		
	    
	    // If the word is found in the list, delete it
	    if (prev.next != null) {
	    	prev.next = prev.next.next;
	    	// if the word was the last node, update the tail;
	    	if (prev.next == null)
	    		tail = prev;
	    } 
	}
	
	
	
	/**
     * Prints all words in the list in specific format.
     */
	public void printAllWords() {
	    WordNode current = head;
	    int count = 0; // Initialize a counter to keep track of the number of words

	    while (current != null) {
	        // Increment count at the beginning to start numbering from 1
	        count++;

	        // Print the word along with its number. Use print() instead of println() to stay on the same line.
	        // The %-20s format specifier is used to left-align the word within a 20 character width field.
	        // This helps in keeping the output aligned for words of different lengths.
	        System.out.printf("%d: %-20s", count, current.word);

	        // After every fourth word, print a newline to start a new line.
	        if (count % 4 == 0) {
	            System.out.println();
	        }

	        // Move to the next node
	        current = current.next;
	    }

	    // If the last line did not end with a newline due to count not being a multiple of 4, add a newline.
	    if (count % 4 != 0) {
	        System.out.println();
	    }
	}
	
	
	
	/**
     * Retrieves the word at the specified index.
     *
     * @param index the index of the word to retrieve
     * @return the word at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
	public String getWordAtIndex(int index) {
	    // Check if the index is valid
	    if (index < 0 || index >= size()) {
	        throw new IndexOutOfBoundsException("Index is out of bounds");
	    }

	    // Traverse the list to find the node at the specified index
	    WordNode current = head;
	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }

	    // Return the word stored in the node at the specified index
	    return current.word;
	}


	
	
	
}
