package checkers;

public class App {
    public static void main(String[] args) throws Exception {
        Player p1 = new Player("AI");
        Player p2 = new Player("Chris");
        Game g = new Game(p1, p2);

        g.movePiece(p1, new Move(2, 3, 3, 2));
        g.movePiece(p2, new Move(5, 0, 4, 1));

        g.movePiece(p1, new Move(2, 1, 2, 3));
        g.movePiece(p2, new Move(5, 2, 4, 3));

        g.movePiece(p1, new Move(1, 0, 3, 0));
        g.printBoard();
        g.movePiece(p2, new Move(4, 3, 2, 1));
        g.printBoard();
        g.movePiece(p1, new Move(3, 0, 5, 2));
        g.printBoard();
        g.movePiece(p2, new Move(4, 3, 2, 1));


        System.out.print(g.getValidPlayer2Moves());
        System.out.println();
    }
}
