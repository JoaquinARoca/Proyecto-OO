import java.util.ArrayList;
import java.util.List;

public class Gestor extends Usuario {
    private List<Proyecto> proyectos = new ArrayList<>();

    public Gestor(String nombre) {
        super(nombre, "Gestor");
    }

    public void crearProyecto(String nombreProyecto, List<Proyecto> proyectosGlobales) {
        Proyecto p = new Proyecto(nombreProyecto, this);
        proyectos.add(p);
        proyectosGlobales.add(p);
        System.out.println("Proyecto " + nombreProyecto + " creado.");
    }

    public void listarProyectos() {
        if (proyectos.isEmpty()) {
            System.out.println("No tienes proyectos.");
        } else {
            for (Proyecto p : proyectos) {
                System.out.println("Proyecto: " + p.getNombre());
            }
        }
    }

    public void listarProgramadores(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            if (u.getRol().equals("Programador")) {
                System.out.println(u.getNombre());
            }
        }
    }

    public void asignarProgramador(Proyecto proyecto, Programador programador) {
        proyecto.asignarProgramador(programador);
    }

    public void listarProgramadoresProyecto(Proyecto proyecto) {
        proyecto.listarProgramadores();
    }

    public void crearTarea(Proyecto proyecto, Programador programador, String nombreTarea) {
        proyecto.crearTarea(programador, nombreTarea);
    }
}
