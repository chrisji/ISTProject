package checkers;

import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Player p1 = new Player("AI");
        Player p2 = new Player("Chris");
        Game g = new Game(p1, p2);

        while (true) {
            try {
                g.printBoard();

//                System.out.println(g.getValidPlayer1Moves());

                Scanner reader = new Scanner(System.in);
                if (g.getCurrentTurn() == p1) {
                    System.out.println(g.getValidPlayer1Moves());
                } else {
                    System.out.println(g.getValidPlayer2Moves());
                }
                System.out.print(g.getCurrentTurn().getName() + ": ");
                String[] move = reader.nextLine().replace(" ", "").replace(",", "").split("");

                int fromRow = Integer.parseInt(move[0].trim());
                int fromCol = Integer.parseInt(move[1].trim());
                int toRow = Integer.parseInt(move[2].trim());
                int toCol = Integer.parseInt(move[3].trim());

                g.movePiece(g.getCurrentTurn(), new Move(fromRow, fromCol, toRow, toCol));
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

//        g.movePiece(p1, new Move(2, 3, 3, 2));
//        g.movePiece(p2, new Move(5, 0, 4, 1));
//
//        g.movePiece(p1, new Move(2, 1, 2, 3));
//        g.movePiece(p2, new Move(5, 2, 4, 3));
//
//        g.movePiece(p1, new Move(1, 0, 3, 0));
//        g.printBoard();
//        g.movePiece(p2, new Move(4, 3, 2, 1));
//        g.printBoard();
//        g.movePiece(p1, new Move(3, 0, 5, 2));
//        g.printBoard();
//        g.movePiece(p2, new Move(4, 3, 2, 1));
//
//
//        System.out.print(g.getValidPlayer2Moves());
//        System.out.println();
    }
}
