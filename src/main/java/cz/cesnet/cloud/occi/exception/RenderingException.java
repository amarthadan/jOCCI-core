package cz.cesnet.cloud.occi.exception;

public class RenderingException extends Exception {

    public RenderingException(String message) {
        super(message);
    }

    public RenderingException(String message, Throwable ex) {
        super(message, ex);
    }
}
