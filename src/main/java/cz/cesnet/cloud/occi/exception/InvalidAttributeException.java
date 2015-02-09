package cz.cesnet.cloud.occi.exception;

/**
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class InvalidAttributeException extends Exception {

    public InvalidAttributeException(String message) {
        super(message);
    }

    public InvalidAttributeException(String message, Throwable ex) {
        super(message, ex);
    }
}
