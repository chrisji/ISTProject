package checkers.graphics;

/**
 * @author Chris Inskip
 * @version 27/11/2015
 */
public interface Screen {

    /**
     * Called when the screen is switched into view.
     */
    void update();

    /**
     * Resets the screen back to its default values.
     */
    void reset();
}