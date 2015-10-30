package checkers;

import checkers.model.Game;
import checkers.model.Move;
import checkers.model.Utils;
import checkers.players.AI;
import checkers.players.Player;
import checkers.players.RandomAI;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        AI ai = new RandomAI("AI");
        AI ai2 = new AI("BI");
        Player human = new Player("Human");

        Game g = new Game(ai, human, human);

        ai.setGame(g);
        ai2.setGame(g);
        human.setGame(g);

        while (!g.hasWinner()) {
            try {
                Utils.printBoard(g.getGameState().getBoard());

                Scanner reader = new Scanner(System.in);
                System.out.println(g.getValidMoves(g.getGameState()));
                System.out.print(g.getGameState().getTurn().getName() + ": ");

                Move m;
//                if (g.getGameState().getTurn() == ai) {
//                    System.out.println();
//                    m = ai.nextMove();
//                } else {
                    String[] move = reader.nextLine().replace(" ", "").replace(",", "").split("");
                    int fromRow = Integer.parseInt(move[0].trim());
                    int fromCol = Integer.parseInt(move[1].trim());
                    int toRow = Integer.parseInt(move[2].trim());
                    int toCol = Integer.parseInt(move[3].trim());
                    m = new Move(fromRow, fromCol, toRow, toCol);
//                }

                g.setGameState(g.movePiece(g.getGameState(), m));
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println(g.getWinner().getName() + " WINS!");
    }
}
