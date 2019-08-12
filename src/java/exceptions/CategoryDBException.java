package exceptions;

public class CategoryDBException extends Exception {
    
    /**
     * Sends message from superclass Exception when there is an exception
     * @param message 
     */
    public CategoryDBException (String message){
        super(message);
    }
}
