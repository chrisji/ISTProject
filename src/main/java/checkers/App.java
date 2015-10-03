package checkers;

public class App {
    public static void main(String[] args) throws Exception {
        Player p1 = new Player("AI");
        Player p2 = new Player("Chris");
        Game g = new Game(p1, p2);

        g.movePiece(p1, 2, 3, 3, 2);
        g.movePiece(p1, 2, 1, 2, 3);
        g.movePiece(p1, 1, 0, 3, 0);
        System.out.println();

        g.movePiece(p2, 5, 0, 4, 1);
        g.printBoard();
        System.out.print(g.getValidPlayer2Moves());
        System.out.println();
    }
}
