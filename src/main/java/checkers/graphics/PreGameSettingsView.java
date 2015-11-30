package checkers.graphics;

import checkers.model.Game;
import checkers.players.AI;
import checkers.players.AIAlphaBeta;
import checkers.players.AIMiniMax;
import checkers.players.Player;
import checkers.players.RandomAI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Chris Inskip
 * @version 28/11/2015
 */
public class PreGameSettingsView extends JPanel {

    private final Controller controller;

    private Player topPlayer;
    private Player bottomPlayer;
    private Player startingPlayer;

    public PreGameSettingsView(Controller controller) {
        this.controller = controller;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
//        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 15, 15));
        this.setPreferredSize(new Dimension(400,600));

        // TODO: remove debug...
        AI firstAI = new AI("AI");
        AI minimax = new AIMiniMax("MM", 5);
        AI alphaBeta = new AIAlphaBeta("AB", 5);
        AI randAI = new RandomAI("Rand_AI");
        Player human = new Player("HUMAN");
        Player human2 = new Player("HUMAN");

        setTopPlayer(alphaBeta);
        setBottomPlayer(human);
        setStartingPlayer(human);

        addStartGameButton();
    }

    private void addStartGameButton() {
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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

    private void setStartingPlayer(Player player) {
        this.startingPlayer = player;
    }

    public Game buildGame() {
        return new Game(topPlayer, bottomPlayer, startingPlayer);
    }
}
