package cz.cesnet.cloud.occi.exception;

public class NonexistingElementException extends Exception {

    public NonexistingElementException(String message) {
        super(message);
    }

    public NonexistingElementException(String message, Throwable ex) {
        super(message, ex);
    }
}
