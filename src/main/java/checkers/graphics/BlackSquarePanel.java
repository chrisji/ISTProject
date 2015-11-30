package checkers.graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Chris Inskip
 * @version 28/11/2015
 */
public class BlackSquarePanel extends SquarePanel {

    // Checker image
    private BufferedImage image;

    // Colours
    private final Color BACKGROUND_COLOUR = new Color(30, 30, 30);
    private final Color SELECTED_BACKGROUND_COLOUR = new Color(50, 50, 50);
    private final Color SELECTED_BORDER_COLOUR = new Color(60, 100, 60);

    private final Color HINT_BACKGROUND_COLOUR = new Color(50, 50, 50);
    private final Color HINT_BORDER_COLOUR = new Color(42, 89, 158);

    /**
     * Constructor for empty black squares.
     */
    public BlackSquarePanel() {
        this.setBackground(BACKGROUND_COLOUR);
    }

    /**
     * Constructor for non-empty black squares.
     *
     * @param isRed `true` if the piece is red, `false` if black.
     * @param isCrowned `true` if the piece is crowned, `false` if uncrowned.
     */
    public BlackSquarePanel(boolean isRed, boolean isCrowned) {
        System.out.println("Adding piece to black square (RED=" + isRed + ", CROWNED=" + isCrowned + ")");
        this.setBackground(BACKGROUND_COLOUR);

        try {
            if (isRed && isCrowned) { // Crowned red
                image = ImageIO.read(new File("res/red-checker-crowned.png"));
            } else if (isRed) { // Normal red
                image = ImageIO.read(new File("res/red-checker.png"));
            } else if (isCrowned) { // Crowned black
                image = ImageIO.read(new File("res/black-checker-crowned.png"));
            } else { // Normal Black
                image = ImageIO.read(new File("res/black-checker.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the appearance of the square to show that it is currently selected.
     */
    public void select() {
        this.setBackground(SELECTED_BACKGROUND_COLOUR);
        this.setBorder(BorderFactory.createLineBorder(SELECTED_BORDER_COLOUR, 3));
    }

    /**
     * Reverts the square back to its default appearance, i.e. before it was selected.
     */
    public void deselect() {
        this.setBackground(BACKGROUND_COLOUR);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public void hintSelect() {
        this.setBackground(HINT_BACKGROUND_COLOUR);
        this.setBorder(BorderFactory.createLineBorder(HINT_BORDER_COLOUR, 3));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int imageBorder = 5; // Gap between image and size of square.
        int imageWidth = this.getWidth() - (2*imageBorder);
        int imageHeight = this.getHeight() - (2*imageBorder);

        g.drawImage(image, imageBorder, imageBorder, imageWidth, imageHeight, null);
    }
}
