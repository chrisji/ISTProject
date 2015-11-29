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

    private BufferedImage image;

    // Colours
    private final Color BACKGROUND_COLOUR = new Color(30, 30, 30);
    private final Color SELECTED_BACKGROUND_COLOUR = new Color(50, 50, 50);
    private final Color SELECTED_BORDER_COLOUR = new Color(60, 100, 60);

    public BlackSquarePanel() {
        this.setBackground(BACKGROUND_COLOUR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int gap = 5; // Gap between image and size of square.
        g.drawImage(image, gap, gap, this.getWidth()-2*gap, this.getHeight()-2*gap, null);
    }

    public BlackSquarePanel(boolean isRed, boolean isCrowned) {
        System.out.println("Adding piece black square: RED=" + isRed + " CROWNED=" + isCrowned);
        this.setBackground(BACKGROUND_COLOUR);

        try {
            if (isRed && isCrowned) { // Crowned red
//                this.add(new JLabel("R")); // TODO REMOVE THIS DEBUG LINE
//                this.setBackground(new Color(250, 140, 150)); // TODO REMOVE THIS DEBUG LINE
                image = ImageIO.read(new File("res/red-checker-crowned.png"));
            } else if (isRed) { // Normal red
//                this.add(new JLabel("r")); // TODO REMOVE THIS DEBUG LINE
//                this.setBackground(new Color(250, 140, 150)); // TODO REMOVE THIS DEBUG LINE
                image = ImageIO.read(new File("res/red-checker.png"));
            } else if (isCrowned) { // Crowned black
//                this.add(new JLabel("B")); // TODO REMOVE THIS DEBUG LINE
//                this.setBackground(new Color(130, 130, 130)); // TODO REMOVE THIS DEBUG LINE
                image = ImageIO.read(new File("res/black-checker-crowned.png"));
            } else { // Normal Black
//                this.add(new JLabel("b")); // TODO REMOVE THIS DEBUG LINE
//                this.setBackground(new Color(130, 130, 130)); // TODO REMOVE THIS DEBUG LINE
                image = ImageIO.read(new File("res/black-checker.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void select() {
        this.setBackground(SELECTED_BACKGROUND_COLOUR);
        this.setBorder(BorderFactory.createLineBorder(SELECTED_BORDER_COLOUR, 3));
    }

    public void deselect() {
        this.setBackground(BACKGROUND_COLOUR);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}
