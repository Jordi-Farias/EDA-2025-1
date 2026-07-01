import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AreaAtencion {
    String area;
    PriorityQueue<Paciente> pacientesHead;
    int maxPacientes;

    AreaAtencion(String area, int maxPacientes) {
        this.area = area;
        this.maxPacientes = maxPacientes;
        pacientesHead = new PriorityQueue<>();
    }

    public boolean addPaciente(Paciente paciente) {
        if (estaSaturado()) {
            System.out.println(" Área " + area + " está saturada.");
            return false;
        }
        return pacientesHead.add(paciente);
    }

    public boolean estaSaturado() {
        if (pacientesHead.size()==maxPacientes) {
            return true;
        }else{
            return false;
        }
    }

    public Paciente atenderPaciente() {
        Paciente paciente = pacientesHead.poll();
        return paciente;
    }

    public List<Paciente> obtenerPacientesPorHeapSort() {

        PriorityQueue<Paciente> copiaHeap = new PriorityQueue<>(pacientesHead);

        List<Paciente> ordenados = new ArrayList<>();
        while (!copiaHeap.isEmpty()) {
            ordenados.add(copiaHeap.poll());
        }
        return ordenados;
    }

    public void removerPaciente(Paciente p) {
        pacientesHead.remove(p);
    }

    public static void main(String[] args) {
        AreaAtencion area = new AreaAtencion("SAPU", 2);
        Paciente p1 = new Paciente("Luis", "Gómez", "22222222-2", 1, 1000, "en_espera", "SAPU");
        Paciente p2 = new Paciente("Carla", "Soto", "33333333-3", 3, 1100, "en_espera", "SAPU");

        area.addPaciente(p1);
        area.addPaciente(p2);

        System.out.println("🧾 Heap ordenado:");
        for (Paciente p : area.obtenerPacientesPorHeapSort()) {
            System.out.println(" - " + p.nombre + " (C" + p.categoria + ")");
        }

        Paciente atendido = area.atenderPaciente();
        System.out.println(" Atendido: " + atendido.nombre);
    }
}
