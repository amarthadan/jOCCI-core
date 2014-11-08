package cz.cesnet.cloud.occi.exception;

public class ParsingException extends Exception {

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable ex) {
        super(message, ex);
    }
}
