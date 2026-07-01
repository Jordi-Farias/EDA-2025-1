import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        int[] sizes = new int[]{100, 10000, 1000000};
        String[] attributes = new String[]{"price", "quality", "category"};
        String[] algorithms = new String[]{"mergeSort", "quickSort", "collectionsSort", "selectionSort", "insertionSort", "bubbleSort"};

        try (FileWriter writer = new FileWriter("Resultados_Ordenamiento_Completo.csv")) {
            writer.write("Algoritmo;Atributo;Tamaño;Promedio(ms)\n");
                for(String algorithm : algorithms) {
                    for(String attribute : attributes) {
                        for(int size : sizes) {
                            boolean muyLento = false;
                            long totalTime = 0L;

                            for(int i = 0; i < 3; ++i) {
                                ArrayList<Game> games = GenerateData.generateGames(size);
                                Dataset dataset = new Dataset(games);
                                if ( (algorithm.equals("bubbleSort") && size == 1000000 ) || ( algorithm.equals("quickSort") && attribute.equals("category") && size == 1000000 ))  {
                                    muyLento = true;
                                    break;
                                }

                                if ( ( algorithm.equals("selectionSort") && size == 1000000 ) || ( algorithm.equals("insertionSort") && size == 1000000 ) ) {
                                    muyLento = true;
                                    break;
                                }

                                long start = System.currentTimeMillis();
                                dataset.sortByAlgorithm(algorithm, attribute);
                                long end = System.currentTimeMillis();
                                long duracion = end - start;
                                if (duracion > 30000) {
                                    muyLento = true;
                                    break;
                                }

                                totalTime += duracion;
                            }

                            if (muyLento) {
                                writer.write(algorithm + ";" + attribute + ";" + size + ";>30s\n");
                            } else {
                                long promedio = totalTime / 3L;
                                writer.write(algorithm + ";" + attribute + ";" + size + ";" + promedio + "\n");
                            }

                            System.out.println("Listo: " + algorithm + " | " + attribute + " | " + size);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Archivo 'Resultados_Ordenamiento_Completo.csv' creado.");

            int n = 1000000;
            int targetPrice = 20000;
            int rangeLow = 10000;
            int rangeHigh = 45000;
            String category = "RPG";

            try (FileWriter writer = new FileWriter("Resultados_Busquedas.csv")) {
                writer.write("Método;Tipo;Tiempo(ms)\n");
                ArrayList<Game> games = GenerateData.generateGames(n);
                Dataset ds1 = new Dataset(games);
                long start = System.currentTimeMillis();
                ds1.getGamesByPrice(targetPrice);
                long end = System.currentTimeMillis();
                writer.write("getGamesByPrice;lineal;" + (end - start) + "\n");
                System.out.println("A");
                Dataset ds2 = new Dataset(games);
                ds2.sortByAlgorithm("mergeSort", "price");
                start = System.currentTimeMillis();
                ds2.getGamesByPrice(targetPrice);
                end = System.currentTimeMillis();
                writer.write("getGamesByPrice;binaria;" + (end - start) + "\n");
                System.out.println("B");
                Dataset ds3 = new Dataset(games);
                start = System.currentTimeMillis();
                ds3.getGamesByPriceRange(rangeLow, rangeHigh);
                end = System.currentTimeMillis();
                writer.write("getGamesByPriceRange;lineal;" + (end - start) + "\n");
                System.out.println("C");
                Dataset ds4 = new Dataset(games);
                ds4.sortByAlgorithm("mergeSort", "price");
                start = System.currentTimeMillis();
                ds4.getGamesByPriceRange(rangeLow, rangeHigh);
                end = System.currentTimeMillis();
                writer.write("getGamesByPriceRange;binaria;" + (end - start) + "\n");
                System.out.println("D");
                Dataset ds5 = new Dataset(games);
                start = System.currentTimeMillis();
                ds5.getGamesByCategory(category);
                end = System.currentTimeMillis();
                writer.write("getGamesByCategory;lineal;" + (end - start) + "\n");
                System.out.println("E");
                Dataset ds6 = new Dataset(games);
                ds6.sortByAlgorithm("mergeSort", "category");
                start = System.currentTimeMillis();
                ds6.getGamesByCategory(category);
                end = System.currentTimeMillis();
                writer.write("getGamesByCategory;binaria;" + (end - start) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Archivo 'Resultados_Busquedas.csv' creado.");
    }
}
