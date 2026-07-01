 import java.util.*;

public class Hospital {

    Map<String,Paciente> pacientesTotales;
    PriorityQueue<Paciente> colaAtencion;
    Map<String,AreaAtencion> areasAtencion;
    List<Paciente> pacientesAtendidos;

    Hospital() {
        pacientesTotales = new HashMap<>();
        colaAtencion = new PriorityQueue<>();
        pacientesAtendidos = new ArrayList<>();
        areasAtencion = new HashMap<>();
        areasAtencion.put("SAPU", new AreaAtencion("SAPU", 20));
        areasAtencion.put("urgencia_adulto", new AreaAtencion("urgencia_adulto", 20));
        areasAtencion.put("infantil", new AreaAtencion("infantil", 20));
    }

    public void registrarPaciente(Paciente p) {
        pacientesTotales.put(p.id, p);
        colaAtencion.add(p);

        System.out.println(" Paciente registrado: " + p.nombre );
    }

    public void reasignarCategoria(String id, int categoria) {
        Paciente paciente = pacientesTotales.get(id);
        if (paciente == null) return;

        // Eliminar de estructuras anteriores
        colaAtencion.remove(paciente);
        AreaAtencion area = areasAtencion.get(paciente.area);
        if (area != null) {
            area.removerPaciente(paciente);
        }

        // Cambiar la categoría
        int categoriaAnterior = paciente.categoria;
        paciente.categoria = categoria;
        paciente.registrarHistorial("Reasignación de categoría: de C" + categoriaAnterior + " a C" + categoria);

        if (categoria != 1) {
            colaAtencion.add(paciente); // vuelve a la cola si no es C1
        } else {
            // Para C1, lo movemos al área si hay espacio (NO se atiende directo)
            AreaAtencion areaNueva = areasAtencion.get(paciente.area);
            if (areaNueva == null) return;

            if (!areaNueva.estaSaturado()) {
                areaNueva.addPaciente(paciente);
                paciente.registrarHistorial("Reasignado a C1 y movido al área " + areaNueva.area);
            } else {
                paciente.registrarHistorial("Reasignado a C1 pero área saturada");
            }
        }
    }

    public Paciente atenderSiguiente() {

        if (colaAtencion.isEmpty()) {
            System.out.println("No hay pacientes en la cola de atención.");
            return null;
        }

        Paciente siguiente = colaAtencion.poll();

        AreaAtencion area = areasAtencion.get(siguiente.area);

        if (area == null) {
            System.out.println("Área de atención no válida para el paciente: " + siguiente.area);
            return null;
        }

        if (area.estaSaturado()) {
            System.out.println("Área saturada: " + siguiente.area + ". No se puede atender por ahora.");

            return null;
        }

        area.addPaciente(siguiente);

        Paciente atendido = area.atenderPaciente();

        atendido.estado = "atendido";

        atendido.registrarHistorial("Paciente en atencion en: " + area.area );

        pacientesAtendidos.add(atendido);

        return atendido;
    }

    public List<Paciente> obtenerPacientesPorCategoria(int categoria) {
        List<Paciente> pacientes = new ArrayList<>();
        for(Paciente paciente : colaAtencion) {
            if(paciente.categoria == categoria) {
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }

    public AreaAtencion obtenerArea(String nombre) {
        return areasAtencion.get(nombre);
    }

    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        List<Paciente> pacientes = GeneradorPacientes.generatePacientes(5);

        for (Paciente p : pacientes) {
            hospital.registrarPaciente(p);
        }

        Paciente atendido = hospital.atenderSiguiente();
        if (atendido != null) {
            System.out.println(" Atendido desde hospital: " + atendido.nombre + " en " + atendido.area);
        }

        List<Paciente> c3 = hospital.obtenerPacientesPorCategoria(3);
        System.out.println(" Pacientes en categoría C3: " + c3.size());
    }

}

