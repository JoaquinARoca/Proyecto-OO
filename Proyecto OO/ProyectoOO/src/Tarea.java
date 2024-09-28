public class Tarea {
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
