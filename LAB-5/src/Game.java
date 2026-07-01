import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Game {
    private String status;
    private String winnerPlayerName;
    private String playerNameA;
    private String playerNameB;
    private ConnectFour connectFour;
    private Scoreboard scoreboard;
    private int turn = 0;

    // En estricto rigor no son necesarias estas variables, pero simplifican mucho la vista del código
    char playerA_Symbol = 'x';
    char playerB_Symbol = 'o';

    Scanner scanner = new Scanner(System.in);

    public Game(String playerNameA, String playerNameB){

        this.connectFour = new ConnectFour();
        this.scoreboard = new Scoreboard();
        this.playerNameA = playerNameA;
        this.playerNameB = playerNameB;

        status = "IN_PROGRESS";
        winnerPlayerName = null;

        scoreboard.registerPlayer(playerNameA);
        scoreboard.registerPlayer(playerNameB);

    }

    public String play(){

        winnerPlayerName = null;
        int startingPlayer = -1;

        while (startingPlayer != 1 && startingPlayer != 2) {
            System.out.println("Seleccione quién empieza: ");
            System.out.println("1. " + playerNameA + " (" + playerA_Symbol + ")");
            System.out.println("2. " + playerNameB + " (" + playerB_Symbol + ")");

            if (scanner.hasNextInt()) {
                startingPlayer = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Error: Entrada inválida. Debe ser 1 o 2.");
                scanner.nextLine();
            }
        }

        if (startingPlayer == 1) {
            connectFour.setCurrentSymbol(playerA_Symbol);
        } else {
            connectFour.setCurrentSymbol(playerB_Symbol);
        }

        connectFour.printBoard();

        while(winnerPlayerName == null){

            String currentPlayer;

            if(connectFour.getCurrentSymbol() == playerA_Symbol) {
                currentPlayer = playerNameA;
            } else {
                currentPlayer = playerNameB;
            }

            System.out.println("Turno de " + currentPlayer + " (" + connectFour.getCurrentSymbol() + ")");
            System.out.println("En que columna desea jugar su ficha? (1-7)");

            int answer = -1;
            if (scanner.hasNextInt()) {
                answer = scanner.nextInt() - 1;
                scanner.nextLine(); // limpiar el salto de línea
            } else {
                System.out.println("Error: Entrada inválida. Debe ingresar un número.");
                scanner.nextLine(); // descartar la entrada incorrecta
                continue;
            }

            if (!connectFour.makeMove(answer)) {
                System.out.println("Movimiento inválido. Intente con otra columna.");
                continue;
            }

            connectFour.printBoard();

            switch(connectFour.isGameOver()) {

                case 'c':
                    continue;

                case 'd':
                    System.out.println("Empate.");
                    scoreboard.addGameResult(playerNameA, playerNameB, true);
                    status = "DRAW";
                    scoreboard.printScoreboard();
                    connectFour.clearBoard();
                    return "";

                case 'x': // Debido a que el primer símbolo es x, se asigna X a playerNameA
                    winnerPlayerName = playerNameA;
                    System.out.println("El jugador " + winnerPlayerName + " (" + playerA_Symbol + ")" + " gana la partida!");
                    scoreboard.addGameResult(winnerPlayerName, playerNameB, false);
                    status = "VICTORY";
                    scoreboard.printScoreboard();
                    connectFour.clearBoard();
                    return winnerPlayerName;

                case 'o': // Gana playerNameB
                    winnerPlayerName = playerNameB;
                    System.out.println("El jugador " + winnerPlayerName + " (" + playerB_Symbol + ")" + " gana la partida!");
                    scoreboard.addGameResult(winnerPlayerName, playerNameA, false);
                    status = "VICTORY";
                    scoreboard.printScoreboard();
                    connectFour.clearBoard();
                    return winnerPlayerName;
            }
        }

        return null;

    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public ConnectFour getConnectFour() {
        return connectFour;
    }



}

