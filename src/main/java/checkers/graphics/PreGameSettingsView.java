package checkers.graphics;

import checkers.model.Game;
import checkers.model.PlayerFactory;
import checkers.players.AI;
import checkers.players.AIAlphaBeta;
import checkers.players.AIMiniMax;
import checkers.players.FirstMoveAI;
import checkers.players.Player;
import checkers.players.RandomAI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chris Inskip
 * @version 28/11/2015
 */
public class PreGameSettingsView extends JPanel {

    private final Controller controller;

    private Player topPlayer;
    private Player bottomPlayer;
    private Player startingPlayer;

    JComboBox<Object> topPlayerComboBox;
    JComboBox<Object> bottomPlayerComboBox;
    JComboBox<Object> startingPlayerComboBox;

    Map<String, Integer> possiblePlayers;

    public PreGameSettingsView(Controller controller) {
        this.controller = controller;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setPreferredSize(new Dimension(350, 600));
        this.setOpaque(false);

        topPlayer = PlayerFactory.buildPlayer(PlayerFactory.HUMAN, "A");
        bottomPlayer = PlayerFactory.buildPlayer(PlayerFactory.HUMAN, "B");
        startingPlayer = bottomPlayer;

        possiblePlayers = new HashMap<String, Integer>();
        possiblePlayers.put("Human", PlayerFactory.HUMAN);
        possiblePlayers.put("Alpha-Beta AI", PlayerFactory.ALPHA_BETA_AI);
        possiblePlayers.put("Minimax AI", PlayerFactory.MINIMAX_AI);
        possiblePlayers.put("First Valid Move AI", PlayerFactory.FIRST_MOVE_AI);
        possiblePlayers.put("Random Valid Move AI", PlayerFactory.RANDOM_MOVE_AI);

        addTopPlayerDropdown();
        addBottomPlayerDropdown();
        addStartingPlayerDropdown();
        addStartGameButton();
    }

    private void addTopPlayerDropdown() {
        JLabel label = new JLabel("Top Player");

        topPlayerComboBox = new JComboBox<Object>(possiblePlayers.keySet().toArray());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(label);
        panel.add(topPlayerComboBox);

        this.add(panel);
    }

    private void addBottomPlayerDropdown() {
        JLabel label = new JLabel("Bottom Player: ");

        bottomPlayerComboBox = new JComboBox<Object>(possiblePlayers.keySet().toArray());

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(label);
        panel.add(bottomPlayerComboBox);

        this.add(panel);
    }

    private void addStartingPlayerDropdown() {
        JLabel label = new JLabel("Starting Player:");

        startingPlayerComboBox = new JComboBox<Object>(new String[]{"Top", "Bottom"});
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(label);
        panel.add(startingPlayerComboBox);

        this.add(panel);
    }

    private void addStartGameButton() {
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Build selected top player
                String selectedTopPlayer = (String) topPlayerComboBox.getSelectedItem();
                setTopPlayer(PlayerFactory.buildPlayer(possiblePlayers.get(selectedTopPlayer), selectedTopPlayer));

                // Build selected bottom player
                String selectedBottomPlayer = (String) bottomPlayerComboBox.getSelectedItem();
                setBottomPlayer(PlayerFactory.buildPlayer(possiblePlayers.get(selectedBottomPlayer), selectedBottomPlayer));

                // Find whether starting player is top or bottom
                String selectedStartPosition = (String) startingPlayerComboBox.getSelectedItem();
                setStartingPlayer(selectedStartPosition);

                controller.setGame(buildGame());
                controller.startGame();
            }
        });

        this.add(startGameButton);
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
