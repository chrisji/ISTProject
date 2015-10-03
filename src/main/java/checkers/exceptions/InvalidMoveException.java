package checkers.exceptions;

/**
 * @author Chris Inskip
 * @version 03/10/2015
 */
public class InvalidMoveException extends Exception {

    public InvalidMoveException() {
        super();
    }

    public InvalidMoveException(String message) {
        super(message);
    }
}
