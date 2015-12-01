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

        addShowHintButton();
        addShowRulesButton();
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
        JButton showHintButton = new JButton("Show Help");
        showHintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.showHint();
            }
        });

        this.add(showHintButton);
    }

    private void addShowRulesButton() {
        JButton showRulesButton = new JButton("Show Rules");
        showRulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.showRules();
            }
        });

        this.add(showRulesButton);
    }

    public void reset() {
        System.out.println("InGameSettingsPanel | resetting view back to default parameters...");
    }
}
