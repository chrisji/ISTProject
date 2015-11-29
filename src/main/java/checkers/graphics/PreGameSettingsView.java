package checkers.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * @author Chris Inskip
 * @version 28/11/2015
 */
public class PreGameSettingsView extends JPanel {

    public PreGameSettingsView() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
//        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 15));
        this.setPreferredSize(new Dimension(400,600));
//        this.setOpaque(false);
    }
}
