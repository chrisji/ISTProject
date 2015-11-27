package checkers;

import checkers.graphics.GUI;
import checkers.model.Game;
import checkers.model.Move;
import checkers.model.MoveChain;
import checkers.model.Utils;
import checkers.players.AI;
import checkers.players.Player;
import checkers.players.RandomAI;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        GUI gui = new GUI();

        AI ai = new RandomAI("AI");
        AI ai2 = new AI("BI", 8);
        Player human = new Player("Human");

        Game g = new Game(ai2, human, human);

        ai.setGame(g);
        ai2.setGame(g);
        human.setGame(g);

        while (!g.hasWinner(g.getGameState())) {
            try {
                Utils.printBoard(g.getGameState().getBoard());

                Scanner reader = new Scanner(System.in);
                System.out.println(g.getValidMoves(g.getGameState()));
                System.out.print(g.getGameState().getTurn().getName() + ": ");

                Move m;
                if (g.getGameState().getTurn() == ai2) {
                    System.out.println();
                    MoveChain mc = ai2.nextMoveChain();
                    g.setGameState(g.doMoveChain(g.getGameState(), mc));
                } else {
                    String[] move = reader.nextLine().replace(" ", "").replace(",", "").split("");
                    int fromRow = Integer.parseInt(move[0].trim());
                    int fromCol = Integer.parseInt(move[1].trim());
                    int toRow = Integer.parseInt(move[2].trim());
                    int toCol = Integer.parseInt(move[3].trim());
                    m = new Move(fromRow, fromCol, toRow, toCol);
                    g.setGameState(g.movePiece(g.getGameState(), m));
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println(g.getWinner(g.getGameState()).getName() + " WINS!");
    }
}
