import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorPacientes {
    static String [] areas = { "SAPU" , "urgencia_adulto" , "infantil" };
    static String estados = "en_espera" ;
    static final String[] NOMBRES = {"Ana", "Luis", "Maria", "Pedro", "Claudia", "Jose"};
    static final String[] APELLIDOS = {"Gonzalez", "Perez", "Soto", "Ramirez", "Munoz", "Rojas"};


    public static List<Paciente> generatePacientes(int n) {
        List<Paciente> pacientes = new ArrayList<>();
        Random random = new Random();
        long timestampInicio = 0L;

        for (int i = 0; i < n; i++) {
            String nombre= NOMBRES[random.nextInt(NOMBRES.length)];
            String apellido= APELLIDOS[random.nextInt(APELLIDOS.length)];
            String area = areas[random.nextInt(areas.length)];
            String id = generarRut();
            int categoria = generarCategoria(random);
            long timeLlegada = timestampInicio + (i * 600L) ;

            pacientes.add(new Paciente(nombre , apellido, id , categoria , timeLlegada , estados , area));
        }

        guardarPacientesEnArchivo(pacientes);

        return pacientes;
    }

    private static int generarCategoria(Random random) {
        double prob = random.nextDouble();
        if (prob < 0.10) return 1;      // 10% C1
        else if (prob < 0.25) return 2; // 15% C2
        else if (prob < 0.43) return 3; // 18% C3
        else if (prob < 0.70) return 4; // 27% C4
        else return 5;                  // 30% C5
    }

    public static String generarRut() {
        Random random = new Random();
        int randomNum = random.nextInt(30000);
        int rut = randomNum;
        int suma = 0;
        int multiplicador = 2;

        while (0 < rut) {
            int digito = rut % 10;
            suma += digito * multiplicador;
            rut /= 10;
            multiplicador = ( multiplicador == 7) ? 2 : multiplicador + 1 ;
        }

        int resto = 11 - ( suma % 11 );

        String dv;

        if (resto == 11) {
            dv = "0";
        }else if (resto == 10) {
            dv = "k";
        }else {
            dv = String.valueOf(resto);
        }
        return randomNum + "-" + dv;
    }

    public static void guardarPacientesEnArchivo(List<Paciente> pacientes) {
        try (FileWriter writer = new FileWriter("Pacientes_24h")) {
            // Cabecera
            writer.write("ID,Nombre,Apellido,Categoria,TiempoLlegada,Estado,Area\n");

            for (Paciente p : pacientes) {
                writer.write(p.id + ";" + p.nombre + ";" + p.apellido + ";" + p.categoria + ";" + p.tiempoLlegada + ";" + p.estado + ";" + p.area + "\n");
            }

            System.out.println(" Archivo guardado como: Pacientes_24h.txt" );
        } catch (IOException e) {
            System.out.println(" Error al guardar el archivo ");
        }
    }

    public static void main(String[] args) {
        List<Paciente> lista = GeneradorPacientes.generatePacientes(3);
        for (Paciente p : lista) {
            System.out.println(" pacienten " + p.id + " : " + p.nombre + " " + p.apellido + " - C" + p.categoria);
        }
    }
}