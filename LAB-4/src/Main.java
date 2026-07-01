import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.*;

public class Main {

    public static void seguimientoIndividualC4CSV() {
        SimuladorUrgencias sim = new SimuladorUrgencias(100);
        sim.simular();

        try (FileWriter writer = new FileWriter("SeguimientoC4.csv")) {
            writer.write("ID;Nombre;Apellido;Categoria;TiempoLlegada;TiempoAtencion;TiempoEsperaMin\n");

            for (Paciente p : sim.getMapPacientes().values()) {
                if (p.categoria == 4 && p.tiempoDeAtencion != -1) {
                    long esperaMin = (p.tiempoDeAtencion - p.tiempoLlegada) / 60;
                    writer.write(p.id + ";" + p.nombre + ";" + p.apellido + ";" + p.categoria + ";" +
                            p.tiempoLlegada + ";" + p.tiempoDeAtencion + ";" + esperaMin + "\n");
                    break;
                }
            }
            System.out.println(" Seguimiento individual exportado a 'SeguimientoC4.csv'");
        } catch (IOException e) {
            System.out.println(" Error al guardar SeguimientoC4.csv");
        }
    }

    public static void promedioPorCategoriaEn15SimulacionesCSV() {
        Map<Integer, Long> suma = new HashMap<>();
        Map<Integer, Integer> conteo = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            suma.put(i, 0L);
            conteo.put(i, 0);
        }

        for (int i = 0; i < 15; i++) {
            SimuladorUrgencias sim = new SimuladorUrgencias(150);
            sim.simular();

            for (Paciente p : sim.getMapPacientes().values()) {
                if (p.tiempoDeAtencion != -1) {
                    int cat = p.categoria;
                    long espera = (p.tiempoDeAtencion - p.tiempoLlegada) / 60;
                    suma.put(cat, suma.get(cat) + espera);
                    conteo.put(cat, conteo.get(cat) + 1);
                }
            }
        }

        try (FileWriter writer = new FileWriter("Promedio15Simulaciones.csv")) {
            writer.write("Categoria;CantidadAtendidos;PromedioEsperaMin\n");
            for (int i = 1; i <= 5; i++) {
                int total = conteo.get(i);
                double promedio = total == 0 ? 0 : (double) suma.get(i) / total;
                writer.write("C" + i + ";" + total + ";" + String.format("%.2f", promedio) + "\n");
            }
            System.out.println(" Promedios exportados a 'Promedio15Simulaciones.csv'");
        } catch (IOException e) {
            System.out.println(" Error al guardar Promedio15Simulaciones.csv");
        }
    }


    public static void pruebaSaturacionSistemaCSV() {
        SimuladorUrgencias sim = new SimuladorUrgencias(300);
        sim.simular();
        sim.exportarTodoEnUnArchivoCSV("SaturacionSistema.csv");
    }

    public static void pruebaCambioDeCategoriaConHistorialCSV() {
        SimuladorUrgencias sim = new SimuladorUrgencias(100);
        sim.simular();

        try (FileWriter writer = new FileWriter("CambioCategoria.csv")) {
            writer.write("ID;Nombre;Apellido;CategoriaAntes;CategoriaDespues;Historial\n");

            for (Paciente p : sim.getMapPacientes().values()) {
                if (p.categoria == 3 && p.tiempoDeAtencion == -1) {
                    String antes = "3";
                    sim.hospital.reasignarCategoria(p.id, 1);
                    String despues = String.valueOf(p.categoria);
                    String historial = p.obtenerUltimoCambio();
                    writer.write(p.id + ";" + p.nombre + ";" + p.apellido + ";" +
                            antes + ";" + despues + ";" + historial + "\n");
                    break;
                }
            }
            System.out.println("📁 Cambio de categoría exportado a 'CambioCategoria.csv'");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar CambioCategoria.csv");
        }
    }


    public static void main(String[] args) {

        seguimientoIndividualC4CSV();
        promedioPorCategoriaEn15SimulacionesCSV();
        pruebaSaturacionSistemaCSV();
        pruebaCambioDeCategoriaConHistorialCSV();

    }
}