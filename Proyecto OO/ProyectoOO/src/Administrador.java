import java.util.List;
import java.util.Scanner;

public class Administrador extends Usuario {
    Scanner scanner = new Scanner(System.in);

    public Administrador() {
        super("admin", "Administrador");
    }

    public void crearUsuario(List<Usuario> usuarios, String nombre) {
        boolean userc = true;

        while (userc){
            String rol = leerEntrada(scanner);
            if (rol.equalsIgnoreCase("Gestor")) {
                usuarios.add(new Gestor(nombre));
                userc = false;
            } else if (rol.equalsIgnoreCase("Programador")) {
                usuarios.add(new Programador(nombre));
                userc = false;
            }

            if(!userc){
                System.out.println("Usuario " + nombre + " con rol " + rol + " creado.");
            }else {
                System.out.println("Error: rol de usuario no coincide con alguna descripcion, vuelve a intentarlo");
            }
        }
    }

    public void eliminarUsuario(List<Usuario> usuarios) {

        boolean eliminado=false;
        do {
            String nombre = leerEntrada(scanner);
            eliminado = usuarios.removeIf(u -> u.getNombre().equals(nombre));
            if(eliminado){
                System.out.println("Usuario " + nombre + " eliminado.");
            }else {
                System.out.println("Error: No se encontró tal usuario");
            }
        }while (!eliminado);


    }

    public void listarUsuarios(List<Usuario> usuarios) {
        for (Usuario u : usuarios) {
            System.out.println(u.getNombre() + " - " + u.getRol());
        }
    }

    public String leerEntrada(Scanner scanner) {
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
