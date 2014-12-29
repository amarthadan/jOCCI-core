package cz.cesnet.cloud.occi.exception;

public class AmbiguousIdentifierException extends Exception {

    public AmbiguousIdentifierException(String message) {
        super(message);
    }

    public AmbiguousIdentifierException(String message, Throwable ex) {
        super(message, ex);
    }
}
