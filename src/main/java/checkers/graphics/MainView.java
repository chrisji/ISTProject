package checkers.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * @author Chris Inskip
 * @version 26/11/2015
 */
public class MainView extends JPanel {

    // TODO take in settings container
    public MainView(BoardView boardView, PreGameSettingsView preGameSettingsView) {
        this.setBackground(new Color(30, 241, 200));
        this.setLayout(new BorderLayout());
        this.add(boardView, BorderLayout.CENTER);
        this.add(preGameSettingsView, BorderLayout.EAST);
    }
}
