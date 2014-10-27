package cz.cesnet.cloud.occi.exception;

public class InvalidAttributeException extends Exception {

    public InvalidAttributeException(String message) {
        super(message);
    }

    public InvalidAttributeException(String message, Throwable ex) {
        super(message, ex);
    }
}
