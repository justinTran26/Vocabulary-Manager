import java.util.ArrayList;

/**
 * A doubly linked list that manages vocabulary topics. Each node in the list contains a {@link Vocab} object
 * that represents a vocabulary topic and its associated words. This class supports adding new vocabulary topics,
 * deleting them, and performing searches and other operations related to vocabulary management.
 */

class VocabList {
	
	/**
     * A private inner class representing a doubly linked list node. Each node holds a {@link Vocab} object,
     * along with references to the next and previous nodes in the list.
     */
	private class DNode{
		private Vocab vocab;
		private DNode next;
		private DNode prev;
		
		/**
         * Constructs an empty node with null references for its vocabulary, next node, and previous node.
         */
		public DNode() {
			vocab = null;
			next = null;
			prev = null;
		}
		
		/**
         * Constructs a node with the specified vocabulary, next node, and previous node.
         * 
         * @param vocab The {@link Vocab} object to be contained in this node.
         * @param next The reference to the next node in the list.
         * @param prev The reference to the previous node in the list.
         */
		public DNode(Vocab vocab, DNode next, DNode prev) {
			this.vocab = vocab;
			this.next = next;
			this.prev = prev;
		}
	}
	
    private DNode head;
    private DNode tail;
    /**
     * Constructs an empty {@code VocabList}.
     */
    public VocabList() {
    	head = null;
    	tail = null;
    }

    
    /**
     * Adds a new vocabulary topic to the end of the list.
     * 
     * @param topic The topic of the new vocabulary to be added.
     */    public void add(String topic) {
    	Vocab newVocab = new Vocab(topic);
    	DNode newNode = new DNode(newVocab, null, null);
    	
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }
     
     /**
      * Adds a new vocabulary topic at the specified index in the list.
      * If the index is invalid (out of bounds), a message is printed and the operation is not performed.
      * 
      * @param index The index at which the new vocabulary should be inserted.
      * @param topic The topic of the new vocabulary to be added.
      */
    public void addAtIndex(int index, String topic) {
        // If index is less than 0, or the list is empty and index is not 0, or index is greater than the number of elements in the list, do nothing.
        if (index < 0 || (index != 0 && head == null) || index > size()) {
            System.out.println("Invalid index.");
            return;
        }
        
        Vocab newVocab = new Vocab(topic);
        DNode newNode = new DNode(newVocab, null, null);
        
        if (index == 0) {
            // Insert at the beginning of the list
            if (head == null) {
                // List is empty, set head and tail to newNode
                head = newNode;
                tail = newNode;
            } else {
                // List is not empty, insert newNode before head
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
        } else {
            // Traverse to the node at index - 1
            DNode current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            
            // Insert newNode between current and current.next
            newNode.next = current.next;
            newNode.prev = current;
            if (current.next != null) {
                current.next.prev = newNode;
            } else {
                // If current.next is null, newNode is becoming the new tail
                tail = newNode;
            }
            current.next = newNode;
        }
    }

    
    /**
     * Deletes the vocabulary topic at the specified index in the list.
     * If the index is invalid (out of bounds), a message is printed and the operation is not performed.
     * 
     * @param index The index of the vocabulary topic to be deleted.
     */
    public void deleteAtIndex(int index) {
        // Check if the index is valid
        if (index < 0 || index >= size()) {
            System.out.println("Invalid index.");
            return;
        }

        // If index is 0, delete the head node
        if (index == 0) {
            if (head == tail) {
                // If there's only one node in the list
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            return;
        }

        // Traverse the list to find the node at the specified index
        DNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        // If the node to delete is the tail node
        if (current == tail) {
            tail = tail.prev;
            tail.next = null;
        } else {
            // If the node to delete is in the middle of the list
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
    }

    
    /**
     * Returns the number of vocabulary topics in the list.
     * 
     * @return The size of the list.
     */
    public int size() {
        int count = 0;
        DNode current = head;
        
        // Traverse the list and count the number of nodes
        while (current != null) {
            count++;
            current = current.next;
        }
        
        return count;
    }
    
    /**
     * Retrieves the node at the specified index.
     * 
     * @param index The index of the node to retrieve.
     * @return The node at the specified index, or null if the index is out of bounds.
     */
    public DNode getNode(int index) {
        if (index < 0 || index >= size()) {
            // Invalid index
            return null;
        }
        
        DNode current = head;
        int count = 0;
        
        // Traverse the list until reaching the desired index
        while (current != null && count < index) {
            current = current.next;
            count++;
        }
        
        return current;
    }
    
   
    
    /**
    * Returns the topic of the vocabulary at the specified index as a String.
    * 
    * @param index The index of the vocabulary topic to retrieve.
    * @return The topic of the vocabulary at the specified index, or a message indicating the node was not found.
    */
    public String printNodeAtIndex(int index) {
        DNode node = getNode(index); // Get the node at the specified index

        if (node != null) {
            // Print the topic of the node
            return node.vocab.getTopic();
        } else {
            return ("Node at index " + index + " not found.");
        }
    }
    
    /**
     * Retrieves the {@link Vocab} object at the specified index in the list.
     * 
     * @param index The index of the vocabulary to retrieve.
     * @return The {@link Vocab} object at the specified index, or null if the index is out of bounds.
     */
    public Vocab getVocabAtIndex(int index) {
        DNode node = getNode(index);
        if (node != null) {
            return node.vocab;
        }
        return null;
    }
    
    
    /**
     * Checks whether the list contains a vocabulary topic that matches the specified topic.
     * 
     * @param topic The topic to search for in the list.
     * @return {@code true} if the list contains the specified topic, {@code false} otherwise.
     */
    public boolean containsTopic(String topic) {
        DNode current = head;
        while (current != null) {
            if (current.vocab.getTopic().equalsIgnoreCase(topic)) {
                return true; // Topic found
            }
            current = current.next;
        }
        return false; // Topic not found
    }

}


