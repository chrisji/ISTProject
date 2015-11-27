package checkers.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Chris Inskip
 * @version 26/11/2015
 */
public class GUI extends JFrame {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = (WIDTH / 16) * 9;

    private CardLayout cardLayout; // Allows JPanels to be switched in and out
    private JPanel screens; // Holds all of the different screens
    private HashMap<String, Screen> screenMap; // Holds mapping from screen names to screens

    // Initialise screens
    JPanel homePanel = new HomePanel();
    JPanel matchPanel = new MatchPanel();

    // Initialise screen names
    private static String HOME_PANEL_NAME = "HOME";
    private static String MATCH_PANEL_NAME = "MATCH";

    // Stack of previous screens
    Stack<Panel> panelHistory;

    public GUI() {
        this.cardLayout = new CardLayout();
        this.screens = new JPanel(cardLayout);

        // Add screens to layout
        this.addScreen(homePanel, HOME_PANEL_NAME);
        this.addScreen(matchPanel, MATCH_PANEL_NAME);

        // JFrame properties
        this.add(screens);
        this.setTitle("IST: Checkers Game  |  " + WIDTH + "x" + HEIGHT);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Adds the screen to the game so that it can be swapped to when needed.
     * Adds a mapping between a screen and its name
     * @param screen
     * @param screenName
     */
    private void addScreen(JPanel screen, String screenName) {
        screenMap.put(screenName, (Screen) screen);
        screens.add(screen, screenName);
    }

    /**
     * Switch to display the specified screen.
     * @param screenName screen to switch in.
     */
    public void switchScreen(String screenName) {
        screenMap.get(screenName).update();
        cardLayout.show(screens, screenName);
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
    }
}
