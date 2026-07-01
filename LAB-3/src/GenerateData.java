import java.util.ArrayList;
import java.util.Random;

public class GenerateData {
    static String[] categorias = {"Accion", "Aventura", "Estrategia", "RPG", "Deportes", "Simulacion"};
    static String[] palabras = {"Dragon", "Empire", "Quest", "Galaxy", "Legends", "Warrior"};

    public static ArrayList<Game> generateGames(int n) {
        ArrayList<Game> games = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            String name= palabras[random.nextInt(palabras.length)]+palabras[random.nextInt(palabras.length)];
            String category = categorias[random.nextInt(categorias.length)];
            int price= random.nextInt(70001);
            int quality= random.nextInt(101);
            games.add(new Game(name,category,price,quality));
        }
        return games;
    }

}
