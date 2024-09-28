import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Proyecto> proyectos = new ArrayList<>();
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear el administrador, gestor y programador por defecto
        Administrador admin = new Administrador();
        Gestor gestorPorDefecto = new Gestor("gestor");
        Programador programadorPorDefecto = new Programador("programador");

        // Agregar los usuarios por defecto
        usuarios.add(admin);  // Administrador predefinido
        usuarios.add(gestorPorDefecto); // Gestor predefinido
        usuarios.add(programadorPorDefecto); // Programador predefinido

        while (true) {
            if (usuarioActual == null) {
                // Si no hay sesión iniciada
                System.out.println("Ingrese nombre de usuario (o 'salir' para cerrar):");
                String nombreUsuario = leerEntrada(scanner);
                if (nombreUsuario.equalsIgnoreCase("salir")) {
                    break;
                }
                usuarioActual = buscarUsuario(nombreUsuario);

                if (usuarioActual != null) {
                    System.out.println("Bienvenido, " + usuarioActual.getNombre() + "!");
                } else {
                    System.out.println("Usuario no encontrado.");
                }
            } else {
                // Mantener el menú activo hasta que el usuario quiera cerrar sesión
                boolean continuar = true;
                while (continuar) {
                    // Mostrar menú según el tipo de usuario en sesión
                    if (usuarioActual instanceof Administrador) {
                        continuar = menuAdministrador((Administrador) usuarioActual);
                    } else if (usuarioActual instanceof Gestor) {
                        continuar = menuGestor((Gestor) usuarioActual);
                    } else if (usuarioActual instanceof Programador) {
                        continuar = menuProgramador((Programador) usuarioActual);
                    }
                }

                // Después de salir del menú, cerrar sesión
                usuarioActual = null;
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

    public static boolean menuAdministrador(Administrador admin) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Crear usuario");
        System.out.println("2. Eliminar usuario");
        System.out.println("3. Listar usuarios");
        System.out.println("0. Cerrar sesión");

        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1:
                System.out.println("Ingrese nombre del usuario:");
                String nombre = leerEntrada(scanner);
                System.out.println("Ingrese rol (Gestor/Programador):");
                admin.crearUsuario(usuarios, nombre);
                break;
            case 2:
                System.out.println("Ingrese nombre del usuario a eliminar:");

                admin.eliminarUsuario(usuarios);
                break;
            case 3:
                admin.listarUsuarios(usuarios);
                break;
            case 0:
                System.out.println("Cerrando sesión...");
                return false;
            default:
                System.out.println("Opción no válida.");
        }
        return true; // Seguir en el menú
    }

    public static boolean menuGestor(Gestor gestor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Crear proyecto");
        System.out.println("2. Listar proyectos");
        System.out.println("3. Listar programadores");
        System.out.println("4. Asignar programador a un proyecto");
        System.out.println("5. Listar programadores de un proyecto");
        System.out.println("6. Crear tarea en un proyecto");
        System.out.println("0. Cerrar sesión");

        int opcion = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer

        switch (opcion) {
            case 1:
                System.out.println("Ingrese nombre del proyecto:");
                String nombreProyecto = leerEntrada(scanner);
                gestor.crearProyecto(nombreProyecto, proyectos);
                break;
            case 2:
                gestor.listarProyectos();
                break;
            case 3:
                gestor.listarProgramadores(usuarios);
                break;
            case 4:
                System.out.println("Ingrese nombre del proyecto:");
                Proyecto proyectoAsignar;
                do {
                    String nombreProyectoAsignar = leerEntrada(scanner);
                    proyectoAsignar = buscarProyecto(nombreProyectoAsignar);
                    if (proyectoAsignar != null) {
                        System.out.println("Ingrese nombre del programador:");
                        Usuario programador;
                        do {
                            String nombreProgramador = leerEntrada(scanner);
                            programador = buscarUsuario(nombreProgramador);
                            if (programador instanceof Programador) {
                                gestor.asignarProgramador(proyectoAsignar, (Programador) programador);
                            } else {
                                System.out.println("Error: programador no encontrado.");
                            }
                        } while (programador == null);
                    } else {
                        System.out.println("Error: Proyecto no encontrado.");
                    }
                }while (proyectoAsignar== null);
                break;
            case 5:
                System.out.println("Ingrese nombre del proyecto:");
                Proyecto proyectoLista;
                do {
                    String nombreProyectoLista = leerEntrada(scanner);
                    proyectoLista = buscarProyecto(nombreProyectoLista);
                    if (proyectoLista != null) {
                        gestor.listarProgramadoresProyecto(proyectoLista);
                    } else {
                        System.out.println("Proyecto no encontrado.");
                    }
                }while (proyectoLista==null);
                break;
            case 6:
                System.out.println("Ingrese nombre del proyecto:");
                Proyecto proyectoTarea;
                do {
                    String nombreProyectoTarea = leerEntrada(scanner);
                    proyectoTarea = buscarProyecto(nombreProyectoTarea);
                    if (proyectoTarea != null) {
                        System.out.println("Ingrese nombre del programador:");
                        Usuario programadorTarea;
                        do {
                            String nombreProgramadorTarea = leerEntrada(scanner);
                            programadorTarea = buscarUsuario(nombreProgramadorTarea);
                            if (programadorTarea instanceof Programador) {
                                System.out.println("Ingrese nombre de la tarea:");
                                String nombreTarea = leerEntrada(scanner);
                                gestor.crearTarea(proyectoTarea, (Programador) programadorTarea, nombreTarea);
                            } else {
                                System.out.println("Error: Programador no encontrado.");
                            }
                        }while (programadorTarea==null);
                    } else {
                        System.out.println("Error: Proyecto no encontrado.");
                    }
                }while (proyectoTarea==null);
                break;
            case 0:
                System.out.println("Cerrando sesión...");
                return false;
            default:
                System.out.println("Opción no válida.");
        }
        return true; // Seguir en el menú
    }

    public static boolean menuProgramador(Programador programador) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Consultar proyectos asignados");
        System.out.println("2. Consultar tareas asignadas");
        System.out.println("3. Marcar tarea como finalizada");
        System.out.println("0. Cerrar sesión");

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
                boolean encontrado = false;
                do {
                    String nombreTarea = leerEntrada(scanner);
                    encontrado = programador.marcarTareaComoFinalizada(nombreTarea);
                }while (encontrado==false);
                break;
            case 0:
                System.out.println("Cerrando sesión...");
                return false;
            default:
                System.out.println("Opción no válida.");
        }
        return true; // Seguir en el menú
    }

    // Método para validar la entrada del usuario, asegurarse de que no esté vacía
    public static String leerEntrada(Scanner scanner) {
        String entrada;
        do {
            entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("Error: la entrada no puede estar vacía. Inténtelo de nuevo.");
            }
        } while (entrada.isEmpty());
        return entrada;
    }
}
