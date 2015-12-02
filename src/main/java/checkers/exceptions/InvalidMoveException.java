package checkers.exceptions;

/**
 * Thrown when a move is attempted that is not valid.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class InvalidMoveException extends Exception {

    /**
     * Constructor for generic exception message
     */
    public InvalidMoveException() {
        super("Invalid move!");
    }

    /**
     * Constructor for custom exception messages.
     *
     * @param message exception message string.
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
