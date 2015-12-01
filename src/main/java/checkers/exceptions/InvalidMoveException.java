package checkers.exceptions;

/**
 * TODO
 *
 * @author 144158
 * @version 02/12/2015
 */
public class InvalidMoveException extends Exception {

    /**
     * TODO
     */
    public InvalidMoveException() {
        super("Invalid move!");
    }

    /**
     * TODO
     * @param message
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
