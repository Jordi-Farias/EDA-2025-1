import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimuladorUrgencias {
    Hospital hospital;
    List<Paciente> pacientesGenerados;
    int pacientesPorDia;
    Map<Integer, Integer> maxEsperaPorCategoria = Map.of(1, 0, 2, 30, 3, 90, 4, 240, 5, 10000);
    Map<Integer, List<Long>> tiemposEsperaPorCategoria = new HashMap<>();
    Map<Integer, Integer> pacientesAtendidosPorCategoria = new HashMap<>();
    List<Paciente> pacientesFueraDeTiempo = new ArrayList<>();



    public SimuladorUrgencias ( int pacientesPorDia ) {
        this.pacientesPorDia = pacientesPorDia;
        this.hospital = new Hospital();
        this.pacientesGenerados = GeneradorPacientes.generatePacientes(pacientesPorDia);
        for (int i = 1; i <= 5; i++) {
            tiemposEsperaPorCategoria.put(i, new ArrayList<>());
            pacientesAtendidosPorCategoria.put(i, 0);
        }
    }

    public void simular() {
        int minutosTotales = 24 * 60; // 1440 minutos
        int pacientesLlegados = 0;
        int nuevosAcumulados = 0;

        List<Paciente> pacientesExcedidos = new ArrayList<>();

        for (int minutos = 0; minutos < minutosTotales; minutos++) {

            if( minutos % 10 == 0 && pacientesLlegados < pacientesGenerados.size()) {
                Paciente nuevo = pacientesGenerados.get(pacientesLlegados);
                pacientesLlegados++;
                hospital.registrarPaciente(nuevo);
                nuevosAcumulados++;
            }

            if( minutos % 15 == 0 && pacientesLlegados < pacientesGenerados.size()) {
                Paciente atendido = hospital.atenderSiguiente();
                if(atendido != null) {
                    atendido.tiempoDeAtencion = minutos * 60L;
                }
            }

            if (nuevosAcumulados >= 3) {
                for(int i = 0 ; i < 2 ; i++ ){
                    Paciente atendido = hospital.atenderSiguiente();
                    if(atendido != null) {
                        atendido.tiempoDeAtencion = minutos * 60L;
                    }
                }
                nuevosAcumulados = 0;
            }

            for (Paciente p : new ArrayList<>(hospital.colaAtencion)) {
                long esperaMin =  minutos  - p.tiempoLlegada ;
                int maxPermitido = maxEsperaPorCategoria.get(p.categoria);

                if (esperaMin > maxPermitido) {
                    // Reasignar al paciente para que tenga prioridad
                    hospital.reasignarCategoria(p.id, 1);
                    pacientesFueraDeTiempo.add(p); // solo lo marcas como fuera de tiempo, no como atendido aún
                }
            }
        }
    }

    public Map<String,Paciente> getMapPacientes() {
        return hospital.pacientesTotales;
    }


    public void exportarTodoEnUnArchivoCSV(String nombreArchivo) {
        Map<Integer, Integer> cantidadPorCategoria = new HashMap<>();
        Map<Integer, Long> sumaEsperaPorCategoria = new HashMap<>();
        Map<Integer, Integer> fueraDeTiempoPorCategoria = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            cantidadPorCategoria.put(i, 0);
            sumaEsperaPorCategoria.put(i, 0L);
            fueraDeTiempoPorCategoria.put(i, 0);
        }

        try (FileWriter writer = new FileWriter(nombreArchivo)) {

            // Sección 1: Cantidad y promedio por categoría
            writer.write("Resumen por categoría\n");
            writer.write("Categoria;CantidadAtendidos;PromedioEspera(min);FueraDeTiempo\n");

            for (Paciente p : hospital.pacientesTotales.values()) {
                if (p.tiempoDeAtencion == -1) break;

                long esperaMin = (p.tiempoDeAtencion - p.tiempoLlegada)/60L;
                int cat = p.categoria;

                cantidadPorCategoria.put(cat, cantidadPorCategoria.get(cat) + 1);
                sumaEsperaPorCategoria.put(cat, sumaEsperaPorCategoria.get(cat) + esperaMin);

                if (esperaMin > maxEsperaPorCategoria.get(cat)) {
                    fueraDeTiempoPorCategoria.put(cat, fueraDeTiempoPorCategoria.get(cat) + 1);
                    pacientesFueraDeTiempo.add(p);
                }
            }

            for (int i = 1; i <= 5; i++) {
                int cantidad = cantidadPorCategoria.get(i);
                long sumaEspera = sumaEsperaPorCategoria.get(i);
                int fueraTiempo = fueraDeTiempoPorCategoria.get(i);
                double promedio = cantidad == 0 ? 0 : (double) sumaEspera / cantidad;

                writer.write("C" + i + ";" + cantidad + ";" + String.format("%.2f", promedio) + ";" + fueraTiempo + "\n");
            }

            // Espacio
            writer.write("\n");

            // Sección 2: Pacientes que excedieron el tiempo máximo
            writer.write("Pacientes que excedieron el tiempo máximo de espera\n");
            writer.write("ID;Nombre;Apellido;Categoria;TiempoLlegada;TiempoAtencion;TiempoEsperaMin\n");

            for (Paciente p : pacientesFueraDeTiempo) {
                long esperaMin = (p.tiempoDeAtencion - p.tiempoLlegada)/60L;

                writer.write(p.id + ";" +
                        p.nombre + ";" +
                        p.apellido + ";" +
                        p.categoria + ";" +
                        p.tiempoLlegada + ";" +
                        p.tiempoDeAtencion + ";" +
                        esperaMin + "\n");
            }

            System.out.println("📄 Reporte completo generado: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("❌ Error al guardar archivo: " + e.getMessage());
        }
    }





    public static void main(String[] args) {
        // Crear simulador con 50 pacientes
        SimuladorUrgencias simulador = new SimuladorUrgencias(144);

        // Ejecutar la simulación de 24 horas
        simulador.simular();

        // Exportar archivo completo con datos requeridos
        simulador.exportarTodoEnUnArchivoCSV("ReporteCompleto.csv");

        // Mensaje final
        System.out.println("✅ Simulación finalizada y reporte generado.");
    }


}