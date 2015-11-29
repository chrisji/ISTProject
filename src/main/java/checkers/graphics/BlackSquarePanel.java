package checkers.graphics;

import checkers.model.Cell;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Chris Inskip
 * @version 28/11/2015
 */
public class BlackSquarePanel extends SquarePanel {

    public BlackSquarePanel() {
        this.setBackground(new Color(30, 30, 30));
    }

    public BlackSquarePanel(boolean isRed, boolean isCrowned) {
        System.out.println("Adding piece black square: RED=" + isRed + " CROWNED=" + isCrowned);
        this.setBackground(new Color(30, 30, 30));

        if (isRed && isCrowned) { // Crowned red
            this.add(new JLabel("R"));
            this.setBackground(new Color(250, 140, 150));
        } else if (isRed) { // Normal red
            this.add(new JLabel("r"));
            this.setBackground(new Color(250, 140, 150));
        } else if (isCrowned) { // Crowned black
            this.add(new JLabel("B"));
            this.setBackground(new Color(130, 130, 130));
        } else { // Normal Black
            this.add(new JLabel("b"));
            this.setBackground(new Color(130, 130, 130));
        }
    }

    public void select() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
    }

    public void deselect() {
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}
