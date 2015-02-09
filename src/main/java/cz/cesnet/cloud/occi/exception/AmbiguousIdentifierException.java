package cz.cesnet.cloud.occi.exception;

/**
 *
 * @author Michal Kimle <kimle.michal@gmail.com>
 */
public class AmbiguousIdentifierException extends Exception {

    public AmbiguousIdentifierException(String message) {
        super(message);
    }

    public AmbiguousIdentifierException(String message, Throwable ex) {
        super(message, ex);
    }
}
