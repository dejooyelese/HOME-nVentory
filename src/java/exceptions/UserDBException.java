package exceptions;

public class UserDBException extends Exception {
    
    /**
     * Sends message from superclass Exception when there is an exception
     * @param message 
     */
    public UserDBException (String message){
        super(message);
    }
}
