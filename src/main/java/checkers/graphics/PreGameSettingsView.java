package checkers.graphics;

import checkers.model.Game;
import checkers.model.PlayerFactory;
import checkers.players.AI;
import checkers.players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 144158
 * @version 02/12/2015
 */
public class PreGameSettingsView extends JPanel {

    private final Controller controller;

    private Player topPlayer;
    private Player bottomPlayer;
    private Player startingPlayer;

    private int topDifficulty = -1;
    private int bottomDifficulty = -1;

    JComboBox<Object> topPlayerComboBox;
    JComboBox<Object> bottomPlayerComboBox;
    JComboBox<Object> startingPlayerComboBox;

    JPanel topDifficultyPanel;
    JComboBox<Object> topDifficultyComboBox;

    JPanel bottomDifficultyPanel;
    JComboBox<Object> bottomDifficultyComboBox;

    Map<String, Integer> possiblePlayers;
    String[] possibleDifficulties;

    public PreGameSettingsView(Controller controller) {
        this.controller = controller;
        this.setLayout(new GridLayout(11, 1));
        this.setPreferredSize(new Dimension(350, 500));
        this.setOpaque(false);

        topPlayer = PlayerFactory.buildPlayer(PlayerFactory.HUMAN, "A");
        bottomPlayer = PlayerFactory.buildPlayer(PlayerFactory.HUMAN, "B");
        startingPlayer = bottomPlayer;

        possiblePlayers = new LinkedHashMap<String, Integer>(); // Keeps ordering
        possiblePlayers.put("Human", PlayerFactory.HUMAN);
        possiblePlayers.put("Alpha-Beta AI", PlayerFactory.ALPHA_BETA_AI);
        possiblePlayers.put("Minimax AI", PlayerFactory.MINIMAX_AI);
        possiblePlayers.put("First Valid Move AI", PlayerFactory.FIRST_MOVE_AI);
        possiblePlayers.put("Random Valid Move AI", PlayerFactory.RANDOM_MOVE_AI);

        possibleDifficulties = new String[]{"Suicidal", "Easy", "Medium", "Hard", "Insane"};

        addTitle();
        addTopPlayerDropdown();
        addTopPlayerDifficultyDropdown();
        addBottomPlayerDropdown();
        addBottomPlayerDifficultyDropdown();
        addStartingPlayerDropdown();
        addSpacer();
        addStartGameButton();
    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("       Game Configuration");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);
    }

    private void addTopPlayerDropdown() {
        JLabel playerTitle = new JLabel("                     Top Player");
        playerTitle.setFont(new Font("Verdana", Font.BOLD, 18));
        playerTitle.setForeground(Color.WHITE);
        this.add(playerTitle);

        topPlayerComboBox = new JComboBox<Object>(possiblePlayers.keySet().toArray());
        topPlayerComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String selectedTopPlayer = (String) topPlayerComboBox.getSelectedItem();
                    Player player = PlayerFactory.buildPlayer(possiblePlayers.get(selectedTopPlayer), selectedTopPlayer);

                    if (player instanceof AI && (((AI) player).hasVariableDifficulty())) {
                        topDifficultyPanel.setVisible(true);
                    } else {
                        topDifficultyPanel.setVisible(false);
                    }
                }
                controller.refreshDisplay();
            }
        });

        JLabel comboLabel = new JLabel("Type: ");
        comboLabel.setForeground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);
        panel.setOpaque(false);
        panel.add(comboLabel);
        panel.add(topPlayerComboBox);
        this.add(panel);
    }

    private void addBottomPlayerDropdown() {
        JLabel playerTitle = new JLabel("                   Bottom Player");
        playerTitle.setFont(new Font("Verdana", Font.BOLD, 18));
        playerTitle.setForeground(Color.WHITE);
        this.add(playerTitle);

        bottomPlayerComboBox = new JComboBox<Object>(possiblePlayers.keySet().toArray());

        bottomPlayerComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String selectedTopPlayer = (String) bottomPlayerComboBox.getSelectedItem();
                    Player player = PlayerFactory.buildPlayer(possiblePlayers.get(selectedTopPlayer), selectedTopPlayer);

                    if (player instanceof AI && (((AI) player).hasVariableDifficulty())) {
                        bottomDifficultyPanel.setVisible(true);
                    } else {
                        bottomDifficultyPanel.setVisible(false);
                    }
                }
                controller.refreshDisplay();
            }
        });

        JLabel comboLabel = new JLabel("Type: ");
        comboLabel.setForeground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);
        panel.setOpaque(false);
        panel.add(comboLabel);
        panel.add(bottomPlayerComboBox);
        this.add(panel);
    }

    private void addStartingPlayerDropdown() {
        JLabel titleLabel = new JLabel("                 Starting Player");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        this.add(titleLabel);

        JLabel comboLabel = new JLabel("Side: ");
        comboLabel.setForeground(Color.WHITE);

        startingPlayerComboBox = new JComboBox<Object>(new String[]{"Top", "Bottom"});
        JPanel panel = new JPanel();
        panel.setSize(300, 30);
        panel.setBackground(Color.CYAN);
        panel.setOpaque(false);
        panel.add(comboLabel);
        panel.add(startingPlayerComboBox);

        this.add(panel);
    }

    private void addTopPlayerDifficultyDropdown() {
        JLabel label = new JLabel("Difficulty: ");
        label.setForeground(Color.WHITE);

        topDifficultyComboBox = new JComboBox<Object>(possibleDifficulties);

        topDifficultyPanel = new JPanel();
        topDifficultyPanel.setSize(300, 30);
        topDifficultyPanel.setBackground(Color.CYAN);
        topDifficultyPanel.setOpaque(false);
        topDifficultyPanel.add(label);
        topDifficultyPanel.add(topDifficultyComboBox);
        topDifficultyPanel.setVisible(false);

        this.add(topDifficultyPanel);
    }

    private void addBottomPlayerDifficultyDropdown() {
        JLabel label = new JLabel("Difficulty:");
        label.setForeground(Color.WHITE);

        bottomDifficultyComboBox = new JComboBox<Object>(possibleDifficulties);

        bottomDifficultyPanel = new JPanel();
        bottomDifficultyPanel.setSize(300, 30);
        bottomDifficultyPanel.setBackground(Color.GREEN);
        bottomDifficultyPanel.setOpaque(false);
        bottomDifficultyPanel.add(label);
        bottomDifficultyPanel.add(bottomDifficultyComboBox);
        bottomDifficultyPanel.setVisible(false);

        this.add(bottomDifficultyPanel);
    }

    private void addStartGameButton() {
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Build selected top player
                String selectedTopPlayer = (String) topPlayerComboBox.getSelectedItem();
                setTopPlayer(PlayerFactory.buildPlayer(possiblePlayers.get(selectedTopPlayer), selectedTopPlayer));
                if (topDifficulty >= 0 && topPlayer instanceof AI) {
                    ((AI) topPlayer).setDifficulty(topDifficulty);
                }

                // Build selected bottom player
                String selectedBottomPlayer = (String) bottomPlayerComboBox.getSelectedItem();
                setBottomPlayer(PlayerFactory.buildPlayer(possiblePlayers.get(selectedBottomPlayer), selectedBottomPlayer));
                if (topDifficulty >= 0 && bottomPlayer instanceof AI) {
                    ((AI) bottomPlayer).setDifficulty(bottomDifficulty);
                }

                // Find whether starting player is top or bottom
                String selectedStartPosition = (String) startingPlayerComboBox.getSelectedItem();
                setStartingPlayer(selectedStartPosition);

                controller.setGame(buildGame());
                controller.startGame();
            }
        });

        this.add(startGameButton);
    }

    private void addSpacer(){
        JPanel spacerPanel = new JPanel();
        spacerPanel.setOpaque(false);
        this.add(spacerPanel);
    }

    private void setTopPlayer(Player player) {
        this.topPlayer = player;
    }

    private void setBottomPlayer(Player player) {
        this.bottomPlayer = player;
    }

    private void setStartingPlayer(String position) {
        if (position.toLowerCase().equals("top")) {
            this.startingPlayer = topPlayer;
        } else if (position.toLowerCase().equals("bottom")) {
            this.startingPlayer = bottomPlayer;
        }
    }

    public Game buildGame() {
        return new Game(topPlayer, bottomPlayer, startingPlayer);
    }

    public void reset() {
        System.out.println("InGameSettingsPanel | resetting view back to default parameters...");
    }
}
