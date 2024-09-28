import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private String nombre;
    private Gestor gestor;
    private List<Programador> programadores = new ArrayList<>();
    private List<Tarea> tareas = new ArrayList<>();

    public Proyecto(String nombre, Gestor gestor) {
        this.nombre = nombre;
        this.gestor = gestor;
    }

    public String getNombre() {
        return nombre;
    }

    public void asignarProgramador(Programador programador) {
        if (!programadores.contains(programador)) {
            programadores.add(programador);
            System.out.println("Programador " + programador.getNombre() + " asignado al proyecto " + nombre);
        } else {
            System.out.println("El programador ya está asignado a este proyecto.");
        }
    }

    public void listarProgramadores() {
        if (programadores.isEmpty()) {
            System.out.println("No hay programadores asignados a este proyecto.");
        } else {
            for (Programador p : programadores) {
                System.out.println("Programador: " + p.getNombre());
            }
        }
    }

    public void crearTarea(Programador programador, String nombreTarea) {
        if (programadores.contains(programador)) {
            Tarea t = new Tarea(nombreTarea, programador);
            tareas.add(t);
            programador.asignarTarea(t);
            System.out.println("Tarea " + nombreTarea + " creada y asignada a " + programador.getNombre());
        } else {
            System.out.println("El programador no está asignado a este proyecto.");
        }
    }

    public boolean tieneProgramador(Programador programador) {
        return programadores.contains(programador);
    }
}

