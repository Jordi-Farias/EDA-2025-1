import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Bienvenido a Conecta 4 ===");

        System.out.print("Ingrese el nombre del Jugador A (x): ");
        String playerA = scanner.nextLine();

        System.out.print("Ingrese el nombre del Jugador B (o): ");
        String playerB = scanner.nextLine();

        Game partida = new Game(playerA, playerB);
        partida.play(); // Ejecuta toda la lógica del juego, registra y muestra resultados

        // Mostrar Scoreboard final
        Scoreboard board = partida.getScoreboard();
        System.out.println("\n=== Score Final ===");
        board.printScoreboard();
    }
}