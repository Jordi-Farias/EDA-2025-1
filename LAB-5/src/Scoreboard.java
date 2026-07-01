import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    BST winTree; //< int , string >
    HashST Players; //< string , player >
    int playeGames;

    Scoreboard(){
        this.winTree = new BST();
        this.Players = new HashST();
        this.playeGames = 0;
    }

    public void addGameResult( String winnerPlayer , String loserPlayer , boolean draw ){

        if ( draw ){
            Players.get(winnerPlayer).addDraws();
            Players.get(loserPlayer).addDraws();
        }else{
            Player winner = Players.get(winnerPlayer);
            Player loser = Players.get(loserPlayer);

            int oldWinner = winner.Wins;

            winner.addWins();
            loser.addLosses();

            winTree.delete(oldWinner);
            winTree.insert(winner);

        }

        playeGames++;

    }

    public void registerPlayer(String playerName) {
        if (Players.get(playerName) == null) {
            Player newPlayer = new Player(playerName);
            Players.insert(newPlayer); // insert en HashST
        }
    }

    public boolean checkPlayer(String playerName) {
        return Players.get(playerName) != null;
    }

    public Player[] winRange(int lo, int hi) {
        List<Integer> keys = winTree.keysInRange(lo, hi);
        List<Player> result = new ArrayList<>();

        for (int key : keys) {
            String playerName = winTree.get(key);
            Player p = (Player) Players.get(playerName);
            if (p != null) result.add(p);
        }

        return result.toArray(new Player[0]);
    }

    public Player[] winSuccessor(int wins) {
        Integer succKey = winTree.successor(wins);

        if (succKey == null) return new Player[0];

        String playerName = winTree.get(succKey);
        Player p = (Player) Players.get(playerName);

        if (p != null) return new Player[]{p};
        return new Player[0];
    }

    public void printScoreboard() {
        System.out.println("=== SCOREBOARD ===");
        System.out.printf("%-15s %-6s %-6s %-6s %-8s%n", "Player", "Wins", "Draws", "Losses", "WinRate");

        for (int i = 0; i < 16; i++) { // CAPACITY de HashST
            Node<String, Player> node = Players.table[i]; // acceder directamente a la tabla

            while (node != null) {
                Player p = node.value;
                System.out.printf("%-15s %-6d %-6d %-6d %-8.2f%n",
                        p.Name, p.Wins, p.Draws, p.Losses, p.winRate());
                node = node.right; // siguiente en la lista
            }
        }
    }


    public static void main(String[] args) {
        Scoreboard sb = new Scoreboard();

        sb.registerPlayer("Alice");
        sb.registerPlayer("Bob");

        sb.addGameResult("Alice", "Bob", false); // Alice gana
        sb.addGameResult("Alice", "Bob", true);  // empate

        sb.printScoreboard();

    }
}