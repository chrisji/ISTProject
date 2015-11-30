package checkers.graphics;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Chris Inskip
 * @version 28/11/2015
 */
public class InGameSettingsPanel extends JPanel {

    private Controller controller;

    public InGameSettingsPanel(Controller controller) {
        this.controller = controller;
        this.setOpaque(false);

        addBackToMainMenuButton();
    }

    private void addBackToMainMenuButton() {
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.requestMainMenu();
            }
        });

        this.add(mainMenuButton);
    }

    private void addShowHintButton() {
        JButton showHintButton = new JButton("Get Hint");
        showHintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.showHint();
            }
        });

        this.add(showHintButton);
    }

    public void reset() {
        System.out.println("InGameSettingsPanel | resetting view back to default parameters...");
    }
}
