package cz.cesnet.cloud.occi.exception;

public class NonexistingEntityException extends Exception {

    public NonexistingEntityException(String message) {
        super(message);
    }

    public NonexistingEntityException(String message, Throwable ex) {
        super(message, ex);
    }
}
