import java.util.ArrayList;
import java.util.List;

public class Programador extends Usuario {
    private List<Tarea> tareas = new ArrayList<>();

    public Programador(String nombre) {
        super(nombre, "Programador");
    }

    public void asignarTarea(Tarea tarea) {
        tareas.add(tarea);
    }

    public void consultarProyectos(List<Proyecto> proyectos) {
        for (Proyecto p : proyectos) {
            if (p.tieneProgramador(this)) {
                System.out.println("Proyecto: " + p.getNombre());
            }
        }
    }

    public void consultarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("No tienes tareas asignadas.");
        } else {
            for (Tarea t : tareas) {
                System.out.println("Tarea: " + t.getNombre() + " - " + (t.isFinalizada() ? "Finalizada" : "Pendiente"));
            }
        }
    }

    public boolean marcarTareaComoFinalizada(String nombreTarea) {
        for (Tarea t : tareas) {
            if (t.getNombre().equals(nombreTarea)) {
                t.setFinalizada(true);
                System.out.println("Tarea " + nombreTarea + " marcada como finalizada.");
                return true;
            }
        }
        System.out.println("Tarea no encontrada.");
        return false;
    }
}
