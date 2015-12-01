package checkers.graphics;

import javax.swing.*;
import java.awt.*;
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
        this.setLayout(new GridLayout(10, 1));

        addTitle();
        addBackToMainMenuButton();
        addSpacer();
        addShowHintButton();
        addShowRulesButton();
    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("                Options");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);
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

    private void addSpacer(){
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        this.add(spacerPanel);
    }

    public void reset() {
        System.out.println("InGameSettingsPanel | resetting view back to default parameters...");
    }
}
