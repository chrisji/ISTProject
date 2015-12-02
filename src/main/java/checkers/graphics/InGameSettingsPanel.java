package checkers.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel representing the settings that will be displayed during a match is being
 * played.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class InGameSettingsPanel extends JPanel {

    private Controller controller;
    private JTextArea messageArea;

    /**
     * @param controller game controller
     */
    public InGameSettingsPanel(Controller controller) {
        this.controller = controller;
        this.setOpaque(false);
        this.setLayout(new GridLayout(10, 1));

        addMessagePanel();
        addSpacer();
        addSpacer();
        addSpacer();
        addSpacer();
        addTitle();
        addBackToMainMenuButton();
        addShowHintButton();
        addShowRulesButton();
    }

    /**
     * Adds the title "Options" to the panel.
     */
    private void addTitle() {
        JLabel titleLabel = new JLabel("                Options");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);
    }

    /**
     * Adds the button to go back to the main menu to the panel.
     */
    private void addBackToMainMenuButton() {
        JButton mainMenuButton = new JButton("New Game");
        mainMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.requestMainMenu();
            }
        });

        this.add(mainMenuButton);
    }

    /**
     * Adds the button to show hints to the panel.
     */
    private void addShowHintButton() {
        JButton showHintButton = new JButton("Show Help");
        showHintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.showHint();
            }
        });

        this.add(showHintButton);
    }

    /**
     * Adds the button to show rules to the panel.
     */
    private void addShowRulesButton() {
        JButton showRulesButton = new JButton("Show Rules");
        showRulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.showRules();
            }
        });

        this.add(showRulesButton);
    }

    /**
     * Adds the messages section to the panel.
     */
    private void addMessagePanel() {
        JLabel titleLabel = new JLabel("              Messages");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);

        messageArea = new JTextArea("- N/A");
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        messageArea.setOpaque(false);
        messageArea.setForeground(Color.WHITE);
        this.add(messageArea);
    }

    /**
     * Given a list of message Strings, shows each one as a bullet point
     * in the message panel.
     *
     * @param messages messages, where each message is a single String.
     */
    public void setMessages(String... messages) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String message : messages) {
            stringBuilder.append(" • ");
            stringBuilder.append(message);
            stringBuilder.append("\n");
        }

        messageArea.setText(stringBuilder.toString());
    }

    /**
     * Adds a spacer - for formatting purposes.
     */
    private void addSpacer() {
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        this.add(spacerPanel);
    }

    /**
     * Resets the settings panel back to before any changes were made to it.
     */
    public void reset() {
        messageArea.setText("");
    }
}
