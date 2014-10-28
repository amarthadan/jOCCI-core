package cz.cesnet.cloud.occi.exception;

public class InvalidAttributeValueException extends Exception {

    public InvalidAttributeValueException(String message) {
        super(message);
    }

    public InvalidAttributeValueException(String message, Throwable ex) {
        super(message, ex);
    }
}
