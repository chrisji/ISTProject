package checkers;

import checkers.players.AI;
import checkers.players.Player;
import checkers.players.RandomAI;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        AI p1 = new RandomAI("AI");
        Player p2 = new Player("Human");

        Game g = new Game(p1, p2, p2);

        while (true) {
            try {
                g.printBoard();

                Scanner reader = new Scanner(System.in);
                System.out.println(g.getValidMoves(g.getCurrentTurn()));
                System.out.print(g.getCurrentTurn().getName() + ": ");

                Move m;
                if (g.getCurrentTurn() == p1) {
                    System.out.println();
                    m = p1.nextMove(g);
                } else {
                    String[] move = reader.nextLine().replace(" ", "").replace(",", "").split("");
                    int fromRow = Integer.parseInt(move[0].trim());
                    int fromCol = Integer.parseInt(move[1].trim());
                    int toRow = Integer.parseInt(move[2].trim());
                    int toCol = Integer.parseInt(move[3].trim());
                    m = new Move(fromRow, fromCol, toRow, toCol);
                }

                g.movePiece(g.getCurrentTurn(), m);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
