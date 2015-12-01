package checkers.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 144158
 * @version 28/11/2015
 */
public class InGameSettingsPanel extends JPanel {

    private Controller controller;
    private JTextArea messageArea;

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

    private void addTitle() {
        JLabel titleLabel = new JLabel("                Options");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);
    }

    private void addBackToMainMenuButton() {
        JButton mainMenuButton = new JButton("New Game");
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

    public void setMessages(String... messages) {
        StringBuilder stringBuilder = new StringBuilder();

        if (messages.length < 1) {
            stringBuilder.append(" • N/A");
        }

        for (String message : messages) {
            stringBuilder.append(" • ");
            stringBuilder.append(message);
            stringBuilder.append("\n");
        }

        messageArea.setText(stringBuilder.toString());
    }

    private void addSpacer() {
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        this.add(spacerPanel);
    }

    public void reset() {
        messageArea.setText("");
    }
}
