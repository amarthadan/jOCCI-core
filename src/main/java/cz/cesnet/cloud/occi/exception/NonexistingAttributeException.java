package cz.cesnet.cloud.occi.exception;

public class NonexistingAttributeException extends Exception{
    
    public NonexistingAttributeException(String message){
        super(message);
    }
    
    public NonexistingAttributeException(String message, Throwable ex){
        super(message, ex);
    }
}
