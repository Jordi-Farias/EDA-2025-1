import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Paciente implements Comparable<Paciente> {
    String nombre;
    String apellido;
    String id;  //rut o pasaporte
    int categoria; // 1 a 5 1: mas urgente 5: menos urgente
    long tiempoLlegada;
    String estado;
    String area;
    long tiempoDeAtencion;
    Stack<String> historial;

    Paciente(String nombre, String apellido, String id, int categoria, long timeLlegada, String estado, String area) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.id = id;
        this.categoria = categoria;
        this.tiempoLlegada = timeLlegada;
        this.estado = estado;
        this.area = area;
        this.tiempoDeAtencion = -1;
        this.historial = new Stack<>();
    }

    public long tiempoEsperaActual(long tiempo) {
        return tiempo - tiempoLlegada;
    }

    public void registrarHistorial(String descripcion){
        historial.push(descripcion);
    }

    public String obtenerUltimoCambio(){
        return historial.pop();
    }

    @Override
    public int compareTo(Paciente otro) {
        if (this.categoria != otro.categoria) {
            return Integer.compare(this.categoria, otro.categoria);
        } else {
            return Long.compare(this.tiempoLlegada, otro.tiempoLlegada);
        }
    }

    public void imprimir() {
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Id: " + id);
        System.out.println("Categoria: " + categoria);
        System.out.println("Tiempo de Llegada: " + tiempoLlegada);
        System.out.println("Estado: " + estado);
        System.out.println("Area: " + area);
        List<String> lista = new ArrayList<>(historial);
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("Historial " + (i + 1) + ": " + lista.get(i));
        }
    }

    public static void main(String[] args) {
        Paciente p = new Paciente("Ana", "Pérez", "11111111-1", 2, 1000, "en_espera", "SAPU");
        p.registrarHistorial("Creado en SAPU");
        p.estado= "en_atencion";

        p.registrarHistorial("Cambio a en_atencion");

        p.imprimir();
    }

}