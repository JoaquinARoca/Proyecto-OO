import java.util.*;

abstract class Usuario {
    protected String nombre;
    protected String rol;

    public Usuario(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRol() {
        return rol;
    }
}

class Administrador extends Usuario {
    public Administrador() {
        super("admin", "Administrador");
    }

    public void crearUsuario(List<Usuario> usuarios, String nombre, String rol) {
        if (rol.equalsIgnoreCase("Gestor")) {
            usuarios.add(new Gestor(nombre));
        } else if (rol.equalsIgnoreCase("Programador")) {
            usuarios.add(new Programador(nombre));
        }
        System.out.println("Usuario " + nombre + " con rol " + rol + " creado.");
    }

    public void eliminarUsuario(List<Usuario> usuarios, String nombre) {
        usuarios.removeIf(u -> u.getNombre().equals(nombre));
        System.out.println("Usuario " + nombre + " eliminado.");
    }

    public void listarUsuarios(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            System.out.println(u.getNombre() + " - " + u.getRol());
        }
    }
}

class Gestor extends Usuario {
    private List<Proyecto> proyectos = new ArrayList<>();

    public Gestor(String nombre) {
        super(nombre, "Gestor");
    }

    public void crearProyecto(String nombreProyecto, List<Proyecto> proyectosGlobales) {
        Proyecto p = new Proyecto(nombreProyecto, this);
        proyectos.add(p); // Agregar a la lista de proyectos del gestor
        proyectosGlobales.add(p); // Agregar a la lista global de proyectos
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

class Programador extends Usuario {
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

    public void marcarTareaComoFinalizada(String nombreTarea) {
        for (Tarea t : tareas) {
            if (t.getNombre().equals(nombreTarea)) {
                t.setFinalizada(true);
                System.out.println("Tarea " + nombreTarea + " marcada como finalizada.");
                return;
            }
        }
        System.out.println("Tarea no encontrada.");
    }
}

class Proyecto {
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

class Tarea {
    private String nombre;
    private Programador programador;
    private boolean finalizada;

    public Tarea(String nombre, Programador programador) {
        this.nombre = nombre;
        this.programador = programador;
        this.finalizada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
}

public class Main {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Proyecto> proyectos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Administrador admin = new Administrador();
        usuarios.add(admin);  // Agregar al administrador predefinido

        while (true) {
            System.out.println("Ingrese nombre de usuario:");
            String nombreUsuario = scanner.nextLine();
            Usuario usuario = buscarUsuario(nombreUsuario);

            if (usuario != null) {
                if (usuario instanceof Administrador) {
                    menuAdministrador((Administrador) usuario);
                } else if (usuario instanceof Gestor) {
                    menuGestor((Gestor) usuario);
                } else if (usuario instanceof Programador) {
                    menuProgramador((Programador) usuario);
                }
            } else {
                System.out.println("Usuario no encontrado.");
            }
        }
    }

    public static Usuario buscarUsuario(String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                return u;
            }
        }
        return null;
    }

    public static Proyecto buscarProyecto(String nombre) {
        for (Proyecto p : proyectos) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null;
    }

    public static void menuAdministrador(Administrador admin) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Crear usuario");
        System.out.println("2. Eliminar usuario");
        System.out.println("3. Listar usuarios");
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1:
                System.out.println("Ingrese nombre del usuario:");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese rol (Gestor/Programador):");
                String rol = scanner.nextLine();
                admin.crearUsuario(usuarios, nombre, rol);
                break;
            case 2:
                System.out.println("Ingrese nombre del usuario a eliminar:");
                String nombreEliminar = scanner.nextLine();
                admin.eliminarUsuario(usuarios, nombreEliminar);
                break;
            case 3:
                admin.listarUsuarios(usuarios);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void menuGestor(Gestor gestor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Crear proyecto");
        System.out.println("2. Listar proyectos");
        System.out.println("3. Listar programadores");
        System.out.println("4. Asignar programador a proyecto");
        System.out.println("5. Crear tarea");
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1:
                System.out.println("Ingrese nombre del proyecto:");
                String nombreProyecto = scanner.nextLine();
                gestor.crearProyecto(nombreProyecto, proyectos);  // Pasar la lista global de proyectos
                break;
            case 2:
                gestor.listarProyectos();
                break;
            case 3:
                gestor.listarProgramadores(usuarios);
                break;
            case 4:
                System.out.println("Ingrese nombre del proyecto:");
                String nombreProy = scanner.nextLine();
                Proyecto proyecto = buscarProyecto(nombreProy);
                if (proyecto != null) {
                    System.out.println("Ingrese nombre del programador:");
                    String nombreProg = scanner.nextLine();
                    Programador programador = (Programador) buscarUsuario(nombreProg);
                    if (programador != null) {
                        gestor.asignarProgramador(proyecto, programador);
                    } else {
                        System.out.println("Programador no encontrado.");
                    }
                } else {
                    System.out.println("Proyecto no encontrado.");
                }
                break;
            case 5:
                System.out.println("Ingrese nombre del proyecto:");
                String nombreProyTarea = scanner.nextLine();
                Proyecto proyectoTarea = buscarProyecto(nombreProyTarea);
                if (proyectoTarea != null) {
                    System.out.println("Ingrese nombre del programador:");
                    String nombreProgTarea = scanner.nextLine();
                    Programador programadorTarea = (Programador) buscarUsuario(nombreProgTarea);
                    if (programadorTarea != null) {
                        System.out.println("Ingrese nombre de la tarea:");
                        String nombreTarea = scanner.nextLine();
                        gestor.crearTarea(proyectoTarea, programadorTarea, nombreTarea);
                    } else {
                        System.out.println("Programador no encontrado.");
                    }
                } else {
                    System.out.println("Proyecto no encontrado.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    public static void menuProgramador(Programador programador) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Consultar proyectos asignados");
        System.out.println("2. Consultar tareas");
        System.out.println("3. Marcar tarea como finalizada");
        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1:
                programador.consultarProyectos(proyectos);
                break;
            case 2:
                programador.consultarTareas();
                break;
            case 3:
                System.out.println("Ingrese nombre de la tarea:");
                String nombreTarea = scanner.nextLine();
                programador.marcarTareaComoFinalizada(nombreTarea);
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }
}
