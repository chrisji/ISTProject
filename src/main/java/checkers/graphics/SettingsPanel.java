package checkers.graphics;

import javax.swing.*;
import java.awt.*;

/**
 * Holds two settings panels (pre-game and in-game), and allows them to be switched
 * in and out.
 *
 * @author 144158
 * @version 02/12/2015
 */
public class SettingsPanel extends JPanel {

    private CardLayout cardLayout; // Allows setting panels to be switched in and out.

    private static final String PRE_GAME_SETTINGS_NAME = "PRE-GAME";
    private static final String IN_GAME_SETTINGS_NAME = "IN-GAME";

    private PreGameSettingsView preGameSettingsView;
    private InGameSettingsPanel inGameSettingsPanel;

    /**
     * @param preGameView
     * @param inGameView
     */
    public SettingsPanel(PreGameSettingsView preGameView, InGameSettingsPanel inGameView) {
        this.setOpaque(false);
        this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        cardLayout = new CardLayout();

        this.setLayout(cardLayout);
        this.add(PRE_GAME_SETTINGS_NAME, preGameView);
        this.add(IN_GAME_SETTINGS_NAME, inGameView);

        this.preGameSettingsView = preGameView;
        this.inGameSettingsPanel = inGameView;
    }

    public void resetInGameSettings() {
        this.inGameSettingsPanel.reset();
    }

    public void showInGameSettings() {
        cardLayout.show(this, IN_GAME_SETTINGS_NAME);
    }

    public void resetPreGameSettings() {
        this.preGameSettingsView.reset();
    }

    public void showPreGameSettings() {
        cardLayout.show(this, PRE_GAME_SETTINGS_NAME);
    }

    public void setMessages(String... messages) {
        inGameSettingsPanel.setMessages(messages);
    }

}
