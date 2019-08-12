package exceptions;

public class ItemDBException extends Exception {
    
    /**
     * Sends message from superclass Exception when there is an exception
     * @param message 
     */
    public ItemDBException (String message) {
        super(message);
    }
}
