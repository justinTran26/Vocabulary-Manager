/**
 * Represents a vocabulary with a specific topic and a list of words associated with that topic.
 * The {@code Vocab} class provides constructors to create a vocabulary object with or without an initial list of words.
 * It allows getting and setting the topic of the vocabulary as well as accessing the words in it.
 */
public class Vocab {
    
    /**
     * The topic of the vocabulary.
     */
    private String topic;

    /**
     * The list of words associated with the vocabulary's topic.
     */
    private WordList words;
    
    /**
     * Constructs an empty vocabulary with no topic and an empty list of words.
     * This constructor is useful when the details of the vocabulary are not yet available at the time of instantiation.
     */
    public Vocab() {
    }

    /**
     * Constructs a vocabulary with a specified topic and an empty list of words.
     * 
     * @param topic The topic of the vocabulary.
     */
    public Vocab(String topic) {
        this.topic = topic;
        this.words = new WordList();
    }
    
    /**
     * Constructs a vocabulary with a specified topic and a predefined list of words.
     * 
     * @param topic The topic of the vocabulary.
     * @param words The initial list of words associated with the vocabulary's topic.
     */
    public Vocab(String topic, WordList words) {
        this.topic = topic;
        this.words = words;
    }
    
    /**
     * Returns the topic of the vocabulary.
     * 
     * @return The topic of the vocabulary.
     */
    public String getTopic() {
        return topic;
    }
    
    /**
     * Sets the topic of the vocabulary to the specified string.
     * 
     * @param topic The new topic of the vocabulary.
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    /**
     * Returns the list of words associated with the vocabulary's topic.
     * 
     * @return The list of words in the vocabulary.
     */
    public WordList getWords() {
        return words;
    }

}
