package cz.cesnet.cloud.occi.exception;

/**
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class InvalidAttributeValueException extends Exception {

    public InvalidAttributeValueException(String message) {
        super(message);
    }

    public InvalidAttributeValueException(String message, Throwable ex) {
        super(message, ex);
    }
}
