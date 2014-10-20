package cz.cesnet.cloud.occi.exception;

public class NonexistingActionException extends Exception {

    public NonexistingActionException(String message) {
        super(message);
    }

    public NonexistingActionException(String message, Throwable ex) {
        super(message, ex);
    }
}
