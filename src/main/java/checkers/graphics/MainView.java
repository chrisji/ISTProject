package checkers.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * `MainView` is simply and container to hold both the board and the settings.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class MainView extends JPanel {

    /**
     * Creates the `MainView` that will be used to hold the board and the
     * settings panels
     *
     * @param boardView
     * @param settingsPanel
     */
    public MainView(BoardView boardView, SettingsPanel settingsPanel) {
        this.setBackground(new Color(73, 73, 73));
        this.setLayout(new BorderLayout());

        // Setup main title
        JLabel titleLabel = new JLabel("IST Checkers");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);

        JLabel leftSpacer = new JLabel("        ");
        leftSpacer.setFont(new Font("Verdana", Font.BOLD, 16));

        JLabel rightSpacer = new JLabel(" ");
        rightSpacer.setFont(new Font("Verdana", Font.BOLD, 20));

        // Add components to correct positions.
        this.add(leftSpacer, BorderLayout.WEST);
        this.add(rightSpacer, BorderLayout.SOUTH);
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(boardView, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.EAST);
    }
}
