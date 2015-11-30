package checkers.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * @author Chris Inskip
 * @version 26/11/2015
 */
public class MainView extends JPanel {

    // TODO take in settings container
    public MainView(BoardView boardView, SettingsPanel settingsPanel) {
        this.setBackground(new Color(70, 50, 22));
        this.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("IST - Checkers");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titlePanel.add(titleLabel);

        JLabel leftSpacer = new JLabel("        ");
        leftSpacer.setFont(new Font("Verdana", Font.BOLD, 16));

        JLabel rightSpacer = new JLabel(" ");
        rightSpacer.setFont(new Font("Verdana", Font.BOLD, 20));

        this.add(leftSpacer, BorderLayout.WEST);
        this.add(rightSpacer, BorderLayout.SOUTH);
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(boardView, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.EAST);
    }
}
