package checkers.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO
 *
 * @author 144158
 * @version 02/12/2015
 */
public class InGameSettingsPanel extends JPanel {

    private Controller controller;
    private JTextArea messageArea;

    /**
     * TODO
     * @param controller
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
     * TODO
     */
    private void addTitle() {
        JLabel titleLabel = new JLabel("                Options");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);
    }

    /**
     * TODO
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
     * TODO
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
     * TODO
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
     * TODO
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
     * TODO
     * @param messages
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
     * TODO
     */
    private void addSpacer() {
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        this.add(spacerPanel);
    }

    /**
     * TODO
     */
    public void reset() {
        messageArea.setText("");
    }
}
